package org.runelive.client.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.runelive.client.Client;
import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;

public class Sprite extends Canvas2D {

	public void setAlphaTransparency(int a) {
		for (int pixel = 0; pixel < myPixels.length; pixel++) {
			if (((myPixels[pixel] >> 24) & 255) == a)
				myPixels[pixel] = 0;
		}
	}

	public Sprite(String img) {
		try {
			Image image = Client.resourceLoader.getImage(img);
			if (image == null)
				return;
			ImageIcon sprite = new ImageIcon(image);
			myWidth = sprite.getIconWidth();
			myHeight = sprite.getIconHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			drawOffsetX = 0;
			drawOffsetY = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
			setTransparency(255, 0, 255);
			if (img.contains("Crowns"))
				setTransparency(255, 255, 255);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
	}

	public static Image scale(Image image, int width, int height) {
		return image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
	}

	public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
		BufferedImage dest = src.getSubimage(0, 0, rect.width, rect.height);
		return dest;
	}

	public static BufferedImage getScaledImage(Image loadingSprites, int w, int h, int origW, int origH) {
		BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(loadingSprites, 0, 0, origW, origH, null);
		g.dispose();
		return resizedImage;
	}

	public Sprite(byte spriteData[], int width, int height, int x) {
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(spriteData);
			myWidth = width;
			myHeight = height;
			maxWidth = myWidth;
			maxHeight = myHeight;
			drawOffsetX = 0;
			drawOffsetY = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
		} catch (Exception _ex) {
			System.out.println(_ex);
		}
	}

	public Sprite(URL url) {
		try {
			if (url == null)
				return;
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedImage image = ImageIO.read(httpcon.getInputStream());
			myWidth = image.getWidth();
			myHeight = image.getHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			drawOffsetX = 0;
			drawOffsetY = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static Image downScaleImage(Image image, int width, int height) {
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	public static Image getImageFromArray(int[] pixels, int width, int height) {
		MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.createImage(mis);
	}

	public static Sprite getCutted(Sprite s, int width, int height) {
		try {
			Sprite sprite = new Sprite();
			Image image = getImageFromArray(s.myPixels, s.myWidth, s.myHeight);
			sprite.myWidth = width;
			sprite.myHeight = height;
			sprite.maxWidth = sprite.myWidth;
			sprite.maxHeight = sprite.myHeight;
			sprite.drawOffsetX = 0;
			sprite.drawOffsetY = 0;
			sprite.myPixels = new int[sprite.myWidth * sprite.myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, sprite.myWidth, sprite.myHeight, sprite.myPixels,
					0, sprite.myWidth);
			pixelgrabber.grabPixels();
			image = null;
			return sprite;
		} catch (Exception _ex) {
			_ex.printStackTrace();
			return null;
		}
	}

	public static Image getImageFromArray2(int[] pixels, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		return image;
	}

	public Image getImage() {
		return getImageFromArray(myPixels, myWidth, myHeight);
	}

	public static Sprite getResizedSprite(Sprite spriteParam, int width, int height) {
		if (width == 0)
			width = 1;
		if (height == 0)
			height = 1;
		Image img = getImageFromArray(spriteParam.myPixels, spriteParam.myWidth, spriteParam.myHeight);
		Image img_2 = downScaleImage(img, width, height);

		ImageIcon sprite = new ImageIcon(img_2);
		Sprite toReturn = new Sprite();
		toReturn.myWidth = sprite.getIconWidth();
		toReturn.myHeight = sprite.getIconHeight();
		toReturn.maxWidth = toReturn.myWidth;
		toReturn.maxHeight = toReturn.myHeight;
		toReturn.drawOffsetX = 0;
		toReturn.drawOffsetY = 0;
		toReturn.myPixels = new int[toReturn.myWidth * toReturn.myHeight];
		PixelGrabber pixelgrabber = new PixelGrabber(img_2, 0, 0, toReturn.myWidth, toReturn.myHeight,
				toReturn.myPixels, 0, toReturn.myWidth);
		try {
			pixelgrabber.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		img_2 = null;
		img = null;
		toReturn.setTransparency(255, 0, 255);
		toReturn.setTransparency(255, 255, 255);
		return toReturn;
	}

	public Sprite(int i, int j) {
		myPixels = new int[i * j];
		myWidth = maxWidth = i;
		myHeight = maxHeight = j;
		drawOffsetX = drawOffsetY = 0;
	}

	public Sprite(byte spriteData[]) {
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(spriteData);
			ImageIcon sprite = new ImageIcon(image);
			myWidth = sprite.getIconWidth();
			myHeight = sprite.getIconHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			drawOffsetX = 0;
			drawOffsetY = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
			setTransparency(255, 0, 255);
			setTransparency(255, 255, 255);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
	}

	public int movingX, movingY = 0;
	public boolean movedEnough = false;
	private boolean startedMoving = false;

	/**
	 * Used to make sprites move
	 * 
	 * @param finalCoordX
	 *            The final X coord of the sprite
	 * @param finalCoordY
	 *            The final Y coord of the sprite
	 * @param startingX
	 *            The starting X coord of the sprite
	 * @param startingY
	 *            The starting y coord of the sprite
	 * @param speed
	 *            How fast the sprite will move
	 * @param coordToMove
	 *            Which coord to move? x? y? or both?
	 * @param advancedSprite
	 */
	public void drawMovingSprite(int finalCoordX, int finalCoordY, int startingX, int startingY, int speed,
			char coordToMove, boolean advancedSprite) {
		if (!startedMoving) {
			movingX = startingX;
			movingY = startingY;
			startedMoving = true;
		}
		if (!movedEnough) {
			if (coordToMove == 'y')
				movingY += speed;
			if (coordToMove == 'x')
				movingX += speed;
			if (coordToMove == 'b') {
				movingX += speed;
				movingY += speed;
			}
			if ((speed < 0 ? movingX <= finalCoordX : movingX >= finalCoordX)
					&& (coordToMove == 'x' || coordToMove == 'b')) {
				movingX = finalCoordX;
				movedEnough = true;
			}
			if ((speed < 0 ? movingY <= finalCoordY : movingY >= finalCoordY)
					&& (coordToMove == 'y' || coordToMove == 'b')) {
				movingY = finalCoordY;
				movedEnough = true;
			}
			setSprite(coordToMove == 'y' ? startingX : movingX, coordToMove == 'x' ? startingY : movingY,
					advancedSprite);
		} else
			setSprite(coordToMove == 'y' ? startingX : movingX, coordToMove == 'x' ? startingY : movingY,
					advancedSprite);
	}

	public void setSprite(int spriteX, int spriteY, boolean advancedSprite) {
		if (advancedSprite)
			drawAdvancedSprite(spriteX, spriteY);
		else
			drawSprite(spriteX, spriteY);
	}

	public void drawSprite1(int i, int j, int k) {
		i += drawOffsetX;
		j += drawOffsetY;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (l1 > 0 && k1 > 0)
			block_copy_alpha(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
	}

	public void drawAdvancedSprite(int i, int j, int alpha) {
		int k = alpha;
		i += drawOffsetX;
		j += drawOffsetY;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			drawAlphaGlow(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	/*
	 * public void drawAdvancedSprite(int i, int j, int k) { int i1 = i + j *
	 * Canvas2D.width; int j1 = 0; int k1 = myHeight; int l1 = myWidth; int i2 =
	 * Canvas2D.width - l1; int j2 = 0; if (j < Canvas2D.topY) { int k2 =
	 * Canvas2D.topY - j; k1 -= k2; j = Canvas2D.topY; j1 += k2 * l1; i1 += k2 *
	 * Canvas2D.width; } if (j + k1 > Canvas2D.bottomY) k1 -= (j + k1) -
	 * Canvas2D.bottomY; if (i < Canvas2D.topX) { int l2 = Canvas2D.topX - i; l1
	 * -= l2; i = Canvas2D.topX; j1 += l2; i1 += l2; j2 += l2; i2 += l2; } if (i
	 * + l1 > Canvas2D.bottomX) { int i3 = (i + l1) - Canvas2D.bottomX; l1 -=
	 * i3; j2 += i3; i2 += i3; } if (!(l1 <= 0 || k1 <= 0)) {
	 * drawAlphaSprite(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1); }
	 * }
	 */

	public void drawAdvancedSprite2(int i, int j, int alpha) {
		int k = alpha;
		i += myHeight;
		j += myWidth;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			drawAlphaGlow(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	private void drawAlphaGlow(int i, int j, int ai[], int ai1[], int l, int i1, int j1, int k1, int l1) {
		int k;// was parameter
		int j2;
		int op = k1;
		for (int k2 = -i1; k2 < 0; k2++) {
			for (int l2 = -j; l2 < 0; l2++) {
				k1 = ((myPixels[i] >> 26) & op);
				// k1 = k1;
				j2 = 256 - k1;
				k = ai1[i++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00)
							+ ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else {
					l1++;
				}
			}

			l1 += j1;
			i += l;
		}
	}

	public void drawAdvancedSprite(int i, int j) {
		int k = 256;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			drawAlphaSprite(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void drawCenteredARGBImage(int x, int y) {
		drawARGBImage(x - (this.myWidth / 2), y - (this.myHeight / 2));
	}

	public void drawARGBImage(int xPos, int yPos) {
		drawARGBSprite(xPos, yPos, 256);
	}

	public void drawARGBSprite(int xPos, int yPos, int alpha) {
		int alphaValue = alpha;
		xPos += drawOffsetX;
		yPos += drawOffsetY;
		int i1 = xPos + yPos * Canvas2D.width;
		int j1 = 0;
		int spriteHeight = myHeight;
		int spriteWidth = myWidth;
		int i2 = Canvas2D.width - spriteWidth;
		int j2 = 0;
		if (yPos < Canvas2D.topY) {
			int k2 = Canvas2D.topY - yPos;
			spriteHeight -= k2;
			yPos = Canvas2D.topY;
			j1 += k2 * spriteWidth;
			i1 += k2 * Canvas2D.width;
		}
		if (yPos + spriteHeight > Canvas2D.bottomY)
			spriteHeight -= (yPos + spriteHeight) - Canvas2D.bottomY;
		if (xPos < Canvas2D.topX) {
			int l2 = Canvas2D.topX - xPos;
			spriteWidth -= l2;
			xPos = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (xPos + spriteWidth > Canvas2D.bottomX) {
			int i3 = (xPos + spriteWidth) - Canvas2D.bottomX;
			spriteWidth -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(spriteWidth <= 0 || spriteHeight <= 0)) {
			renderARGBPixels(spriteWidth, spriteHeight, myPixels, Canvas2D.pixels, i1, alphaValue, j1, j2, i2);
		}
	}

	private void renderARGBPixels(int spriteWidth, int spriteHeight, int spritePixels[], int renderAreaPixels[],
			int pixel, int alphaValue, int i, int l, int j1) {
		int pixelLevel;
		int alphaLevel;
		for (int height = -spriteHeight; height < 0; height++) {
			for (int width = -spriteWidth; width < 0; width++) {
				alphaValue = ((myPixels[i] >> 24) & 255);
				alphaLevel = 256 - alphaValue;
				pixelLevel = spritePixels[i++];
				if (pixelLevel != 0) {
					int pixelValue = renderAreaPixels[pixel];
					renderAreaPixels[pixel++] = ((pixelLevel & 0xff00ff) * alphaValue
							+ (pixelValue & 0xff00ff) * alphaLevel & 0xff00ff00)
							+ ((pixelLevel & 0xff00) * alphaValue + (pixelValue & 0xff00) * alphaLevel & 0xff0000) >> 8;
				} else {
					pixel++;
				}
			}
			pixel += j1;
			i += l;
		}
	}

	private void drawAlphaSprite(int i, int j, int ai[], int ai1[], int l, int i1, int j1, int k1, int l1) {
		int k;// was parameter
		int j2;
		for (int k2 = -i1; k2 < 0; k2++) {
			for (int l2 = -j; l2 < 0; l2++) {
				k1 = ((myPixels[i] >> 24) & 255);
				j2 = 256 - k1;
				k = ai1[i++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00)
							+ ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else {
					l1++;
				}
			}

			l1 += j1;
			i += l;
		}
	}

	public Sprite(Image image) {
		try {
			ImageIcon sprite = new ImageIcon(image);
			myWidth = sprite.getIconWidth();
			myHeight = sprite.getIconHeight();
			maxWidth = myWidth;
			maxHeight = myHeight;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
			setTransparency(255, 0, 255);
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
	}

	public Sprite(Image image, int width, int height) {
		try {
			if (image == null) {
				return;
			}
			myWidth = width;
			myHeight = height;
			maxWidth = myWidth;
			maxHeight = myHeight;
			drawOffsetX = 0;
			drawOffsetY = 0;
			myPixels = new int[myWidth * myHeight];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, myWidth, myHeight, myPixels, 0, myWidth);
			pixelgrabber.grabPixels();
			image = null;
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
	}

	public void setTransparency(int transRed, int transGreen, int transBlue) {
		for (int index = 0; index < myPixels.length; index++)
			if (((myPixels[index] >> 16) & 255) == transRed && ((myPixels[index] >> 8) & 255) == transGreen
					&& (myPixels[index] & 255) == transBlue)
				myPixels[index] = 0;
	}

	public Sprite(Archive streamLoader, String s, int i) {
		ByteBuffer stream = new ByteBuffer(streamLoader.get(s + ".dat"));
		ByteBuffer stream_1 = new ByteBuffer(streamLoader.get("index.dat"));
		stream_1.position = stream.getUnsignedShort();
		maxWidth = stream_1.getUnsignedShort();
		maxHeight = stream_1.getUnsignedShort();
		int j = stream_1.getUnsignedByte();
		int ai[] = new int[j];
		for (int k = 0; k < j - 1; k++) {
			ai[k + 1] = stream_1.getMediumInt();
			if (ai[k + 1] == 0)
				ai[k + 1] = 1;
		}

		for (int l = 0; l < i; l++) {
			stream_1.position += 2;
			stream.position += stream_1.getUnsignedShort() * stream_1.getUnsignedShort();
			stream_1.position++;
		}

		drawOffsetX = stream_1.getUnsignedByte();
		drawOffsetY = stream_1.getUnsignedByte();
		myWidth = stream_1.getUnsignedShort();
		myHeight = stream_1.getUnsignedShort();
		int i1 = stream_1.getUnsignedByte();
		int j1 = myWidth * myHeight;
		myPixels = new int[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++)
				myPixels[k1] = ai[stream.getUnsignedByte()];
			if ((s.equals("mod_icons") && (i == 7 || i == 8 || i == 9)))
				setTransparency(255, 255, 255);
			else
				setTransparency(255, 0, 255);
			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < myWidth; l1++) {
				for (int i2 = 0; i2 < myHeight; i2++)
					myPixels[l1 + i2 * myWidth] = ai[stream.getUnsignedByte()];
			}

		}
		// System.out.println(s);
		if ((s.equals("mod_icons") && (i == 7 || i == 8 || i == 9)))
			setTransparency(255, 255, 255);
		else
			setTransparency(255, 0, 255);
	}

	public void method343() {
		Canvas2D.initDrawingArea(myHeight, myWidth, myPixels, null);
	}

	public void decodePalette(int i, int j, int k) {
		autoUpdate();
		for (int i1 = 0; i1 < myPixels.length; i1++) {
			int j1 = myPixels[i1];
			if (j1 != 0) {
				int k1 = j1 >> 16 & 0xff;
				k1 += i;
				if (k1 < 1)
					k1 = 1;
				else if (k1 > 255)
					k1 = 255;
				int l1 = j1 >> 8 & 0xff;
				l1 += j;
				if (l1 < 1)
					l1 = 1;
				else if (l1 > 255)
					l1 = 255;
				int i2 = j1 & 0xff;
				i2 += k;
				if (i2 < 1)
					i2 = 1;
				else if (i2 > 255)
					i2 = 255;
				myPixels[i1] = (k1 << 16) + (l1 << 8) + i2;
			}
		}

	}

	public void method345() {
		autoUpdate();
		int ai[] = new int[maxWidth * maxHeight];
		for (int j = 0; j < myHeight; j++) {
			System.arraycopy(myPixels, j * myWidth, ai, j + drawOffsetY * maxWidth + drawOffsetX, myWidth);
		}

		myPixels = ai;
		myWidth = maxWidth;
		myHeight = maxHeight;
		drawOffsetX = 0;
		drawOffsetY = 0;
	}

	public void method346(int i, int j) {
		autoUpdate();
		i += drawOffsetX;
		j += drawOffsetY;
		int l = i + j * Canvas2D.width;
		int i1 = 0;
		int j1 = myHeight;
		int k1 = myWidth;
		int l1 = Canvas2D.width - k1;
		int i2 = 0;
		if (j < Canvas2D.topY) {
			int j2 = Canvas2D.topY - j;
			j1 -= j2;
			j = Canvas2D.topY;
			i1 += j2 * k1;
			l += j2 * Canvas2D.width;
		}
		if (j + j1 > Canvas2D.bottomY)
			j1 -= (j + j1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int k2 = Canvas2D.topX - i;
			k1 -= k2;
			i = Canvas2D.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > Canvas2D.bottomX) {
			int l2 = (i + k1) - Canvas2D.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (k1 <= 0 || j1 <= 0) {
		} else {
			block_copy(l, k1, j1, i2, i1, l1, myPixels, Canvas2D.pixels);
		}
	}

	public void block_copy(int i, int j, int k, int l, int i1, int k1, int ai[], int ai1[]) {
		int l1 = -(j >> 2);
		j = -(j & 3);
		for (int i2 = -k; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
				ai1[i++] = ai[i1++];
			}

			for (int k2 = j; k2 < 0; k2++)
				ai1[i++] = ai[i1++];

			i += k1;
			i1 += l;
		}
	}

	public void drawSprite1(int i, int j) {
		autoUpdate();
		int k = 128;// was parameter
		i += drawOffsetX;
		j += drawOffsetY;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			block_copy_alpha(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void drawTransparentSprite(int i, int j, int opacity) {
		int k = opacity;// was parameter
		i += drawOffsetX;
		j += drawOffsetY;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			block_copy_alpha(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void drawSprite(int i, int k) {
		i += drawOffsetX;
		k += drawOffsetY;
		int l = i + k * Canvas2D.width;
		int i1 = 0;
		int j1 = myHeight;
		int k1 = myWidth;
		int l1 = Canvas2D.width - k1;
		int i2 = 0;
		if (k < Canvas2D.topY) {
			int j2 = Canvas2D.topY - k;
			j1 -= j2;
			k = Canvas2D.topY;
			i1 += j2 * k1;
			l += j2 * Canvas2D.width;
		}
		if (k + j1 > Canvas2D.bottomY)
			j1 -= (k + j1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int k2 = Canvas2D.topX - i;
			k1 -= k2;
			i = Canvas2D.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > Canvas2D.bottomX) {
			int l2 = (i + k1) - Canvas2D.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			try {
				block_copy_trans(Canvas2D.pixels, myPixels, i1, l, k1, j1, l1, i2);
			} catch (Exception e) {
				
			}
		}
	}

	public void drawSprite3(int x, int y, int opacity) {
		int alpha = opacity;
		x += this.drawOffsetX;// offsetX
		y += this.drawOffsetY;// offsetY
		int destOffset = x + y * Canvas2D.width;
		int srcOffset = 0;
		int height = this.myHeight;
		int width = this.myWidth;
		int destStep = Canvas2D.width - width;
		int srcStep = 0;
		if (y < Canvas2D.topY) {
			int trimHeight = Canvas2D.topY - y;
			height -= trimHeight;
			y = Canvas2D.topY;
			srcOffset += trimHeight * width;
			destOffset += trimHeight * Canvas2D.width;
		}
		if (y + height > Canvas2D.bottomY) {
			height -= (y + height) - Canvas2D.bottomY;
		}
		if (x < Canvas2D.topX) {
			int trimLeft = Canvas2D.topX - x;
			width -= trimLeft;
			x = Canvas2D.topX;
			srcOffset += trimLeft;
			destOffset += trimLeft;
			srcStep += trimLeft;
			destStep += trimLeft;
		}
		if (x + width > Canvas2D.bottomX) {
			int trimRight = (x + width) - Canvas2D.bottomX;
			width -= trimRight;
			srcStep += trimRight;
			destStep += trimRight;
		}
		if (!((width <= 0) || (height <= 0))) {
			setPixels(width, height, Canvas2D.pixels, myPixels, alpha, destOffset, srcOffset, destStep, srcStep);
		}
	}

	private void setPixels(int width, int height, int destPixels[], int srcPixels[], int srcAlpha, int destOffset,
			int srcOffset, int destStep, int srcStep) {
		int srcColor;
		int destAlpha;
		int rofl = srcAlpha;
		for (int loop = -height; loop < 0; loop++) {
			for (int loop2 = -width; loop2 < 0; loop2++) {
				srcAlpha = ((this.myPixels[srcOffset] >> 24) & rofl);
				destAlpha = 256 - srcAlpha;
				srcColor = srcPixels[srcOffset++];
				if (srcColor != 0 && srcColor != 0xffffff) {
					int destColor = destPixels[destOffset];
					destPixels[destOffset++] = ((srcColor & 0xff00ff) * srcAlpha + (destColor & 0xff00ff) * destAlpha
							& 0xff00ff00)
							+ ((srcColor & 0xff00) * srcAlpha + (destColor & 0xff00) * destAlpha & 0xff0000) >> 8;
				} else {
					destOffset++;
				}
			}
			destOffset += destStep;
			srcOffset += srcStep;
		}
	}

	public void drawSprite(int i, int k, int color) {
		autoUpdate();
		int tempWidth = myWidth + 2;
		int tempHeight = myHeight + 2;
		int[] tempArray = new int[tempWidth * tempHeight];
		for (int x = 0; x < myWidth; x++) {
			for (int y = 0; y < myHeight; y++) {
				if (myPixels[x + y * myWidth] != 0)
					tempArray[(x + 1) + (y + 1) * tempWidth] = myPixels[x + y * myWidth];
			}
		}
		for (int x = 0; x < tempWidth; x++) {
			for (int y = 0; y < tempHeight; y++) {
				if (tempArray[(x) + (y) * tempWidth] == 0) {
					if (x < tempWidth - 1 && tempArray[(x + 1) + ((y) * tempWidth)] > 0
							&& tempArray[(x + 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (x > 0 && tempArray[(x - 1) + ((y) * tempWidth)] > 0
							&& tempArray[(x - 1) + ((y) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (y < tempHeight - 1 && tempArray[(x) + ((y + 1) * tempWidth)] > 0
							&& tempArray[(x) + ((y + 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
					if (y > 0 && tempArray[(x) + ((y - 1) * tempWidth)] > 0
							&& tempArray[(x) + ((y - 1) * tempWidth)] != 0xffffff) {
						tempArray[(x) + (y) * tempWidth] = color;
					}
				}
			}
		}
		i--;
		k--;
		i += drawOffsetX;
		k += drawOffsetY;
		int l = i + k * Canvas2D.width;
		int i1 = 0;
		int j1 = tempHeight;
		int k1 = tempWidth;
		int l1 = Canvas2D.width - k1;
		int i2 = 0;
		if (k < Canvas2D.topY) {
			int j2 = Canvas2D.topY - k;
			j1 -= j2;
			k = Canvas2D.topY;
			i1 += j2 * k1;
			l += j2 * Canvas2D.width;
		}
		if (k + j1 > Canvas2D.bottomY) {
			j1 -= (k + j1) - Canvas2D.bottomY;
		}
		if (i < Canvas2D.topX) {
			int k2 = Canvas2D.topX - i;
			k1 -= k2;
			i = Canvas2D.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + k1 > Canvas2D.bottomX) {
			int l2 = (i + k1) - Canvas2D.bottomX;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(k1 <= 0 || j1 <= 0)) {
			block_copy_trans(Canvas2D.pixels, tempArray, i1, l, k1, j1, l1, i2);
		}
	}

	public void drawSprite2(int i, int j) {
		int k = 225;// was parameter
		i += drawOffsetX;
		j += drawOffsetY;
		int i1 = i + j * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (j < Canvas2D.topY) {
			int k2 = Canvas2D.topY - j;
			k1 -= k2;
			j = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (j + k1 > Canvas2D.bottomY)
			k1 -= (j + k1) - Canvas2D.bottomY;
		if (i < Canvas2D.topX) {
			int l2 = Canvas2D.topX - i;
			l1 -= l2;
			i = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (i + l1 > Canvas2D.bottomX) {
			int i3 = (i + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			block_copy_alpha(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, k, i1);
		}
	}

	public void block_copy_trans(int ai[], int ai1[], int j, int k, int l, int i1, int j1, int k1) {
		int i;// was parameter
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
			}

			for (int k2 = l; k2 < 0; k2++) {
				i = ai1[j++];
				if (i != 0 && i != -1) {
					ai[k++] = i;
				} else {
					k++;
				}
			}
			k += j1;
			j += k1;
		}
	}

	public void block_copy_alpha(int i, int j, int ai[], int ai1[], int l, int i1, int j1, int k1, int l1) {
		int k;// was parameter
		int j2 = 256 - k1;
		for (int k2 = -i1; k2 < 0; k2++) {
			for (int l2 = -j; l2 < 0; l2++) {
				k = ai1[i++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00)
							+ ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else {
					l1++;
				}
			}

			l1 += j1;
			i += l;
		}
	}

	public void rotate(int dimension, int rotation, int yPosArray[], int zoom, int xPosArray[], int basePosition,
			int yPosition, int xPosition, int dimension_1, int middle) {
		autoUpdate();
		try {
			int j2 = -dimension_1 / 2;
			int k2 = -dimension / 2;
			int l2 = (int) (Math.sin((double) rotation / 326.11000000000001D) * 65536D);
			int i3 = (int) (Math.cos((double) rotation / 326.11000000000001D) * 65536D);
			l2 = l2 * zoom >> 8;
			i3 = i3 * zoom >> 8;
			int j3 = (middle << 16) + (k2 * l2 + j2 * i3);
			int k3 = (basePosition << 16) + (k2 * i3 - j2 * l2);
			int l3 = xPosition + yPosition * Canvas2D.width;
			for (yPosition = 0; yPosition < dimension; yPosition++) {
				int i4 = xPosArray[yPosition];
				int j4 = l3 + i4;
				int k4 = j3 + i3 * i4;
				int l4 = k3 - l2 * i4;
				for (xPosition = -yPosArray[yPosition]; xPosition < 0; xPosition++) {
					int x1 = k4 >> 16;
					int y1 = l4 >> 16;
					int x2 = x1 + 1;
					int y2 = y1 + 1;
					int c1 = myPixels[x1 + y1 * myWidth];
					int c2 = myPixels[x2 + y1 * myWidth];
					int c3 = myPixels[x1 + y2 * myWidth];
					int c4 = myPixels[x2 + y2 * myWidth];
					int u1 = (k4 >> 8) - (x1 << 8);
					int v1 = (l4 >> 8) - (y1 << 8);
					int u2 = (x2 << 8) - (k4 >> 8);
					int v2 = (y2 << 8) - (l4 >> 8);
					int a1 = u2 * v2;
					int a2 = u1 * v2;
					int a3 = u2 * v1;
					int a4 = u1 * v1;
					int r = (c1 >> 16 & 0xff) * a1 + (c2 >> 16 & 0xff) * a2 + (c3 >> 16 & 0xff) * a3
							+ (c4 >> 16 & 0xff) * a4 & 0xff0000;
					int g = (c1 >> 8 & 0xff) * a1 + (c2 >> 8 & 0xff) * a2 + (c3 >> 8 & 0xff) * a3
							+ (c4 >> 8 & 0xff) * a4 >> 8 & 0xff00;
					int b = (c1 & 0xff) * a1 + (c2 & 0xff) * a2 + (c3 & 0xff) * a3 + (c4 & 0xff) * a4 >> 16;
					Canvas2D.pixels[j4++] = r | g | b;
					k4 += i3;
					l4 -= l2;
				}

				j3 += l2;
				k3 += i3;
				l3 += Canvas2D.width;
			}

		} catch (Exception _ex) {
		}
	}

	public void rotate(int y, double d, int x) {
		autoUpdate();
		// all of the following were parameters
		int j = 15;
		int k = 20;
		int l = 15;
		int j1 = 256;
		int k1 = 20;
		// all of the previous were parameters
		try {
			int i2 = -k / 2;
			int j2 = -k1 / 2;
			int k2 = (int) (Math.sin(d) * 65536D);
			int l2 = (int) (Math.cos(d) * 65536D);
			k2 = k2 * j1 >> 8;
			l2 = l2 * j1 >> 8;
			int i3 = (l << 16) + (j2 * k2 + i2 * l2);
			int j3 = (j << 16) + (j2 * l2 - i2 * k2);
			int k3 = x + y * Canvas2D.width;
			for (y = 0; y < k1; y++) {
				int l3 = k3;
				int i4 = i3;
				int j4 = j3;
				for (x = -k; x < 0; x++) {
					int k4 = myPixels[(i4 >> 16) + (j4 >> 16) * myWidth];
					if (k4 != 0)
						Canvas2D.pixels[l3++] = k4;
					else
						l3++;
					i4 += l2;
					j4 -= k2;
				}

				i3 += k2;
				j3 += l2;
				k3 += Canvas2D.width;
			}

		} catch (Exception _ex) {
		}
	}

	public void draw(Background background, int i, int j) {
		autoUpdate();
		j += drawOffsetX;
		i += drawOffsetY;
		int k = j + i * Canvas2D.width;
		int l = 0;
		int i1 = myHeight;
		int j1 = myWidth;
		int k1 = Canvas2D.width - j1;
		int l1 = 0;
		if (i < Canvas2D.topY) {
			int i2 = Canvas2D.topY - i;
			i1 -= i2;
			i = Canvas2D.topY;
			l += i2 * j1;
			k += i2 * Canvas2D.width;
		}
		if (i + i1 > Canvas2D.bottomY)
			i1 -= (i + i1) - Canvas2D.bottomY;
		if (j < Canvas2D.topX) {
			int j2 = Canvas2D.topX - j;
			j1 -= j2;
			j = Canvas2D.topX;
			l += j2;
			k += j2;
			l1 += j2;
			k1 += j2;
		}
		if (j + j1 > Canvas2D.bottomX) {
			int k2 = (j + j1) - Canvas2D.bottomX;
			j1 -= k2;
			l1 += k2;
			k1 += k2;
		}
		if (!(j1 <= 0 || i1 <= 0)) {
			block_copy_mask(myPixels, j1, background.imgPixels, i1, Canvas2D.pixels, 0, k1, k, l1, l);
		}
	}

	public void greyScale() {
		for (int index = 0; index < myWidth * myHeight; index++) {
			int alpha = myPixels[index] >>> 24;
			int red = myPixels[index] >>> 16 & 0xff;
			int green = myPixels[index] >>> 8 & 0xff;
			int blue = myPixels[index] & 0xff;
			int delta = (red + green + blue) / 3;
			myPixels[index] = delta | delta << 8 | delta << 16 | alpha << 24;
		}

	}

	public void drawSpriteWithOpacity(int xPos, int yPos, int o) {
		int opacity = o;// was parameter
		xPos += drawOffsetX;
		yPos += drawOffsetY;
		int i1 = xPos + yPos * Canvas2D.width;
		int j1 = 0;
		int k1 = myHeight;
		int l1 = myWidth;
		int i2 = Canvas2D.width - l1;
		int j2 = 0;
		if (yPos < Canvas2D.topY) {
			int k2 = Canvas2D.topY - yPos;
			k1 -= k2;
			yPos = Canvas2D.topY;
			j1 += k2 * l1;
			i1 += k2 * Canvas2D.width;
		}
		if (yPos + k1 > Canvas2D.bottomY)
			k1 -= (yPos + k1) - Canvas2D.bottomY;
		if (xPos < Canvas2D.topX) {
			int l2 = Canvas2D.topX - xPos;
			l1 -= l2;
			xPos = Canvas2D.topX;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (xPos + l1 > Canvas2D.bottomX) {
			int i3 = (xPos + l1) - Canvas2D.bottomX;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (!(l1 <= 0 || k1 <= 0)) {
			block_copy_alpha(j1, l1, Canvas2D.pixels, myPixels, j2, k1, i2, opacity, i1);
		}
	}

	public void block_copy_mask(int ai[], int i, byte abyte0[], int j, int ai1[], int k, int l, int i1, int j1,
			int k1) {
		int l1 = -(i >> 2);
		i = -(i & 3);
		for (int j2 = -j; j2 < 0; j2++) {
			for (int k2 = l1; k2 < 0; k2++) {
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
			}

			for (int l2 = i; l2 < 0; l2++) {
				k = ai[k1++];
				if (k != 0 && abyte0[i1] == 0)
					ai1[i1++] = k;
				else
					i1++;
			}

			i1 += l;
			k1 += j1;
		}

	}

	public void drawARGBSprite(int xPos, int yPos) {
		drawARGBSprite(xPos, yPos, 256);
	}

	public void autoUpdate() {
	}

	public Sprite() {
	}

	public int myPixels[];
	public int myWidth;
	public int myHeight;
	public int drawOffsetX;
	public int drawOffsetY;
	public int maxWidth;
	public int maxHeight;
}
