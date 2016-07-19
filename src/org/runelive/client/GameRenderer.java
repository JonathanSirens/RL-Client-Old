package org.runelive.client;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.runelive.Configuration;
import org.runelive.client.graphics.CacheSpriteLoader;
import org.runelive.client.graphics.CursorData;
import org.runelive.client.graphics.RSImageProducer;
import org.runelive.client.graphics.Sprite;
import org.runelive.client.graphics.gameframe.GameFrame;
import org.runelive.client.graphics.gameframe.GameFrame.ScreenMode;

public class GameRenderer extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener,
		FocusListener, WindowListener, MouseWheelListener {

	private static final long serialVersionUID = 1L;
	private final long[] aLongArray7;
	private int anInt4;
	public boolean awtFocus;
	private final int[] charQueue;
	private int clickMode1;
	private int clickMode2;
	public int clickMode3;
	protected int clickX;
	protected int clickY;
	private int delayTime;
	int fps;
	RSImageProducer fullGameScreen;
	public Graphics graphics;
	public int idleTime;
	final int[] keyArray;
	GameWindow mainFrame;
	int minDelay;
	public int mouseX;
	public int mouseY;
	int myHeight;
	int myWidth;
	private int readIndex;
	boolean resized;
	int saveClickX;
	int saveClickY;
	private boolean shouldClearScreen;
	private boolean shouldDebug;
	private int writeIndex;
	private long clickTime;
	protected boolean isApplet;
	public int forceWidth = -1;
	public int forceHeight = -1;
	public int offsetX = 0;
	public int offsetY = 0;

	void prepareGraphics() {
		while (graphics == null) {
			graphics = (isApplet ? this : mainFrame).getGraphics();
			try {
				getGameComponent().repaint();
			} catch (Exception _ex) {
			}

			try {
				// Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		}
		Font font = new Font("Serif", 1, 16);
		graphics.setFont(font);
		graphics.setColor(Color.white);
	}

	public int getScreenWidth() {
		if (forceWidth >= 0) {
			return forceWidth;
		}
		return getRealScreenWidth();
	}

	public int getScreenHeight() {
		if (forceHeight >= 0) {
			return forceHeight;
		}

		return getRealScreenHeight();
	}

	public int getRealScreenWidth() {
		Component component = getGameComponent();
		if (component == null) {
			return forceWidth >= 0 ? forceWidth : 765;
		}

		int w = component.getWidth();
		if (component instanceof java.awt.Container) {
			java.awt.Insets insets = ((java.awt.Container) component).getInsets();
			w -= insets.left + insets.right;
		}
		return w;
	}

	public int getRealScreenHeight() {
		Component component = getGameComponent();
		if (component == null) {
			return forceHeight >= 0 ? forceHeight : 503;
		}

		int h = component.getHeight();
		if (component instanceof java.awt.Container) {
			java.awt.Insets insets = ((java.awt.Container) component).getInsets();
			h -= insets.top + insets.bottom;
		}
		return h;
	}

	GameRenderer() {
		delayTime = 20;
		minDelay = 1;
		aLongArray7 = new long[10];
		shouldDebug = false;
		shouldClearScreen = true;
		awtFocus = true;
		keyArray = new int[128];
		charQueue = new int[128];
	}

	void cleanUpForQuit() {
	}

	final void initClientFrame(int width, int height) {
		try {
			isApplet = true;
			myWidth = width;
			myHeight = height;
			forceWidth = myWidth;
			forceHeight = myHeight;
			getGameComponent().setBackground(Color.black);
			updateGraphics(true);
			fullGameScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
			startRunnable(this, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final void createClientFrame(int w, int h) {
		isApplet = false;
		myWidth = w;
		myHeight = h;
		forceWidth = w;
		forceHeight = myHeight;
		mainFrame = new GameWindow(this, myWidth, myHeight, false, false);
		getGameComponent().setBackground(Color.black);
		updateGraphics(true);
		fullGameScreen = new RSImageProducer(myWidth, myHeight, getGameComponent());
		startRunnable(this, 1);
	}

	public void updateGraphics(boolean clear) {
		offsetX = (getRealScreenWidth() - getScreenWidth()) / 2;
		offsetY = (getRealScreenHeight() - getScreenHeight()) / 2;
		graphics = getGameComponent().getGraphics();
		if (clear) {
			graphics.clearRect(0, 0, getRealScreenWidth(), getRealScreenHeight());
		}

		graphics = graphics.create(offsetX, offsetY, myWidth, myHeight);
	}

	@Override
	public final void destroy() {
		anInt4 = -1;

		try {
			Thread.sleep(5000L);
		} catch (Exception _ex) {
		}

		if (anInt4 == -1) {
			exit();
		}
	}

	private static final Color FONT_COLOR = Color.white;
	private static final Font LOADING_FONT = new Font("Helvetica", 1, 13);
	private static final int barWidth = 300;
	private static final int barHeight = 30;
	private static final int barSpace = 2;
	private static final int barMax = barSpace + barHeight + barSpace + barWidth + barSpace + barHeight + barSpace;

	private static int currentLoadingColor = -1;
	private static int nextLoadingColor = -1;

	private Image loadingBuffer;

	private static void options(Graphics g) {
		try {
			if (g instanceof Graphics2D) {
				Graphics2D r = (Graphics2D) g;
				r.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				r.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
		} catch (Throwable t) {
		}
	}

	private static int randomColor() {
		int red = 0;
		int green = (int) (Math.random() * 17.0D);
		int blue = 0;

		green += 50 + (int) (Math.random() * 200.0D);

		return red << 16 | green << 8 | blue;
	}

	private long startTime = 0L;
	private long colorStart = 0L;

	private static int blend(int dst, int src, int src_alpha) {
		if (src_alpha <= 0) {
			return dst;
		}

		if (src_alpha >= 0xff) {
			return src;
		}

		int src_delta = 0xff - src_alpha;
		return ((0xff00ff00 & (0xff00ff & src) * src_alpha | 0xff0000 & (src & 0xff00) * src_alpha) >>> 8)
				+ ((0xff0000 & src_delta * (dst & 0xff00) | src_delta * (dst & 0xff00ff) & 0xff00ff00) >>> 8);
	}

	private static final Color BACKGROUND_COLOR = Color.black;

	void resetGraphic() {
		graphics = (isApplet ? this : mainFrame).getGraphics();
		try {
			getGameComponent().repaint();
		} catch (Exception _ex) {
		}
	}

	private void exit() {
		anInt4 = -2;
		cleanUpForQuit();

		if (mainFrame != null) {
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}

			try {
				System.exit(0);
			} catch (Throwable _ex) {
			}
		}
	}

	@Override
	public final void focusGained(FocusEvent event) {
		awtFocus = true;
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	@Override
	public final void focusLost(FocusEvent event) {
		awtFocus = false;

		for (int i = 0; i < 128; i++) {
			keyArray[i] = 0;
		}
	}

	public void recreateClientFrame(boolean decorative, int width, int height, boolean resizable) {
		Component component = getGameComponent();
		component.setBackground(Color.black);
		component.removeMouseWheelListener(this);
		component.removeMouseListener(this);
		component.removeMouseMotionListener(this);
		component.removeKeyListener(this);
		component.removeFocusListener(this);
		if (mainFrame != null) {
			mainFrame.removeWindowListener(this);
			mainFrame.setVisible(false);
			mainFrame.dispose();
			mainFrame = null;
		}
		if (!isApplet || decorative) {
			mainFrame = new GameWindow(this, width, height, decorative, resizable);
			mainFrame.addWindowListener(this);
		}
		if (resizable && decorative) {
			if (!isApplet) {
				mainFrame.setMinimumSize(new Dimension(800, 600));
			}
		} else if (!resizable) {
			if (!isApplet) {
				mainFrame.setMinimumSize(new Dimension(765, 503));
			} else {
				setMinimumSize(new Dimension(765, 503));
			}
		}
		component = getGameComponent();
		component.setBackground(Color.black);
		component.addMouseWheelListener(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addKeyListener(this);
		component.addFocusListener(this);
		mouseX = mouseY = -1;
	}

	private int getChildHeight(RSInterface Interface, int Index) {
		return RSInterface.interfaceCache[Interface.children[Index]].height;
	}

	private int getChildWidth(RSInterface Interface, int Index) {
		return RSInterface.interfaceCache[Interface.children[Index]].width;
	}

	public Component getGameComponent() {
		if (mainFrame != null) {
			return mainFrame;
		} else {
			return this;
		}
	}

	private void interfaceScrollCheck(MouseWheelEvent e) {
		try {
			int Rotation = e.getWheelRotation();
			int TAB = 0, WINDOW = 1;
			int POSX = 0, POSY = 1, WIDTH = 2, HEIGHT = 3, OFFX = 4, OFFY = 5, CHILD = 6, SENSITIVITY = 7;
			int tabInterfaceID = Client.tabInterfaceIDs[Client.tabID];
			if (tabInterfaceID == 11000)
				tabInterfaceID = 1151;
			int[] InterfaceID = { tabInterfaceID, Client.openInterfaceID };
			int[] InterfaceSetting = { 0, 0, /* positionX, positionY */
					0, 0, /* Width, Height */
					offsetX, offsetY, /* offsetX, offsetY */
					0, 15 /* Child ID, Sensitivity */
			};
			int[] IS = InterfaceSetting;

			if (InterfaceID[TAB] != -1) {
				RSInterface Tab = RSInterface.interfaceCache[InterfaceID[TAB]];
				if (Tab == null || Tab.children == null) {
					return;
				}
				InterfaceSetting[OFFX] = getRealScreenWidth() - 218;
				InterfaceSetting[OFFY] = getRealScreenHeight() - 298;

				for (int Index = 0; Index < Tab.children.length; Index++) {
					if (RSInterface.interfaceCache[Tab.children[Index]].scrollMax > 0) {
						InterfaceSetting[CHILD] = Index;
						InterfaceSetting[POSX] = Tab.childX[Index];
						InterfaceSetting[POSY] = Tab.childY[Index];
						InterfaceSetting[WIDTH] = getChildWidth(Tab, Index);
						InterfaceSetting[HEIGHT] = getChildHeight(Tab, Index);
						break;
					}
				}
				if (mouseX > IS[OFFX] + IS[POSX] && mouseY > IS[OFFY] + IS[POSY]
						&& mouseX < IS[OFFX] + IS[POSX] + IS[WIDTH] && mouseY < IS[OFFY] + IS[POSY] + IS[HEIGHT]) {
					canZoom = false;
				} else {
					canZoom = true;
				}
				if (mouseX > IS[OFFX] + IS[POSX] && mouseY > IS[OFFY] + IS[POSY]
						&& mouseX < IS[OFFX] + IS[POSX] + IS[WIDTH] && mouseY < IS[OFFY] + IS[POSY] + IS[HEIGHT]) {
					switch (InterfaceID[TAB]) {
					case 962: /* Music Tab */
						IS[SENSITIVITY] = 30;
						break;

					case 638: /* Quest Tab */
						IS[SENSITIVITY] = 30;
						break;

					case 1151: /* Magic Tab */
						IS[SENSITIVITY] = 7;
						break;

					case 147: /* Emote Tab */
						IS[SENSITIVITY] = 15;
						break;

					default:
						IS[SENSITIVITY] = 15;
						break;
					}

					switch (Rotation) {
					case -1:
						if (RSInterface.interfaceCache[Tab.children[IS[CHILD]]].scrollPosition != 0) {
							RSInterface.interfaceCache[Tab.children[IS[CHILD]]].scrollPosition += Rotation
									* IS[SENSITIVITY];
							// Client.needDrawTabArea = true;
							Client.tabAreaAltered = true;
						}
						break;

					case 1:
						if (RSInterface.interfaceCache[Tab.children[IS[CHILD]]].scrollPosition != RSInterface.interfaceCache[Tab.children[IS[CHILD]]].scrollMax
								- RSInterface.interfaceCache[Tab.children[IS[CHILD]]].height) {
							RSInterface.interfaceCache[Tab.children[IS[CHILD]]].scrollPosition += Rotation
									* IS[SENSITIVITY];
							// Client.needDrawTabArea = true;
							Client.tabAreaAltered = true;
						}
					}
				}
			}

			if (InterfaceID[WINDOW] != -1) {
				int w = 512, h = 334;
				int x = (Client.clientWidth / 2) - 256;
				int y = (Client.clientHeight / 2) - 167;
				int count = 4;
				if (GameFrame.getScreenMode() != ScreenMode.FIXED) {
					for (int i = 0; i < count; i++) {
						if (x + w > (Client.clientWidth - 225)) {
							x = x - 30;
							if (x < 0) {
								x = 0;
							}
						}
						if (y + h > (Client.clientHeight - 182)) {
							y = y - 30;
							if (y < 0) {
								y = 0;
							}
						}
					}
				}
				RSInterface Window = RSInterface.interfaceCache[InterfaceID[WINDOW]];
				if (Client.openInterfaceID == 5292) {
					InterfaceSetting[OFFX] = GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : (Client.clientWidth / 2) - 356;
					InterfaceSetting[OFFY] = GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : (Client.clientHeight / 2) - 230;
				} else {
					InterfaceSetting[OFFX] = GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : x;
					InterfaceSetting[OFFY] = GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : y;
				}

				for (int Index = 0; Index < Window.children.length; Index++) {
					if (RSInterface.interfaceCache[Window.children[Index]].scrollMax > 0) {
						InterfaceSetting[CHILD] = Index;
						InterfaceSetting[POSX] = Window.childX[Index];
						InterfaceSetting[POSY] = Window.childY[Index];
						InterfaceSetting[WIDTH] = getChildWidth(Window, Index);
						InterfaceSetting[HEIGHT] = getChildHeight(Window, Index);
						break;
					}
				}
				if (mouseX > IS[OFFX] + IS[POSX] && mouseY > IS[OFFY] + IS[POSY]
						&& mouseX < IS[OFFX] + IS[POSX] + IS[WIDTH] && mouseY < IS[OFFY] + IS[POSY] + IS[HEIGHT]) {
					canZoom = false;
				} else {
					canZoom = true;
				}
				if (mouseX > IS[OFFX] + IS[POSX] && mouseY > IS[OFFY] + IS[POSY]
						&& mouseX < IS[OFFX] + IS[POSX] + IS[WIDTH] && mouseY < IS[OFFY] + IS[POSY] + IS[HEIGHT]) {

					switch (InterfaceID[WINDOW]) {
					default:
						IS[SENSITIVITY] = 30;
						break;
					}

					switch (Rotation) {
					case -1:
						if (RSInterface.interfaceCache[Window.children[IS[CHILD]]].scrollPosition != 0) {
							RSInterface.interfaceCache[Window.children[IS[CHILD]]].scrollPosition += Rotation
									* IS[SENSITIVITY];
							// Client.needDrawTabArea = true;
							Client.tabAreaAltered = true;
						}
						break;

					case 1:
						if (RSInterface.interfaceCache[Window.children[IS[CHILD]]].scrollPosition != RSInterface.interfaceCache[Window.children[IS[CHILD]]].scrollMax
								- RSInterface.interfaceCache[Window.children[IS[CHILD]]].height) {
							RSInterface.interfaceCache[Window.children[IS[CHILD]]].scrollPosition += Rotation
									* IS[SENSITIVITY];
						}
					}

					RSInterface.interfaceCache[Window.children[InterfaceSetting[CHILD]]].scrollPosition += Rotation
							* 30;
				}
			}
			if (Client.getClient().inputDialogState == 3 && Client.getClient().getGrandExchange().searching) {
				Client.getClient().getGrandExchange().itemResultScrollPos += Rotation * 10;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public final void keyPressed(KeyEvent keyevent) {
		idleTime = 0;
		int keyCode = keyevent.getKeyCode();
		int keyChar = keyevent.getKeyChar();

		if (keyChar == 96) {
			Client.consoleOpen = !Client.consoleOpen;
		}

		// System.out.println(keyCode + " : " + keyChar);
		if (!Configuration.NEW_FUNCTION_KEYS) {
			if (keyCode == KeyEvent.VK_ESCAPE) {
				Client.setTab(14);
			} else if (keyCode == KeyEvent.VK_F1) {
				Client.setTab(0);
			} else if (keyCode == KeyEvent.VK_F2) {
				Client.setTab(1);
			} else if (keyCode == KeyEvent.VK_F3) {
				Client.setTab(2);
			} else if (keyCode == KeyEvent.VK_F4) {
				Client.setTab(3);
			} else if (keyCode == KeyEvent.VK_F5) {
				Client.setTab(4);
			} else if (keyCode == KeyEvent.VK_F6) {
				Client.setTab(5);
			} else if (keyCode == KeyEvent.VK_F7) {
				Client.setTab(6);
			} else if (keyCode == KeyEvent.VK_F8) {
				Client.setTab(7);
			} else if (keyCode == KeyEvent.VK_F9) {
				Client.setTab(8);
			} else if (keyCode == KeyEvent.VK_F10) {
				Client.setTab(9);
			} else if (keyCode == KeyEvent.VK_F11) {
				Client.setTab(10);
			} else if (keyCode == KeyEvent.VK_F12) {
				Client.setTab(11);
			}
		} else {
			if (keyCode == KeyEvent.VK_ESCAPE) {
				Client.setTab(14);
			} else if (keyCode == KeyEvent.VK_F1) {
				Client.setTab(3);
			} else if (keyCode == KeyEvent.VK_F2) {
				Client.setTab(4);
			} else if (keyCode == KeyEvent.VK_F3) {
				Client.setTab(5);
			} else if (keyCode == KeyEvent.VK_F4) {
				Client.setTab(6);
			} else if (keyCode == KeyEvent.VK_F5) {
				Client.setTab(0);
			} else if (keyCode == KeyEvent.VK_F6) {
				Client.setTab(1);
			} else if (keyCode == KeyEvent.VK_F7) {
				Client.setTab(2);
			} else if (keyCode == KeyEvent.VK_F8) {
				Client.setTab(7);
			} else if (keyCode == KeyEvent.VK_F9) {
				Client.setTab(8);
			} else if (keyCode == KeyEvent.VK_F10) {
				Client.setTab(9);
			} else if (keyCode == KeyEvent.VK_F11) {
				Client.setTab(11);
			} else if (keyCode == KeyEvent.VK_F12) {
				Client.setTab(12);
			}
		}

		if (keyChar < 30) {
			keyChar = 0;
		}

		if (keyCode == 37) {
			keyChar = 1;
		}

		if (keyCode == 39) {
			keyChar = 2;
		}

		if (keyCode == 38) {
			keyChar = 3;
		}

		if (keyCode == 40) {
			keyChar = 4;
		}

		if (keyCode == 17) {
			keyChar = 5;
		}

		if (keyCode == 8) {
			keyChar = 8;
		}

		if (keyCode == 127) {
			keyChar = 8;
		}

		if (keyCode == 9) {
			keyChar = 9;
		}

		if (keyCode == 10) {
			keyChar = 10;
		}

		if (keyCode >= 112 && keyCode <= 123) {
			keyChar = 1008 + keyCode - 112;
		}

		if (keyCode == 36) {
			keyChar = 1000;
		}

		if (keyCode == 35) {
			keyChar = 1001;
		}

		if (keyCode == 33) {
			keyChar = 1002;
		}

		if (keyCode == 34) {
			keyChar = 1003;
		}

		if (keyChar > 0 && keyChar < 128) {
			keyArray[keyChar] = 1;
		}

		if (keyChar > 4) {
			charQueue[writeIndex] = keyChar;
			writeIndex = writeIndex + 1 & 0x7f;
		}
	}

	@Override
	public final void keyReleased(KeyEvent keyevent) {
		idleTime = 0;
		int keyCode = keyevent.getKeyCode();
		char keyChar = keyevent.getKeyChar();

		if (keyChar < '\036') {
			keyChar = '\0';
		}

		if (keyCode == 37) {
			keyChar = '\001';
		}

		if (keyCode == 39) {
			keyChar = '\002';
		}

		if (keyCode == 38) {
			keyChar = '\003';
		}

		if (keyCode == 40) {
			keyChar = '\004';
		}

		if (keyCode == 17) {
			keyChar = '\005';
		}

		if (keyCode == 8) {
			keyChar = '\b';
		}

		if (keyCode == 127) {
			keyChar = '\b';
		}

		if (keyCode == 9) {
			keyChar = '\t';
		}

		if (keyCode == 10) {
			keyChar = '\n';
		}

		if (keyChar > 0 && keyChar < '\200') {
			keyArray[keyChar] = 0;
		}
	}

	@Override
	public final void keyTyped(KeyEvent keyevent) {

	}

	final void method4(int i) {
		delayTime = 1000 / i;
	}

	@Override
	public final void mouseClicked(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();

		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		x -= offsetX;
		y -= offsetY;
		idleTime = 0;
		//System.out.println("Mouse x: "+x);
		//System.out.println("Mouse y: "+y);
		clickX = x;
		clickY = y;
	}

	@Override
	public final void mouseDragged(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();

		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}

		x -= offsetX;
		y -= offsetY;

		if (mouseWheelDown) {
			y = mouseWheelX - mouseevent.getX();
			int k = mouseWheelY - mouseevent.getY();
			mouseWheelDragged(y, -k);
			mouseWheelX = mouseevent.getX();
			mouseWheelY = mouseevent.getY();
			return;
		}

		if (x < 0 || y < 0) {
			return;
		}
		if (System.currentTimeMillis() - clickTime >= 250L || Math.abs(saveClickX - x) > 5
				|| Math.abs(saveClickY - y) > 5) {
			idleTime = 0;
			mouseX = x;
			mouseY = y;
		}
	}

	@Override
	public final void mouseEntered(MouseEvent mouseevent) {
	}

	@Override
	public final void mouseExited(MouseEvent mouseevent) {
		if (idleTime > 0) {
			idleTime = 0;
		}
		mouseX = -1;
		mouseY = -1;
	}

	@Override
	public final void mouseMoved(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();

		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		x -= offsetX;
		y -= offsetY;
		if (x < 0 || y < 0) {
			return;
		}
		if (System.currentTimeMillis() - clickTime >= 250L || Math.abs(saveClickX - x) > 5
				|| Math.abs(saveClickY - y) > 5) {
			idleTime = 0;
			mouseX = x;
			mouseY = y;
		}
	}

	public int mouseWheelX;
	public int mouseWheelY;
	public boolean mouseWheelDown;

	@Override
	public final void mousePressed(MouseEvent mouseevent) {
		int x = mouseevent.getX();
		int y = mouseevent.getY();

		if (mainFrame != null) {
			Insets insets = mainFrame.getInsets();
			x -= insets.left;// 4
			y -= insets.top;// 22
		}
		x -= offsetX;
		y -= offsetY;
		idleTime = 0;
		clickX = x;
		clickY = y;
		clickTime = System.currentTimeMillis();
		// wheel
		int type = mouseevent.getButton();
		if (type == 2) {
			mouseWheelDown = true;
			mouseWheelX = x;
			mouseWheelY = y;
			return;
		}
		// wheel

		if (mouseevent.isMetaDown()) {
			clickMode1 = 2;
			setClickMode2(2);
		} else {
			clickMode1 = 1;
			setClickMode2(1);
		}
	}

	@Override
	public final void mouseReleased(MouseEvent e) {
		idleTime = 0;
		mouseWheelDown = false;
		setClickMode2(0);
	}

	/*public void mouseWheelMoved(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		int xPos = Client.instance.chatArea.getxPos();
		int yPos = Client.instance.chatArea.getyPos();
		interfaceScrollCheck(e);

		if (mouseX > xPos && mouseX < xPos + 512 && mouseY > yPos && mouseY < yPos + 140) {
			int ChatPosition = Client.anInt1089;
			ChatPosition -= rotation * 28;

			if (ChatPosition < 0) {
				ChatPosition = 0;
			}

			if (ChatPosition > Client.anInt1211 - 77) {
				ChatPosition = Client.anInt1211 - 77;
			}

			if (Client.anInt1089 != ChatPosition) {
				Client.anInt1089 = ChatPosition;
				Client.instance.setInputTaken(true);
			}
		} else if (Client.instance.loggedIn) {
			boolean zoom = GameFrame.getScreenMode() == ScreenMode.FIXED ? (mouseX < 512) : (mouseX < Client.clientWidth - 200);
			if (zoom && Client.openInterfaceID == -1) {
				Client.clientZoom += rotation * 30;

				if (rotation == -1) {
					if (Client.clientZoom > 200) {
						Client.clientZoom -= 15;
					}
				} else {
					if (Client.clientZoom < 900) {
						Client.clientZoom += 15;
					}
				}
			}
			Client.instance.setInputTaken(true);
		}
	}*/
	public boolean canZoom = true;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		int rotation = event.getWheelRotation();
		interfaceScrollCheck(event);
		if(mouseX > 0 && mouseX < 512 && mouseY > Client.clientHeight - 165 && mouseY < Client.clientHeight - 25) {
			int scrollPos = Client.anInt1089;
			scrollPos -= rotation * 30;		
			if(scrollPos < 0)
				scrollPos = 0;
			if(scrollPos > Client.anInt1211 - 110)
				scrollPos = Client.anInt1211 - 110;
			if(Client.anInt1089 != scrollPos) {
				Client.anInt1089 = scrollPos;
				Client.instance.setInputTaken(true);
			}
		}
		if (Client.openInterfaceID == -1) {
			if (canZoom) {
				if(mouseX > 0 && mouseX < 512 && mouseY > Client.clientHeight - 165 && mouseY < Client.clientHeight - 25) {
					return;
				}
				if (rotation == -1) {
					if (Client.clientZoom > 200) {
						Client.clientZoom -= 15;
					}
				} else {
					if (Client.clientZoom < 900) {
						Client.clientZoom += 15;
					}
				}
			}
		}
		Client.instance.setInputTaken(true);
	}

	@Override
	public final void paint(Graphics g) {
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	void mouseWheelDragged(int param1, int param2) {

	}

	void processDrawing() {
	}

	void processGameLoop() {
	}

	void raiseWelcomeScreen() {
	}

	final int readChar(int dummy) {
		// while (dummy >= 0)
		// {
		// for (int j = 1; j > 0; j++)
		// {
		// }
		// }
		int k = -1;

		if (writeIndex != readIndex) {
			k = charQueue[readIndex];
			readIndex = readIndex + 1 & 0x7f;
		}

		return k;
	}

	/*
	 * void rebuildFrame(boolean undecorated, int width, int height, boolean
	 * resizable, boolean full) { setMinimumSize(new
	 * Dimension(GameFrame.getScreenMode() == ScreenMode.FIXED ? 765 :
	 * GameFrameConstants.minWidth, GameFrame.getScreenMode() ==
	 * ScreenMode.FIXED ? 503 : GameFrameConstants.minHeight)); boolean
	 * createdByApplet = ((GameFrame.getScreenMode() == ScreenMode.FIXED) &&
	 * (mainFrame == null)) || ((mainFrame == null) && !full); myWidth = width;
	 * myHeight = height;
	 *
	 * if (mainFrame != null) { mainFrame.dispose(); }
	 *
	 * if (!createdByApplet) { mainFrame = new RSFrame(this, width, height,
	 * undecorated, resizable); mainFrame.addWindowListener(this); }
	 *
	 * graphics = (createdByApplet ? this : mainFrame).getGraphics();
	 *
	 * if (!createdByApplet) { getGameComponent().addMouseWheelListener(this);
	 * getGameComponent().addMouseListener(this);
	 * getGameComponent().addMouseMotionListener(this);
	 * getGameComponent().addKeyListener(this);
	 * getGameComponent().addFocusListener(this);
	 * getGameComponent().addComponentListener(this); } }
	 */

	@Override
	public void run() {
		try {
			getGameComponent().addMouseWheelListener(this);
			getGameComponent().addMouseListener(this);
			getGameComponent().addMouseMotionListener(this);
			getGameComponent().addKeyListener(this);
			getGameComponent().addFocusListener(this);

			if (mainFrame != null) {
				mainFrame.addWindowListener(this);
			}
			startUp();
			updateGraphics(true);
			int i = 0;
			int j = 256;
			int k = 1;
			int i1 = 0;
			int j1 = 0;

			for (int k1 = 0; k1 < 10; k1++) {
				aLongArray7[k1] = System.currentTimeMillis();
			}

			while (anInt4 >= 0) {
				if (anInt4 > 0) {
					anInt4--;

					if (anInt4 == 0) {
						exit();
						return;
					}
				}

				int i2 = j;
				int j2 = k;
				j = 300;
				k = 1;
				long l1 = System.currentTimeMillis();

				if (aLongArray7[i] == 0L) {
					j = i2;
					k = j2;
				} else if (l1 > aLongArray7[i]) {
					j = (int) (2560 * delayTime / (l1 - aLongArray7[i]));
				}

				if (j < 25) {
					j = 25;
				}

				if (j > 256) {
					j = 256;
					k = (int) (delayTime - (l1 - aLongArray7[i]) / 10L);
				}

				if (k > delayTime) {
					k = delayTime;
				}

				aLongArray7[i] = l1;
				i = (i + 1) % 10;

				if (k > 1) {
					for (int k2 = 0; k2 < 10; k2++) {
						if (aLongArray7[k2] != 0L) {
							aLongArray7[k2] += k;
						}
					}
				}

				if (k < minDelay) {
					k = minDelay;
				}

				try {
					Thread.sleep(k);
				} catch (InterruptedException _ex) {
					j1++;
				}

				for (; i1 < 256; i1 += j) {
					clickMode3 = clickMode1;
					saveClickX = clickX;
					saveClickY = clickY;
					clickMode1 = 0;
					processGameLoop();
					readIndex = writeIndex;
				}

				i1 &= 0xFF;

				if (delayTime > 0) {
					fps = 1000 * j / (delayTime * 256);
				}
				updateGraphics(false);
				processDrawing();

				if (shouldDebug) {
					System.out.println("ntime:" + l1);

					for (int l2 = 0; l2 < 10; l2++) {
						int i3 = (i - l2 - 1 + 20) % 10;
						System.out.println("otim" + i3 + ":" + aLongArray7[i3]);
					}

					System.out.println("fps:" + fps + " ratio:" + j + " count:" + i1);
					System.out.println("del:" + k + " deltime:" + delayTime + " mindel:" + minDelay);
					System.out.println("intex:" + j1 + " opos:" + i);
					shouldDebug = false;
					j1 = 0;
				}
			}

			if (anInt4 == -1) {
				exit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public final void start() {
		if (anInt4 >= 0) {
			anInt4 = 0;
		}
	}

	public void startRunnable(Runnable runnable, int priority) {
		Thread thread = new Thread(runnable);
		thread.start();
		thread.setPriority(priority);
	}

	void startUp() {
	}

	@Override
	public final void stop() {
		if (anInt4 >= 0) {
			anInt4 = 4000 / delayTime;
		}
	}

	@Override
	public final void update(Graphics graphics) {
		shouldClearScreen = true;
		raiseWelcomeScreen();
	}

	@Override
	public final void windowActivated(WindowEvent event) {
	}

	@Override
	public final void windowClosed(WindowEvent event) {
	}

	@Override
	public final void windowClosing(WindowEvent event) {
		destroy();
	}

	@Override
	public final void windowDeactivated(WindowEvent event) {
	}

	@Override
	public final void windowDeiconified(WindowEvent event) {
	}

	@Override
	public final void windowIconified(WindowEvent event) {
	}

	@Override
	public final void windowOpened(WindowEvent event) {
	}

	public int getClickMode2() {
		return clickMode2;
	}

	public void setClickMode2(int clickMode2) {
		this.clickMode2 = clickMode2;
	}

	public void setCursor(CursorData cursor) {
		if (Client.getClient().oldCursor != null && Client.getClient().oldCursor == cursor)
			return;
		Sprite sprite = CacheSpriteLoader.getCacheSprite(cursor.sprite);
		Image image = sprite.getImage();
		// Image image =
		// getGameComponent().getToolkit().createImage(FileOperations.ReadFile(signlink.findcachedir()
		// + "Sprites/Cursors/Cursor " + id + ".PNG"));
		getGameComponent().setCursor(getGameComponent().getToolkit().createCustomCursor(image, new Point(0, 0), null));
		Client.getClient().oldCursor = cursor;
	}
}