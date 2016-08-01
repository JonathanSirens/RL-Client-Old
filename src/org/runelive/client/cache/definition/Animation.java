package org.runelive.client.cache.definition;

import org.runelive.client.FrameReader;
import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;

public final class Animation {

    public static void unpackConfig(Archive streamLoader) {
        ByteBuffer stream = new ByteBuffer(streamLoader.get("seq.dat"));
        int length = stream.getUnsignedShort();
        if (cache == null)
            cache = new Animation[length];
        for (int j = 0; j < length; j++) {
            if (cache[j] == null)
                cache[j] = new Animation();
            cache[j].readValues(stream);
            /*
			 * Zulrah
			 */
            if (j == 692) {

            }
            switch (j) {
                case 4550:
                    cache[j] = new Animation();
                    // cache[j].id = 15448;
                    // cache[j].fileId = 13;
                    cache[j].frameCount = 60;
                    cache[j].forcedPriority = 6;
                    cache[j].resetWhenWalk = 2;
                    cache[j].priority = 1;
                    cache[j].delays = new int[]{1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4,
                            4, 4, 3};
                    cache[j].frameIDs = new int[]{852053, 852207, 852152, 852151, 852072, 852107, 852142, 852176, 852058,
                            852116, 852104, 852070, 852007, 852165, 852140, 852164, 852051, 852195, 851983, 852242, 852189,
                            852047, 852113, 852200, 851976, 852035, 852114, 852112, 852126, 852046, 852040, 852004, 852218,
                            852054, 852132, 852083, 852236, 852228, 852182, 852052, 852156, 851989, 852111, 852124, 852031,
                            852108, 852171, 852010, 852133, 852023, 852069, 852224, 852139, 852062, 852059, 852038, 852138,
                            852000, 852146, 852053};
                    break;
                //Bor
                case 8752:
                    cache[j].frameCount = 32;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{141557823, 141557986, 141557896, 141557779, 141557955, 141557928, 141557964, 141557968, 141557953, 141557884, 141557852, 141557950, 141557869, 141557806, 141557770, 141557824, 141558012, 141557854, 141557803, 141557818, 141557822, 141558026, 141558004, 141557985, 141557904, 141557910, 141557826, 141557981, 141557971, 141557899, 141557858, 141557845};
                    break;
                case 8753:
                    cache[j].frameCount = 48;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{141557870, 141557809, 141557901, 141557864, 141557932, 141557876, 141557831, 141557817, 141557984, 141557881, 141558008, 141557882, 141558017, 141557773, 141558009, 141557885, 141557870, 141557809, 141557901, 141558024, 141557778, 141557876, 141557831, 141557817, 141557984, 141557881, 141558008, 141557882, 141558017, 141557773, 141558009, 141557885, 141557933, 141557895, 141557862, 141557812, 141557942, 141557919, 141557893, 141557918, 141557859, 141558003, 141557857, 141557914, 141557927, 141557925, 141557878, 141557960};
                    break;
                case 8757:
                    cache[j].frameCount = 28;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{141557920, 141557865, 141557900, 141557856, 141557872, 141558057, 141557877, 141557792, 141557966, 141557906, 141557958, 141557979, 141557974, 141557975, 141557836, 141558020, 141557967, 141558041, 141558034, 141558039, 141557996, 141557946, 141557867, 141558031, 141558049, 141558050, 141557866, 141557920};
                    break;
                case 8754:
                    cache[j].frameCount = 18;
                    cache[j].priority = 7;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{141557879, 141557903, 141557892, 141557863, 141557915, 141557938, 141557990, 141557840, 141557814, 141557772, 141557924, 141558023, 141558014, 141557952, 141557763, 141558058, 141557992, 141557879};
                    break;
                case 8756:
                    cache[j].frameCount = 41;
                    cache[j].priority = 10;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 25, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 50};
                    cache[j].frameIDs = new int[]{141558040, 141557784, 141557843, 141557861, 141557828, 141557931, 141557771, 141557761, 141557907, 141557998, 141558036, 141558052, 141557994, 141557978, 141557800, 141558030, 141558053, 141557875, 141558038, 141557851, 141558015, 141557880, 141557951, 141557830, 141557948, 141557948, 141557988, 141557890, 141558056, 141557956, 141557874, 141557911, 141557853, 141557825, 141557775, 141557886, 141557807, 141557983, 141558005, 141557940, 141557908};
                    break;
                case 8755:
                    cache[j].frameCount = 15;
                    cache[j].priority = 6;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{141557848, 141557820, 141557797, 141557945, 141557977, 141558019, 141558000, 141557954, 141557929, 141557804, 141557849, 141558044, 141557980, 141558033, 141557848};
                    break;
                case 7218:
                    cache[7218].frameCount = 18;
                    cache[7218].frameIDs = new int[]{121110531, 121110603, 121110555, 121110552, 121110586, 121110596, 121110563, 121110599, 121110566, 121110585, 121110542, 121110538, 121110570, 121110617, 121110600, 121110559, 121110537, 121110568};
                    cache[7218].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[7218].delays = new int[]{4, 4, 4, 4, 4, 5, 5, 5, 5, 10, 4, 4, 4, 5, 5, 5, 5, 5};
                    cache[7218].loopDelay = -1;
                    cache[7218].animationFlowControl = null;
                    cache[7218].oneSquareAnimation = false;
                    cache[7218].forcedPriority = 5;
                    cache[7218].leftHandItem = -1;
                    cache[7218].rightHandItem = -1;
                    cache[7218].frameStep = 99;
                    cache[7218].resetWhenWalk = 0;
                    cache[7218].priority = 2;
                    cache[7218].delayType = 1;
                    break;
                case 7219:
                    cache[7219].frameCount = 16;
                    cache[7219].frameIDs = new int[]{121110539, 121110593, 121110571, 121110619, 121110532, 121110573, 121110612, 121110604, 121110605, 121110572, 121110529, 121110549, 121110618, 121110616, 121110554};
                    cache[7219].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[7219].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5};
                    cache[7219].loopDelay = -1;
                    cache[7219].animationFlowControl = null;
                    cache[7219].oneSquareAnimation = true;
                    cache[7219].forcedPriority = 5;
                    cache[7219].leftHandItem = -1;
                    cache[7219].rightHandItem = -1;
                    cache[7219].frameStep = 99;
                    cache[7219].resetWhenWalk = 0;
                    cache[7219].priority = 1;
                    cache[7219].delayType = 1;
                    break;
                case 7220:
                    cache[7220].frameCount = 13;
                    cache[7220].frameIDs = new int[]{121110558, 121110528, 121110543, 121110584, 121110587, 121110534, 121110575, 121110533, 121110546, 121110620, 121110536, 121110624, 121110569};
                    cache[7220].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[7220].delays = new int[]{4, 5, 4, 5, 5, 5, 4, 5, 4, 5, 5, 5, 4};
                    cache[7220].loopDelay = -1;
                    cache[7220].animationFlowControl = null;
                    cache[7220].oneSquareAnimation = false;
                    cache[7220].forcedPriority = 5;
                    cache[7220].frameStep = 99;
                    cache[7220].resetWhenWalk = 0;
                    cache[7220].priority = 0;
                    cache[7220].delayType = 1;
                    cache[7220].rightHandItem = -1;
                    cache[7220].leftHandItem = -1;
                    cache[7220].rightHandItem = -1;
                    break;
                case 7221:
                    cache[7221].frameCount = 16;
                    cache[7221].frameIDs = new int[]{121110564, 121110589, 121110623, 121110553, 121110541, 121110591, 121110615, 121110540, 121110622, 121110597, 121110625, 121110590, 121110577, 121110530, 121110592, 121110560};
                    cache[7221].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[7221].delays = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
                    cache[7221].loopDelay = -1;
                    cache[7221].animationFlowControl = null;
                    cache[7221].oneSquareAnimation = false;
                    cache[7221].forcedPriority = 5;
                    cache[7221].leftHandItem = -1;
                    cache[7221].rightHandItem = -1;
                    cache[7221].frameStep = 99;
                    cache[7221].resetWhenWalk = 0;
                    cache[7221].priority = 0;
                    cache[7221].delayType = 1;
                    break;

                case 7222:
                    cache[7222].frameCount = 15;
                    cache[7222].frameIDs = new int[]{121110557, 121110574, 121110579, 121110547, 121110602, 121110544, 121110561, 121110621, 121110598, 121110609, 121110556, 121110594, 121110550, 121110562, 121110610};
                    cache[7222].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[7222].delays = new int[]{4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[7222].loopDelay = -1;
                    cache[7222].animationFlowControl = null;
                    cache[7222].oneSquareAnimation = false;
                    cache[7222].forcedPriority = 5;
                    cache[7222].leftHandItem = -1;
                    cache[7222].rightHandItem = -1;
                    cache[7222].frameStep = 99;
                    cache[7222].resetWhenWalk = 0;
                    cache[7222].priority = 1;
                    cache[7222].delayType = 1;

                    break;

                case 7223:
                    cache[7223].frameCount = 16;
                    cache[7223].frameIDs = new int[]{121110578, 121110614, 121110606, 121110588, 121110613, 121110607, 121110608, 121110535, 121110595, 121110611, 121110548, 121110582, 121110565, 121110601, 121110545, 121110583};
                    cache[7223].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[7223].delays = new int[]{3, 3, 3, 2, 3, 2, 2, 2, 3, 2, 3, 2, 3, 2, 3, 2};
                    cache[7223].loopDelay = -1;
                    cache[7223].animationFlowControl = null;
                    cache[7223].oneSquareAnimation = false;
                    cache[7223].forcedPriority = 5;
                    cache[7223].leftHandItem = -1;
                    cache[7223].rightHandItem = -1;
                    cache[7223].frameStep = 99;
                    cache[7223].resetWhenWalk = 0;
                    cache[7223].priority = 0;
                    cache[7223].delayType = 1;
                    break;
                case 1005:
                    cache[j].frameCount = 21;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5};
                    cache[j].frameIDs = new int[]{95158273, 95158283, 95158286, 95158287, 95158288, 95158289, 95158290, 95158291, 95158292, 95158272, 95158274, 95158275, 95158276, 95158277, 95158278, 95158279, 95158280, 95158281, 95158282, 95158284, 95158285};
                    break;
                case 4551:
                    // cache[j].id = 15449;
                    // cache[j].fileId = 3828;
                    cache[j].frameCount = 19;
                    cache[j].forcedPriority = 6;
                    cache[j].resetWhenWalk = 2;
                    cache[j].priority = 1;
                    cache[j].delays = new int[]{15, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 7, 13, 5, 3};
                    cache[j].frameIDs = new int[]{250872027, 250871886, 250871895, 250872017, 250871909, 250871892,
                            250871963, 250871953, 250871905, 250871823, 250871882, 250872046, 250872050, 250871913,
                            250871939, 250871844, 250871877, 250871858, 250871964};
                    break;
                case 4559: // Tortured gorilla stand
                    // cache[j] = new Animation();
                    cache[j].frameCount = 19;
                    // cache[j].forcedPriority = 5;
                    cache[j].delays = new int[]{4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2};
                    cache[j].frameIDs = new int[]{121176177, 121176113, 121176100, 121176088, 121176248, 121176124,
                            121176071, 121176282, 121176209, 121176084, 121176263, 121176146, 121176278, 121176129,
                            121176207, 121176258, 121176139, 121176077, 121176250};
                    break;
                case 4552:
                    // anims[j].id = 15450;
                    // anims[j].fileId = 3828;
                    cache[j].frameCount = 10;
                    cache[j].loopDelay = 10;
                    cache[j].delays = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
                    cache[j].frameIDs = new int[]{250871880, 250871926, 250871936, 250871945, 250871941, 250871994,
                            250871873, 250871809, 250871821, 250871850};
                    break;
                case 4553: // Ganodermic beast stand
                    // cache[j].id = 15451;
                    // cache[j].fileId = 3828;
                    cache[j].frameCount = 24;
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
                    cache[j].frameIDs = new int[]{250609690, 250609706, 250609808, 250609726, 250609712, 250609765,
                            250609680, 250609705, 250609738, 250609709, 250609687, 250609748, 250609722, 250609766,
                            250609739, 250609747, 250609741, 250609771, 250609778, 250609780, 250609720, 250609727,
                            250609784, 250609814};
                    break;
                case 4554:
                    cache[j].frameCount = 18;
                    cache[j].delays = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6, 12, 4, 1, 1};
                    cache[j].frameIDs = new int[]{250871894, 250871885, 250871954, 250871891, 250871878, 250872021,
                            250872053, 250872016, 250871957, 250871974, 250872032, 250871985, 250871903, 250871908,
                            250872024, 250871890, 250871983, 250871912};
                    break;
                case 4555: // Ganodermic beast walk
                    // cache[j].id = 15451;
                    // cache[j].fileId = 3828;
                    cache[j].frameCount = 28;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                            3, 3, 3};
                    cache[j].frameIDs = new int[]{250609792, 250609730, 250609769, 250609806, 250609801, 250609760,
                            250609787, 250609749, 250609734, 250609696, 250609689, 250609685, 250609700, 250609764,
                            250609671, 250609731, 250609681, 250609774, 250609821, 250609677, 250609711, 250609742,
                            250609758, 250609703, 250609721, 250609804, 250609666, 250609737};
                    break;
                case 4556: // Ganodermic beast attack
                    // cache[j].id = 15451;
                    // cache[j].fileId = 3828;
                    cache[j].frameCount = 20;
                    cache[j].priority = 6;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{250609789, 250609819, 250609762, 250609728, 250609793, 250609818,
                            250609718, 250609704, 250609740, 250609672, 250609756, 250609686, 250609719, 250609753,
                            250609682, 250609674, 250609668, 250609708, 250609678, 250609746};
                    break;
                case 4557: // Ganodermic beast death
                    cache[j].frameCount = 43;
                    cache[j].loopDelay = 1;
                    cache[j].priority = 10;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                            6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3};
                    cache[j].frameIDs = new int[]{250609813, 250609795, 250609699, 250609754, 250609736, 250609732,
                            250609775, 250609688, 250609664, 250609683, 250609786, 250609707, 250609724, 250609768,
                            250609761, 250609697, 250609782, 250609796, 250609757, 250609805, 250609791, 250609715,
                            250609735, 250609701, 250609693, 250609817, 250609692, 250609788, 250609783, 250609670,
                            250609751, 250609752, 250609723, 250609695, 250609684, 250609716, 250609673, 250609812,
                            250609729, 250609772, 250609676, 250609820, 250609773};
                    break;
                case 4558: // Ganodermic beast defend
                    cache[j].frameCount = 23;
                    cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                    cache[j].frameIDs = new int[]{250609744, 250609798, 250609698, 250609675, 250609669, 250609750,
                            250609694, 250609807, 250609803, 250609811, 250609810, 250609714, 250609776, 250609800,
                            250609710, 250609665, 250609713, 250609691, 250609781, 250609743, 250609797, 250609777,
                            250609794};
                    break;
                case 4497:
                    // fileID = 1793
                    cache[j].frameCount = 3;
                    cache[j].frameIDs = new int[]{117506056, 117506050, 117506060,};
                    cache[j].delays = new int[]{5, 5, 5,};
                    break;

                case 4498:
                    // fileID = 1793
                    cache[j].frameCount = 12;
                    cache[j].frameIDs = new int[]{117506068, 117506063, 117506048, 117506072, 117506058, 117506074,
                            117506064, 117506066, 117506054, 117506052, 117506071, 117506055,};
                    cache[j].delays = new int[]{3, 2, 3, 3, 2, 3, 3, 3, 2, 2, 2, 2,};
                    break;

                case 4499:
                    // fileID = 1801
                    cache[j].frameCount = 10;
                    cache[j].frameIDs = new int[]{118030351, 118030343, 118030349, 118030348, 118030337, 118030353,
                            118030350, 118030347, 118030346, 118030345,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5,};
                    break;

                case 4500:
                    // fileID = 1801
                    cache[j].frameCount = 8;
                    cache[j].frameIDs = new int[]{118030336, 118030338, 118030342, 118030340, 118030352, 118030344,
                            118030339, 118030341,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5,};
                    break;

                case 4501:
                    // fileID = 1789
                    cache[j].frameCount = 19;
                    cache[j].frameIDs = new int[]{117243919, 117243911, 117243910, 117243905, 117243914, 117243915,
                            117243916, 117243917, 117243913, 117243921, 117243920, 117243907, 117243922, 117243918,
                            117243909, 117243908, 117243906, 117243904, 117243912,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 5, 5, 4, 4, 5, 5, 5, 5,};
                    break;

                case 4502:
                    // fileID = 1794
                    cache[j].frameCount = 15;
                    cache[j].frameIDs = new int[]{117571593, 117571584, 117571585, 117571586, 117571597, 117571598,
                            117571596, 117571592, 117571590, 117571589, 117571595, 117571591, 117571587, 117571588,
                            117571594,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 6, 8, 8, 8, 8, 8, 8, 8, 5, 3,};
                    break;

                case 4503:
                    // fileID = 1402
                    cache[j].frameCount = 16;
                    cache[j].frameIDs = new int[]{91881476, 91881488, 91881495, 91881479, 91881486, 91881482, 91881489,
                            91881484, 91881490, 91881497, 91881501, 91881473, 91881499, 91881477, 91881504, 91881475,};
                    cache[j].delays = new int[]{5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3,};
                    cache[j].forcedPriority = 6;
                    break;

                case 4504:
                    // fileID = 1402
                    cache[j].frameCount = 15;
                    cache[j].frameIDs = new int[]{91881493, 91881509, 91881478, 91881505, 91881483, 91881503, 91881474,
                            91881472, 91881494, 91881507, 91881481, 91881508, 91881485, 91881506, 91881496,};
                    cache[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,};
                    cache[j].forcedPriority = 6;
                    break;

                case 4505:
                    // fileID = 1402
                    cache[j].frameCount = 7;
                    cache[j].frameIDs = new int[]{91881480, 91881487, 91881492, 91881498, 91881502, 91881491, 91881500,};
                    cache[j].delays = new int[]{5, 4, 4, 4, 4, 4, 5,};
                    break;

                case 4484:
                    // fileID = 1790
                    cache[j].frameCount = 14;
                    cache[j].frameIDs = new int[]{117309461, 117309547, 117309462, 117309623, 117309548, 117309621,
                            117309454, 117309599, 117309473, 117309488, 117309559, 117309541, 117309588, 117309622,};
                    cache[j].delays = new int[]{3, 5, 7, 7, 11, 7, 7, 5, 7, 5, 6, 9, 8, 4,};
                    break;

                case 4485:
                    // fileID = 1790
                    cache[j].frameCount = 19;
                    cache[j].frameIDs = new int[]{117309460, 117309503, 117309620, 117309504, 117309533, 117309482,
                            117309527, 117309600, 117309539, 117309605, 117309518, 117309487, 117309556, 117309529,
                            117309472, 117309596, 117309465, 117309611, 117309604,};
                    cache[j].delays = new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12,
                            15,};
                    break;

                case 4486:
                    // fileID = 1790
                    cache[j].frameCount = 12;
                    cache[j].frameIDs = new int[]{117309564, 117309440, 117309577, 117309542, 117309526, 117309500,
                            117309572, 117309584, 117309510, 117309573, 117309449, 117309568,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 4,};
                    cache[j].loopDelay = 1;
                    cache[j].forcedPriority = 9;
                    cache[j].delayType = 0;
                    break;

                case 4487:
                    // fileID = 1790
                    cache[j].frameCount = 12;
                    cache[j].frameIDs = new int[]{117309522, 117309451, 117309602, 117309618, 117309515, 117309565,
                            117309517, 117309489, 117309520, 117309566, 117309555, 117309505,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,};
                    cache[j].loopDelay = 1;
                    cache[j].forcedPriority = 9;
                    cache[j].delayType = 0;
                    break;

                case 4488:
                    // fileID = 1790
                    cache[j].frameCount = 15;
                    cache[j].frameIDs = new int[]{117309535, 117309468, 117309534, 117309569, 117309581, 117309507,
                            117309443, 117309598, 117309444, 117309466, 117309576, 117309551, 117309464, 117309543,
                            117309446,};
                    cache[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,};
                    break;

                case 4489:
                    // fileID = 1790
                    cache[j].frameCount = 9;
                    cache[j].frameIDs = new int[]{117309471, 117309511, 117309580, 117309617, 117309624, 117309595,
                            117309491, 117309453, 117309447,};
                    cache[j].delays = new int[]{7, 7, 7, 7, 7, 7, 7, 6, 5,};
                    break;

                case 4490:
                    // fileID = 1790
                    cache[j].frameCount = 24;
                    cache[j].frameIDs = new int[]{117309601, 117309493, 117309467, 117309549, 117309474, 117309585,
                            117309608, 117309492, 117309597, 117309574, 117309496, 117309554, 117309459, 117309528,
                            117309582, 117309587, 117309475, 117309560, 117309442, 117309469, 117309523, 117309508,
                            117309497, 117309550,};
                    cache[j].delays = new int[]{5, 5, 10, 4, 4, 4, 6, 5, 4, 4, 4, 4, 9, 6, 5, 9, 4, 3, 3, 4, 4, 4, 5,
                            5,};
                    cache[j].forcedPriority = 6;
                    break;

                case 4491:
                    // fileID = 1790
                    cache[j].frameCount = 12;
                    cache[j].frameIDs = new int[]{117309506, 117309477, 117309519, 117309483, 117309558, 117309619,
                            117309613, 117309561, 117309476, 117309512, 117309452, 117309495,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,};
                    cache[j].forcedPriority = 6;
                    break;

                case 4492:
                    // fileID = 1790
                    cache[j].frameCount = 18;
                    cache[j].frameIDs = new int[]{117309553, 117309490, 117309485, 117309530, 117309592, 117309531,
                            117309594, 117309583, 117309458, 117309614, 117309538, 117309524, 117309521, 117309537,
                            117309562, 117309513, 117309484, 117309616,};
                    cache[j].delays = new int[]{7, 6, 6, 7, 9, 9, 9, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6,};
                    cache[j].forcedPriority = 6;
                    break;

                case 4493:
                    // fileID = 1790
                    cache[j].frameCount = 19;
                    cache[j].frameIDs = new int[]{117309579, 117309501, 117309478, 117309486, 117309610, 117309499,
                            117309544, 117309457, 117309470, 117309591, 117309545, 117309525, 117309450, 117309494,
                            117309606, 117309456, 117309455, 117309590, 117309448,};
                    cache[j].delays = new int[]{7, 6, 7, 7, 7, 6, 7, 6, 7, 6, 7, 6, 7, 6, 7, 7, 6, 5, 3,};
                    cache[j].forcedPriority = 6;
                    break;

                case 4494:
                    // fileID = 1790
                    cache[j].frameCount = 19;
                    cache[j].frameIDs = new int[]{117309540, 117309479, 117309509, 117309552, 117309480, 117309589,
                            117309578, 117309445, 117309586, 117309615, 117309532, 117309575, 117309570, 117309498,
                            117309593, 117309571, 117309546, 117309625, 117309481,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 3, 17, 14, 8, 5, 5, 5, 5, 5, 5, 8,};
                    cache[j].forcedPriority = 9;
                    break;

                case 4495:
                    // fileID = 1790
                    cache[j].frameCount = 14;
                    cache[j].frameIDs = new int[]{117309441, 117309557, 117309612, 117309536, 117309603, 117309563,
                            117309609, 117309567, 117309502, 117309607, 117309516, 117309626, 117309463, 117309514,};
                    cache[j].delays = new int[]{5, 5, 5, 5, 5, 5, 3, 3, 5, 5, 3, 3, 4, 4,};
                    cache[j].loopDelay = 1;
                    cache[j].forcedPriority = 10;
                    break;
            }
            if (j == 5061) {
                cache[j].frameCount = 13;
                cache[j].frameIDs = new int[]{19267601, 19267602, 19267603, 19267604, 19267605, 19267606, 19267607,
                        19267606, 19267605, 19267604, 19267603, 19267602, 19267601,};
                cache[j].delays = new int[]{4, 3, 3, 4, 10, 10, 15, 10, 10, 4, 3, 3, 4,};
                // cache[j].animationFlowControl = new int[] { 1, 2, 9, 11, 13,
                // 15, 17, 19, 37, 39, 41, 43, 45, 164, 166, 168, 170, 172, 174,
                // 176, 178, 180, 182, 183, 185, 191, 192, 9999999, };
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
                cache[j].frameIDs = new int[]{11927608, 11927625, 11927598, 11927678, 11927582, 11927600, 11927669,
                        11927597, 11927678};
                cache[j].delays = new int[]{5, 4, 4, 4, 5, 5, 5, 4, 4};
            }
            if (j == 876) {
                cache[j].frameCount = 9;
                cache[j].frameIDs = new int[]{118751240, 118751239, 118751235, 118751234, 118751237, 118751238,
                        118751232, 118751233, 118751236};
                cache[j].delays = new int[]{5, 5, 5, 5, 4, 4, 4, 4, 4};
            }
            if (j == 1720) {
                cache[j].frameCount = 16;
                cache[j].frameIDs = new int[]{18087977, 18087978, 18087979, 18087980, 18087981, 18087969, 18087970,
                        18087971, 18087972, 18087973, 18087979, 18087978, 18087977, 18087976, 18087975, 18087974};
                cache[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                cache[j].delays = new int[]{3, 2, 3, 6, 2, 2, 5, 5, 5, 5, 3, 2, 3, 3, 4, 4};
                cache[j].loopDelay = -1;
                cache[j].animationFlowControl = new int[]{1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166,
                        168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 127, 9999999};
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
                cache[j].frameIDs = new int[]{21495808, 21495808, 21495815, 21495815, 21495816, 21495816, 21495817,
                        21495817, 21495818, 21495818, 21495819, 21495819, 21495819, 21495819, 21495818, 21495818,
                        21495817, 21495817, 21495817, 21495817, 21495818, 21495818, 21495819, 21495819, 21495819,
                        21495819, 21495818, 21495818, 21495817, 21495817, 21495816, 21495816, 21495815, 21495815,
                        21495808, 21495808};
                cache[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                cache[j].delays = new int[]{4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                        5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
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
                cache[j].frameIDs = new int[]{93716480, 93716481, 93716482, 93716483, 93716484, 93716485, 93716486,
                        93716487,};
                cache[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4,};
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
                cache[j].frameIDs = new int[]{11927613, 11927599, 11927574, 11927659, 11927676, 11927562, 11927626,
                        11927628, 11927684, 11927647, 11927602, 11927576, 11927586, 11927653, 11927616};
                cache[j].delays = new int[]{3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5};
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
                cache[j].frameIDs = new int[]{11927623, 11927595, 11927685, 11927636, 11927670, 11927579, 11927664,
                        11927666, 11927661, 11927673, 11927633, 11927624, 11927555, 11927588, 11927692, 11927667,
                        11927556, 11927630, 11927695, 11927643, 11927640};
                cache[j].delays = new int[]{2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
            }

            if (j == 7083) {
                cache[j].frameCount = 16;
                cache[j].frameIDs = new int[]{18087977, 18087978, 18087979, 18087980, 18087981, 18087969, 18087970,
                        18087971, 18087972, 18087973, 18087979, 18087978, 18087977, 18087976, 18087975, 18087974};
                cache[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                cache[j].delays = new int[]{3, 2, 3, 6, 2, 2, 5, 5, 5, 5, 3, 2, 3, 3, 4, 4};
                cache[j].loopDelay = -1;
                cache[j].animationFlowControl = new int[]{1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166,
                        168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 127, 9999999};
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
                cache[j].frameIDs = new int[]{31522826, 31522817, 31522825, 31522818, 31522831, 31522821, 31522828,
                        31522822, 31522823, 31522830, 31522824, 31522829, 31522832, 31522827, 31522819, 31522816,
                        31522820, 31522828};
                cache[j].frameIDs2 = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                        -1};
                cache[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6};
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
                cache[j].frameIDs = new int[]{11927640, 11927643, 11927695, 11927630, 11927556, 11927667, 11927692,
                        11927588, 11927555, 11927624, 11927633, 11927673, 11927661, 11927666, 11927664, 11927579,
                        11927670, 11927636, 11927685, 11927595, 11927623};
                cache[j].delays = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2};
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
                cache[j].frameIDs = new int[]{11927612, 11927677, 11927615, 11927573, 11927618, 11927567, 11927564,
                        11927606, 11927675, 11927657, 11927690, 11927583, 11927672, 11927552, 11927563, 11927683,
                        11927639, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656,
                        11927660, 11927629, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644,
                        11927656, 11927660, 11927635, 11927668, 11927614, 11927560, 11927687, 11927577, 11927569,
                        11927557, 11927569, 11927577, 11927687, 11927560, 11927651, 11927611, 11927680, 11927622,
                        11927691, 11927571, 11927601};
                cache[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 3};
            }
            if (j == 692) {
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
                cache[j].frameIDs = new int[]{67436544, 67436545, 67436546};
                cache[j].delays = new int[]{2, 2, 2};
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
                cache[j].frameIDs = new int[]{11927612, 11927677, 11927615, 11927573, 11927618, 11927567, 11927564,
                        11927606, 11927675, 11927657, 11927690, 11927583, 11927672, 11927552, 11927563, 11927683,
                        11927639, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644, 11927656,
                        11927660, 11927629, 11927635, 11927668, 11927614, 11927627, 11927610, 11927693, 11927644,
                        11927656, 11927604, 11927637, 11927688, 11927696, 11927681, 11927605, 11927681, 11927696,
                        11927688, 11927637, 11927604, 11927656, 11927611, 11927680, 11927622, 11927691, 11927571,
                        11927601};
                cache[j].delays = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 3};
            }
        }
    }

    public int getFrameLength(int i) {
        if (i > delays.length)
            return 1;
        int j = delays[i];
        if (j == 0) {
            FrameReader reader = FrameReader.forId(frameIDs[i]);
            if (reader != null)
                j = delays[i] = reader.displayLength;
        }
        if (j == 0)
            j = 1;
        return j;
    }

    public void readValues(ByteBuffer stream) {
        do {
            int i = stream.getUnsignedByte();
            if (i == 0)
                break;
            if (i == 1) {
                frameCount = stream.getUnsignedShort();
                frameIDs = new int[frameCount];
                frameIDs2 = new int[frameCount];
                delays = new int[frameCount];
                for (int i_ = 0; i_ < frameCount; i_++) {
                    frameIDs[i_] = stream.getIntLittleEndian();
                    frameIDs2[i_] = -1;
                }
                for (int i_ = 0; i_ < frameCount; i_++)
                    delays[i_] = stream.getUnsignedByte();
            } else if (i == 2)
                loopDelay = stream.getUnsignedShort();
            else if (i == 3) {
                int k = stream.getUnsignedByte();
                animationFlowControl = new int[k + 1];
                for (int l = 0; l < k; l++)
                    animationFlowControl[l] = stream.getUnsignedByte();
                animationFlowControl[k] = 0x98967f;
            } else if (i == 4)
                oneSquareAnimation = true;
            else if (i == 5)
                forcedPriority = stream.getUnsignedByte();
            else if (i == 6)
                leftHandItem = stream.getUnsignedShort();
            else if (i == 7)
                rightHandItem = stream.getUnsignedShort();
            else if (i == 8)
                frameStep = stream.getUnsignedByte();
            else if (i == 9)
                resetWhenWalk = stream.getUnsignedByte();
            else if (i == 10)
                priority = stream.getUnsignedByte();
            else if (i == 11)
                delayType = stream.getUnsignedByte();
            else
                System.out.println("Unrecognized seq.dat config code: " + i);
        } while (true);
        if (frameCount == 0) {
            frameCount = 1;
            frameIDs = new int[1];
            frameIDs[0] = -1;
            frameIDs2 = new int[1];
            frameIDs2[0] = -1;
            delays = new int[1];
            delays[0] = -1;
        }
        if (resetWhenWalk == -1)
            if (animationFlowControl != null)
                resetWhenWalk = 2;
            else
                resetWhenWalk = 0;
        if (priority == -1) {
            if (animationFlowControl != null) {
                priority = 2;
                return;
            }
            priority = 0;
        }
    }

    public void immitate(Animation anim) {
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
        System.out.println("loopDelay " + loopDelay);
        System.out.println("oneSquareAnimation " + oneSquareAnimation);
        System.out.println("forcedPriority " + forcedPriority);
        System.out.println("leftHandItem " + leftHandItem);
        System.out.println("rightHandItem " + rightHandItem);
        System.out.println("frameStep " + frameStep);
        System.out.println("resetWhenWalk " + resetWhenWalk);
        System.out.println("priority " + priority);
        System.out.println("delayType " + delayType);
    }

    private Animation() {
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