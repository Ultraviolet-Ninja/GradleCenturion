package bomb.modules.c;

import bomb.enumerations.Indicators;
import bomb.enumerations.Ports;
import bomb.Widget;

public class Caesar extends Widget {

    public static String reshuffle(String input){
        return restructure(input, offset());
    }

    private static String restructure(String encoded, int numbShift){
        char extract;
        encoded = encoded.toUpperCase();
        StringBuilder result = new StringBuilder();

        for (char letter : encoded.toCharArray()){
            extract = (char) (letter - numbShift);
            if (extract < 'A'){
                extract = (char) ('[' - (numbShift - (letter - 'A')));
            }
            result.append(extract);
        }

        return result.toString();
    }

    private static int offset(){
        if (hasLitIndicator(Indicators.NSA) && getPort(Ports.PARALLEL) > 0) return 0;
        int out = 0;
        out += getAllBatteries();
        if (hasVowel()) out--;
        if (hasEven()==0) out++;
        if (hasIndicator(Indicators.CAR)) out++;
        return out;
    }
}
