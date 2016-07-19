package org.runelive.client.world.fog;

import org.runelive.client.Client;
import org.runelive.client.graphics.Canvas2D;

/**
 * Created by Evan2 on 15/07/2016.
 */
public class FogProcessor {

    public static void render(int begin, int end, int rgb) {
        for (int depth = Canvas2D.depthBuffer.length - 1; depth >= 0; depth--) {
            if (Canvas2D.depthBuffer == null) {
                continue;
            }
            if (Canvas2D.depthBuffer[depth] >= end) {
                Canvas2D.pixels[depth] = rgb;
            } else if (Canvas2D.depthBuffer[depth] >= begin) {
                int alpha = ((int) Canvas2D.depthBuffer[depth] - begin) / 3;
                int src = ((rgb & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((rgb & 0xff00) * alpha >> 8 & 0xff00);
                alpha = 256 - alpha;
                int dst = Canvas2D.pixels[depth];
                dst = ((dst & 0xff00ff) * alpha >> 8 & 0xff00ff) + ((dst & 0xff00) * alpha >> 8 & 0xff00);
                Canvas2D.pixels[depth] = src + dst;
            }
        }
    }
}
