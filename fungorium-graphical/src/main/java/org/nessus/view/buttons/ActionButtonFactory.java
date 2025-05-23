package org.nessus.view.buttons;

import org.nessus.controller.*;
import org.nessus.model.bug.Bug;
import org.nessus.model.shroom.ShroomBody;
import org.nessus.model.shroom.Spore;
import org.nessus.model.tecton.Tecton;
import org.nessus.view.View;
import org.nessus.view.entities.IEntityView;
import org.nessus.view.panels.ControlPanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Ez az osztály felel a felel a gombok létrehozásáért, illetve azok kezeléésért, frissítéséért
 * Lekezeli azok funkcióit, meghívja a megfelelő függvényeket
 */
public class ActionButtonFactory {
    private View view; // a nézet
    private Controller controller; // a controller példánya
    private ControlPanel controlPanel; // a controlpanel példánya

    /**
     * A konstruktor
     * @param view - A nézet
     * @param controlPanel - A ControlPanel
     */
    public ActionButtonFactory(View view, ControlPanel controlPanel) {
        this.view = view;
        this.controller = view.GetController();
        this.controlPanel = controlPanel;
    }

    /**
     * Frissíti az akciópontokat a ControlPanelen belül
     * @return void
     */
    private void UpdateActionPoints() {
        controlPanel.UpdateActionPoints();
    }

    /**
     * Frissíti a gombokon megjelenített szöveget
     * @return void
     */
    private void UpdateButtonTexts() {
        controlPanel.UpdateButtonTexts();
    }

    /**
     * Frissíti a megadott entitás információit
     * @param e - Az entitás
     * @return void
     */
    private void UpdateEntityInfo(IEntityView e) {
        controlPanel.UpdateEntityInfo(e);
    }

    /**
     * Létrehoz egy ActionButton-t
     * @param name - A gomb neve
     * @param action - A gomb funkciója (IActionController)
     * @return JButton - A létrehozott akciógomb példánya
     */
    private JButton CreateActionButton(String name, IActionController action){
        JButton button = new JButton(name);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.StartAction(action);
                view.requestFocus();
                view.GetGamePanel().GetMapPanel().GetCamera().FlushKeys();
            }
        });

        return button;
    }

    /**
     * Létrehozza a rovar mozgatása gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreateBugMoveButton(){
        return CreateActionButton("Rovar mozgás" , () -> {
            List<Tecton> tectons = view.GetSelection().GetTectons();
            
            if(!tectons.isEmpty()) {
                Bug bug = view.GetSelection().GetBug();
                Tecton destination = tectons.get(tectons.size() - 1);
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();

                if(bug != null && bugOwner != null && bugOwner == bug.GetOwner() && bug.GetCanMove()){
                    bugOwner.Move(bug, destination);
                    UpdateActionPoints();
                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a rovar elfogyasztása gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreateBugEatButton() {
        return CreateActionButton("Spóraevés", () -> {
            Bug bug = view.GetSelection().GetBug();
            Spore spore = view.GetSelection().GetSpore();
            if(bug != null && spore != null) {
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null && bugOwner == bug.GetOwner()) {
                    view.GetSelection().SelectSpore(null);
                    bugOwner.Eat(bug, spore);

                    UpdateActionPoints();
                    UpdateButtonTexts();
                    UpdateEntityInfo(view.GetObjectStore().FindEntityView(bug));

                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a gombafonal elvágása gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreateBugCutButton(){
        return CreateActionButton("Gombafonal elvágása", () -> {
            var bug = view.GetSelection().GetBug();
            var shroomThread = view.GetSelection().GetShroomThread();
            if(bug != null && shroomThread != null) {
                IBugOwnerController bugOwner = controller.GetCurrentBugOwnerController();
                if(bugOwner != null && bugOwner == bug.GetOwner() && bug.GetCanCut()) {
                    bugOwner.CutThread(bug, shroomThread);
                    UpdateActionPoints();
                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a spóra köpése gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreateThrowSporeButton() {
        return CreateActionButton("Spóraköpés", () -> {
            List<Tecton> tectons = view.GetSelection().GetTectons();
            ShroomBody body = view.GetSelection().GetShroomBody();

            if(!tectons.isEmpty()) {
                Tecton destination = tectons.get(tectons.size() - 1);
                IShroomController shroomOwner = controller.GetCurrentShroomController();

                if(shroomOwner != null && body != null) {
                    shroomOwner.ThrowSpore(body, destination);
                    UpdateActionPoints();
                    UpdateEntityInfo(view.GetObjectStore().FindEntityView(body));
                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a rovar gombafonallal való elfogyasztása gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreateShroomThreadDevourButton(){
        return CreateActionButton("Rovar elfogyasztása gombafonallal", () -> {
            var shroomThread = view.GetSelection().GetShroomThread();
            var bug = view.GetSelection().GetBug();

            if (shroomThread != null && bug != null) {
                var shroom = controller.GetCurrentShroomController();

                if (shroomThread.GetShroom() == view.GetController().GetCurrentShroomController()) {
                    shroom.ShroomThreadDevourBug(shroomThread, bug);

                    UpdateActionPoints();
                    UpdateButtonTexts();

                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a gombatest elhelyezése gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreatePlaceShroomBodyButton() {
        return CreateActionButton("Gombatest elhelyezése", () -> {
            List<Tecton> tectons = view.GetSelection().GetTectons();

            if(!tectons.isEmpty()) {
                Tecton destination = tectons.get(tectons.size() - 1);
                IShroomController shroomOwner = controller.GetCurrentShroomController();

                if(shroomOwner != null) {
                    shroomOwner.PlaceShroomBody(destination);
                    UpdateActionPoints();
                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a gombatest fejlesztése gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreateUpgradeShroomBodyButton(){
        return CreateActionButton("Gombatest fejlesztése", () -> {
            var shroomBody = view.GetSelection().GetShroomBody();
            
            if (shroomBody != null) {
                var shroom = controller.GetCurrentShroomController();
                
                if (shroom != null && shroomBody.GetShroom() == shroom) {
                    shroom.UpgradeShroomBody(shroomBody);

                    UpdateActionPoints();
                    UpdateEntityInfo(view.GetObjectStore().FindEntityView(shroomBody));

                    return true;
                }
            }

            return false;
        });
    }

    /**
     * Létrehozza a gombafonal növesztése gombot
     * @return JButton - A létrehozott akciógomb példánya
     */
    public JButton CreatePlaceShroomThreadButton(){
        return CreateActionButton("Gombafonal növesztése", () -> {
            var tectons = view.GetSelection().GetTectons();

            if (tectons.size() == 2) {
                Tecton t1 = tectons.get(0);
                Tecton t2 = tectons.get(1);

                var shroom = controller.GetCurrentShroomController();
                
                if (shroom != null) {
                    shroom.PlaceShroomThread(t1, t2);
                    UpdateActionPoints();
                    return true;
                }
            }

            return false;
        });
    }
}
