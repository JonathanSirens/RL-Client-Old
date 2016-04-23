package org.ikov.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.*;

import org.ikov.Configuration;

@Deprecated
final class GameShell extends JFrame {

	private static final long serialVersionUID = 1L;

	final GameRenderer applet;

	@Deprecated
	public GameShell(GameRenderer applet, int width, int height, boolean undecorative, boolean resizable) {
		this.applet = applet;
		setTitle(""+Configuration.CLIENT_NAME+"");
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
		//setClientIcon();
	}

	@Deprecated
	public void setClientIcon() {
			try {
				BufferedImage localBufferedImage = ImageIO.read(new URL("https://dl.dropboxusercontent.com/u/344464529/IKov/ikov_64.png"));
				setIconImage(localBufferedImage);
			} catch (Exception exception) {
			}
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

	/*@Override
	public void paint(Graphics g) {
		applet.paint(g);
	}

	@Override
	public void update(Graphics g) {
		applet.update(g);
	}*/

}