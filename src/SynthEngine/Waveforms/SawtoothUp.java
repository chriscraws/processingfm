package SynthEngine.Waveforms;

import SynthEngine.Waveform;

import static processing.core.PConstants.TWO_PI;

/**
 * Created by Christopher on 8/29/16.
 */
public class SawtoothUp extends Waveform {
    @Override
    public float sample(float theta) {
        return (theta % TWO_PI) / TWO_PI;
    }
}
