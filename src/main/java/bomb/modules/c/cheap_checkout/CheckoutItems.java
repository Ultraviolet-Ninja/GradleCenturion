package bomb.modules.c.cheap_checkout;

public enum CheckoutItems {
    ;

    private final boolean byThePound;
    private final Category category;
    private final double price;

    CheckoutItems(boolean byThePound, Category category, double price) {
        this.byThePound = byThePound;
        this.category = category;
        this.price = price;
    }

    public boolean isByThePound() {
        return byThePound;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    enum Category {
        DAIRY, FRUIT, VEGETABLE, GRAIN, PROTEIN, OIL, GRAIN_OIL, SWEET, CLOTHING, CARE, WATER, BEAN, EXTRA
    }
}
