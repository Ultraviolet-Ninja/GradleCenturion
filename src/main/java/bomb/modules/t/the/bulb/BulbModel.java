package bomb.modules.t.the.bulb;

public final class BulbModel {
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
        RED, YELLOW, GREEN, BLUE, PURPLE, WHITE
    }

    public enum Light {
        ON, OFF
    }

    public enum Opacity {
        OPAQUE, TRANSLUCENT
    }

    public enum Position {
        SCREWED, UNSCREWED
    }
}
