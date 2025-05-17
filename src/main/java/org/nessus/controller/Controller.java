package org.nessus.controller;

import java.util.*;
import java.util.stream.IntStream;

import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.tecton.*;

import org.nessus.model.effect.*;
import org.nessus.view.IGameObjectStore;
import org.nessus.view.View;

/**
 * Ez az osztály implementálja a Controllert, amely a játék logikájáért felelős.
 * A Controller osztály kezeli a parancsokat, és irányítja a játék állapotát.
 * A Controller osztályban található parancsok a játék különböző fázisait kezelik, mint például az elrendezést, a cselekvést és az állításokat.
 */
public class Controller implements IRandomProvider {
    private static Random rand = new Random();

    private IActionController currentAction = null;
    private IShroomController currentShroom = null; // a shroom
    private IBugOwnerController currentBugOwner = null; // a bug owner

    private boolean bugOwnerRound = false; // Bug owner köre
    private List<IBugOwnerController> bugOwners = new ArrayList<>(); // Bug owner lista
    private List<IShroomController> shrooms = new ArrayList<>(); // Shroom lista
    private List<ITectonController> tectons = new ArrayList<>();

    private View view; // static lett
    private int playerIndex = 0;

    /**
     * A Controller osztály konstruktora, amely inicializálja a parancsokat és beállítja a nézetet.
     * @param view A nézet, amelyet a Controller használ.
     */
    public Controller(View view)
    {
        this.view = view;
    }

    public void ClearMap() {
        bugOwners.clear();
        shrooms.clear();
        tectons.clear();
    }

    private void AddEdge(Tecton t1, Tecton t2) {
        t1.SetNeighbour(t2);
        t2.SetNeighbour(t1);
    }

    private <T> T RandomOf(List<T> list) {
        return list.get(RandomNumber(0, list.size() - 1));
    }

    private List<Tecton> AddRandomEdge(List<Tecton> list1, List<Tecton> list2) {
        Tecton t1 = null;
        Tecton t2 = null;

        while (t1 == t2) {
            t1 = RandomOf(list1);
            t2 = RandomOf(list2);
            if(t1.IsNeighbourOf(t2)){
                t1 = null;
                t2 = null;
            }
        }

        AddEdge(t1, t2);
        return List.of(t1, t2);
    }

    public void GenerateMap(int tectonCount)
    {
        List<Tecton> disconnected = new ArrayList<>();
        List<Tecton> connected = new ArrayList<>();
        
        for (int i = 0; i < tectonCount; i++){
            // 0-7 intervallumban generálunk számokat
            // az első 4 szám egyenként egy típust jelöl
            // az utolsó 4 szám esetén pedig az egyszerű tektont generáljuk le
            // így 50% az esély hogy egyszerű tektont kapunk

            int tectonTypes = 4;
            int num = RandomNumber(0, tectonTypes * 2);
            Tecton tecton;
            
            switch(num){
                case 0:
                    tecton = new DesertTecton();
                    break;
                case 1:
                {
                    if (disconnected.size() >= shrooms.size())
                    {
                        tecton = new InfertileTecton();
                    }
                    else
                    {
                        tecton = new Tecton();
                    }
                    break;
                }
                case 2:
                    tecton = new SingleThreadTecton();
                    break;
                case 3:
                    tecton = new ThreadSustainerTecton();
                    break;
                default: 
                    tecton = new Tecton();
                    break;
            }

            disconnected.add(tecton);
        }


        var pickedTectons = AddRandomEdge(disconnected, disconnected);
        for (var tecton : List.copyOf(pickedTectons)) {
            disconnected.remove(tecton);
            connected.add(tecton);
        }

        for (int i = 0; i < tectonCount - 2; i++) {
            pickedTectons = AddRandomEdge(disconnected, connected);
            var lastConnected = pickedTectons.get(0);
            disconnected.remove(lastConnected);
            connected.add(lastConnected);
        }

        int additionalEdges = (int)Math.sqrt(tectonCount * (tectonCount - 1) / 4);
        for (int i = 0; i < additionalEdges; i++)
            AddRandomEdge(connected, connected);

        tectons.addAll(connected);
        var store = view.GetObjectStore();
        connected.forEach(store::AddTecton);

        for (var bugOwner : bugOwners) {
            var tecton = RandomOf(connected);
            Bug bug = new Bug((BugOwner)bugOwner, tecton);
            tecton.AddBug(bug);
            store.AddBug(bug);
        }

        for (var shroom : shrooms) {
            var tecton = RandomOf(connected);

            while (tecton.GetShroomBody() != null || tecton instanceof InfertileTecton)
                tecton = RandomOf(connected);

            ShroomBody shroomBody = new ShroomBody((Shroom)shroom, tecton);
            tecton.SetShroomBody(shroomBody);
            store.AddShroomBody(shroomBody);
        }
        NextPlayer();
    }

