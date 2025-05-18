package org.nessus.utility.geometry;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.Map;

enum CameraAction {
    UP, DOWN, RIGHT, LEFT, ZOOM_IN, ZOOM_OUT
}

public class Camera extends KeyAdapter {
    private Vec2 cameraPos;
    private Map<CameraAction, Boolean> movement = new EnumMap<>(CameraAction.class);
    private double zoom = 1.0;

    public Camera(double centerX, double centerY) {
        cameraPos = new Vec2(centerX, centerY);

        movement.put(CameraAction.UP, false);
        movement.put(CameraAction.DOWN, false);
        movement.put(CameraAction.RIGHT, false);
        movement.put(CameraAction.LEFT, false);
        movement.put(CameraAction.ZOOM_IN, false);
        movement.put(CameraAction.ZOOM_OUT, false);
    }

    public Vec2 GetPos() {
        return cameraPos;
    }

    public double GetZoom() {
        return zoom;
    }

    public void Update() {
        double moveStep = 10;
        double zoomStep = 0.05;

        double minZoom = 0.5;
        double maxZoom = 5.0;

        if (movement.get(CameraAction.UP).equals(Boolean.TRUE))
            cameraPos.y -= moveStep;
        if (movement.get(CameraAction.DOWN).equals(Boolean.TRUE))
            cameraPos.y += moveStep;
        if (movement.get(CameraAction.LEFT).equals(Boolean.TRUE))
            cameraPos.x -= moveStep;
        if (movement.get(CameraAction.RIGHT).equals(Boolean.TRUE))
            cameraPos.x += moveStep;
        if (movement.get(CameraAction.ZOOM_IN).equals(Boolean.TRUE))
            zoom = Math.min(zoom + zoomStep, maxZoom);
        if (movement.get(CameraAction.ZOOM_OUT).equals(Boolean.TRUE))
            zoom = Math.max(zoom - zoomStep, minZoom);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> movement.replace(CameraAction.LEFT, true);
            case KeyEvent.VK_D -> movement.replace(CameraAction.RIGHT, true);
            case KeyEvent.VK_W -> movement.replace(CameraAction.UP, true);
            case KeyEvent.VK_S -> movement.replace(CameraAction.DOWN, true);
            case KeyEvent.VK_E -> movement.replace(CameraAction.ZOOM_IN, true);
            case KeyEvent.VK_Q -> movement.replace(CameraAction.ZOOM_OUT, true);
            default -> {}
        }
    }

    @Override public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> movement.replace(CameraAction.LEFT, false);
            case KeyEvent.VK_D -> movement.replace(CameraAction.RIGHT, false);
            case KeyEvent.VK_W -> movement.replace(CameraAction.UP, false);
            case KeyEvent.VK_S -> movement.replace(CameraAction.DOWN, false);
            case KeyEvent.VK_E -> movement.replace(CameraAction.ZOOM_IN, false);
            case KeyEvent.VK_Q -> movement.replace(CameraAction.ZOOM_OUT, false);
            default -> {}
        }
    }
}
