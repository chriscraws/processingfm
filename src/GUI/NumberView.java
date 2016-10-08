package GUI;

import processing.core.PConstants;
import processing.core.PFont.Glyph;
import processing.core.PFont;
import processing.core.PVector;

public class NumberView extends View implements MouseListener {

    private char[] digits = {'0', '0'};
    private float size = 51f; // pixels
    private float sensitivity = 7; // pixels per 1 value change
    private PFont font = app.createFont("helvetica", size, true);
    private int selectedDigit = -1;
    private int baseValue = 0;
    private float lastMouseY = mouseY;

    public NumberView(float x, float y) {
        super(x, y);
        app.registerMouseListener(this);
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

        int newValue = (baseValue + increment) % 10;
        if (newValue < 0) {
            newValue += 10;
        }

        digits[selectedDigit] = (char) ('0' + newValue);

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
                lastMouseY = mouseY;
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