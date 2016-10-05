package GUI;

import processing.core.PConstants;
import processing.core.PFont.Glyph;
import processing.core.PFont;
import processing.core.PVector;

public class NumberView extends View implements MouseListener {

    private char[] digits = {'0', '0'};
    private float size = 51f; // pixels
    private float sensitivity;
    private PFont font = app.createFont("helvetica", size, true);
    private boolean mouseDown = false;
    private PVector lastMousePosition = new PVector(0, 0);

    public NumberView(float x, float y) {
        super(x, y);
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
        PVector delta = PVector.sub(lastMousePosition, new PVector(mouseX, mouseY));
        

    }

    @Override
    public void draw() {

        if (mouseDown) {
            handleDrag();
        }

        app.textFont(font, size);
        app.textSize(size);
        app.textAlign(PConstants.LEFT, PConstants.TOP);

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
        if (isMouseOverRect(0, 0, digits.length * size, digits.length * size)) {
            mouseDown = true;
            lastMousePosition.x = mouseX;
            lastMousePosition.y = mouseY;
        }
    }

    @Override
    public void mouseReleased() {
        mouseDown = false;
    }
}