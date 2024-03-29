package org.runelive.client.graphics;

import org.runelive.client.cache.node.NodeSub;

public class Canvas2D extends NodeSub {

	public static int pixels[];
	public static int width;
	public static int height;
	public static int topY;
	public static int bottomY;
	public static int topX;
	public static int bottomX;
	public static int centerX;
	public static int centerY;
	public static int middleY;
	public static float[] depthBuffer;

	public static void fillRectangle(int color, int y, int widthz, int heightz, int opacity, int x) {
		if (x < topX) {
			widthz -= topX - x;
			x = topX;
		}
		if (y < topY) {
			heightz -= topY - y;
			y = topY;
		}
		if (x + widthz > bottomX)
			widthz = bottomX - x;
		if (y + heightz > bottomY)
			heightz = bottomY - y;
		int decodedOpacity = 256 - opacity;
		int red = (color >> 16 & 0xff) * opacity;
		int green = (color >> 8 & 0xff) * opacity;
		int blue = (color & 0xff) * opacity;
		int pixelWidthStep = width - widthz;
		int startPixel = x + y * width;
		for (int h = 0; h < heightz; h++) {
			for (int w = -widthz; w < 0; w++) {
				int pixelRed = (pixels[startPixel] >> 16 & 0xff) * decodedOpacity;
				int pixelBlue = (pixels[startPixel] >> 8 & 0xff) * decodedOpacity;
				int pixelGreen = (pixels[startPixel] & 0xff) * decodedOpacity;
				int pixelRGB = ((red + pixelRed >> 8) << 16) + ((green + pixelBlue >> 8) << 8)
						+ (blue + pixelGreen >> 8);
				pixels[startPixel++] = pixelRGB;
			}

			startPixel += pixelWidthStep;
		}
	}

	protected static void drawHLine(int i, int j, int k, int l, int i1) {
		if (k < topY || k >= bottomY)
			return;
		if (i1 < topX) {
			j -= topX - i1;
			i1 = topX;
		}
		if (i1 + j > bottomX)
			j = bottomX - i1;
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * width;
		for (int j3 = 0; j3 < j; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}

	}

	protected static void drawVLine(int i, int j, int k, int l, int i1) {
		if (j < topX || j >= bottomX)
			return;
		if (l < topY) {
			i1 -= topY - l;
			l = topY;
		}
		if (l + i1 > bottomY)
			i1 = bottomY - l;
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * width;
		for (int j3 = 0; j3 < i1; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}
	}

	public static void drawRectangle(int y, int height, int opacity, int color, int width, int x) {
		drawHLine(color, width, y, opacity, x);
		drawHLine(color, width, (y + height) - 1, opacity, x);
		if (height >= 3) {
			drawVLine(color, x, opacity, y + 1, height - 2);
			drawVLine(color, (x + width) - 1, opacity, y + 1, height - 2);
		}
	}

	public static void drawAlphaBox(int x, int y, int lineWidth, int lineHeight, int color, int alpha) {// drawAlphaHorizontalLine
		if (y < topY) {
			if (y > (topY - lineHeight)) {
				lineHeight -= (topY - y);
				y += (topY - y);
			} else {
				return;
			}
		}
		if (y + lineHeight > bottomY) {
			lineHeight -= y + lineHeight - bottomY;
		}
		// if (y >= bottomY - lineHeight)
		// return;
		if (x < topX) {
			lineWidth -= topX - x;
			x = topX;
		}
		if (x + lineWidth > bottomX)
			lineWidth = bottomX - x;
		for (int yOff = 0; yOff < lineHeight; yOff++) {
			int i3 = x + (y + (yOff)) * width;
			for (int j3 = 0; j3 < lineWidth; j3++) {
				// int alpha2 = (lineWidth-j3) / (lineWidth/alpha);
				int j1 = 256 - alpha;// alpha2 is for gradient
				int k1 = (color >> 16 & 0xff) * alpha;
				int l1 = (color >> 8 & 0xff) * alpha;
				int i2 = (color & 0xff) * alpha;
				int j2 = (pixels[i3] >> 16 & 0xff) * j1;
				int k2 = (pixels[i3] >> 8 & 0xff) * j1;
				int l2 = (pixels[i3] & 0xff) * j1;
				int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
				pixels[i3++] = k3;
			}
		}
	}

