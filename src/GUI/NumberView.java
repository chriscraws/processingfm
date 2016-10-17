package GUI;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont.Glyph;
import processing.core.PFont;

public class NumberView extends View implements MouseListener {

    private char[] digits;
    private int[] originalDigits;
    private float size = 51f; // pixels
    private PFont font = app.createFont("helvetica", size, true);
    private int selectedDigit = -1;

    public NumberView(float x, float y, int numDigits) {
        super(x, y);
        app.registerMouseListener(this);

        digits = new char[numDigits];
        originalDigits = new int[numDigits];

        for (int i = 0; i < numDigits; i++) {
            digits[i] = '0';
            originalDigits[i] = 0;
        }

    }

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


        // increment digits to the left if high enough
        for (int digit = selectedDigit; digit >= 0; digit--) {

            int newValue = (originalDigits[digit] + increment) % 10;
            if (newValue < 0) {
                newValue += 10;
            }

            digits[digit] = (char) ('0' + newValue);

            increment /= 10;
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
                return;
            }
        }
    }

    @Override
    public void mouseReleased() {
        selectedDigit = -1;
    }
}