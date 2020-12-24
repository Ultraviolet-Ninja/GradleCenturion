package bomb.enumerations;

import bomb.interfaces.Labeled;

public enum WhosOnFirstPictures implements Labeled {
    YES("Yes"), FIRST("First"), DISPLAY("Display"), OKAY("Okay"),
    SAYS("Says"), NO("No"), NOTHING("Nothing"), EMPTY("Empty"),
    BLANK("Blank"), LED("Led"),LEAD("Lead"), READ("Read"),
    RED("Red"), REED("Reed"), LEED("Leed"), HOLDON("Hold On"),
    YOU("You"), YOUARE("You Are"), YOUR("Your"), YOURE("You're"),
    UR("ur"),THERE("There"), THEIR("Their"), THEYRE("They're"),
    THEYARE("They Are"), SEE("See"),C("C"),CEE("Cee");

    private final String label;

    @Override
    public String getLabel() {
        return "file:src\\Step1_Panels\\" + label + ".PNG";
    }

    WhosOnFirstPictures(String location){
        label=location;
    }
}
