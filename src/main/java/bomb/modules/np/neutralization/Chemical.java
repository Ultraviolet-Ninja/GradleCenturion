package bomb.modules.np.neutralization;

public class Chemical {
    public enum Acid {
        Hydrofluoric_Acid(9, "HF", "F"),
        Hydrochoric_Acid(17, "HCl", "Cl"),
        Hydrobromic_Acid(35, "HBr", "Br"),
        Hydroiodic_Acid(53, "HI", "I");

        public String getFormula() {
            return formula;
        }

        public String getSymbol() {
            return symbol;
        }

        public int getAtomicNum() {
            return atomicNum;
        }

        private final String formula, symbol;
        private final int atomicNum;

        Acid(int atomic, String formula, String symbol) {
            this.formula = formula;
            this.atomicNum = atomic;
            this.symbol = symbol;
        }
    }

    public enum Base {
        Ammonia(1, "NH3", "H"),
        Lithium_Hydroxide(3, "LiOH", "Li"),
        Sodium_Hydroxide(11, "NaOH", "Na"),
        Potassium_Hydroxide(19, "KOH", "K");

        public String getFormula() {
            return formula;
        }

        public String getSymbol() {
            return symbol;
        }

        public int getAtomicNum() {
            return atomicNum;
        }

        private final String formula, symbol;
        private final int atomicNum;

        Base(int atomic, String formula, String symbol) {
            this.formula = formula;
            this.atomicNum = atomic;
            this.symbol = symbol;
        }
    }
}
