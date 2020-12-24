package bomb.enumerations;

import bomb.interfaces.Index;
import javafx.scene.paint.Color;

public enum HexTraits {
    ;
    public enum HexShape {
        Circle, Hexagon, LeftTriangle, RightTriangle, UpTriangle, DownTriangle
    }

    public enum HexWall implements Index {
        TopLeft(0), Top(1), TopRight(2),
        BottomLeft(3), Bottom(4),  BottomRight(5);

        private final int idx;
        @Override
        public int getIdx() {
            return idx;
        }

        HexWall(int idx){
            this.idx = idx;
        }
    }
}