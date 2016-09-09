package SynthEngine;

import themidibus.Note;

/**
 * Created by Christopher on 8/27/16.
 */
public interface NoteListener {

    void noteOn(Note note);

    void noteOff(Note note);
}