	public static void drawLine(int yPos, int color, int widthToDraw, int xPos) {
		if (yPos < topY || yPos >= bottomY)
			return;
		if (xPos < topX) {
			widthToDraw -= topX - xPos;
			xPos = topX;
		}
		if (xPos + widthToDraw > bottomX)
			widthToDraw = bottomX - xPos;
		int base = xPos + yPos * width;
		for (int ptr = 0; ptr < widthToDraw; ptr++)
			pixels[base + ptr] = color;

	}

	public static void drawAlphaPixels(int x, int y, int w, int h, int color, int alpha) {
		if (x < topX) {
			w -= topX - x;
			x = topX;
		}
		if (y < topY) {
			h -= topY - y;
			y = topY;
		}
		if (x + w > bottomX)
			w = bottomX - x;
		if (y + h > bottomY)
			h = bottomY - y;
		int l1 = 256 - alpha;
		int i2 = (color >> 16 & 0xff) * alpha;
		int j2 = (color >> 8 & 0xff) * alpha;
		int k2 = (color & 0xff) * alpha;
		int k3 = width - w;
		int l3 = x + y * width;
		for (int i4 = 0; i4 < h; i4++) {
			for (int j4 = -w; j4 < 0; j4++) {
				int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				int j3 = (pixels[l3] & 0xff) * l1;
				int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8)
						+ (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}

			l3 += k3;
		}
	}

	public static void setDrawingArea(int yBottom, int xTop, int xBottom, int yTop) {
		if (xTop < 0)
			xTop = 0;
		if (yTop < 0)
			yTop = 0;
		if (xBottom > width)
			xBottom = width;
		if (yBottom > height)
			yBottom = height;
		topX = xTop;
		topY = yTop;
		bottomX = xBottom;
		bottomY = yBottom;
		// viewportRX = bottomX - 0;
		// viewport_centerX = bottomX / 2;
		// viewport_centerY = bottomY / 2;
	}

	public static void fillCircle(int x, int y, int radius, int color, int alpha) {
		int a2 = 256 - alpha;
		int r1 = (color >> 16 & 0xff) * alpha;
		int g1 = (color >> 8 & 0xff) * alpha;
		int b1 = (color & 0xff) * alpha;
		int y1 = y - radius;
		if (y1 < 0) {
			y1 = 0;
		}
		int y2 = y + radius;
		if (y2 >= height) {
			y2 = height - 1;
		}
		for (int iy = y1; iy <= y2; iy++) {
			int dy = iy - y;
			int dist = (int) Math.sqrt(radius * radius - dy * dy);
			int x1 = x - dist;
			if (x1 < 0) {
				x1 = 0;
			}
			int x2 = x + dist;
			if (x2 >= width) {
				x2 = width - 1;
			}
			int pos = x1 + iy * width;
			for (int ix = x1; ix <= x2; ix++) {
				int r2 = (pixels[pos] >> 16 & 0xff) * a2;
				int g2 = (pixels[pos] >> 8 & 0xff) * a2;
				int b2 = (pixels[pos] & 0xff) * a2;
				pixels[pos++] = ((r1 + r2 >> 8) << 16) + ((g1 + g2 >> 8) << 8) + (b1 + b2 >> 8);
			}
		}
	}

	public static void transparentBox(int i, int j, int k, int l, int i1, int j1, int opac) {
		int j3 = 256 - opac;
		if (k < topX) {
			i1 -= topX - k;
			k = topX;
		}
		if (j < topY) {
			i -= topY - j;
			j = topY;
		}
		if (k + i1 > bottomX) {
			i1 = bottomX - k;
		}
		if (j + i > bottomY) {
			i = bottomY - j;
		}
		int k1 = width - i1;
		int l1 = k + j * width;
		for (int i2 = -i; i2 < 0; i2++) {
			for (int j2 = -i1; j2 < 0; j2++) {
				int i3 = pixels[l1];
				pixels[l1++] = ((l & 0xff00ff) * opac + (i3 & 0xff00ff) * j3 & 0xff00ff00)
						+ ((l & 0xff00) * opac + (i3 & 0xff00) * j3 & 0xff0000) >> 8;
			}
			l1 += k1;
		}

	}

	public static void defaultDrawingAreaSize() {
		topX = 0;
		topY = 0;
		bottomX = width;
		bottomY = height;
		centerX = bottomX - 0;
		centerY = bottomX / 2;
	}

