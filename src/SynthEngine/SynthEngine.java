package SynthEngine;

import ddf.minim.ugens.Frequency;
import themidibus.Note;

import java.util.*;

import static processing.core.PConstants.TWO_PI;

/**
 * Contains code for doing FM synthesis. Is accessed by both GUI and by the
 * audio engine.
 *
 *
 * Overview -
 *  FMO // waveform
 *  Harmonic // waveform
 *  ADSREnvelope // modulator
 *
 *  // waveform
 *
 *  - has phase
 *  - contains instructions for one full cycle
 *  - full waveform can be sampled independently from playback
 *  - last full waveform can be sampled independently from playback
 *  - playback pushes internal clock forwar
 */


public class SynthEngine {

  private static SynthEngine singleton = null;


  public FMOsc fmOsc;
  public ADSREnvelope ampEnv;

  private Frequency frequency = Frequency.ofHertz(100f);
  private ArrayList<NoteListener> noteListeners = new ArrayList<>();
  private ArrayList<Harmonic> harmonics = new ArrayList<Harmonic>();

  private SynthEngine() {
    // Set up SynthEngine
    harmonics.add(new Harmonic());

    fmOsc = new FMOsc(harmonics);
    ampEnv = new ADSREnvelope(10f, 1000f, 0f, 50f);
    fmOsc.amp.addModulator(ampEnv);

    registerNoteListener(ampEnv);
  }

  public static SynthEngine get() {
    if (singleton == null) {
      singleton = new SynthEngine();
    }
    return singleton;
  }

  public float next(float sampleRate) {
    float delta = TWO_PI * frequency.asHz() / sampleRate;

    //fmOsc.setFmAmt(fmEnv.next(sampleRate) * 200f);
    fmOsc.setFmAmt(10);
    ampEnv.next(1f / sampleRate);

    float sample = fmOsc.next(delta);

    return sample;
  }

  public List<Harmonic> getHarmonics() {
    return harmonics;
  }

  public void registerNoteListener(NoteListener listener) {
    if (!noteListeners.contains(listener)) {
      noteListeners.add(listener);
    }
  }

  public void noteOn(Note note) {
    frequency = Frequency.ofMidiNote(note.pitch);
    noteListeners.forEach((noteListener -> noteListener.noteOn(note)));
  }

  public void noteOff(Note note) {
    if (frequency.asMidiNote() == note.pitch) {
      noteListeners.forEach((noteListener -> noteListener.noteOff(note)));
    }
  }

}
