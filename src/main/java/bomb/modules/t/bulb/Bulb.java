package bomb.modules.t.bulb;

public enum Bulb {
    THE_BULB;

    private Color color;
    private Light light;
    private Opacity opacity;
    private Position position;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Opacity getOpacity() {
        return opacity;
    }

    public void setOpacity(Opacity opacity) {
        this.opacity = opacity;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public enum Color {
        RED(javafx.scene.paint.Color.RED), YELLOW(javafx.scene.paint.Color.YELLOW),
        GREEN(javafx.scene.paint.Color.web("#07c307")), BLUE(javafx.scene.paint.Color.DODGERBLUE),
        PURPLE(javafx.scene.paint.Color.web("#cc0bdd")), WHITE(javafx.scene.paint.Color.WHITE);

        private final javafx.scene.paint.Color associatedColor;

        Color(javafx.scene.paint.Color color){
            associatedColor = color;
        }

        public javafx.scene.paint.Color getAssociatedColor(){
            return associatedColor;
        }
    }

    public enum Light{
        ON, OFF
    }

    public enum Opacity {
        OPAQUE, TRANSLUCENT
    }

    public enum Position{
        SCREWED, UNSCREWED
    }
}
