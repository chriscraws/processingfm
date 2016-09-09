package GUI;

import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PFont.Glyph;
import processing.core.PVector;

import static processing.core.PApplet.min;
import static sun.swing.MenuItemLayoutHelper.max;

/**
 * Created by Christopher on 8/29/16.
 */
public class NumberView extends View {


    private float numberHeight = 51f;
    private NumberBox leftDigit;
    private NumberBox rightDigit;

    private class NumberBox extends View implements MouseListener {

        private PVector dragStart;
        private boolean dragging;
        private int dragStartValue;
        private int value = 2;
        private float division = 10;

        char c = '0';

        NumberBox(float x, float y) {
            super(x, y);
            app.registerMouseListener(this);
        }

        public void setValue(int value) {
            this.c = String.valueOf(max(min(value, 9), 0)).charAt(0);
        }

        @Override
        public void draw() {
            app.stroke(0xFF999999);

            app.noFill();

            PFont font = app.createFont("helvetica", numberHeight, true);
            Glyph glyph = font.getGlyph(c);
            app.textFont(font, numberHeight);
            app.textSize(numberHeight);
            app.textAlign(PConstants.LEFT, PConstants.TOP);

            // app.text(v, 0, 0);
            float charTopOffset = app.textAscent() - glyph.height;
            // float y = (numberHeight - (charTopOffset + glyph.height)) / 2f;
            app.text(c, (numberHeight - glyph.width) / 2f, (numberHeight - glyph.height) / 2f - charTopOffset);

            // app.rect(glyph.leftExtent + (numberHeight - glyph.width) / 2f, (numberHeight - glyph.height) / 2f + app.textAscent() - glyph.height - charTopOffset, glyph.width, glyph.height);


            if (isMouseOverRect(0, 0, numberHeight, numberHeight)) {
                app.rect(0, 0, numberHeight, numberHeight);
            }
        }

        @Override
        public void mouseMoved() {
            if (dragging) {
                float dragValue = dragStart.y - mouseY;

                value =  max(0, (int) (dragStartValue + dragValue / division) % 10);
            }
        }

        @Override
        public void mouseClicked() {

        }

        @Override
        public void mousePressed() {
            if (isMouseOverRect(0, 0, numberHeight, numberHeight)) {
                dragStart = new PVector(mouseX, mouseY);
                dragStartValue = value;
                dragging = true;
            }
        }

        @Override
        public void mouseReleased() {
            if (dragging) {
                dragging = false;
            }
        }
    }

    public NumberView(float x, float y) {
        super(x, y);

        leftDigit = new NumberBox(0, 0);
        rightDigit = new NumberBox(numberHeight, 0);

        addChild(leftDigit);
        addChild(rightDigit);
    }

    @Override
    public void draw() {

    }
}