	public static void drawAlphaGradient(int x, int y, int gradientWidth, int gradientHeight, int startColor, int endColor, int alpha) {
		int k1 = 0;
		int l1 = 0x10000 / gradientHeight;
		if (x < topX) {
			gradientWidth -= topX - x;
			x = topX;
		}
		if (y < topY) {
			k1 += (topY - y) * l1;
			gradientHeight -= topY - y;
			y = topY;
		}
		if (x + gradientWidth > bottomX) {
			gradientWidth = bottomX - x;
		}
		if (y + gradientHeight > bottomY) {
			gradientHeight = bottomY - y;
		}
		int i2 = width - gradientWidth;
		int result_alpha = 256 - alpha;
		int total_pixels = x + y * width;
		for (int k2 = -gradientHeight; k2 < 0; k2++) {
			int gradient1 = 0x10000 - k1 >> 8;
			int gradient2 = k1 >> 8;
			int gradient_color = ((startColor & 0xff00ff) * gradient1 + (endColor & 0xff00ff) * gradient2 & 0xff00ff00)
					+ ((startColor & 0xff00) * gradient1 + (endColor & 0xff00) * gradient2 & 0xff0000) >>> 8;
			int color = ((gradient_color & 0xff00ff) * alpha >> 8 & 0xff00ff)
					+ ((gradient_color & 0xff00) * alpha >> 8 & 0xff00);
			for (int k3 = -gradientWidth; k3 < 0; k3++) {
				int pixel_pixels = pixels[total_pixels];
				pixel_pixels = ((pixel_pixels & 0xff00ff) * result_alpha >> 8 & 0xff00ff)
						+ ((pixel_pixels & 0xff00) * result_alpha >> 8 & 0xff00);
				pixels[total_pixels++] = color + pixel_pixels;
			}
			total_pixels += i2;
			k1 += l1;
		}
	}

	public static void drawFilledPixels(int x, int y, int pixelWidth, int pixelHeight, int color) {// method578
		if (x < topX) {
			pixelWidth -= topX - x;
			x = topX;
		}
		if (y < topY) {
			pixelHeight -= topY - y;
			y = topY;
		}
		if (x + pixelWidth > bottomX) {
			pixelWidth = bottomX - x;
		}
		if (y + pixelHeight > bottomY) {
			pixelHeight = bottomY - y;
		}
		int j1 = width - pixelWidth;
		int k1 = x + y * width;
		for (int l1 = -pixelHeight; l1 < 0; l1++) {
			for (int i2 = -pixelWidth; i2 < 0; i2++) {
				pixels[k1++] = color;
			}
			k1 += j1;
		}
	}

	public static void drawHorizontalLine(int i, int j, int k, int l) {
		if (i < topY || i >= bottomY) {
			return;
		}
		if (l < topX) {
			k -= topX - l;
			l = topX;
		}
		if (l + k > bottomX) {
			k = bottomX - l;
		}
		int i1 = l + i * width;
		for (int j1 = 0; j1 < k; j1++) {
			pixels[i1 + j1] = j;
		}

	}

	public static void drawPixels(int height, int posY, int posX, int color, int width) {
		if (posX < topX) {
			width -= topX - posX;
			posX = topX;
		}
		if (posY < topY) {
			height -= topY - posY;
			posY = topY;
		}
		if (posX + width > bottomX) {
			width = bottomX - posX;
		}
		if (posY + height > bottomY) {
			height = bottomY - posY;
		}
		int k1 = Canvas2D.width - width;
		int l1 = posX + posY * Canvas2D.width;
		for (int i2 = -height; i2 < 0; i2++) {
			for (int j2 = -width; j2 < 0; j2++) {
				pixels[l1++] = color;
			}

			l1 += k1;
		}

	}

	public static void fillPixels(int offSetX, int width, int height, int l, int offSetY) {
		drawHorizontalLine(offSetY, l, width, offSetX);
		drawHorizontalLine(offSetY + height - 1, l, width, offSetX);
		method341(offSetY, l, height, offSetX);
		method341(offSetY, l, height, offSetX + width - 1);
	}

