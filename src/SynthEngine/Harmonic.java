package SynthEngine;

import static processing.core.PApplet.*;

/**
 * A harmonic is essentially a sine-wave grain.
 */
public class Harmonic extends Waveform {

    private ModularValue amp = ModularValue.value(1f);
    private ModularValue ratio = ModularValue.value(2f);

    public float sample(float theta) {
        return amp.getValue() * sin(theta * ratio.getValue());
    }


    public ModularValue getAmp() {
        return amp;
    }

    public ModularValue getRatio() {
        return ratio;
    }
}
