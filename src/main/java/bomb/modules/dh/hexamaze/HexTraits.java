package bomb.modules.dh.hexamaze;

import bomb.interfaces.Index;
import bomb.tools.Base91;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public enum HexTraits {
    ;
    public enum HexShape {
        Circle, Hexagon, LeftTriangle, RightTriangle, UpTriangle, DownTriangle;

        public static int shapeOrdinality(HexShape shape){
            if (shape == null) return DownTriangle.ordinal() + 1;
            return shape.ordinal();
        }

        public static HexShape fromOrdinality(int num){
            for (HexShape shape : HexShape.values()){
                if (shape.ordinal() == num) return shape;
            }
            return null;
        }
    }

    public enum HexWall implements Index {
        TopLeft(0), Top(1), TopRight(2),
        BottomLeft(3), Bottom(4),  BottomRight(5);

        private static final Map<String, List<HexWall>> fromHashCode = new HashMap<>();
        private static final Map<List<HexWall>, String> toHashCode = new HashMap<>();
        private static final BiConsumer<List<HexWall>, Integer> action = (list, num) -> {
            String encoded = Base91.encrypt(num);
            fromHashCode.put(encoded, list);
            toHashCode.put(list, encoded);
        };

        static{
            applyToPermutations(HexWall.values());
        }

        private final int idx;

        @Override
        public int getIdx() {
            return idx;
        }

        HexWall(int idx){
            this.idx = idx;
        }

        private static void applyToPermutations(HexWall[] arr){
            for (int i = 0; i < Math.pow(2, arr.length); i++){
                List<HexWall> temp = new ArrayList<>();
                for (int j = 0; j < arr.length; j++){
                    if ((i >> j) % 2 == 1){
                        temp.add(arr[j]);
                    }
                }
                action.accept(temp, i);
            }
        }

        public static List<HexWall> fromHash(String hashLetter){
            return fromHashCode.getOrDefault(hashLetter, null);
        }

        public static String toHash(List<HexWall> walls){
            return toHashCode.getOrDefault(walls, "-1");
        }
    }
}