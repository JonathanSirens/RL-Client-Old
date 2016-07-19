package org.runelive.client.graphics;

import org.runelive.client.Client;

/**
 * Created by Evan2 on 17/07/2016.
 */
public class Snowflake {

    public Snowflake(int x, int y) {
        this(0, x, y);
        this.alpha = 256;
    }

    public Snowflake(int type, int x, int y, int radius) {
        this(type, x, y);
        this.radius = radius;
        this.alpha = 100 + random(100);
    }

    private Snowflake(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.moving = true;
        this.lifespan = 250 + random(500);
        this.fallType = 1;//this.type == 0 || Misc.random(100) > 20 ? 1 : 0;
    }

    public void draw(int x, int y) {
        if (this.type == 0) {
            //Client.cacheSprite[1119].drawARGBSprite(this.x + x, this.y + y, this.alpha);
        } else if (this.type == 1) {
            Canvas2D.fillCircle(this.x + x - (this.radius / 2), this.y + y - (this.radius / 2), radius, 0xFFFFFF, this.alpha);
        }
    }

    public void reset() {
        this.x = random(765);
        this.y = -10;
        this.cycle = 0;
        this.moving = true;
        this.touched = false;
        this.lifespan = 250 + random(500);
        this.type = random(100) < 25 ? 0 : 1;
        if (this.type == 1) {
            this.radius = 3 + random(4);
            this.alpha = 100 + random(100);
        } else {
            this.alpha = 256;
        }
    }

    public static int random(int range) {
        return (int) (Math.random() * (range + 1));
    }

    public void adjustX(int x) {
        this.x += x;
    }

    public void adjustY(int y) {
        this.y += y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getType() {
        return this.type;
    }

    public int getCycle() {
        return this.cycle;
    }

    public void resetCycle() {
        this.cycle = 0;
    }

    public void cycle() {
        this.cycle++;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void adjustAlpha(int alpha) {
        this.alpha += alpha;
        if (this.alpha < 0) {
            this.alpha = 0;
        }
        if (this.alpha > 256) {
            this.alpha = 256;
        }
    }

    public int getAlpha() {
        return this.alpha;
    }

    public boolean isMelted() {
        return this.alpha == 0 && !this.moving;
    }

    public int getLifespan() {
        return this.lifespan;
    }

    public boolean isMelting() {
        return this.melting;
    }

    public void setMelting(boolean melting) {
        this.melting = melting;
    }

    public boolean touched() {
        if (Client.instance.inCircle(this.x - (this.type == 1 ? this.radius * 2 - this.radius / 2: 0), this.y - (this.type == 1 ? this.radius * 2 - this.radius / 2 : 0), Client.instance.mouseX, Client.instance.mouseY - 54, this.type == 0 ? 15 : radius)) {
            return true;
        }
        return false;
    }

    public boolean wasTouched() {
        return this.touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public int getFallType() {
        return this.fallType;
    }

    private boolean touched;
    private boolean melting;
    private boolean moving;
    private int x;
    private int y;
    private int type;
    private int cycle;
    private int radius;
    private int alpha;
    private int lifespan;
    private int fallType;

}