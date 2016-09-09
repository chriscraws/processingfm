package SynthEngine;

import SynthEngine.Waveforms.AddWave;
import SynthEngine.Waveforms.ScaleWave;
import processing.core.PApplet;

import static processing.core.PConstants.TWO_PI;

/**
 * Waveform
 *
 * class is for 'oscillator' type periodic sounds
 *
 * TODO: make it work for more than 4 hours at a time
 */
public abstract class Waveform {

    private float theta = 0;
    private int periods = 0;


    public Waveform add(ModularValue x) {
        return new AddWave(this, x);
    }

    public Waveform scale(ModularValue scale) {
        return new ScaleWave(this, scale);
    }


    public abstract float sample(float theta);

    public final float next(float delta) {
        theta += delta;

        periods += PApplet.floor(theta / TWO_PI);
        theta %= TWO_PI;

        return sample(periods * TWO_PI + theta);
    }
}
