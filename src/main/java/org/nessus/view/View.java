package org.nessus.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.nessus.controller.IRandomProvider;
import org.nessus.model.shroom.*;
import org.nessus.controller.Controller;
import org.nessus.model.bug.*;
import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entityviews.IEntityView;
import org.nessus.view.entityviews.ShroomBodyView;
import org.nessus.view.entityviews.TectonView;
import org.nessus.view.factories.BugViewFactory;
import org.nessus.view.factories.ShroomViewFactory;
import org.nessus.view.panels.GamePanel;
import org.nessus.view.panels.MainMenuPanel;
import org.nessus.view.panels.SettingsPanel;
import org.nessus.view.panels.ControlPanel;

import javax.swing.*;

/**
 * Ez a singleton View osztály felelős a program futtatásáért.
 * A View osztály a Controller osztályt használja a parancsok feldolgozására.
 * A View osztály a parancsok végrehajtásáért és a felhasználói interakcióért felelős.
 */
public class View extends JFrame implements IGameObjectStore {
    private static View instance;
    Map<String, Object> objects = new HashMap<>();

    private Tecton[] selectedTectons;
    private Spore selectedSpore;
    private Bug selectedBug;
    private ShroomThread selectedShroomThread;
    private ShroomBody selectedShroomBody;
    private TectonView[] tectons;
    private Map<BugOwner, BugViewFactory> bugOwners;
    private Map<Shroom, ShroomViewFactory> shrooms;

    private Controller controller = new Controller(this);
    private List<IEntityView> views = new ArrayList<>();

    private JPanel mainPanel;
    private MainMenuPanel mainMenuPanel;
    private SettingsPanel settingsPanel;
    private GamePanel gamePanel;

    /**
     * A {@code View} osztály konstruktora.
     * A konstruktor privát, mert nem szükséges példányosítani az osztályt.
     */
    private View() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        mainPanel = new JPanel(new CardLayout());

        // A stringeket egy mapbe lehetne mozgatni, és a viewtől lekérni hogy a MainMenuPanelhez milyen aktivációs
        // string tartozik
        mainPanel.add(new MainMenuPanel(mainPanel), "menu");
        mainPanel.add(new SettingsPanel(this, mainPanel), "settings");
        mainPanel.add(new GamePanel(this), "game");

        add(mainPanel);
        pack();
        setVisible(true);
    }

    /**
     * Ez a metódus visszaadja a {@code View} osztály egy példányát.
     * Ha a példány még nem létezik, akkor létrehozza azt.
     * @return A {@code View} osztály egy példánya.
     */
    static View GetInstance() {
        if (instance == null)
            instance = new View();
        return instance;
    }

    public Controller GetController() {
        return controller;
    }

    /**
     * Ezzela metódussal kaphatunk vissza egy ObjectStore-t.
     * @return IGameObjectStore - Az objektumok tárolására szolgáló objektum.
     */
    public static IGameObjectStore GetObjectStore() {
        return GetInstance();
    }

    /**
     * Ezzel a metódussal kérhetjük le a programban tárolt objektumokat.
     * @return Set<String> - Az objektumok tárolására szolgáló térkép.
     */
    public Set<String> GetObjects() {
        return objects.keySet();
    }

    /**
     * Ezzel a metódussal adhatunk hozzá objektumot a loghoz.
     * @param name
     * @param object
     */
    public void AddObject(String name, Object object) {
        if (object instanceof BugOwner bugOwner)
            controller.AddBugOwner(bugOwner);
        else if (object instanceof Shroom shroom)
            controller.AddShroom(shroom);

        objects.put(name, object);
    }

    /**
     * Ezzel a metódussal törölhetjük az összes objektumot a tárolókból.
     * @return void
     */
    public void ResetObjects() {
        objects.clear();
    }

    /**
     * Visszaadja a paraméterként kapott objektum nevét.
     * @param object
     * @return String - Az objektum neve
     */
    public String GetName(Object object) {
        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            if (entry.getValue() == object) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Visszaadja azt az objektumot, amelynek a neve megegyezik a paraméterként kapott névvel.
     * @param name - Az objektum neve
     * @return Object - Az objektum, amelynek a neve megegyezik a paraméterként kapott névvel
     */
    public Object GetObject(String name) {
        return objects.get(name);
    }

    /**
     * Lekér egy random generátort.
     * @return IRandomProvider - A random generátor
     */
    @Override
    public Controller GetRandomProvider() {
        return controller;
    }

    /**
     * Visszaadja a Controller példányát.
     * @return Controller - A Controller példánya
     */
    public Controller GetController() {
        return controller;
    }

    public void OpenMenu(){
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"menu");
    }

    public void OpenSettings(){
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"settings");
    }

    public void OpenGame(){
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"game");
    }

    public void HandleSelection(IEntityView entity){

    }
    public void ClearSelection(){

    }

    public void AddShroomBody(ShroomBody shroomBody){
        ShroomViewFactory factory = shrooms.get(shroomBody.GetShroom());
        views.add(factory.CreateShroomBodyView(shroomBody));
    }
    public void AddShroomThread(ShroomThread shroomThread){
        ShroomViewFactory factory = shrooms.get(shroomThread.GetShroom());
        views.add(factory.CreateShroomThreadView(shroomThread));
    }
    public void AddSpore(){
        ShroomViewFactory factory = shrooms.get(selectedSpore.GetShroom());
        views.add(factory.CreateSporeView(selectedSpore));
    }
    public void AddBug(Bug bug){
        BugViewFactory factory = bugOwners.get(bug.GetOwner());
        views.add(factory.CreateBugView(bug));
    }
    public void AddTecton(Tecton tecton){
        var texturer = new TectonTexturer();
        tecton.accept(texturer);
    }

    public void AddBugOwner(BugOwner bugOwner){

    }

    public IEntityView FindEntity(Object entity){
        for(IEntityView view : views){
            if(view.equals(entity))
                return view;
        }
        return null;
    }

    public void ShowBugActions(){

    }
    public void ShowShroomBodyActions(){

    }
    public void ShowShroomThreadActions(){

    }
    public void ShowTectonActions(){

    }

    public void UpdatePlayerInfo(){

    }
    /**
     * A program belépési pontja.
     * @param args - A parancssori argumentumok
     */
    public static void main(String[] args) {
        System.out.println("Üdv a grafikus fázisban");

        SwingUtilities.invokeLater(() -> {
            View view = View.GetInstance();
            view.OpenMenu();
        });

        //shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
            System.out.println("A program leáll.")
        ));
    }
}