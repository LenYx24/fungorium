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

public class Controller implements IRandomProvider {
    private static Random rand = new Random();

    private enum CmdMode {
        ARRANGE,
        ACT,
        ASSERT
    }

    private IBugOwnerController currentBugOwner = null;
    private IShroomController currentShroom = null;

    private boolean bugOwnerRound = false;
    private List<IBugOwnerController> bugOwners = new ArrayList<>();
    private List<IShroomController> shrooms = new ArrayList<>();
    
    private CmdMode mode = CmdMode.ARRANGE;
    private HashMap<String, BaseCommand> normalCmds = new HashMap<>();

    private HashMap<String, BaseCommand> arrangeCmds = new HashMap<>();
    private HashMap<String, BaseCommand> actCmds = new HashMap<>();
    private HashMap<String, BaseCommand> assertCmds = new HashMap<>();

    Path runpath = null;

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
                
                Tecton t1 = (Tecton)view.GetObject(args[0]);
                Tecton t2 = (Tecton)view.GetObject(args[1]);

                t1.SetNeighbour(t2);
                t2.SetNeighbour(t1);
            }
        });
        arrangeCmds.put("place", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;
                
                Object obj = view.GetObject(args[0]);
                Tecton tecton = (Tecton)view.GetObject(args[1]);

                if(obj instanceof Bug bug) {
                    tecton.AddBug(bug);
                    bug.SetTecton(tecton);
                }
                else if(obj instanceof ShroomBody body) {
                    tecton.SetShroomBody(body);
                    body.SetTecton(tecton);
                }
                else if(obj instanceof Spore spore) {
                    tecton.ThrowSpore(spore);
                    spore.SetTecton(tecton);
                }
            }
        });
        arrangeCmds.put("placethread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,3))
                    return;

                Object obj = view.GetObject(args[0]);

                Tecton tecton1 = (Tecton)view.GetObject(args[1]);
                Tecton tecton2 = (Tecton)view.GetObject(args[2]);

                if(obj instanceof ShroomThread shroomThread) {
                    ShroomThread thread = shroomThread;

                    tecton1.GrowShroomThread(thread);
                    tecton2.GrowShroomThread(thread);

                    thread.SetTecton1(tecton1);
                    thread.SetTecton2(tecton2);
                }
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
                        System.err.println(line);
                        ProcessCommand(line);
                    }
                } catch (IOException e) {
                    System.out.println("Hiba a fájl olvasása közben: " + e.getMessage());
                }
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
        arrangeCmds.put("addeffect", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;
                Bug bug = (Bug)view.GetObject(args[0]);
                BugEffect effect = (BugEffect)view.GetObject(args[1]);
                bug.AddEffect(effect);
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

    public String GetPrompt() {
        var objStore = View.GetObjectStore();
        return switch(mode) {
            case ARRANGE -> "arrange";
            case ACT -> "act#" + objStore.GetName(bugOwnerRound ? currentBugOwner : currentShroom);
            case ASSERT -> "assert";
        };
    }

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

    public Map<String, BaseCommand> GetCurrentModesCommands() {
        return switch (mode) {
            case ARRANGE -> arrangeCmds;
            case ACT -> actCmds;
            case ASSERT -> assertCmds;
        };
    }

    public void AddBugOwner(IBugOwnerController bugOwner) {
        bugOwners.add(bugOwner);
    }

    public void AddShroom(IShroomController shroom) {
        shrooms.add(shroom);
    }

    @Override
    public int RandomNumber(int min, int max) {
        return rand.nextInt(min, max + 1);
    }

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

    @Override
    public boolean RandomBoolean() {
        return rand.nextBoolean();
    }

    @Override
    public void SetSeed(long seed) {
        rand.setSeed(seed);
}

}