	public static void fillRect(int xPos, int yPos, int areaWidth, int areaHeight, int color, int transparency) {
		if (xPos < topX) {
			areaWidth -= topX - xPos;
			xPos = topX;
		}
		if (yPos < topY) {
			areaHeight -= topY - yPos;
			yPos = topY;
		}
		if (xPos + areaWidth > bottomX) {
			areaWidth = bottomX - xPos;
		}
		if (yPos + areaHeight > bottomY) {
			areaHeight = bottomY - yPos;
		}
		int opacity = 256 - transparency;
		int red = (color >> 16 & 0xff) * transparency;
		int green = (color >> 8 & 0xff) * transparency;
		int blue = (color & 0xff) * transparency;
		int xOffset = width - areaWidth;
		int pixel = xPos + yPos * width;
		for (int y = 0; y < areaHeight; y++) {
			for (int x = -areaWidth; x < 0; x++) {
				int originRed = (pixels[pixel] >> 16 & 0xff) * opacity;
				int originGreen = (pixels[pixel] >> 8 & 0xff) * opacity;
				int oritinBlue = (pixels[pixel] & 0xff) * opacity;
				int blindedColor = (red + originRed >> 8 << 16) + (green + originGreen >> 8 << 8)
						+ (blue + oritinBlue >> 8);
				pixels[pixel++] = blindedColor;
			}

			pixel += xOffset;
		}
	}

	public static void initDrawingArea(int h, int w, int ai[], float depth[]) {
		depthBuffer = depth;
		pixels = ai;
		width = w;
		height = h;
		setBounds(0, 0, w, h);
	}

	public static void method335(int i, int j, int k, int l, int i1, int k1) {
		if (k1 < topX) {
			k -= topX - k1;
			k1 = topX;
		}
		if (j < topY) {
			l -= topY - j;
			j = topY;
		}
		if (k1 + k > bottomX) {
			k = bottomX - k1;
		}
		if (j + l > bottomY) {
			l = bottomY - j;
		}
		int l1 = 256 - i1;
		int i2 = (i >> 16 & 0xff) * i1;
		int j2 = (i >> 8 & 0xff) * i1;
		int k2 = (i & 0xff) * i1;
		int k3 = width - k;
		int l3 = k1 + j * width;
		for (int i4 = 0; i4 < l; i4++) {
			for (int j4 = -k; j4 < 0; j4++) {
				int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				int j3 = (pixels[l3] & 0xff) * l1;
				int k4 = (i2 + l2 >> 8 << 16) + (j2 + i3 >> 8 << 8) + (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}

			l3 += k3;
		}
	}

	public static void method338(int i, int j, int k, int l, int i1, int j1) {
		method340(l, i1, i, k, j1);
		method340(l, i1, i + j - 1, k, j1);

		if (j >= 3) {
			method342(l, j1, k, i + 1, j - 2);
			method342(l, j1 + i1 - 1, k, i + 1, j - 2);
		}
	}

	private static void method340(int i, int j, int k, int l, int i1) {
		if (k < topY || k >= bottomY) {
			return;
		}
		if (i1 < topX) {
			j -= topX - i1;
			i1 = topX;
		}
		if (i1 + j > bottomX) {
			j = bottomX - i1;
		}
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * width;
		for (int j3 = 0; j3 < j; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}

	}

	private static void method341(int i, int j, int k, int l) {
		if (l < topX || l >= bottomX) {
			return;
		}
		if (i < topY) {
			k -= topY - i;
			i = topY;
		}
		if (i + k > bottomY) {
			k = bottomY - i;
		}
		int j1 = l + i * width;
		for (int k1 = 0; k1 < k; k1++) {
			pixels[j1 + k1 * width] = j;
		}

	}

	private static void method342(int i, int j, int k, int l, int i1) {
		if (j < topX || j >= bottomX) {
			return;
		}
		if (l < topY) {
			i1 -= topY - l;
			l = topY;
		}
		if (l + i1 > bottomY) {
			i1 = bottomY - l;
		}
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * width;
		for (int j3 = 0; j3 < i1; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}

	}

	public static void setAllPixelsToZero() {
		int i = width * height;
		if (i > pixels.length - 1) {
			i = pixels.length - 1;
		}
		for (int j = 0; j < i; j++) {
			pixels[j] = 0;
			depthBuffer[j] = Float.MAX_VALUE;
		}

	}

	public static void setBounds(int posX, int posY, int width, int height) {
		if (posX < 0) {
			posX = 0;
		}

		if (posY < 0) {
			posY = 0;
		}

		if (width > Canvas2D.width) {
			width = Canvas2D.width;
		}

		if (height > Canvas2D.height) {
			height = Canvas2D.height;
		}

		Canvas2D.topX = posX;
		Canvas2D.topY = posY;
		Canvas2D.bottomX = width;
		Canvas2D.bottomY = height;
		Canvas2D.centerX = bottomX - 0;
		Canvas2D.centerY = bottomX / 2;
		middleY = bottomY / 2;
	}

	public Canvas2D() {
	}

}