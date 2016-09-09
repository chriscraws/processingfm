package SynthEngine;

import ddf.minim.AudioOutput;
import ddf.minim.UGen;
import ddf.minim.Minim;

/**
 * Group of Minim objects that use SynthEngine.SynthEngine to create audio
 */


public class AudioEngine extends UGen {

  private static boolean initialized = false;
  private static AudioEngine singleton = null;
  private static final int bufferSize = 256;

  private Minim minim;
  private boolean running = false;
  private SynthEngine synthEngine;

  public static AudioEngine get() {
    if (initialized) {
      return singleton;
    }
    return null;
  }

  private AudioEngine() {

  }

  @Override
  protected void uGenerate(float[] channels) {
    float sample = synthEngine.next(sampleRate());

    for (int i = 0; i < channels.length; i++) {
      channels[i] = sample;
    }
  }

  public static AudioEngine initialize(Minim minim) {
    if (!initialized) {
      singleton = new AudioEngine();
      singleton.minim = minim;
      singleton.synthEngine = SynthEngine.get();
      initialized = true;
    }
    return singleton;
  }

  public void start() {
    if (initialized) {
      AudioOutput out = minim.getLineOut(Minim.MONO, bufferSize);
      this.patch(out);
      running = true;
    }
  }

}
