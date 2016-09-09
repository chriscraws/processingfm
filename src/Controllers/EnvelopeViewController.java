package Controllers;

import GUI.EnvelopeView;
import SynthEngine.ADSREnvelope;

/**
 * Created by Christopher on 8/26/16.
 */
class EnvelopeViewController extends ViewController {

    private EnvelopeView view;
    private ADSREnvelope env;

    EnvelopeViewController(EnvelopeView view, ADSREnvelope env) {
        this.env = env;
        this.view = view;
    }

    @Override
    public void step () {
        env.setAttack(view.getAttack() * 2000f);
        env.setDecay(view.getDecay() * 2000f);
        env.setSustain(view.getSustain());
        env.setRelease(view.getRelease() * 2000f);
        //env.setAmplitude(view.getAmp());
    }
 }
