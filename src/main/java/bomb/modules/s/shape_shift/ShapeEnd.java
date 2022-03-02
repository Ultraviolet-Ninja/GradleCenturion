package bomb.modules.s.shape_shift;

public enum ShapeEnd {
    ROUND, POINT, FLAT, TICKET;

    static final ShapeEnd[] SHAPE_END_ARRAY;

    static {
        SHAPE_END_ARRAY = values();
    }
}
