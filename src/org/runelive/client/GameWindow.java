package org.runelive.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.runelive.Configuration;

final class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static GameRenderer applet;
	private static GameWindow instance;
	private static Dimension minimumSize;

	public static GameWindow getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		/*
		 * System.out.println("Checking client version...");
		 * ClientUpdate.checkVersion(); if(new
		 * File(Signlink.getCacheDirectory()).exists()) { if(!new
		 * File(Signlink.getCacheDirectory() +"versions").exists()) { new
		 * File(Signlink.getCacheDirectory() +"versions").mkdir(); }
		 * System.out.println("Checking map version...");
		 * MapUpdate.checkVersion(); System.out.println(
		 * "Checking sprite version..."); SpriteUpdate.checkVersion();
		 * System.out.println("Checking model version...");
		 * ModelsUpdate.checkVersion(); System.out.println(
		 * "Checking animation version..."); AnimationsUpdate.checkVersion(); }
		 */
		images = new Image[2];
		labels = new Image[7];
		try {
			images[0] = ImageIO
					.read(GameWindow.class.getResourceAsStream("/org/runelive/client/resources/nav_button.png"));
			images[1] = ImageIO
					.read(GameWindow.class.getResourceAsStream("/org/runelive/client/resources/nav_hover.png"));
			for (int tab = 0; tab < 7; tab++) {
				labels[tab] = ImageIO.read(
						GameWindow.class.getResourceAsStream("/org/runelive/client/resources/label_" + tab + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		icons = new ArrayList<>();
		try {
			icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/runelive/client/resources/16x16.png")));
			icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/runelive/client/resources/32x32.png")));
			icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/runelive/client/resources/64x64.png")));
			icons.add(ImageIO.read(GameWindow.class.getResourceAsStream("/org/runelive/client/resources/128x128.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		instance = new GameWindow(new Client(), 765, 503, false, false);
		minimumSize = instance.getSize();
		instance.applet.init();
		instance.setAlwaysOnTop(true);
		instance.toFront();
		instance.requestFocus();
		instance.setAlwaysOnTop(false);
	}

	public static void setFixed() {
		instance.dispose();
		instance = new GameWindow(applet, 765, 503, false, false);
		instance.setLocationRelativeTo(null);
	}

	public static void setResizable() {
		if (instance.isUndecorated()) {
			instance.dispose();
			instance = new GameWindow(applet, minimumSize.width + 20, minimumSize.height + 47, false, true);
		} else {
			instance.setMinimumSize(new Dimension(minimumSize.width + 20, minimumSize.height + 47));
			instance.setSize(new Dimension(minimumSize.width + 20, minimumSize.height + 47));
			instance.setResizable(true);
		}
		instance.setLocationRelativeTo(null);
	}

	public static void setFullscreen() {
		instance.dispose();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		instance = new GameWindow(applet, size.width, size.height, true, false);
	}

	private static Image[] images;
	private static Image[] labels;
	private static java.util.List<Image> icons;

	private int hoverIndex = -1;

	public GameWindow(GameRenderer applet, int width, int height, boolean undecorative, boolean resizable) {
		this.applet = applet;
		this.applet.setFocusTraversalKeysEnabled(false);
		this.setTitle("" + Configuration.CLIENT_NAME + "");
		this.setFocusTraversalKeysEnabled(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(undecorative);
		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		this.setIconImages(icons);

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
		this.add(panel, BorderLayout.NORTH);

		this.add(this.applet, BorderLayout.CENTER);

		this.setResizable(resizable);
		this.setVisible(true);

		if (undecorative) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			if (!this.isResizable()) {
				Insets insets = getInsets();
				this.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom + 44);
				if (minimumSize == null) {
					this.setMinimumSize(this.getSize());
				} else {
					this.setMinimumSize(new Dimension(minimumSize.width, minimumSize.height));
				}
			} else {
				this.setSize(new Dimension(minimumSize.width + 20, minimumSize.height + 47));
				this.setMinimumSize(new Dimension(minimumSize.width + 20, minimumSize.height + 47));
			}
			setLocationRelativeTo(null);
		}

		this.requestFocus();
		this.toFront();
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
			if (SwingUtilities.isLeftMouseButton(e)) {
				int x = (panel.getWidth() / 2 - (765 / 2));
				int tab = (e.getX() - x) / 109;
				if (tab > -1 && tab < Configuration.NAV_LINKS.length) {
					Client.launchURL(Configuration.NAV_LINKS[tab]);
				}
			}
		}
	}

}