package bomb.modules.np.neutralization;

public class Chemical {
    public enum Acid {
        Hydrofluoric_Acid(9, "HF", "F", "Fluorine"),
        Hydrochoric_Acid(17, "HCl", "Cl", "Chlorine"),
        Hydrobromic_Acid(35, "HBr", "Br", "Bromine"),
        Hydroiodic_Acid(53, "HI", "I", "Iodine");

        public String getFormula() {
            return formula;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getAnion() {
            return anion;
        }

        public int getAtomicNum() {
            return atomicNum;
        }

        private final String formula, symbol, anion;
        private final int atomicNum;

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

        public String getFormula() {
            return formula;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getCation() {
            return cation;
        }

        public int getAtomicNum() {
            return atomicNum;
        }

        private final String formula, symbol, cation;
        private final int atomicNum;

        Base(int atomic, String formula, String symbol, String cation){
            this.formula = formula;
            this.atomicNum = atomic;
            this.symbol = symbol;
            this.cation = cation;
        }
    }
}
