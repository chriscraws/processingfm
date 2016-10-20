package GUI;


/**
 * Created by Christopher on 8/27/16.
 */
public class HarmonicView extends View {

    private WaveformView waveformView;
    private RotaryView ampKnobView;
    private NumberView ratioControl;

    public static float defaultHeight = 70f;

    public HarmonicView(float x, float y) {
        super(x, y);

        waveformView = new WaveformView(0, 0, 200, 50);
        addChild(waveformView);

        ampKnobView = new RotaryView(220, 0, 20);
        ampKnobView.setLabel("Amp");
        addChild(ampKnobView);

        ratioControl = new NumberView(270, 0, 2);
        addChild(ratioControl);


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

    public float getRatio() {
        return ratioControl.getValue() / 100f;
    }
}
