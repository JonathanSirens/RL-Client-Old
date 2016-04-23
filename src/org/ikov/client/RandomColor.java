package org.ikov.client;

public class RandomColor {
	
private static int currentLoadingColor = -1;
private static int nextLoadingColor = -1;
private static long startTime = 0L;
private static long colorStart = 0L;
public static int currentColour;

	public static void process() {	
		currentColour = processRandomColour();
	}
	
private static int randomColor() {
	
int red = (int)(Math.random() * 17.0D);
int green = (int)(Math.random() * 17.0D);
int blue = (int)(Math.random() * 17.0D);
int type = (int)(Math.random() * 8.0D);
/*  20: 19 */     if (type == 0) {
/*  21: 20 */       type |= 1 << (int)(Math.random() * 3.0D);
/*  22: 21 */     } else if (type == 7) {
/*  23: 22 */       type &= (1 << (int)(Math.random() * 3.0D) ^ 0xFFFFFFFF);
/*  24:    */     }
/*  25: 24 */     if ((type & 0x1) != 0) {
/*  26: 25 */       red += 196 + (int)(Math.random() * 33.0D);
/*  27:    */     }
/*  28: 27 */     if ((type & 0x2) != 0) {
/*  29: 28 */       green += 196 + (int)(Math.random() * 33.0D);
/*  30:    */     }
/*  31: 30 */     if ((type & 0x4) != 0) {
/*  32: 31 */       blue += 196 + (int)(Math.random() * 33.0D);
/*  33:    */     }
/*  34: 33 */     return red << 16 | green << 8 | blue;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private static int blend(int dst, int src, int src_alpha)
/*  38:    */   {
/*  39: 37 */     if (src_alpha <= 0) {
/*  40: 38 */       return dst;
/*  41:    */     }
/*  42: 40 */     if (src_alpha >= 255) {
/*  43: 41 */       return src;
/*  44:    */     }
/*  45: 43 */     int src_delta = 255 - src_alpha;
/*  46: 44 */     return ((0xFF00FF00 & (0xFF00FF & src) * src_alpha | 0xFF0000 & 
/*  47: 45 */       (src & 0xFF00) * src_alpha) >>> 8) + (
/*  48: 46 */       (0xFF0000 & src_delta * (dst & 0xFF00) | src_delta * (
/*  49: 47 */       dst & 0xFF00FF) & 0xFF00FF00) >>> 8);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private static int processRandomColour()
/*  53:    */   {
/*  54: 51 */     long time = System.currentTimeMillis();
/*  55: 52 */     if (startTime == 0L) {
/*  56: 53 */       startTime = time;
/*  57:    */     }
/*  58: 54 */     int n = -1;
/*  59: 55 */     long elapse = (time - startTime) / 20L;
/*  60: 56 */     if (n == -1)
/*  61:    */     {
/*  62: 57 */       n = (int)(elapse % 2000L);
/*  63: 58 */       if (n > 1000) {
/*  64: 59 */         n = 2000 - n;
/*  65:    */       }
/*  66:    */     }
/*  67: 62 */     if (n < 0) {
/*  68: 63 */       n = 0;
/*  69: 64 */     } else if (n > 1000) {
/*  70: 65 */       n = 1000;
/*  71:    */     }
/*  72: 66 */     if ((colorStart == 0L) || (nextLoadingColor == -1))
/*  73:    */     {
/*  74: 67 */       colorStart = time;
/*  75: 68 */       nextLoadingColor = randomColor();
/*  76:    */     }
/*  77: 70 */     if (currentLoadingColor == -1) {
/*  78: 71 */       currentLoadingColor = 3329330;
/*  79:    */     }
/*  80: 72 */     long colorElapse = (time - colorStart) / 20L;
/*  81: 73 */     int alpha = (int)(colorElapse * 255L / 200L);
/*  82: 74 */     int color = blend(currentLoadingColor, nextLoadingColor, alpha);
/*  83: 75 */     if (alpha >= 255)
/*  84:    */     {
/*  85: 76 */       currentLoadingColor = nextLoadingColor;
/*  86: 77 */       nextLoadingColor = -1;
/*  87:    */     }
/*  88: 79 */     return rgbToHSL(color);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static int rgbToHSL(int color)
/*  92:    */   {
/*  93: 83 */     double r = (color >> 16 & 0xFF) / 256.0D;
/*  94: 84 */     double g = (color >> 8 & 0xFF) / 256.0D;
/*  95: 85 */     double b = (color & 0xFF) / 256.0D;
/*  96: 86 */     double red_val1 = r;
/*  97: 87 */     if (g < red_val1) {
/*  98: 88 */       red_val1 = g;
/*  99:    */     }
/* 100: 90 */     if (b < red_val1) {
/* 101: 91 */       red_val1 = b;
/* 102:    */     }
/* 103: 93 */     double red_val2 = r;
/* 104: 94 */     if (g > red_val2) {
/* 105: 95 */       red_val2 = g;
/* 106:    */     }
/* 107: 97 */     if (b > red_val2) {
/* 108: 98 */       red_val2 = b;
/* 109:    */     }
/* 110:100 */     double hueCalc = 0.0D;
/* 111:101 */     double satCalc = 0.0D;
/* 112:102 */     double lightCalc = (red_val1 + red_val2) / 2.0D;
/* 113:103 */     if (red_val1 != red_val2)
/* 114:    */     {
/* 115:104 */       if (lightCalc < 0.5D) {
/* 116:105 */         satCalc = (red_val2 - red_val1) / (red_val2 + red_val1);
/* 117:    */       }
/* 118:107 */       if (lightCalc >= 0.5D) {
/* 119:108 */         satCalc = (red_val2 - red_val1) / (2.0D - red_val2 - red_val1);
/* 120:    */       }
/* 121:110 */       if (r == red_val2) {
/* 122:111 */         hueCalc = (g - b) / (red_val2 - red_val1);
/* 123:112 */       } else if (g == red_val2) {
/* 124:113 */         hueCalc = 2.0D + (b - r) / (red_val2 - red_val1);
/* 125:114 */       } else if (b == red_val2) {
/* 126:115 */         hueCalc = 4.0D + (r - g) / (red_val2 - red_val1);
/* 127:    */       }
/* 128:    */     }
/* 129:118 */     hueCalc /= 6.0D;
/* 130:119 */     int hue = (int)(hueCalc * 256.0D);
/* 131:120 */     int saturation = (int)(satCalc * 256.0D);
/* 132:121 */     int lightness = (int)(lightCalc * 256.0D);
/* 133:122 */     if (saturation < 0) {
/* 134:123 */       saturation = 0;
/* 135:124 */     } else if (saturation > 255) {
/* 136:125 */       saturation = 255;
/* 137:    */     }
/* 138:127 */     if (lightness < 0) {
/* 139:128 */       lightness = 0;
/* 140:129 */     } else if (lightness > 255) {
/* 141:130 */       lightness = 255;
/* 142:    */     }
/* 143:132 */     int divisor = 1;
/* 144:133 */     if (lightCalc > 0.5D) {
/* 145:134 */       divisor = (int)((1.0D - lightCalc) * satCalc * 512.0D);
/* 146:    */     } else {
/* 147:136 */       divisor = (int)(lightCalc * satCalc * 512.0D);
/* 148:    */     }
/* 149:138 */     if (divisor < 1) {
/* 150:139 */       divisor = 1;
/* 151:    */     }
/* 152:141 */     hue = (int)(hueCalc * divisor);
/* 153:142 */     int hueOffset = hue;
/* 154:143 */     int saturationOffset = saturation;
/* 155:144 */     int lightnessOffset = lightness;
/* 156:145 */     return getHSLValue(hueOffset, saturationOffset, lightnessOffset);
/* 157:    */   }
/* 158:    */   
/* 159:    */   private static int getHSLValue(int hue, int saturation, int lightness)
/* 160:    */   {
/* 161:149 */     if (lightness > 179) {
/* 162:150 */       saturation /= 2;
/* 163:    */     }
/* 164:152 */     if (lightness > 192) {
/* 165:153 */       saturation /= 2;
/* 166:    */     }
/* 167:155 */     if (lightness > 217) {
/* 168:156 */       saturation /= 2;
/* 169:    */     }
/* 170:158 */     if (lightness > 243) {
/* 171:159 */       saturation /= 2;
/* 172:    */     }
/* 173:161 */     return (hue / 4 << 10) + (saturation / 32 << 7) + lightness / 2;
/* 174:    */   }
/* 175:    */ }



/* Location:           C:\Users\Jonathan\Desktop\old ikov.jar

 * Qualified Name:     RandomColor

 * JD-Core Version:    0.7.0.1

 */