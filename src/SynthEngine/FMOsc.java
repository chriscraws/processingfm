package SynthEngine;

import java.util.List;

import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.TWO_PI;
import static processing.core.PApplet.*;

/**
 * Created by Christopher on 8/24/16.
 */
public class FMOsc extends Waveform {

    private float fmAmt = 0;
    ModularValue amp = ModularValue.value(0f);

    List<Harmonic> harmonics;

    public FMOsc(List<Harmonic> harmonics) {
        this.harmonics = harmonics;
    }

    public float sample(float theta) {
        float sum = 0;
        for (Harmonic h : harmonics) {
            sum += h.sample(theta);
        }
        return amp.getValue() * sin(theta + HALF_PI * fmAmt * sum);
    }

    public void setFmAmt(float newValue) {
        fmAmt = newValue;
    }
}
