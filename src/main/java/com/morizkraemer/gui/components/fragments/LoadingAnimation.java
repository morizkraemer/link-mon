package com.morizkraemer.gui.components.fragments;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadingAnimation extends JPanel {
    private double angle = 0; // Angle for rotation
    private long lastTime = System.nanoTime(); // Time tracking for smooth motion
    private final ScheduledExecutorService executor;
    private Color color;

    public LoadingAnimation(Color color) {
        setPreferredSize(new Dimension(50, 50));
        setOpaque(false); // Transparent background
        this.color = color;

        // Start animation
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            long currentTime = System.nanoTime();
            double deltaTime = (currentTime - lastTime) / 1_000_000_000.0; // Convert to seconds
            lastTime = currentTime;

            angle += 360 * deltaTime / 1.5; // Smooth rotation speed
            if (angle >= 360) angle -= 360;

            repaint();
        }, 0, 16, TimeUnit.MILLISECONDS); // ~60 FPS
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight());
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Save original transformation
        AffineTransform oldTransform = g2d.getTransform();

        // Move origin to center, then rotate, then draw
        g2d.translate(centerX, centerY);
        g2d.rotate(Math.toRadians(angle));

        // Draw arc centered at (0,0) after shifting
        int arcSize = size / 2;
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(color);
        g2d.drawArc(-arcSize / 2, -arcSize / 2, arcSize, arcSize, 0, 270);

        // Restore original transform
        g2d.setTransform(oldTransform);
        g2d.dispose();

        // Sync with screen refresh to prevent tearing
        Toolkit.getDefaultToolkit().sync();
    }
}
