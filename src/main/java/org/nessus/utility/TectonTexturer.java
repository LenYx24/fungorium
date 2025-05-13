package org.nessus.utility;

import org.nessus.model.tecton.DesertTecton;
import org.nessus.model.tecton.InfertileTecton;
import org.nessus.model.tecton.SingleThreadTecton;
import org.nessus.model.tecton.Tecton;
import org.nessus.model.tecton.ThreadSustainerTecton;
import org.nessus.view.entityviews.TectonView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.nessus.utility.ImageReader.GetImage;

public class TectonTexturer {
    private TectonView view = null;

    public TectonView GetResult() {
        return view;
    }

    public void Visit(Tecton tecton) {
        view = new TectonView(tecton, GetImage("default_tecton"));
    }

    public void Visit(DesertTecton tecton) {
        view = new TectonView(tecton, GetImage("desert_tecton"));
    }

    public void Visit(InfertileTecton tecton) {
        view = new TectonView(tecton, GetImage("infertile_tecton"));
    }

    public void Visit(ThreadSustainerTecton tecton) {
        view = new TectonView(tecton, GetImage("sustainer_tecton"));
    }

    public void Visit(SingleThreadTecton tecton) {
        view = new TectonView(tecton, GetImage("default_tecton"));
    }
}
