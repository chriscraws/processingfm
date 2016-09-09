package SynthEngine;


import SynthEngine.Waveforms.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import themidibus.Note;


/**
 *
 */
public class ADSREnvelope extends Envelope implements NoteListener {

    private Waveform attackWave;
    private Waveform decayWave;
    private Waveform sustainWave;
    private Waveform releaseWave;
    private ModularValue releaseStart;
    private ModularValue attackStart;

    public ADSREnvelope(float attack, float decay, float sustain, float release) {
        super(attack, decay, sustain, release);

        releaseStart = ModularValue.value(sustain);
        attackStart = ModularValue.value(0f);

        // attack scale = 1 - attackstart
        ModularValue attackScale = ModularValue.value(1).add(attackStart.scale(ModularValue.value(-1f)));
        attackWave = new SawtoothUp().scale(attackScale).add(attackStart);

        // decay scale = 1 - sustain
        ModularValue decayScale = ModularValue.value(1).add(this.sustain.scale(ModularValue.value(-1f)));
        decayWave = new SawtoothDown().scale(decayScale).add(this.sustain);
        sustainWave = new Flat(this.sustain);
        releaseWave = new SawtoothDown().scale(releaseStart);

    }

    @Override
    public void noteOn(Note note) {
        attackStart.setValue(next(0));
        super.noteOn();
    }

    @Override
    public void noteOff(Note note) {
        releaseStart.setValue(next(0));
        super.noteOff();
    }

    @Override
    public Waveform getAttackWave() {
        return attackWave;
    }

    @Override
    public Waveform getDecayWave() {
        return decayWave;
    }

    @Override
    public Waveform getSustainWave() {
        return sustainWave;
    }

    @Override
    public Waveform getReleaseWave() {
        return releaseWave;
    }
}
