package bomb.modules.dh.hexamaze.hexalgorithm;

import bomb.tools.string.Base91;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public enum HexNodeProperties {
    ;

    public enum HexShape {
        Circle, Hexagon, LeftTriangle, RightTriangle, UpTriangle, DownTriangle;

        public static int toShapeOrdinal(HexShape shape) {
            return shape == null ?
                    DownTriangle.ordinal() + 1 :
                    shape.ordinal();
        }

        public static HexShape fromShapeOrdinal(int num) {
            if (num < 0 || num > HexShape.values().length)
                return null;
            return HexShape.values()[num];
        }
    }

    public enum HexWall {
        TopLeft, Top, TopRight, BottomLeft, Bottom, BottomRight;

        private static final Map<String, EnumSet<HexWall>> fromHashCode = new HashMap<>();
        private static final Map<EnumSet<HexWall>, String> toHashCode = new HashMap<>();
        private static final BiConsumer<EnumSet<HexWall>, Integer> action = (list, num) -> {
            String encoded = Base91.encrypt(num);
            fromHashCode.put(encoded, list);
            toHashCode.put(list, encoded);
        };

        static {
            applyToPermutations(HexWall.values());
        }

        private static void applyToPermutations(HexWall[] arr) {
            for (int i = 0; i < Math.pow(2, arr.length); i++) {
                EnumSet<HexWall> temp = EnumSet.noneOf(HexWall.class);
                for (int j = 0; j < arr.length; j++) {
                    if ((i >> j) % 2 == 1) {
                        temp.add(arr[j]);
                    }
                }
                action.accept(temp, i);
            }
        }

        public static EnumSet<HexWall> fromHash(String hashLetter) {
            return fromHashCode.getOrDefault(hashLetter, null);
        }

        public static String toHash(EnumSet<HexWall> walls) {
            return toHashCode.getOrDefault(walls, "-1");
        }
    }
}