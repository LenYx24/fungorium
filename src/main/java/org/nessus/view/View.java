package org.nessus.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.nessus.controller.IRandomProvider;
import org.nessus.controller.Controller;
import org.nessus.model.shroom.*;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.bug.*;
import org.nessus.view.entityviews.*;
import org.nessus.view.factories.*;
import org.nessus.view.panels.*;

import javax.swing.*;

/**
 * Ez a singleton View osztály felelős a program futtatásáért.
 * A View osztály a Controller osztályt használja a parancsok feldolgozására.
 * A View osztály a parancsok végrehajtásáért és a felhasználói interakcióért felelős.
 */
public class View extends JFrame implements IGameObjectStore {
    private static View instance;

    private List<Tecton> selectedTectons = new ArrayList<>();
    private Spore selectedSpore = null;
    private Bug selectedBug = null;
    private ShroomThread selectedShroomThread = null;
    private ShroomBody selectedShroomBody = null;

    private List<TectonView> tectons = new ArrayList<>();
    
    private Map<BugOwner, BugViewFactory> bugOwners = new HashMap<>();
    private Map<Shroom, ShroomViewFactory> shrooms = new HashMap<>();

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
     * Lekér egy random generátort.
     * @return IRandomProvider - A random generátor
     */
    @Override
    public Controller GetRandomProvider() {
        return controller;
    }

    /**
     * Ezzela metódussal kaphatunk vissza egy ObjectStore-t.
     * @return IGameObjectStore - Az objektumok tárolására szolgáló objektum.
     */
    public static IGameObjectStore GetObjectStore() {
        return GetInstance();
    }

    public List<IEntityView> GetEntityViews() {
        return views;
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
        var controlPanel = gamePanel.GetControlPanel();
        controlPanel.UpdateEntityInfo(entity);
        var selector = new EntitySelector(this);
        entity.Accept(selector);
        controller.ViewSelectionChanged();
    }

    public void ClearSelection(){
        selectedBug = null;
        selectedShroomBody = null;
        selectedShroomThread = null;
        selectedSpore = null;
        selectedTectons.clear();
    }

    public void AddShroomBody(ShroomBody shroomBody) {
        ShroomViewFactory factory = shrooms.get(shroomBody.GetShroom());
        views.add(factory.CreateShroomBodyView(shroomBody));
    }

    public void AddShroomThread(ShroomThread shroomThread) {
        ShroomViewFactory factory = shrooms.get(shroomThread.GetShroom());
        views.add(factory.CreateShroomThreadView(shroomThread));
    }

    public void AddSpore(Spore spore) {
        ShroomViewFactory factory = shrooms.get(spore.GetShroom());
        views.add(factory.CreateSporeView(spore));
    }

    public void AddBug(Bug bug) {
        BugViewFactory factory = bugOwners.get(bug.GetOwner());
        views.add(factory.CreateBugView(bug));
    }

    public void AddTecton(Tecton tecton) {
        var texturer = new TectonTexturer();
        tecton.Accept(texturer);
    }

    public void AddBugOwner(BugOwner bugOwner) {

    }

    public void SelectBug(Bug bug) {
        selectedBug = bug;
    }

    public void SelectShroomThread(ShroomThread thread){
        selectedShroomThread = thread;
    }

    public void SelectShroomBody(ShroomBody body) {
        selectedShroomBody = body;
    }

    public void SelectSpore(Spore spore) {
        selectedSpore = spore;
    }

    public void SelectTecton(Tecton tecton) {
        selectedTectons.remove(0);
        selectedTectons.add(tecton);
    }

    public Bug GetSelectedBug() {
        return selectedBug;
    }

    public ShroomThread GetSelectedShroomThread() {
        return selectedShroomThread;
    }

    public ShroomBody GetSelectedShroomBody() {
        return selectedShroomBody;
    }

    public Spore GetSelectedSpore() {
        return selectedSpore;
    }

    public List<Tecton> GetSelectedTectons() {
        return selectedTectons;
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