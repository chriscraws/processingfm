package SynthEngine.Waveforms;

import SynthEngine.ModularValue;
import SynthEngine.Waveform;

/**
 * Created by Christopher on 8/29/16.
 */
public class Flat extends Waveform {

    private ModularValue value;

    public Flat(ModularValue value) {
        this.value = value;
    }

    @Override
    public float sample(float theta) {
        return value.getValue();
    }
}
