package Controllers;

import SynthEngine.*;
import GUI.*;
import processing.core.PApplet;
import ddf.minim.Minim;
import themidibus.MidiBus;

import javax.sound.midi.*;
import java.util.ArrayList;

/**
 * FM Additive Grain Synthesis
 *
 *
 *
 * Goals:
 * 1) FM synthesis via adding a bunch of independent harmonics
 *
 *    harmonics can be modulated freely in the following ways:
 *    -pitch ratio
 *    -volume
 *    -phase
 *    -pan
 *
 *
 *    user-specified groups of harmoics can be modulated in the following ways:
 *    -pitch
 *
 *
 *    feature wishlist:
 *      2 better control for harmonic ratio
 *      7 midi control from anywhere
 *      X 1 modular modulation
 *      5 nice envelopes
 *      6 GUI layout
 *      4 Oscilloscope-peek
 *      3 Curves at knob-input combined with modular control (YESSS)
 *      8 tests
 *
 *
 *
 *
 * FM Synthesis:
 *
 * Carrier, Modulator.
 *
 * Carrier - ratio = 1.0 = grid 1
 * Modulator - second grid
 *
 *
 */
public class AppController extends PApplet implements Receiver {

  private ArrayList<MouseListener> mouseListeners;
  private ArrayList<KeyListener> keyListeners;
  private ViewController firstController = null;
  private MidiController midiController;
  private View rootView;

  private static AppController runningInstance = null;

  public static void main(String[] args) {
    PApplet.main("Controllers.AppController");
  }

  public static AppController getRunningInstance() {
    return runningInstance;
  }
  @Override
  public void settings() {
    size(500, 500, P2D);
    smooth(8);
  }

  @Override
  public void setup() {

    // Finish class setup
    runningInstance = this;
    mouseListeners = new ArrayList<>();
    keyListeners = new ArrayList<>();

    // Rev up AudioEngine
    Minim minim = new Minim(this);
    AudioEngine aEngine = AudioEngine.initialize(minim);
    aEngine.start();

    // Detect novation launch-key TODO: make this actually detect keyboards
    new MidiBus(SynthEngine.get(), 2, -1);
    registerKeyListener(new MidiController());

    // initialize graphical user-interface
    initializeGUI();

    colorMode(RGB, 1.0f);
  }

  private void initializeGUI() {

    rootView = new View(0, 0);

    WaveformView waveformView = new WaveformView(0, 0, width, 100);
    new WaveformViewController(waveformView);
    rootView.addChild(waveformView);

    EnvelopeView ampEnvView = new EnvelopeView(10, 150);
    new EnvelopeViewController(ampEnvView, SynthEngine.get().ampEnv);
    rootView.addChild(ampEnvView);

//    EnvelopeView fmEnvView = new EnvelopeView(10, 210);
//    new EnvelopeViewController(fmEnvView, SynthEngine.get().fmEnv);
//    rootView.addChild(fmEnvView);

    View harmonicsView = new View(10, 300);
    new HarmonicsController(harmonicsView);
    rootView.addChild(harmonicsView);

  }

  public View getRootView() {
    return rootView;
  }

  float theta = 0;

  @Override
  public void draw() {
    background(0);
    for (ViewController vc = firstController; vc != null; vc = vc.nextController) {
      vc.step();
    }
    rootView.render();
  }

  @Override
  public void mouseClicked() {
    mouseListeners.forEach(MouseListener::mouseClicked);
  }

  @Override
  public void mouseMoved() {
    mouseListeners.forEach(MouseListener::mouseMoved);
  }

  @Override
  public void mouseDragged() {
    mouseListeners.forEach(MouseListener::mouseMoved);
  }

  @Override
  public void mousePressed() {
    mouseListeners.forEach(MouseListener::mousePressed);
  }

  @Override
  public void mouseReleased() {
    mouseListeners.forEach(MouseListener::mouseReleased);
  }

  public void registerMouseListener(MouseListener listener) {
    mouseListeners.add(listener);
  }


  @Override
  public void keyPressed() {
    keyListeners.forEach(k -> k.keyPressed(key));
  }

  @Override
  public void keyReleased() {
    keyListeners.forEach(k -> k.keyReleased(key));
  }

  public void registerKeyListener(KeyListener listener) {
    keyListeners.add(listener);
  }


  @Override
  public void send(MidiMessage message, long timeStamp) {

  }

  @Override
  public void close() {

  }

  void registerViewController(ViewController viewController) {
    if (firstController == null) {
      firstController = viewController;
    } else {
      ViewController vc;
      for (vc = firstController; vc.nextController != null; vc = vc.nextController);
      vc.nextController = viewController;
      viewController.previousController = vc;
    }
  }

  void unregisterViewController(ViewController viewController) {

    ViewController next = viewController.nextController;
    ViewController prev = viewController.previousController;

    if (prev != null) {
      prev.nextController = next;
    }

    if (next != null) {
      next.previousController = prev;
    }

    if (firstController == viewController) {
      firstController = prev;
    }
  }

}
