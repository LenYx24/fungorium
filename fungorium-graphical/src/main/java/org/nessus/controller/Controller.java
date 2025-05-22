package org.nessus.controller;

import java.util.*;

import org.nessus.model.bug.Bug;
import org.nessus.model.bug.BugOwner;
import org.nessus.model.shroom.Shroom;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.tecton.*;
import org.nessus.model.effect.*;
import org.nessus.view.View;

/**
 * Ez az osztály implementálja a Controllert, amely a játék logikájáért felelős.
 * A Controller osztály kezeli a parancsokat, és irányítja a játék állapotát.
 * A Controller osztályban található parancsok a játék különböző fázisait kezelik, mint például az elrendezést, a cselekvést és az állításokat.
 */
public class Controller implements IRandomProvider {
    private static Random rand = new Random();
    private static final int TECTON_SPLIT_CHANCE = 8;

    private IActionController currentAction = null;
    private IShroomController currentShroom = null; // a shroom
    private IBugOwnerController currentBugOwner = null; // a bug owner

    private boolean bugOwnerRound = false; // Bug owner köre
    private List<IBugOwnerController> bugOwners = new ArrayList<>(); // Bug owner lista
    private List<IShroomController> shrooms = new ArrayList<>(); // Shroom lista
    private List<ITectonController> tectons = new ArrayList<>(); // Tecton lista

    private View view; // static lett
    private int playerIndex = 0;

    /**
     * A Controller osztály konstruktora, amely inicializálja a parancsokat és beállítja a nézetet.
     * @param view A nézet, amelyet a Controller használ.
     * @return void
     */
    public Controller(View view) {
        this.view = view;
    }

    /**
     * Törli az összes játékelemet a térképről.
     * Eltávolítja az összes bug ownert, shroomot és tektont a megfelelő listákból.
     * @return void
     */
    public void ClearMap() {
        bugOwners.clear();
        shrooms.clear();
        tectons.clear();
    }

    /**
     * Kétirányú kapcsolatot hoz létre két tekton között.
     * @param t1 Az első tekton a kapcsolathoz
     * @param t2 A második tekton a kapcsolathoz
     * @return void
     */
    private void AddEdge(Tecton t1, Tecton t2) {
        t1.SetNeighbour(t2);
        t2.SetNeighbour(t1);
    }

    /**
     * Visszaad egy véletlenszerű elemet a megadott listából.
     * @param <T> A lista elemeinek típusa
     * @param list A lista, amelyből véletlenszerű elemet választunk
     * @return <T> Egy véletlenszerűen kiválasztott elem a listából
     */
    private <T> T RandomOf(List<T> list) {
        return list.get(RandomNumber(0, list.size() - 1));
    }

    /**
     * Véletlenszerű élet hoz létre két tekton lista között.
     * Kiválaszt egy véletlenszerű tektont mindkét listából, és összeköti őket, ha még nincsenek összekötve.
     * 
     * @param list1 Az első tekton lista
     * @param list2 A második tekton lista
     * @return List<Tecton> - Egy lista, amely tartalmazza a két összekötött tektont
     */
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
    
    /**
     * Létrehoz egy véletlenszerű tektont különböző típusokból.
     * 50% eséllyel hoz létre egy alap Tektont, és egyenlő esélyekkel a speciális tekton típusokat.
     * @param infertileAllowed Meghatározza, hogy a terméketlen tektonok létrehozhatók-e
     * @return Tecton - Egy újonnan létrehozott véletlenszerű tekton
     */
    private Tecton RandomTecton(boolean infertileAllowed) {
        // 0-7 intervallumban generálunk számokat
        // az első 4 szám egyenként egy típust jelöl
        // az utolsó 4 szám esetén pedig az egyszerű tektont generáljuk le
        // így 50% az esély hogy egyszerű tektont kapunk

        return switch(RandomNumber(0, 7)) {
            case 0 -> new DesertTecton();
            case 1 -> infertileAllowed ? new InfertileTecton() : new Tecton();
            case 2 -> new SingleThreadTecton();
            case 3 -> new ThreadSustainerTecton();
            default -> new Tecton();
        };
    }