    public void StartAction(IActionController action){
        view.GetSelection().ClearSelection();
        currentAction = action;
    }

    public void NextPlayer()
    {
        if (bugOwnerRound)
        {
            if (playerIndex == bugOwners.size())
            {
                bugOwnerRound = false;
                playerIndex = 0;
                currentShroom = shrooms.get(playerIndex);
            }
            currentBugOwner = bugOwners.get(playerIndex);
        }
        else
        {
            if (playerIndex == shrooms.size())
            {
                bugOwnerRound = true;
                playerIndex = 0;
                currentBugOwner = bugOwners.get(playerIndex);
            }
            currentShroom = shrooms.get(playerIndex);
        }
        playerIndex++;
        view.UpdatePlayerInfo();
    }

    public Object GetCurrentPlayer()
    {
        return bugOwnerRound ? currentBugOwner : currentShroom;
    }
    public IBugOwnerController GetCurrentBugOwnerController(){
        return currentBugOwner;
    }
    public IShroomController GetCurrentShroomController(){
        return currentShroom;
    }

    public int GetPlayerActionPoints(){
        // TODO
        return 0;
    }

    /**
     * Ez a metódus felvesz egy új BugOwner-t a bugOwners listába.
     * @param bugOwner A BugOwner, amelyet hozzá szeretnénk adni a listához
     * @return void
     */
    public void AddBugOwner(IBugOwnerController bugOwner) {
        bugOwners.add(bugOwner);
        currentBugOwner = bugOwner;
    }

    /**
     * Ez a metódus felvesz egy új Shroom-ot a shrooms listába.
     * @param shroom A Shroom, amelyet hozzá szeretnénk adni a listához
     * @return void
     */
    public void AddShroom(IShroomController shroom) {
        shrooms.add(shroom);
        currentShroom = shroom;
    }

    public void AddTecton(ITectonController tecton) {
        tectons.add(tecton);
    }

    public void ViewSelectionChanged(){
        if (currentAction != null) {
            if (currentAction.TryAction(view)) {
                currentAction = null;
                view.GetSelection().ClearSelection();
            }
        }
    }

    /**
     * Ez a metódus egy random számot generál a megadott minimum és maximum érték között.
     * @param min A minimum érték
     * @param max A maximum érték
     * @return int - A generált random szám
     */
    public int RandomNumber(int min, int max) {
        return rand.nextInt(min, max + 1);
    }

    /**
     * Ez a metódus egy random BugEffect-et generál.
     * @return BugEffect - A generált random BugEffect
     */
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
    public boolean RandomBoolean() {
        return rand.nextBoolean();
    }

    /**
     * Ez a metódus egy random Seed értéket állít be.
     * @param seed A Seed érték, amelyet be szeretnénk állítani
     * @return void
     */
    public void SetSeed(long seed) {
        rand.setSeed(seed);
    }

    public boolean IsBugOwnerRound() {return bugOwnerRound;}

    public View GetView() {return view;}
}