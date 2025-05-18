package org.nessus.view;

import java.awt.*;
import java.io.InputStream;
import java.util.Objects;
import java.net.URL;

import org.nessus.controller.IRandomProvider;
import org.nessus.utility.BGMPlayer;
import org.nessus.utility.EntitySelector;
import org.nessus.controller.Controller;
import org.nessus.view.entities.*;
import org.nessus.view.panels.*;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;

/**
 * Ez a singleton View osztály felelős a program futtatásáért.
 * A View osztály a Controller osztályt használja a parancsok feldolgozására.
 * A View osztály a parancsok végrehajtásáért és a felhasználói interakcióért felelős.
 */
public class View extends JFrame {
    private static View instance;

    URL bgmUrl = Objects.requireNonNull(getClass().getResource("/bgm/fields_covered_in_goop.wav"));
    BGMPlayer bgmPlayer = new BGMPlayer(bgmUrl);

    private Controller controller = new Controller(this);
    private SelectionCatalog selection;
    private ObjectStore objectStore = new ObjectStore();

    private JPanel mainPanel;
    private GamePanel gamePanel;

    private Timer renderTimer;

    /**
     * A {@code View} osztály konstruktora.
     * A konstruktor privát, mert nem szükséges példányosítani az osztályt.
     */
    private View() {
        selection = new SelectionCatalog(objectStore);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setTitle("Fungorium");
        setIconImage(new ImageIcon(getClass().getResource("/textures/icon.png")).getImage());
        setResizable(false);
        mainPanel = new JPanel(new CardLayout());
        gamePanel = new GamePanel(this);

        // A stringeket egy mapbe lehetne mozgatni, és a viewtől lekérni hogy a MainMenuPanelhez milyen aktivációs
        // string tartozik
        mainPanel.add(new MainMenuPanel(mainPanel), "menu");
        mainPanel.add(new SettingsPanel(this, mainPanel), "settings");
        mainPanel.add(gamePanel, "game");

        mainPanel.add(new ScoreBoardPanel(mainPanel, this), "scoreBoard");
        renderTimer = new Timer(0, e -> gamePanel.repaint());

        add(mainPanel);
        pack();
        setVisible(true);
        bgmPlayer.playLoop();
    }

    /**
     * Ez a metódus visszaadja a {@code View} osztály egy példányát.
     * Ha a példány még nem létezik, akkor létrehozza azt.
     * @return A {@code View} osztály egy példánya.
     */
    private static View GetInstance() {
        if (instance == null)
            instance = new View();
        return instance;
    }

    /**
     * Lekérdezi a random generátor példányát
     * @return IRandomProvider - A randomgenerátor példánya
     */
    public static IRandomProvider GetRandomProvider() {
        return GetInstance().controller;
    }

    /**
     * Ezzela metódussal kaphatunk vissza egy ObjectStore-t.
     * @return IGameObjectStore - Az objektumok tárolására szolgáló objektum.
     */
    public static IGameObjectStore GetGameObjectStore() {
        return GetInstance().objectStore;
    }

    public ObjectStore GetObjectStore() {
        return objectStore;
    }

    /**
     * Visszaadja a Controller példányát.
     * @return Controller - A Controller példánya
     */
    public Controller GetController() {
        return controller;
    }

    /**
     * Visszaadja a SelectionCatalog példányát
     * @return SelectionCatalog - A SelectionCatalog példánya
     */
    public SelectionCatalog GetSelection() {
        return selection;
    }

    /**
     * Megnyitja a menüt
     * @return void
     */
    public void OpenMenu() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"menu");
        renderTimer.stop();
    }

    /**
     * Megnyitja a játékbeállításokat
     * @return void
     */
    public void OpenSettings() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"settings");
    }

    /**
     * Elindítja a játékot
     * @return void
     */
    public void OpenGame() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"game");
        renderTimer.start();
    }

    public void OpenScoreBoard() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"scoreBoard");
    }
    /**
     * Kiválasztáskezelő függvény, a kiválasztott entitás alapján frissíti a felületet
     * @param entity - A kiválasztott entitás
     * @return void
     */
    public void HandleSelection(IEntityView entity) {
        var controlPanel = gamePanel.GetControlPanel();
        var selector = new EntitySelector(selection);
        entity.Accept(selector);
        controller.ViewSelectionChanged();
        controlPanel.UpdateEntityInfo(entity);
        controlPanel.UpdateButtonTexts();
    }

    /**
     * Frissíti a játékosok információit az adott körtípus alapján
     * @return void
     */
    public void UpdatePlayerInfo() {
        var controlPanel = gamePanel.GetControlPanel();
        
        if (controller.IsBugOwnerRound()) {
            var bugowner = controller.GetCurrentBugOwnerController();
            controlPanel.UpdatePlayerInfo(objectStore.GetBugOwnerName(bugowner));
        } else {
            var shroom = controller.GetCurrentShroomController();
            controlPanel.UpdatePlayerInfo(objectStore.GetShroomName(shroom));
        }
    }

    /**
     * Lekérdezi a GamePanel példányát
     * @return GamePanel - A GamePanel példánya
     */
    public GamePanel GetGamePanel() {
        return gamePanel;
    }

    /**
     * A program belépési pontja.
     * @param args - A parancssori argumentumok
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        
        // Set up FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // Changed to FlatDarkLaf
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        
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