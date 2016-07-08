package org.runelive.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.runelive.Configuration;

@Deprecated
final class GameShell extends JFrame {

	private static final long serialVersionUID = 1L;

	final GameRenderer applet;

	@Deprecated
	public GameShell(GameRenderer applet, int width, int height, boolean undecorative, boolean resizable) {
		this.applet = applet;
		setTitle("" + Configuration.CLIENT_NAME + "");
		setFocusTraversalKeysEnabled(false);
		setUndecorated(undecorative);
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem("Test"));
		bar.add(menu);
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.add(this.applet, BorderLayout.CENTER);
		setResizable(resizable);
		setVisible(true);
		Insets insets = getInsets();
		setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
		setLocationRelativeTo(null);
		requestFocus();
		toFront();
		setBackground(Color.BLACK);
		// setClientIcon();
	}

	@Deprecated
	public void setClientIcon() {
	}

	@Override
	public Graphics getGraphics() {
		if (true) {
			return this.applet.getGraphics();
		}
		Graphics g = super.getGraphics();
		Insets insets = getInsets();
		g.translate(insets.left, insets.top);
		return g;
	}

	/*
	 * @Override public void paint(Graphics g) { applet.paint(g); }
	 * 
	 * @Override public void update(Graphics g) { applet.update(g); }
	 */

}