package GUI;


/**
 * Created by Christopher on 8/27/16.
 */
public class HarmonicView extends View {

    private WaveformView waveformView;
    private RotaryView ampKnobView;
    private RotaryView ratioKnobView;
    private NumberView numberView;

    public static float defaultHeight = 70f;

    public HarmonicView(float x, float y) {
        super(x, y);

        waveformView = new WaveformView(0, 0, 200, 50);
        addChild(waveformView);

        ampKnobView = new RotaryView(220, 0, 20);
        ampKnobView.setLabel("Amp");
        addChild(ampKnobView);

        ratioKnobView = new RotaryView(270, 0, 20);
        ratioKnobView.setLabel("Ratio");
        addChild(ratioKnobView);

        numberView = new NumberView(350, 0);
        addChild(numberView);

    }

    public void writeWave(float[] buffer) {
        waveformView.writeWave(buffer);
    }

    public int getBufferLength() {
        return waveformView.getBufferLength();
    }

    public void setAmp(float amp) {
        ampKnobView.setValue(amp);
    }

    public float getAmp() {
        return ampKnobView.getValue();
    }

    public void setRatio(float ratio) {
        ratioKnobView.setValue(ratio);
    }

    public float getRatio() {
        return ratioKnobView.getValue();
    }
}
