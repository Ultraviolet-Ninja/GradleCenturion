open module centurion {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.opencsv;
    requires com.jfoenix;
    requires org.jgrapht.core;
    requires javatuples;
    requires MaterialFX;
    requires org.jetbrains.annotations;

    exports bomb to javafx.fxml;

    exports bomb.components.chord to javafx.fxml;
    exports bomb.components.hex to javafx.fxml;
    exports bomb.components.microcontroller to javafx.fxml;
    exports bomb.components.simon.screams to javafx.fxml;
    exports bomb.components.simon.states to javafx.fxml;

    exports bomb.enumerations to javafx.fxml;

    exports bomb.modules.ab.alphabet to javafx.fxml;
    exports bomb.modules.ab.astrology to javafx.fxml;
    exports bomb.modules.ab.bitwise to javafx.fxml;
    exports bomb.modules.ab.blind.alley to javafx.fxml;
    exports bomb.modules.ab.bool.venn.diagram to javafx.fxml;
    exports bomb.modules.c.caesar.cipher to javafx.fxml;
    exports bomb.modules.c.cheap.checkout to javafx.fxml;
    exports bomb.modules.c.chess to javafx.fxml;
    exports bomb.modules.c.chords to javafx.fxml;
    exports bomb.modules.c.color.flash to javafx.fxml;
    exports bomb.modules.c.colored.switches to javafx.fxml;
    exports bomb.modules.dh.emoji.math to javafx.fxml;
    exports bomb.modules.dh.fast.math to javafx.fxml;
    exports bomb.modules.dh.forget.me.not to javafx.fxml;
    exports bomb.modules.dh.hexamaze to javafx.fxml;
    exports bomb.modules.dh.hexamaze.hexalgorithm.storage to javafx.fxml;
    exports bomb.modules.il.ice.cream to javafx.fxml;
    exports bomb.modules.il.laundry to javafx.fxml;
    exports bomb.modules.il.led.encryption to javafx.fxml;
    exports bomb.modules.m.microcontroller to javafx.fxml;
    exports bomb.modules.m.microcontroller.chip to javafx.fxml;
    exports bomb.modules.m.monsplode to javafx.fxml;
    exports bomb.modules.np.neutralization to javafx.fxml;
    exports bomb.modules.np.number.pad to javafx.fxml;
    exports bomb.modules.r.round.keypads to javafx.fxml;
    exports bomb.modules.s.seashells to javafx.fxml;
    exports bomb.modules.s.semaphore to javafx.fxml;
    exports bomb.modules.s.shape.shift to javafx.fxml;
    exports bomb.modules.s.simon to javafx.fxml;
    exports bomb.modules.s.simon.screams to javafx.fxml;
    exports bomb.modules.s.simon.states to javafx.fxml;
    exports bomb.modules.s.souvenir to javafx.fxml;
    exports bomb.modules.s.switches to javafx.fxml;
    exports bomb.modules.t.the.bulb to javafx.fxml;
    exports bomb.modules.t.three.d.maze to javafx.fxml;
    exports bomb.modules.t.translated to javafx.fxml;
    exports bomb.modules.t.translated.solutions to javafx.fxml;
    exports bomb.modules.t.translated.solutions.button to javafx.fxml;
    exports bomb.modules.t.translated.solutions.gas to javafx.fxml;
    exports bomb.modules.t.translated.solutions.morse to javafx.fxml;
    exports bomb.modules.t.translated.solutions.password to javafx.fxml;
    exports bomb.modules.t.translated.solutions.wof to javafx.fxml;
    exports bomb.modules.t.two.bit to javafx.fxml;
    exports bomb.modules.wz.word.search to javafx.fxml;
    exports bomb.modules.wz.zoo to javafx.fxml;

    exports bomb.tools to javafx.fxml;
    exports bomb.tools.data.structures.queue to javafx.fxml;
    exports bomb.tools.data.structures.ring to javafx.fxml;
    exports bomb.tools.logic to javafx.fxml;
    exports bomb.tools.note to javafx.fxml;
}