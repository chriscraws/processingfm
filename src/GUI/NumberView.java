package GUI;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont.Glyph;
import processing.core.PFont;

/**
 * NumberView is an integer-selector. Click and drag a particular digit up or down to edit the value.
 */
public class NumberView extends View implements MouseListener {

    private char[] digits;
    private int[] originalDigits;
    private int originalValue;
    private float size = 50f; // pixels
    private PFont font = app.createFont("helvetica", size, true);
    private int selectedDigit = -1;
    private int minimum;
    private int maximum;

    public NumberView(float x, float y, int numDigits) {
        super(x, y);
        app.registerMouseListener(this);

        digits = new char[numDigits];
        originalDigits = new int[numDigits];

        for (int i = 0; i < numDigits; i++) {
            digits[i] = '0';
            originalDigits[i] = 0;
        }

        minimum = 0;
        maximum = (int) PApplet.pow(10, numDigits) - 1;
    }

    public int getValue() {
        int value = 0;
        for (int i = 0; i < digits.length; i++) {
            value += ('0' - digits[i]) * PApplet.pow(10, digits.length - i - 1);
        }
        return value;
    }

    private void drawDigit(int i) {
        app.pushMatrix();

        app.translate(i * size, 0);

        Glyph glyph = font.getGlyph(digits[i]);
        float charTopOffset = app.textAscent() - glyph.height;
        app.text(digits[i], (size - glyph.width) / 2f, (size - glyph.height) / 2f - charTopOffset);

        app.popMatrix();
    }

    private void handleDrag() {
        app.rect(selectedDigit * size, 0, size, size);

        int increment = (int) -((mouseY - size * .5f) / 10f);

        app.text(increment, mouseX, mouseY);
        app.line(selectedDigit * size + 0.5f * size, size/2f, selectedDigit * size + 0.5f * size, mouseY);
        app.line(selectedDigit * size, mouseY, (selectedDigit + 1) * size, mouseY);

        int newValue = originalValue + increment * (int) PApplet.pow(10, digits.length - selectedDigit - 1);

        newValue = PApplet.max(newValue, minimum);
        newValue = PApplet.min(newValue, maximum);

        setDigits(newValue);
    }

    private void setDigits(int value) {
        for (int digit = 0; digit < digits.length; digit++) {
            // what is the value of 1 in this digit
            int one = (int) PApplet.pow(10, digits.length - digit - 1);

            // what is the numerical value of this digit
            //  digits   <-->   1   2   3   4
            //  ind      <-->   0   1   2   3
            //  power    <-->   3   2   1   0
            //  ones     <-->   1   1   1   1

            int digitValue = value / one;
            value %= one;

            // set each digit character
            digits[digit] = (char) ('0' + digitValue);
        }
    }

    @Override
    public void draw() {

        app.textFont(font, size);
        app.textSize(size);
        app.textAlign(PConstants.LEFT, PConstants.TOP);
        app.stroke(1f);
        app.noFill();

        if (selectedDigit >= 0) {
            handleDrag();
        }

        for (int i = 0; i < digits.length; i++) {
            drawDigit(i);
        }
    }

    @Override
    public void mouseMoved() {

    }

    @Override
    public void mouseClicked() {

    }

    @Override
    public void mousePressed() {
        for (int digit = 0; digit < digits.length; digit++) {
            if (isMouseOverRect(digit * size, 0, size, size)) {
                selectedDigit = digit;
                for (int i = 0; i < originalDigits.length; i++) {
                    originalDigits[i] = digits[i] - '0';
                }
                originalValue = getValue();
                return;
            }
        }
    }

    @Override
    public void mouseReleased() {
        selectedDigit = -1;
    }
}