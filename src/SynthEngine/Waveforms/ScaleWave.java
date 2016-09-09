package SynthEngine.Waveforms;

import SynthEngine.ModularValue;
import SynthEngine.Waveform;

/**
 * Created by Christopher on 8/29/16.
 */
public class ScaleWave extends Waveform {

    private ModularValue scale;
    private Waveform baseWave;

    public ScaleWave(Waveform base, ModularValue scale) {
        this.baseWave = base;
        this.scale = scale;
    }

    @Override
    public float sample(float theta) {
        return scale.getValue() * baseWave.sample(theta);
    }
}
