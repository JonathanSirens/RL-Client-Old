package org.ikov.client.cache.definition;

import org.ikov.client.FrameReader;
import org.ikov.client.cache.Archive;
import org.ikov.client.io.ByteBuffer;

public final class Animation {

	public static void unpackConfig(Archive streamLoader)
	{
		ByteBuffer stream = new ByteBuffer(streamLoader.get("seq.dat"));
		int length = stream.getUnsignedShort();
		if(cache == null)
			cache = new Animation[length];
		for(int j = 0; j < length; j++)
		{
			if(cache[j] == null)
				cache[j] = new Animation();
			cache[j].readValues(stream);
			/*
			* Zulrah
			*/ 
			if(j == 692) {
				
			}

			if(j == 5061) {
				cache[j].frameCount = 13;
				cache[j].frameIDs = new int[] { 19267601, 19267602, 19267603, 19267604, 19267605, 19267606, 19267607, 19267606, 19267605, 19267604, 19267603, 19267602, 19267601, };
				cache[j].delays = new int[] { 4, 3, 3, 4, 10, 10, 15, 10, 10, 4, 3, 3, 4, };
				//cache[j].animationFlowControl = new int[] { 1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 9999999, };
				cache[j].forcedPriority = 6;
				cache[j].leftHandItem = 0;
				cache[j].rightHandItem = 13438;
				cache[j].delayType = 1;
				cache[j].loopDelay = -1;
				cache[j].oneSquareAnimation = false;
				cache[j].forcedPriority = 5;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = -1;
				cache[j].priority = -1;
            }
			if (j == 5070) {
				cache[j] = new Animation();
				cache[j].frameCount = 9;
				cache[j].loopDelay = -1;
				cache[j].forcedPriority = 5;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
				cache[j].oneSquareAnimation = false;
				cache[j].frameIDs = new int[] { 11927608, 11927625, 11927598, 11927678, 11927582, 11927600, 11927669,
						11927597, 11927678 };
				cache[j].delays = new int[] { 5, 4, 4, 4, 5, 5, 5, 4, 4 };
			}
			if(j == 876) {
				cache[j].frameCount = 9;
				cache[j].frameIDs = new int[] { 118751240, 118751239, 118751235, 118751234, 118751237, 118751238, 118751232, 118751233, 118751236 };
				cache[j].delays = new int[] { 5, 5, 5, 5, 4, 4, 4, 4, 4 };
			}
			if (j == 1720) {
				cache[j].frameCount = 16;
				cache[j].frameIDs = new int[] {18087977, 18087978, 18087979, 18087980, 18087981, 18087969, 18087970, 18087971, 18087972, 18087973, 18087979, 18087978, 18087977, 18087976, 18087975, 18087974};
				cache[j].frameIDs2 = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				cache[j].delays = new int[] {3, 2, 3, 6, 2, 2, 5, 5, 5, 5, 3, 2, 3, 3, 4, 4};
				cache[j].loopDelay = -1;
				cache[j].animationFlowControl = new int[] {1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 127, 9999999};
				cache[j].oneSquareAnimation = false;
				cache[j].forcedPriority = 6;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = 21589;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 2;
				cache[j].priority = 2;
				cache[j].delayType = 2;
			}
			if (j == 7084) {
				cache[j].frameCount = 36;
				cache[j].frameIDs = new int[] {21495808, 21495808, 21495815, 21495815, 21495816, 21495816, 21495817, 21495817, 21495818, 21495818, 21495819, 21495819, 21495819, 21495819, 21495818, 21495818, 21495817, 21495817, 21495817, 21495817, 21495818, 21495818, 21495819, 21495819, 21495819, 21495819, 21495818, 21495818, 21495817, 21495817, 21495816, 21495816, 21495815, 21495815, 21495808, 21495808};
				cache[j].frameIDs2 = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				cache[j].delays = new int[] {4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
				cache[j].loopDelay = -1;
				cache[j].animationFlowControl = null;
				cache[j].oneSquareAnimation = false;
				cache[j].forcedPriority = 5;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
			}
			if (j == 5461) {
				cache[j].frameCount = 8;
				cache[j].frameIDs = new int[] { 93716480, 93716481, 93716482, 93716483, 93716484, 93716485, 93716486, 93716487, };
				cache[j].delays = new int[] { 4, 4, 4, 4, 4, 4, 4, 4, };
			}
			if (j == 5069) {
				cache[j].frameCount = 15;
				cache[j].loopDelay = -1;
				cache[j].forcedPriority = 6;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 1;
				cache[j].oneSquareAnimation = false;
				cache[j].frameIDs = new int[] { 11927613, 11927599, 11927574, 11927659, 11927676, 11927562, 11927626,
						11927628, 11927684, 11927647, 11927602, 11927576, 11927586, 11927653, 11927616 };
				cache[j].delays = new int[] { 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5 };
			}
			if (j == 5072) {
				cache[j].frameCount = 21;
				cache[j].loopDelay = 1;
				cache[j].forcedPriority = 8;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
				cache[j].oneSquareAnimation = false;
				cache[j].frameIDs = new int[] { 11927623, 11927595, 11927685, 11927636, 11927670, 11927579, 11927664,
						11927666, 11927661, 11927673, 11927633, 11927624, 11927555, 11927588, 11927692, 11927667, 11927556,
						11927630, 11927695, 11927643, 11927640 };
				cache[j].delays = new int[] { 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
			}
			if (j == 7083) {
				cache[j].frameCount = 16;
				cache[j].frameIDs = new int[] {18087977, 18087978, 18087979, 18087980, 18087981, 18087969, 18087970, 18087971, 18087972, 18087973, 18087979, 18087978, 18087977, 18087976, 18087975, 18087974};
				cache[j].frameIDs2 = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				cache[j].delays = new int[] {3, 2, 3, 6, 2, 2, 5, 5, 5, 5, 3, 2, 3, 3, 4, 4};
				cache[j].loopDelay = -1;
				cache[j].animationFlowControl = new int[] {1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 127, 9999999};
				cache[j].oneSquareAnimation = false;
				cache[j].forcedPriority = 6;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = 21586;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 2;
				cache[j].priority = 2;
				cache[j].delayType = 2;
			}
			if (j == 1714) {
				cache[j].frameCount = 18;
				cache[j].frameIDs = new int[] {31522826, 31522817, 31522825, 31522818, 31522831, 31522821, 31522828, 31522822, 31522823, 31522830, 31522824, 31522829, 31522832, 31522827, 31522819, 31522816, 31522820, 31522828};
				cache[j].frameIDs2 = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				cache[j].delays = new int[] {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6};
				cache[j].loopDelay = -1;
				cache[j].animationFlowControl = null;
				cache[j].oneSquareAnimation = false;
				cache[j].forcedPriority = 5;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
			}
			if (j == 5073) {
				cache[j].frameCount = 21;
				cache[j].loopDelay = -1;
				cache[j].forcedPriority = 9;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
				cache[j].oneSquareAnimation = false;
				cache[j].frameIDs = new int[] { 11927640, 11927643, 11927695, 11927630, 11927556, 11927667, 11927692,
						11927588, 11927555, 11927624, 11927633, 11927673, 11927661, 11927666, 11927664, 11927579, 11927670,
						11927636, 11927685, 11927595, 11927623 };
				cache[j].delays = new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2 };
			}
			if (j == 5806) {
				cache[j].frameCount = 55;
				cache[j].loopDelay = -1;
				cache[j].forcedPriority = 6;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
				cache[j].oneSquareAnimation = true;
				cache[j].frameIDs = new int[] { 11927612, 11927677, 11927615, 11927573, 11927618, 11927567, 11927564,
						11927606, 11927675, 11927657, 11927690, 11927583, 11927672, 11927552, 11927563, 11927683, 11927639,
						11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656, 11927660, 11927629,
						11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656, 11927660, 11927635,
						11927668, 11927614, 11927560, 11927687, 11927577, 11927569, 11927557, 11927569, 11927577, 11927687,
						11927560, 11927651, 11927611, 11927680, 11927622, 11927691, 11927571, 11927601 };
				cache[j].delays = new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
					4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 3 };
			}
			if(j == 692) {
				cache[j].frameCount = 3;
				cache[j].loopDelay = -1;
				cache[j].forcedPriority = 5;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
				cache[j].oneSquareAnimation = true;
				cache[j].frameIDs = new int[] { 67436544, 67436545, 67436546};
				cache[j].delays = new int[] { 2, 2, 2};
			}
			if (j == 5807) {
				cache[j].frameCount = 53;
				cache[j].loopDelay = -1;
				cache[j].forcedPriority = 6;
				cache[j].leftHandItem = -1;
				cache[j].rightHandItem = -1;
				cache[j].frameStep = 99;
				cache[j].resetWhenWalk = 0;
				cache[j].priority = 0;
				cache[j].delayType = 2;
				cache[j].oneSquareAnimation = true;
				cache[j].frameIDs = new int[] { 11927612, 11927677, 11927615, 11927573, 11927618, 11927567, 11927564,
						11927606, 11927675, 11927657, 11927690, 11927583, 11927672, 11927552, 11927563, 11927683, 11927639,
						11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656, 11927660, 11927629,
						11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656, 11927604, 11927637,
						11927688, 11927696, 11927681, 11927605, 11927681, 11927696, 11927688, 11927637, 11927604, 11927656,
						11927611, 11927680, 11927622, 11927691, 11927571, 11927601 };
				cache[j].delays = new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
						4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 3 };
			}
		}
	}

	public int getFrameLength(int i)
	{
		if(i > delays.length)
			return 1;
		int j = delays[i];
		if(j == 0)
		{
			FrameReader reader = FrameReader.forId(frameIDs[i]);
			if(reader != null)
				j = delays[i] = reader.displayLength;
		}
		if(j == 0)
			j = 1;
		return j;
	}

	public void readValues(ByteBuffer stream)
	{
		do {
			int i = stream.getUnsignedByte();
			if(i == 0)
				break;
			if(i == 1) {
				frameCount = stream.getUnsignedShort();
				frameIDs = new int[frameCount];
				frameIDs2 = new int[frameCount];
				delays = new int[frameCount];
				for(int i_ = 0; i_ < frameCount; i_++){
					frameIDs[i_] = stream.getIntLittleEndian();
					frameIDs2[i_] = -1;
				}
				for(int i_ = 0; i_ < frameCount; i_++)
					delays[i_] = stream.getUnsignedByte();
			}
			else if(i == 2)
				loopDelay = stream.getUnsignedShort();
			else if(i == 3) {
				int k = stream.getUnsignedByte();
				animationFlowControl = new int[k + 1];
				for(int l = 0; l < k; l++)
					animationFlowControl[l] = stream.getUnsignedByte();
				animationFlowControl[k] = 0x98967f;
			}
			else if(i == 4)
				oneSquareAnimation = true;
			else if(i == 5)
				forcedPriority = stream.getUnsignedByte();
			else if(i == 6)
				leftHandItem = stream.getUnsignedShort();
			else if(i == 7)
				rightHandItem = stream.getUnsignedShort();
			else if(i == 8)
				frameStep = stream.getUnsignedByte();
			else if(i == 9)
				resetWhenWalk = stream.getUnsignedByte();
			else if(i == 10)
				priority = stream.getUnsignedByte();
			else if(i == 11)
				delayType = stream.getUnsignedByte();
			else
				System.out.println("Unrecognized seq.dat config code: "+i);
		} while(true);
		if(frameCount == 0)
		{
			frameCount = 1;
			frameIDs = new int[1];
			frameIDs[0] = -1;
			frameIDs2 = new int[1];
			frameIDs2[0] = -1;
			delays = new int[1];
			delays[0] = -1;
		}
		if(resetWhenWalk == -1)
			if(animationFlowControl != null)
				resetWhenWalk = 2;
			else
				resetWhenWalk = 0;
		if(priority == -1)
		{
			if(animationFlowControl != null)
			{
				priority = 2;
				return;
			}
			priority = 0;
		}
	}
	
	public void immitate (Animation anim) {
		loopDelay = anim.loopDelay;
		oneSquareAnimation = anim.oneSquareAnimation;
		forcedPriority = anim.forcedPriority;
		leftHandItem = anim.leftHandItem;
		rightHandItem = anim.rightHandItem;
		frameStep = anim.frameStep;
		resetWhenWalk = anim.resetWhenWalk;
		priority = anim.priority;
		delayType = anim.delayType;
	}	
	
	public void sysOut() {
		System.out.println("loopDelay "+loopDelay);
		System.out.println("oneSquareAnimation "+oneSquareAnimation);
		System.out.println("forcedPriority "+forcedPriority);
		System.out.println("leftHandItem "+leftHandItem);
		System.out.println("rightHandItem "+rightHandItem);
		System.out.println("frameStep "+frameStep);
		System.out.println("resetWhenWalk "+resetWhenWalk);
		System.out.println("priority "+priority);
		System.out.println("delayType "+delayType);
	}
	
	private Animation()
	{
		loopDelay = -1;
		oneSquareAnimation = false;
		forcedPriority = 5;
		leftHandItem = -1;
		rightHandItem = -1;
		frameStep = 99;
		resetWhenWalk = -1;
		priority = -1;
		delayType = 2;
	}
	public static Animation cache[];
	public int frameCount;
	public int frameIDs[];
	public int frameIDs2[];
	public int[] delays;
	public int loopDelay;
	public int animationFlowControl[];
	public boolean oneSquareAnimation;
	public int forcedPriority;
	public int leftHandItem;
	public int rightHandItem;
	public int frameStep;
	public int resetWhenWalk;
	public int priority;
	public int delayType;
	public static int anInt367;
}