    /**
     * Generál egy térképet a megadott számú tektonnal.
     * Létrehoz egy összefüggő gráfot tektonokból, és elhelyez rajtuk rovarokat és gombatesteket.
     * @param tectonCount A generálandó tektonok száma
     * @return void
     */
    public void GenerateMap(int tectonCount) {
        List<Tecton> disconnected = new ArrayList<>();
        List<Tecton> connected = new ArrayList<>();
        
        for (int i = 0; i < tectonCount; i++){
            Tecton tecton = RandomTecton(tectonCount > shrooms.size());
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

        int additionalEdges = (int)Math.sqrt(tectonCount * (tectonCount - 1) / 4.0);
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
            ShroomBody shroomBody = new ShroomBody((Shroom)shroom);
            Tecton bodyTecton = null;

            while (bodyTecton == null) {
                var tecton = RandomOf(connected);
                if (tecton.GetShroomBody() != null)
                    continue;
                bodyTecton = tecton.SetShroomBody(shroomBody) ? tecton : null;
            }

            store.AddShroomBody(shroomBody);
        }

        bugOwnerRound = false;
        playerIndex = 0;
        currentShroom = shrooms.get(playerIndex);
        view.UpdatePlayerInfo();
    }

    /**
     * Elindít egy új akciót a megadott akció vezérlővel.
     * Törli az aktuális kijelölést és beállítja az aktuális akciót.
     * @param action Az akció vezérlő, amelyet el kell indítani
     * @return void
     */
    public void StartAction(IActionController action){
        view.GetSelection().Clear();
        currentAction = action;
    }

    /**
     * Továbblép a következő játékos körére.
     * Kezeli az átmenetet a rovarász és a gombász körök között.
     * Frissíti a játékos információkat és esélyt ad egy tekton hasadásra.
     * @return void
     */
    public void NextPlayer() {
        playerIndex++;

        if (bugOwnerRound && playerIndex == bugOwners.size()) {
            bugOwnerRound = false;
            playerIndex = 0;

            tectons.forEach(ITectonController::UpdateTecton);
            if (RandomNumber(0, 100) < TECTON_SPLIT_CHANCE)
                tectons.get(RandomNumber(0, tectons.size() - 1)).Split();

        } else if (!bugOwnerRound && playerIndex == shrooms.size()) {
            bugOwnerRound = true;
            playerIndex = 0;
        }
        
        if (bugOwnerRound) {
            currentBugOwner = bugOwners.get(playerIndex);
            currentBugOwner.UpdateBugOwner();
        } else {
            currentShroom = shrooms.get(playerIndex);
            currentShroom.UpdateShroom();
        }
        
        view.UpdatePlayerInfo();
        view.GetGamePanel().GetControlPanel().UpdateButtonTexts();
    }

    /**
     * Visszaadja az aktuális játékost (vagy egy rovarászt vagy egy gombászt).
     * @return Object - Az aktuális játékos objektumként
     */
    public Object GetCurrentPlayer() {
        return bugOwnerRound ? currentBugOwner : currentShroom;
    }

    /**
     * Visszaadja az aktuális rovarász vezérlőt.
     * 
     * @return IBugOwnerController - Az aktuális rovarász vezérlő
     */
    public IBugOwnerController GetCurrentBugOwnerController() {
        return currentBugOwner;
    }
    
    /**
     * Visszaadja az aktuális gombász vezérlőt.
     * 
     * @return IShroomController - Az aktuális gombász vezérlő
     */
    public IShroomController GetCurrentShroomController() {
        return currentShroom;
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

    /**
     * Hozzáad egy új tekton vezérlőt a tektonok listájához.
     * @param tecton A hozzáadandó tekton vezérlő
     * @return void
     */
    public void AddTecton(ITectonController tecton) {
        tectons.add(tecton);
    }

    /**
     * Kezeli a nézet kijelölésének változásait.
     * Ha van aktuális akció és az végrehajtható, végrehajtja és törli a kijelölést.
     * @return void
     */
    public void ViewSelectionChanged() {
        if (currentAction != null && currentAction.TryAction()) {
            currentAction = null;
            view.GetSelection().Clear();
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
     * @return Boolean - A generált random boolean érték
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

    /**
     * Ellenőrzi, hogy jelenleg rovarász köre van-e.
     * 
     * @return Boolean - igaz, ha rovarász köre van, hamis, ha gombász köre van
     */
    public boolean IsBugOwnerRound() {return bugOwnerRound;}

    /**
     * Visszaadja a vezérlőhöz tartozó nézetet.
     * 
     * @return View - A nézet objektum
     */
    public View GetView() {return view;}

    public List<IShroomController> GetShrooms() {
        return this.shrooms;
    }

    public List<IBugOwnerController> GetBugOwners() {
        return this.bugOwners;
    }
}