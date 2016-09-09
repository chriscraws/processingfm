package Controllers;

import GUI.HarmonicView;
import SynthEngine.Harmonic;

import static processing.core.PConstants.TWO_PI;

/**
 * Created by Christopher on 8/27/16.
 */
public class HarmonicViewController extends ViewController {

    Harmonic harmonic;
    HarmonicView view;
    private float[] waveBuffer;

    public HarmonicViewController(Harmonic harmonic, HarmonicView view) {
        this.harmonic = harmonic;
        this.view = view;
        waveBuffer = new float[view.getBufferLength()];
    }

    @Override
    public void step() {

        harmonic.getAmp().setValue(view.getAmp());
        harmonic.getRatio().setValue(view.getRatio() * 8 + 0.1f);

        for (int i = 0; i < waveBuffer.length; i++) {
            float theta = TWO_PI * (float) i / (float) waveBuffer.length;
            theta /= harmonic.getRatio().getValue();
            waveBuffer[i] = harmonic.sample(theta);
        }

        view.writeWave(waveBuffer);

    }
}
