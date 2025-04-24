package org.nessus.view;

import java.util.*;

import org.nessus.model.shroom.*;
import org.nessus.model.tecton.*;
import org.nessus.controller.Controller;
import org.nessus.controller.IBugController;
import org.nessus.controller.IRandomProvider;
import org.nessus.controller.IShroomController;
import org.nessus.controller.ITectonController;
import org.nessus.controller.command.*;
import org.nessus.controller.command.arrangecmd.*;
import org.nessus.controller.command.assertcmd.*;
import org.nessus.model.bug.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class View implements IGameObjectStore {
    private static View instance;

    private boolean running = true; // A program futását jelző változó
    private Map<String,Object> objects = new LinkedHashMap<>();

    private IBugController currentBugOwner = null;
    private IShroomController currentShroom = null;
    private IRandomProvider rand = new Controller();

    private enum CmdMode{
        ARRANGE,
        ACT,
        ASSERT
    }
    
    private CmdMode mode = CmdMode.ARRANGE;
    private HashMap<String, BaseCommand> normalCmds = new HashMap<>();

    private HashMap<String, BaseCommand> arrangeCmds = new HashMap<>();
    private HashMap<String, BaseCommand> actCmds = new HashMap<>();
    private HashMap<String, BaseCommand> assertCmds = new HashMap<>();

    private List<String> order = new LinkedList<>();

    /**
     * A {@code View} osztály konstruktora.
     * A konstruktor privát, mert nem szükséges példányosítani az osztályt.
     */
    private View() {
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
                running = false;
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
                GetNum(args[0]).ifPresent(rand::SetSeed);
            }
        });
        arrangeCmds.put("neighbour", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;
                
                Tecton t1 = (Tecton)GetObject(args[0]);
                Tecton t2 = (Tecton)GetObject(args[1]);

                t1.SetNeighbour(t2);
                t2.SetNeighbour(t1);
            }
        });
        arrangeCmds.put("place", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;
                
                    Object obj = GetObject(args[0]);
                Tecton tecton = (Tecton)GetObject(args[1]);

                if(obj instanceof Bug)
                    tecton.AddBug((Bug)obj);
                else if(obj instanceof ShroomBody)
                    tecton.SetShroomBody((ShroomBody)obj);
                else if(obj instanceof Spore)
                    tecton.ThrowSpore((Spore)obj);
            }
        });
        arrangeCmds.put("placethread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,3))
                    return;

                Object obj = GetObject(args[0]);

                Tecton tecton1 = (Tecton)GetObject(args[1]);
                Tecton tecton2 = (Tecton)GetObject(args[2]);

                if(obj instanceof ShroomThread) {
                    ShroomThread thread = (ShroomThread)obj;

                    tecton1.GrowShroomThread(thread);
                    tecton2.GrowShroomThread(thread);
                }
            }
        });

        arrangeCmds.put("run", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))return;
                // todo
            }
        });
        arrangeCmds.put("currentplayer", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))return;
                String name = args[0];
                Object obj = GetObject(name);
                if(obj instanceof BugOwner) {
                    currentBugOwner = (BugOwner)obj;
                    currentShroom = null;
                }
                if(obj instanceof Shroom) {
                    currentBugOwner = null;
                    currentShroom = (Shroom)obj;
                }
            }
        });
        // ACT
        actCmds.put("placeshroomthread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Tecton tecton1 = (Tecton)GetObject(args[0]);
                Tecton tecton2 = (Tecton)GetObject(args[1]);

                currentShroom.PlaceShroomThread(tecton1, tecton2);
            }
        });

        actCmds.put("placeshroombody", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;

                Tecton tecton = (Tecton)GetObject(args[0]);
                currentShroom.PlaceShroomBody(tecton);
            }
        });

        actCmds.put("upgradeshroombody", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;

                ShroomBody body = (ShroomBody)GetObject(args[0]);
                currentShroom.UpgradeShroomBody(body);
            }
        });

        actCmds.put("throwspore", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                ShroomBody body = (ShroomBody)GetObject(args[0]);
                Tecton tecton = (Tecton)GetObject(args[1]);
                currentShroom.ThrowSpore(body, tecton);
            }
        });
        
        actCmds.put("devour", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                ShroomThread thread = (ShroomThread)GetObject(args[0]);
                Bug bug = (Bug)GetObject(args[1]);
                currentShroom.ShroomThreadDevourBug(thread, bug);
            }
        });
        
        actCmds.put("move", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Bug bug = (Bug)GetObject(args[0]);
                Tecton tecton = (Tecton)GetObject(args[1]);
                currentBugOwner.Move(bug, tecton);
            }
        });
        
        actCmds.put("eatspore", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Bug bug = (Bug)GetObject(args[0]);
                Spore spore = (Spore)GetObject(args[1]);
                currentBugOwner.Eat(bug, spore);
            }
        });

        actCmds.put("cutthread", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,2))
                    return;

                Bug bug = (Bug)GetObject(args[0]);
                ShroomThread thread = (ShroomThread)GetObject(args[1]);
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
                ITectonController tecton = (ITectonController)GetObject(args[0]);
                tecton.UpdateTecton();
            }
        });

        actCmds.put("split", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                if(NotEnoughArgs(args,1))
                    return;
                ITectonController tecton = (ITectonController)GetObject(args[0]);
                tecton.Split();
            }
        });

        actCmds.put("nextplayer", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                System.out.println("unimplemented");
            }
        });

        // ASSERT
        assertCmds.put("show",new ShowCmd());
        assertCmds.put("showall", new BaseCommand() {
            @Override
            public void Run(String[] args) {
                BaseCommand show = assertCmds.get("show");
                objects.forEach((name, obj) -> show.Run(new String[] {name}));
            }
        });
        assertCmds.put("save",new ShowCmd());
    }

    static View GetInstance() {
        if (instance == null)
            instance = new View();
        return instance;
    }

    public static IGameObjectStore GetObjectStore() {
        return GetInstance();
    }

    private Map<String, BaseCommand> GetCurrentModesCommands(CmdMode mode) {
        return switch (mode) {
            case ARRANGE -> arrangeCmds;
            case ACT -> actCmds;
            case ASSERT -> assertCmds;
        };
    }
    /**
     * A program futását megvalósító metódus.
     * @return void
     */
    public void Run() {
        Scanner scanner = new Scanner(in);
        System.out.println("Üdv a prototípus fázisban");

        while (running) {
            String prompt = switch(mode) {
                case ARRANGE -> "arrange";
                case ACT -> "act";
                case ASSERT -> "assert";
            };

            System.out.print(prompt + ">");

            String[] cmd_parts = scanner.nextLine().split(" ");
            String cmd = cmd_parts[0];
            String[] params = Arrays.copyOfRange(cmd_parts, 1, cmd_parts.length);
            
            BaseCommand command = normalCmds.get(cmd);
            if(command != null) {
                command.Run(params);
                continue;
            }

            Map<String,BaseCommand> currentCmds = GetCurrentModesCommands(mode);
            command = currentCmds.get(cmd);
            if(command != null)
                command.Run(params);
            else
                System.out.println("A parancs nem található");
        }

        scanner.close();
    }

    /**
     * Ezzel a metódussal adhatunk hozzá objektumot a loghoz.
     * @param name
     * @param object
     */
    public void AddObject(String name, Object object) {
        objects.put(name,object);
    }

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param object
     * @return String - Az objektum neve
     */
    public String GetName(Object object) {
        return "null";
    }

    public Object GetObject(String name) {
        return objects.get(name);
    }

    public static void main(String[] args) {
        View view = View.GetInstance();
        view.Run();
    }
}