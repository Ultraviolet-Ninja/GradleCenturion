package bomb.modules.il.laundry;

import bomb.abstractions.Labeled;
import bomb.abstractions.Listed;

public enum Clothing {
    ARTICLE;

    private Material material;
    private Item item;
    private Color color;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material mat) {
        this.material = mat;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public enum Item implements Listed {
        CORSET("300°F", "Bleach"), SHIRT("No Steam", "No Tetrachlorethylene"),
        SKIRT("Iron", "Reduced Moisture"), SKORT("200°C", "Reduced Moisture"),
        SHORTS("300°F", "Do Not Bleach"), SCARF("110°C", "Do Not Dry Clean");

        private final String[] conditions;

        Item(String ironing, String special) {
            conditions = new String[]{ironing, special};
        }

        @Override
        public String[] getWords() {
            return conditions;
        }
    }

    public enum Material implements Labeled {
        POLYESTER("120F.png", "Petroleum Solvent Only"), COTTON("200F.png", "Do not Dry Clean"),
        WOOL("Hand Wash.png", "Reduced Moisture"), NYLON("80F.png", "Low Heat"),
        CORDUROY("105F.png", "Wet Cleaning"), LEATHER("Don't Wash", "No Tetrachlorethylene");

        private final String picLocation, specialInstr;

        Material(String pic, String instr) {
            picLocation = "wash/" + pic;
            specialInstr = instr;
        }

        @Override
        public String getLabel() {
            return picLocation;
        }

        public String getSpecialInstr() {
            return specialInstr;
        }
    }

    public enum Color implements Labeled {
        RUBY("High Heat.png", "Any Solvent"), STAR("Dry Flat.png", "Low Heat"),
        SAPPHIRE("Tumble Dry.png", "Short Cycle"), JADE("Don't Tumble Dry.png", "No Steam Finishing"),
        PEARL("Low Heat.png", "Low Heat"), MALINITE("Medium Heat.png", "Non-Chlorine Bleach");

        private final String picLocation, specialInstr;

        Color(String pic, String instr) {
            picLocation = "dry/" + pic;
            specialInstr = instr;
        }

        @Override
        public String getLabel() {
            return picLocation;
        }

        public String getSpecialInstr() {
            return specialInstr;
        }
    }
}
