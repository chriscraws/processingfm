package Controllers;

import SynthEngine.SynthEngine;
import themidibus.Note;

import java.security.Key;

/**
 * Created by Christopher on 8/29/16.
 */
public class MidiController implements KeyListener {

    private int[] midiMap = new int[128];

    public MidiController() {

        midiMap['z'] = 60;
            midiMap['s'] = 61;
        midiMap['x'] = 62;
            midiMap['d'] = 63;
        midiMap['c'] = 64;
        midiMap['v'] = 65;
            midiMap['g'] = 66;
        midiMap['b'] = 67;
            midiMap['h'] = 68;
        midiMap['n'] = 69;
            midiMap['j'] = 70;
        midiMap['m'] = 71;
        midiMap[','] = 72;
            midiMap['l'] = 73;
        midiMap['.'] = 74;
            midiMap[';'] = 75;
        midiMap['/'] = 76;
            midiMap['\''] = 77;

    }

    @Override
    public void keyPressed(char key) {
        if (key < 128 && midiMap[key] != 0) {
            SynthEngine.get().noteOn(new Note(0, midiMap[key], 100));
        }
    }

    @Override
    public void keyReleased(char key) {
        if (key < 128 && midiMap[key] != 0) {
            SynthEngine.get().noteOff(new Note(0, midiMap[key], 100));
        }
    }
}
