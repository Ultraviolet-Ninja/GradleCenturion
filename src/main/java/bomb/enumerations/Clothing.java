package bomb.enumerations;

import bomb.interfaces.Labeled;
import bomb.interfaces.Listed;

public enum Clothing {
    ARTICLE;

    public Material mat;
    public Item item;
    public Color color;

    public enum Item implements Listed {
        CORSET("300°F", "Bleach"), SHIRT("No Steam", "No Tetrachlorethylene"),
        SKIRT("Iron", "Reduced Moisture"), SKORT("200°C", "Reduced Moisture"),
        SHORTS("300°F", "Do Not Bleach"), SCARF("110°C", "Do Not Dry Clean");

        private final String[] conditions;

        Item(String ironing, String special){
            conditions = new String[]{ironing, special};
        }

        @Override
        public String[] getWords() {
            return conditions;
        }
    }

    public enum Material implements Labeled{
        POLYESTER("120F.png", "Petroleum Solvent Only"), COTTON("200F.png", "Do not Dry Clean"),
        WOOL("Hand Wash.png", "Reduced Moisture"), NYLON("80F.png", "Low Heat"),
        CORDUROY("105F.png", "Wet Cleaning"), LEATHER("Don't Wash", "Any Solvent except Tetrachlorethylene");

        private final String picLocation, specialInstr;

        Material(String pic, String instr){
            picLocation = "file:src\\bomb\\resources\\laundry\\wash\\" + pic;
            specialInstr = instr;
        }

        @Override
        public String getLabel() {
            return picLocation;
        }

        public String getSpecialInstr(){
            return specialInstr;
        }
    }

    public enum Color implements Labeled{
        RUBY("High Heat.png", "Any Solvent"), STAR("Dry Flat.png", "Low Heat"),
        SAPPHIRE("Tumble Dry.png", "Short Cycle"), JADE("Don't Tumble Dry.png", "No Steam Finishing"),
        PEARL("Low Heat.png", "Low Heat"), MALINITE("Medium Heat.png", "Non-Chlorine Bleach");

        private final String picLocation, specialInstr;

        Color(String pic, String instr){
            picLocation = "file:src\\bomb\\resources\\laundry\\dry\\" + pic;
            specialInstr = instr;
        }

        @Override
        public String getLabel() {
            return picLocation;
        }

        public String getSpecialInstr(){
            return specialInstr;
        }
    }
}
