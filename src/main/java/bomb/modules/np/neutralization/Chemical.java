package bomb.modules.np.neutralization;

public final class Chemical {
    public enum Acid {
        HYDROFLUORIC_ACID(9, "HF", "F"),
        HYDROCHORIC_ACID(17, "HCl", "Cl"),
        HYDROBROMIC_ACID(35, "HBr", "Br"),
        HYDROIODIC_ACID(53, "HI", "I");

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
        AMMONIA(1, "NH3", "H"),
        LITHIUM_HYDROXIDE(3, "LiOH", "Li"),
        SODIUM_HYDROXIDE(11, "NaOH", "Na"),
        POTASSIUM_HYDROXIDE(19, "KOH", "K");

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
