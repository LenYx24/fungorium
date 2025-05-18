package org.nessus.view;

import java.awt.*;
import org.nessus.controller.IRandomProvider;
import org.nessus.utility.EntitySelector;
import org.nessus.controller.Controller;
import org.nessus.view.entities.*;
import org.nessus.view.panels.*;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf; // Changed from FlatLightLaf to FlatDarkLaf

/**
 * Ez a singleton View osztály felelős a program futtatásáért.
 * A View osztály a Controller osztályt használja a parancsok feldolgozására.
 * A View osztály a parancsok végrehajtásáért és a felhasználói interakcióért felelős.
 */
public class View extends JFrame {
    private static View instance;
    
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
        renderTimer = new Timer(0, e -> gamePanel.repaint());

        add(mainPanel);
        pack();
        setVisible(true);
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

    public SelectionCatalog GetSelection() {
        return selection;
    }

    public void OpenMenu() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"menu");
        renderTimer.stop();
    }

    public void OpenSettings() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"settings");
    }

    public void OpenGame() {
        CardLayout cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"game");
        renderTimer.start();
    }

    public void HandleSelection(IEntityView entity) {
        var controlPanel = gamePanel.GetControlPanel();
        var selector = new EntitySelector(selection);
        entity.Accept(selector);
        controller.ViewSelectionChanged();
        controlPanel.UpdateEntityInfo(entity);
        controlPanel.UpdateButtonTexts();
    }

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