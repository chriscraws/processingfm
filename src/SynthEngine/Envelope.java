package SynthEngine;


import SynthEngine.Waveforms.Flat;

import static SynthEngine.Envelope.EnvState.*;
import static processing.core.PConstants.TWO_PI;

/**
 * takes time in MS
 */
public abstract class Envelope {

    ModularValue attack;
    ModularValue decay;
    ModularValue sustain;
    ModularValue release;

    enum EnvState {ATTACK, DECAY, SUSTAIN, RELEASE, OFF}

    private float time = 0;
    private EnvState state = OFF;
    private Waveform offWaveform = new Flat(ModularValue.value(0));


    public Envelope(float attack, float decay, float sustain, float release) {
        this.attack = ModularValue.value(attack);
        this.decay = ModularValue.value(decay);
        this.sustain = ModularValue.value(sustain);
        this.release = ModularValue.value(release);
    }

    public void noteOn() {
        time = 0;
        state = ATTACK;
    }

    public void noteOff() {
        time = 0;
        state = RELEASE;
    }

    private Waveform getStateWaveform() {
        switch (state) {
            case ATTACK:
                return getAttackWave();
            case DECAY:
                return getDecayWave();
            case SUSTAIN:
                return getSustainWave();
            case RELEASE:
                return getReleaseWave();
            default:
                return offWaveform;
        }
    }

    private float getStateTime() {
        switch (state) {
            case ATTACK:
                return attack.getValue();
            case DECAY:
                return decay.getValue();
            case RELEASE:
                return release.getValue();
            default:
                return 0;
        }
    }

    private EnvState getNextState() {
        switch (state) {
            case ATTACK:
                return DECAY;
            case DECAY:
                return SUSTAIN;
            case SUSTAIN:
                return RELEASE;
            default:
                return OFF;
        }
    }

    public float next(float delta) {

        if (state == OFF) {
            return 0f;
        }

        // convert to milliseconds
        delta *= 1000;

        time += delta;

        for (;;) {
            if (state != SUSTAIN && time > getStateTime()) {
                time = 0;
                state = getNextState();
            } else {
                float theta = TWO_PI * time / getStateTime();
                return getStateWaveform().sample(theta);
            }
        }
    }

    public abstract Waveform getAttackWave();
    public abstract Waveform getDecayWave();
    public abstract Waveform getSustainWave();
    public abstract Waveform getReleaseWave();

    public ModularValue getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack.setValue(attack);
    }

    public ModularValue getDecay() {
        return decay;
    }

    public void setDecay(float decay) {
        this.decay.setValue(decay);
    }

    public ModularValue getSustain() {
        return sustain;
    }

    public void setSustain(float sustain) {
        this.sustain.setValue(sustain);
    }

    public ModularValue getRelease() {
        return release;
    }

    public void setRelease(float release) {
        this.release.setValue(release);
    }
}
