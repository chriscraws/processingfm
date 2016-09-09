package SynthEngine.Waveforms;

import SynthEngine.ModularValue;
import SynthEngine.Waveform;

/**
 * Created by Christopher on 8/29/16.
 */
public class AddWave extends Waveform {

    private Waveform a;
    private ModularValue add;

    public AddWave(Waveform a, ModularValue add) {
        this.a = a;
        this.add = add;
    }

    @Override
    public float sample(float theta) {
        return a.sample(theta) + add.getValue();
    }
}
