module jasmine.jragon.GradleCenturion.main {
    requires transitive javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires com.opencsv;
    requires com.jfoenix;
    requires org.jgrapht.core;
    requires javatuples;
    requires MaterialFX;

    exports bomb to javafx.fxml;
    exports bomb.components.chord to javafx.fxml;
    exports bomb.components.hex to javafx.fxml;
    exports bomb.components.microcontroller to javafx.fxml;
    exports bomb.components.simon.screams to javafx.fxml;
    exports bomb.components.simon.states to javafx.fxml;
    exports bomb.modules.ab.alphabet to javafx.fxml;
    exports bomb.modules.ab.astrology to javafx.fxml;
    exports bomb.modules.ab.bitwise to javafx.fxml;
    exports bomb.modules.ab.blind_alley to javafx.fxml;
    exports bomb.modules.ab.boolean_venn to javafx.fxml;
    exports bomb.modules.c.caesar to javafx.fxml;
    exports bomb.modules.c.cheap_checkout to javafx.fxml;
    exports bomb.modules.c.chess to javafx.fxml;
    exports bomb.modules.c.chords to javafx.fxml;
    exports bomb.modules.c.color_flash to javafx.fxml;
    exports bomb.modules.c.colored_switches to javafx.fxml;
    exports bomb.modules.dh.emoji to javafx.fxml;
    exports bomb.modules.dh.fast_math to javafx.fxml;
    exports bomb.modules.dh.forget_me to javafx.fxml;
    exports bomb.modules.dh.hexamaze to javafx.fxml;
    exports bomb.modules.il.ice_cream to javafx.fxml;
    exports bomb.modules.il.laundry to javafx.fxml;
    exports bomb.modules.il.led_encryption to javafx.fxml;
    exports bomb.modules.m.microcontroller to javafx.fxml;
    exports bomb.modules.m.monsplode to javafx.fxml;
    exports bomb.modules.np.neutralization to javafx.fxml;
    exports bomb.modules.np.number_pad to javafx.fxml;
    exports bomb.modules.r.round_keypads to javafx.fxml;
    exports bomb.modules.s.seashells to javafx.fxml;
    exports bomb.modules.s.semaphore to javafx.fxml;
    exports bomb.modules.s.shape_shift to javafx.fxml;
    exports bomb.modules.s.simon.screams to javafx.fxml;
    exports bomb.modules.s.simon.states to javafx.fxml;
    exports bomb.modules.s.souvenir to javafx.fxml;
    exports bomb.modules.s.switches to javafx.fxml;
    exports bomb.modules.t.bulb to javafx.fxml;
    exports bomb.modules.t.three_d_maze to javafx.fxml;
    exports bomb.modules.t.translated to javafx.fxml;
    exports bomb.modules.t.translated.solutions to javafx.fxml;
    exports bomb.modules.t.translated.solutions.button to javafx.fxml;
    exports bomb.modules.t.translated.solutions.gas to javafx.fxml;
    exports bomb.modules.t.translated.solutions.morse to javafx.fxml;
    exports bomb.modules.t.translated.solutions.password to javafx.fxml;
    exports bomb.modules.t.translated.solutions.wof to javafx.fxml;
    exports bomb.modules.t.two_bit to javafx.fxml;
    exports bomb.modules.wz.zoo to javafx.fxml;
    exports bomb.tools.note to javafx.fxml;

    opens bomb;
    opens bomb.components.chord;
    opens bomb.components.hex;
    opens bomb.components.microcontroller;
    opens bomb.components.simon.screams;
    opens bomb.components.simon.states;
    opens bomb.modules.ab.alphabet;
    opens bomb.modules.ab.astrology;
    opens bomb.modules.ab.bitwise;
    opens bomb.modules.ab.blind_alley;
    opens bomb.modules.ab.boolean_venn;
    opens bomb.modules.c.caesar;
    opens bomb.modules.c.cheap_checkout;
    opens bomb.modules.c.chess;
    opens bomb.modules.c.chords;
    opens bomb.modules.c.color_flash;
    opens bomb.modules.c.colored_switches;
    opens bomb.modules.dh.emoji;
    opens bomb.modules.dh.fast_math;
    opens bomb.modules.dh.forget_me;
    opens bomb.modules.dh.hexamaze;
    opens bomb.modules.il.ice_cream;
    opens bomb.modules.il.laundry;
    opens bomb.modules.il.led_encryption;
    opens bomb.modules.m.microcontroller;
    opens bomb.modules.m.monsplode;
    opens bomb.modules.np.neutralization;
    opens bomb.modules.np.number_pad;
    opens bomb.modules.r.round_keypads;
    opens bomb.modules.s.seashells;
    opens bomb.modules.s.semaphore;
    opens bomb.modules.s.shape_shift;
    opens bomb.modules.s.simon.screams;
    opens bomb.modules.s.simon.states;
    opens bomb.modules.s.souvenir;
    opens bomb.modules.s.switches;
    opens bomb.modules.t.bulb;
    opens bomb.modules.t.three_d_maze;
    opens bomb.modules.t.translated;
    opens bomb.modules.t.translated.solutions;
    opens bomb.modules.t.translated.solutions.button;
    opens bomb.modules.t.translated.solutions.morse;
    opens bomb.modules.t.translated.solutions.password;
    opens bomb.modules.t.translated.solutions.gas;
    opens bomb.modules.t.translated.solutions.wof;
    opens bomb.modules.t.two_bit;
    opens bomb.modules.wz.zoo;
    opens bomb.tools.note;

}