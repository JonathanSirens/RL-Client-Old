package org.ikov.client;

import org.ikov.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

final class GameWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private final GameRenderer applet;
    private static GameWindow instance;

    public static GameWindow getInstance() {
        return instance;
    }

    public static void setFixed() {
        instance.setSize(instance.getMinimumSize());
        instance.setResizable(false);
        instance.setLocationRelativeTo(null);
    }

    public static void setResizable() {
        if (instance.isUndecorated()) {
            instance.setUndecorated(false);
        }
        instance.setSize(new Dimension(instance.getMinimumSize().width + 40, instance.getMinimumSize().height + 100));
        instance.setResizable(true);
        instance.setLocationRelativeTo(null);
    }

    public static void setFullscreen() {
        instance.setResizable(true);
        instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        instance = new GameWindow(new Client(), 765, 503, false, false);
        instance.applet.init();
    }

    private int hoverIndex = -1;

    public GameWindow(GameRenderer applet, int width, int height, boolean undecorative, boolean resizable) {
        this.applet = applet;
        setTitle("" + Configuration.CLIENT_NAME + "");
        setFocusTraversalKeysEnabled(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(undecorative);
        this.setLayout(new BorderLayout());

        final Image[] images = new Image[2];
        final Image[] labels = new Image[7];
        try {
            images[0] = ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/nav_button.png"));
            images[1] = ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/nav_hover.png"));
            for (int tab = 0; tab < 7; tab++) {
                labels[tab] = ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/label_" + tab + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                int x = (this.getWidth() / 2 - (765 / 2));
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), 41);

                for (int tab = 0; tab < 7; tab++) {
                    g.drawImage(hoverIndex == tab ? images[1] : images[0], x + (109 * tab), 0, null);
                    g.drawImage(labels[tab], x + (109 * tab), 0, null);
                }
            }
        };
        NavListener navListener = new NavListener(panel);
        panel.addMouseMotionListener(navListener);
        panel.addMouseListener(navListener);
        panel.setSize(new Dimension(765, 41));
        panel.setPreferredSize(new Dimension(765, 41));
        panel.setMinimumSize(new Dimension(765, 41));
        /*JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(new JMenuItem("Test"));
        bar.add(menu);*/
        this.add(panel, BorderLayout.NORTH);

        this.add(this.applet, BorderLayout.CENTER);
        setResizable(resizable);
        setVisible(true);
        Insets insets = getInsets();
        setSize(width + insets.left + insets.right, height + insets.top + insets.bottom + 41);
        setLocationRelativeTo(null);
        requestFocus();
        toFront();
        setBackground(Color.BLACK);
        setClientIcon();
        this.setMinimumSize(this.getSize());
    }

    public void setClientIcon() {
        try {
            //BufferedImage localBufferedImage = ImageIO.read(new URL("https://dl.dropboxusercontent.com/u/344464529/IKov/ikov_64.png"));
            //setIconImage(localBufferedImage);
            java.util.List<Image> images = new ArrayList<>();
            images.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/16x16.png")));
            images.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/32x32.png")));
            images.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/64x64.png")));
            images.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/ikov/client/resources/128x128.png")));
            this.setIconImages(images);
        } catch (Exception exception) {
        }
    }

    private final class NavListener extends MouseAdapter {

        private final JPanel panel;

        public NavListener(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            int x = (panel.getWidth() / 2 - (765 / 2));
            int hover = (e.getX() - x) / 109;
            if (hover != hoverIndex) {
                hoverIndex = hover;
                panel.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = (panel.getWidth() / 2 - (765 / 2));
            int hover = (e.getX() - x) / 109;
            if (hover != hoverIndex) {
                hoverIndex = hover;
                panel.repaint();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            int hover = hoverIndex;
            hoverIndex = -1;
            if (hover != hoverIndex) {
                panel.repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = (panel.getWidth() / 2 - (765 / 2));
            int tab = (e.getX() - x) / 109;
            if (tab > -1 && tab < Configuration.NAV_LINKS.length) {
                Client.launchURL(Configuration.NAV_LINKS[tab]);
            }
        }
    }

}