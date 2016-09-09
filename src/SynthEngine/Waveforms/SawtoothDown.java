package SynthEngine.Waveforms;

import SynthEngine.Waveform;

import static processing.core.PConstants.TWO_PI;

/**
 * Created by Christopher on 8/29/16.
 */
public class SawtoothDown extends Waveform {
    @Override
    public float sample(float theta) {
        return 1f - (theta % TWO_PI) / TWO_PI;
    }
}
