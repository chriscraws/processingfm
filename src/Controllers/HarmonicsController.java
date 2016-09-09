package Controllers;

import GUI.HarmonicView;
import GUI.View;
import SynthEngine.SynthEngine;
import SynthEngine.Harmonic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static processing.core.PConstants.TWO_PI;

/**
 * Mangages all the harmonics of SynthEngine (for now)
 */
public class HarmonicsController extends ViewController {

    private SynthEngine synthEngine;
    private View view;
    private List<HarmonicView> harmonicViews;
    private List<float[]> buffers;

    public HarmonicsController(View view) {
        this.view = view;
        harmonicViews = new ArrayList<>();
        synthEngine = SynthEngine.get();
        buffers = new ArrayList<>();
    }

    @Override
    public void step() {
        List<Harmonic> harmonics = synthEngine.getHarmonics();

        while (harmonicViews.size() < harmonics.size()) {
            HarmonicView harmonicView = new HarmonicView(0, 0);
            view.addChild(harmonicView);
            harmonicViews.add(harmonicView);
            buffers.add(new float[harmonicView.getBufferLength()]);
        }

        while (harmonicViews.size() > harmonics.size()) {
            View v = harmonicViews.remove(harmonicViews.size() - 1);
            v.reset();
        }

        for (int i = 0; i < harmonicViews.size(); i++) {
            Harmonic harmonic = harmonics.get(i);
            float[] waveBuffer = buffers.get(i);
            HarmonicView harmonicView = harmonicViews.get(i);

            harmonicView.setY(i * HarmonicView.defaultHeight);

            if (waveBuffer.length != harmonicView.getBufferLength()) {
                waveBuffer = new float[harmonicView.getBufferLength()];
            }

            harmonic.getAmp().setValue(harmonicView.getAmp());
            harmonic.getRatio().setValue(harmonicView.getRatio() * 8 + 0.1f);

            for (int bufferIndex = 0; bufferIndex < waveBuffer.length; bufferIndex++) {
                float theta = TWO_PI * (float) bufferIndex / (float) waveBuffer.length;
                theta /= harmonic.getRatio().getValue();
                waveBuffer[bufferIndex] = harmonic.sample(theta);
            }

            harmonicView.writeWave(waveBuffer);

        }
    }
}
