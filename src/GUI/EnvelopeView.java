package GUI;

import Controllers.AppController;

/**
 * Created by Christopher on 8/26/16.
 */
public class EnvelopeView extends View {


    private static final float KNOB_SPACING = 15f;
    private static final float KNOB_RADIUS = 20f;

    private RotaryView attackKnob;
    private RotaryView decayKnob;
    private RotaryView sustainKnob;
    private RotaryView releaseKnob;
    private RotaryView modAmtKnob;

    public EnvelopeView(float x, float y) {
        super(x, y);

        attackKnob = new RotaryView(0, 0, KNOB_RADIUS);
        attackKnob.setLabel("Attack");
        addChild(attackKnob);

        decayKnob = new RotaryView(2 * KNOB_RADIUS + KNOB_SPACING, 0, KNOB_RADIUS);
        decayKnob.setLabel("Decay");
        addChild(decayKnob);

        sustainKnob = new RotaryView(4 * KNOB_RADIUS + 2 * KNOB_SPACING, 0, KNOB_RADIUS);
        sustainKnob.setLabel("Sustain");
        addChild(sustainKnob);

        releaseKnob = new RotaryView(6 * KNOB_RADIUS + 3 * KNOB_SPACING, 0, KNOB_RADIUS);
        releaseKnob.setLabel("Release");
        addChild(releaseKnob);

        modAmtKnob = new RotaryView(8 * KNOB_RADIUS + 4 * KNOB_SPACING, 0, KNOB_RADIUS);
        modAmtKnob.setLabel("Amplitude");
        addChild(modAmtKnob);
    }

    public float getAttack() {
        return attackKnob.getValue();
    }

    public float getDecay() {
        return decayKnob.getValue();
    }

    public float getSustain() {
        return sustainKnob.getValue();
    }

    public float getRelease() {
        return releaseKnob.getValue();
    }

    public float getAmp() {
        return modAmtKnob.getValue();
    }
}
