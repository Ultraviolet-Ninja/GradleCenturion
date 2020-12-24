package bomb.enumerations;

public enum Chemical {
    ;

    public enum Acid {
        Hydrofluoric_Acid(9, "HF", "F", "Fluorine"),
        Hydrochoric_Acid(17, "HCl", "Cl", "Chlorine"),
        Hydrobromic_Acid(35, "HBr", "Br", "Bromine"),
        Hydroiodic_Acid(53, "HI", "I", "Iodine");

        public final String formula, symbol, anion;
        public final int atomicNum;

        Acid(int atomic, String formula, String symbol, String anion){
            this.formula = formula;
            this.atomicNum = atomic;
            this.symbol = symbol;
            this.anion = anion;
        }
    }

    public enum Base {
        Ammonia(1, "NH3", "H", "Hydrogen"),
        Lithium_Hydroxide(3, "LiOH", "Li", "Lithium"),
        Sodium_Hydroxide(11, "NaOH", "Na", "Sodium"),
        Potassium_Hydroxide(19, "KOH", "K", "Potassium");

        public final String formula, symbol, cation;
        public final int atomicNum;

        Base(int atomic, String formula, String symbol, String cation){
            this.formula = formula;
            this.atomicNum = atomic;
            this.symbol = symbol;
            this.cation = cation;
        }
    }
}
