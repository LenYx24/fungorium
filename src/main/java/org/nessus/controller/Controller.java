package org.nessus.controller;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.nessus.controller.command.*;
import org.nessus.controller.command.arrangecmd.*;
import org.nessus.controller.command.assertcmd.ShowCmd;
import org.nessus.model.bug.*;
import org.nessus.model.effect.*;
import org.nessus.model.shroom.*;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;

/**
 * Ez az osztály implementálja a Controllert, amely a játék logikájáért felelős.
 * A Controller osztály kezeli a parancsokat, és irányítja a játék állapotát.
 * A Controller osztályban található parancsok a játék különböző fázisait kezelik, mint például az elrendezést, a cselekvést és az állításokat.
 */
public class Controller implements IRandomProvider {
    private static Random rand = new Random();

    /**
     * A parancsok módjait definiáló enum.
     * Az ARRANGE mód a játék előkészítéséért felelős, ahol a játékosok elhelyezhetik a bogaraikat és gombáikat.
     * Az ACT mód a játék aktív fázisát jelenti, ahol a játékosok cselekedhetnek a bogaraikkal és gombáikkal.
     * Az ASSERT mód a játék állapotának ellenőrzésére szolgál, ahol a játékosok megvizsgálhatják a játék állapotát és eredményeit.
     * Az enum értékei: ARRANGE, ACT, ASSERT.
     */
    private enum CmdMode {
        ARRANGE,
        ACT,
        ASSERT
    }

    private IBugOwnerController currentBugOwner = null; // a bug owner
    private IShroomController currentShroom = null; // a shroom

    private boolean bugOwnerRound = false; // Bug owner köre
    private List<IBugOwnerController> bugOwners = new ArrayList<>(); // Bug owner lista
    private List<IShroomController> shrooms = new ArrayList<>(); // Shroom lista
    
    private CmdMode mode = CmdMode.ARRANGE; // A Controller CmdMode-ja // See: CmdMode enum
    private HashMap<String, BaseCommand> normalCmds = new HashMap<>(); // A Controller "Normál" parancsai

    private HashMap<String, BaseCommand> arrangeCmds = new HashMap<>(); // A Controller "Elrendezés" parancsai
    private HashMap<String, BaseCommand> actCmds = new HashMap<>(); // A Controller "Cselekvés" parancsai
    private HashMap<String, BaseCommand> assertCmds = new HashMap<>(); // A Controller "Állítás" parancsai

    Path runpath = null; // A fájlok elérési útja, ahol a tesztfájlok találhatóak

