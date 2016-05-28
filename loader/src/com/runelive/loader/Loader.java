package com.runelive.loader;

import javax.swing.*;
import java.awt.*;

public final class Loader {

    private static final Color LOADING_COLOR = new Color(88, 170, 231);
    private static final Color LOADING_COLOR_GLOSS = new Color(92, 178, 244);
    private static final Font LOADING_FONT = new Font("Helvetica", Font.BOLD, 12);
    private static String loadingString = "Loading...";
    private static int loadingPercent;
    private static JFrame frame;

    public static void main(String[] args) {
        buildFrame();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (index <= 10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateProgress("Step " + index, index * 10);
                    index++;
                }
            }
        }).start();
    }

    public static void updateProgress(String str, int percent) {
        loadingString = str;
        loadingPercent = percent;
        frame.getContentPane().repaint();
        frame.repaint();
    }

    private static void buildFrame() {
        frame = new JFrame("RuneLive");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(LOADING_COLOR);
                int fillWidth = (int) ((getWidth() * loadingPercent) / 100D);
                g.fillRect(0, 0, fillWidth, getHeight());

                g.setColor(LOADING_COLOR_GLOSS);
                g.fillRect(0, 0, fillWidth, getHeight() / 2);

                g.setFont(LOADING_FONT);

                g.setColor(Color.BLACK);
                g.drawString(loadingString, getWidth() / 2 - getFontMetrics(LOADING_FONT).stringWidth(loadingString) / 2 + 1, getHeight() / 2 + 4 + 1);
                g.setColor(Color.WHITE);
                g.drawString(loadingString, getWidth() / 2 - getFontMetrics(LOADING_FONT).stringWidth(loadingString) / 2, getHeight() / 2 + 4);
            }
        };

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        Dimension size = new Dimension(400, 75);
        frame.setMinimumSize(size);
        frame.setSize(size);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.toFront();
    }

}
