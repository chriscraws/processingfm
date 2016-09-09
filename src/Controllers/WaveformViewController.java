package Controllers;

import GUI.MouseListener;
import GUI.WaveformView;
import SynthEngine.SynthEngine;
import static processing.core.PApplet.TWO_PI;

/**
 * Created by Christopher on 8/24/16.
 */
class WaveformViewController extends ViewController implements MouseListener{

    private WaveformView view;
    private float[] waveformBuffer;
    private boolean isCarrier = true;

    WaveformViewController(WaveformView view) {
        this.view = view;
        waveformBuffer = new float[view.getBufferLength()];
        AppController.getRunningInstance().registerMouseListener(this);
    }

    @Override
    public void step() {
        SynthEngine synthEngine = SynthEngine.get();

        for (int i = 0; i < view.getViewWidth(); i++) {
            float theta = TWO_PI * i / view.getViewWidth();
            if (!isCarrier) {
                theta *= 2;
            }
            waveformBuffer[i] = synthEngine.fmOsc.sample(theta);
        }

        view.writeWave(waveformBuffer);
    }

    @Override
    public void mouseMoved() {

    }

    @Override
    public void mouseClicked() {
        if (view.isMouseOver()) {
            isCarrier = !isCarrier;
        }
    }

    @Override
    public void mousePressed() {

    }

    @Override
    public void mouseReleased() {

    }
}