    /**
     * A Controller osztály konstruktora, amely inicializálja a parancsokat és beállítja a nézetet.
     * @param view A nézet, amelyet a Controller használ.
     */
    public Controller(View view) {
        normalCmds.put("hi", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                System.out.println("hi");
            }
        });

        normalCmds.put("arrange", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                mode = CmdMode.ARRANGE;
            }
        });

        normalCmds.put("assert", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                mode = CmdMode.ASSERT;
            }
        });

        normalCmds.put("act", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                mode = CmdMode.ACT;
            }
        });

        normalCmds.put("resetobjects", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                view.ResetObjects();
            }
        });

        normalCmds.put("exit", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                view.Stop();
            }
        });

        normalCmds.put("help",new HelpCmd());
        // ARRANGE
        arrangeCmds.put("create",new CreateCmd());

        arrangeCmds.put("setval",new SetValCmd());
        arrangeCmds.put("setref",new SetRefCmd());

        arrangeCmds.put("seed", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                ConvertToInteger(args[0]).ifPresent(x -> SetSeed(x));
            }
        });

        arrangeCmds.put("neighbour", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;
                
                ITectonController t1 = (ITectonController)view.GetObject(args[0]);
                ITectonController t2 = (ITectonController)view.GetObject(args[1]);

                t1.SetNeighbour((Tecton)t2);
                t2.SetNeighbour((Tecton)t1);
            }
        });
        
        arrangeCmds.put("addeffect", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if (NotEnoughArgs(args, 2))
                    return;

                Bug bug = (Bug)view.GetObject(args[0]);
                BugEffect effect = (BugEffect)view.GetObject(args[1]);

                bug.AddEffect(effect);
            }
            
        });

        arrangeCmds.put("place", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;
                
                Object obj = view.GetObject(args[0]);
                ITectonController tecton = (ITectonController)view.GetObject(args[1]);

                if(obj instanceof Bug bug) {
                    tecton.AddBug(bug);
                    bug.SetTecton((Tecton)tecton);
                }
                else if(obj instanceof ShroomBody body) {
                    tecton.SetShroomBody(body);
                    body.SetTecton((Tecton)tecton);
                }
                else if(obj instanceof Spore spore) {
                    tecton.ThrowSpore(spore);
                    spore.SetTecton((Tecton)tecton);
                }
            }
        });
        arrangeCmds.put("placethread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,3))
                    return;

                ShroomThread thread = (ShroomThread)view.GetObject(args[0]);

                ITectonController tecton1 = (ITectonController)view.GetObject(args[1]);
                ITectonController tecton2 = (ITectonController)view.GetObject(args[2]);

                tecton1.GrowShroomThread(thread);
                tecton2.GrowShroomThread(thread);

                thread.SetTecton1((Tecton)tecton1);
                thread.SetTecton2((Tecton)tecton2);
            }
        });

        arrangeCmds.put("run", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;

                var path = Paths.get("src", "main", "resources", "test", args[0], "input.txt");
                runpath = Paths.get("src", "main", "resources", "test", args[0]);
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println(GetPrompt() + line);
                        ProcessCommand(line);
                    }
                } catch (IOException e) {
                    System.out.println("Hiba a fájl olvasása közben: " + e.getMessage());
                }
            }
        });

        arrangeCmds.put("runall", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                var runcmd = arrangeCmds.get("run");
                final int nTest = 34;
                for (int i = 1; i <= nTest; i++)
                    runcmd.Run(new String[] { "test" + i });
            }
        });

        arrangeCmds.put("currentplayer", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))return;
                String name = args[0];
                Object obj = view.GetObject(name);

                if(obj instanceof BugOwner bugOwner) {
                    currentBugOwner = bugOwner;
                    bugOwnerRound = true;
                }

                if(obj instanceof Shroom shroom) {
                    currentShroom = shroom;
                    bugOwnerRound = false;
                }
            }
        });
        // ACT
        actCmds.put("placeshroomthread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Tecton tecton1 = (Tecton)view.GetObject(args[0]);
                Tecton tecton2 = (Tecton)view.GetObject(args[1]);

                currentShroom.PlaceShroomThread(tecton1, tecton2);
            }
        });

        actCmds.put("placeshroombody", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;

                Tecton tecton = (Tecton)view.GetObject(args[0]);
                currentShroom.PlaceShroomBody(tecton);
            }
        });

        actCmds.put("upgradeshroombody", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;

                ShroomBody body = (ShroomBody)view.GetObject(args[0]);
                currentShroom.UpgradeShroomBody(body);
            }
        });

        actCmds.put("throwspore", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                ShroomBody body = (ShroomBody)view.GetObject(args[0]);
                Tecton tecton = (Tecton)view.GetObject(args[1]);
                currentShroom.ThrowSpore(body, tecton);
            }
        });
        
        actCmds.put("devour", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                ShroomThread thread = (ShroomThread)view.GetObject(args[0]);
                Bug bug = (Bug)view.GetObject(args[1]);
                currentShroom.ShroomThreadDevourBug(thread, bug);
            }
        });
        
        actCmds.put("move", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Bug bug = (Bug)view.GetObject(args[0]);
                Tecton tecton = (Tecton)view.GetObject(args[1]);
                currentBugOwner.Move(bug, tecton);
            }
        });
        
        actCmds.put("eatspore", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Bug bug = (Bug)view.GetObject(args[0]);
                Spore spore = (Spore)view.GetObject(args[1]);
                currentBugOwner.Eat(bug, spore);
            }
        });

        actCmds.put("cutthread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Bug bug = (Bug)view.GetObject(args[0]);
                ShroomThread thread = (ShroomThread)view.GetObject(args[1]);
                currentBugOwner.CutThread(bug, thread);
            }
        });

        actCmds.put("updatebugs", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                currentBugOwner.UpdateBugOwner();
            }
        });
        
        actCmds.put("updateshroom", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                currentShroom.UpdateShroom();
            }
        });

        actCmds.put("updatetecton", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;
                ITectonController tecton = (ITectonController)view.GetObject(args[0]);
                tecton.UpdateTecton();
            }
        });

        actCmds.put("split", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;
                ITectonController tecton = (ITectonController)view.GetObject(args[0]);
                tecton.Split();
            }
        });

        actCmds.put("nextplayer", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if (bugOwnerRound) {
                    int idx = bugOwners.indexOf(currentBugOwner);
                    if (idx == bugOwners.size() - 1) {
                        bugOwnerRound = false;
                        currentShroom = shrooms.get(0);
                    }
                } else {
                    int idx = shrooms.indexOf(currentShroom);
                    if (idx == shrooms.size() - 1) {
                        bugOwnerRound = true;
                        currentBugOwner = bugOwners.get(0);
                    }
                }
            }
        });

        // ASSERT
        assertCmds.put("show",new ShowCmd(System.out));
        assertCmds.put("showall", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                BaseCommand show = assertCmds.get("show");
                view.GetObjects().forEach(name -> show.Run(new String[] { name }));
            }
        });

        assertCmds.put("save",new BaseCommand() {
            @Override
            public void Run(String[] args) {
                var p = Paths.get("target", args[0]);
                if(runpath != null){
                    p = runpath.resolve(args[0]);
                }

                try (FileOutputStream fstream = new FileOutputStream(p.toFile())) {
                    var save = new ShowCmd(fstream);
                    view.GetObjects().forEach(name -> save.Run(new String[] { name }));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Ez a metódus ellenőrzi, hogy elegendő argumentum van-e a parancs végrehajtásához.
     * @return String - A parancs neve
     */
    public String GetPrompt() {
        var objStore = View.GetObjectStore();
        return switch(mode) {
            case ARRANGE -> "arrange>";
            case ACT -> "act#" + objStore.GetName(bugOwnerRound ? currentBugOwner : currentShroom) + ">";
            case ASSERT -> "assert>";
        };
    }

    /**
     * A parancsok feldolgozásáért felelős metódus.
     * @param cmd A parancs, amelyet végre kell hajtani.
     * @return void
     */
    public void ProcessCommand(String cmd) {
        if (cmd.isEmpty())
            return;

        String[] cmdParts = cmd.trim().split(" ");
        String cmdName = cmdParts[0];
        String[] params = Arrays.copyOfRange(cmdParts, 1, cmdParts.length);

        BaseCommand command = normalCmds.get(cmdName);
        if(command != null) {
            command.Run(params);
            return;
        }

        Map<String,BaseCommand> currentCmds = GetCurrentModesCommands();
        command = currentCmds.get(cmdName);
        if(command != null)
            command.Run(params);
        else
            System.out.println("A parancs nem található");
    }

    /**
     * Ez a metódus adja vissza a parancsok aktuális módját.
     * @return Map<String, BaseCommand> - A parancsok aktuális módja
     */
    public Map<String, BaseCommand> GetCurrentModesCommands() {
        return switch (mode) {
            case ARRANGE -> arrangeCmds;
            case ACT -> actCmds;
            case ASSERT -> assertCmds;
        };
    }

    /**
     * Ez a metódus felvesz egy új BugOwner-t a bugOwners listába.
     * @param bugOwner A BugOwner, amelyet hozzá szeretnénk adni a listához
     * @return void
     */
    public void AddBugOwner(IBugOwnerController bugOwner) {
        bugOwners.add(bugOwner);
    }

    /**
     * Ez a metódus felvesz egy új Shroom-ot a shrooms listába.
     * @param shroom A Shroom, amelyet hozzá szeretnénk adni a listához
     * @return void
     */
    public void AddShroom(IShroomController shroom) {
        shrooms.add(shroom);
    }

    /**
     * Ez a metódus egy random számot generál a megadott minimum és maximum érték között.
     * @param min A minimum érték
     * @param max A maximum érték
     * @return int - A generált random szám
     */
    @Override
    public int RandomNumber(int min, int max) {
        return rand.nextInt(min, max + 1);
    }

    /**
     * Ez a metódus egy random BugEffect-et generál.
     * @return BugEffect - A generált random BugEffect
     */
    @Override
    public BugEffect RandomBugEffect() {
        return switch (RandomNumber(1, 5)) {
            case 1 -> new CoffeeEffect();
            case 2 -> new SlowEffect();
            case 3 -> new JawLockEffect();
            case 4 -> new CripplingEffect();
            default -> new DivisionEffect();
        };
    }

    /**
     * Ez a metódus egy random boolean értéket generál.
     * @return boolean - A generált random boolean érték
     */
    @Override
    public boolean RandomBoolean() {
        return rand.nextBoolean();
    }

    /**
     * Ez a metódus egy random Seed értéket állít be.
     * @param seed A Seed érték, amelyet be szeretnénk állítani
     * @return void
     */
    @Override
    public void SetSeed(long seed) {
        rand.setSeed(seed);
    }
}