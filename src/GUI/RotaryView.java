package GUI;

import Controllers.AppController;
import processing.core.PVector;

import static processing.core.PApplet.*;
import static processing.core.PConstants.TWO_PI;

/**
 * Rotary button for
 */
public class RotaryView extends View implements MouseListener {

    private float value = 0;
    private float radius;
    private float dragRatio = 3; // dragRatio * radius = how far to drag the mouse for 100%
    private float innerRadius = .7f;
    private boolean mouseOver;
    private boolean mouseDown = false;
    private PVector dragStartPosition;
    private float dragStartValue;
    private String label = "";


    // style
    private int backgroundColor = 0xFF404040;
    private int foregroundColor = 0xFF909090;
    private int strokeColor = 0xFF404040;
    private float gapAngle = TWO_PI / 6.0f;
    private int resolution = 60;
    private float textSpacer = 5;

    public RotaryView(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
        app.registerMouseListener(this);
    }

    public void draw() {

        // draw text
        app.textAlign(CENTER, TOP);
        app.text(label, radius, 2 * radius + textSpacer);

        if (mouseDown) {
            app.stroke(strokeColor);
            app.strokeWeight(1f / radius);
        } else {
            app.noStroke();
        }
        app.translate(radius, radius);
        app.scale(radius);

        app.fill(backgroundColor);
        drawArc(getValue(), 1f);

        app.fill(foregroundColor);
        drawArc(0f, getValue());

    }



    private float getTheta(float i) {
        float maxTheta = TWO_PI - gapAngle;
        return HALF_PI + (i / (float) resolution) * maxTheta + gapAngle / 2f;
    }

    private void drawArc(float start, float end) {
        float theta;
        float py, px;

        int startInd = (int) (start * resolution);
        int endInd = (int) (end * resolution);

        app.beginShape();
        px = innerRadius * cos(getTheta(startInd));
        py = innerRadius * sin(getTheta(startInd));
        app.vertex(px, py);


        // outer edge
        for (int i = startInd; i <= endInd; i++) {

            theta = getTheta(i);

            px = cos(theta);
            py = sin(theta);

            app.vertex(px, py);
        }

        px = innerRadius * cos(getTheta(endInd));
        py = innerRadius * sin(getTheta(endInd));
        app.vertex(px, py);

        // inner edge
        for (int i = endInd; i >= startInd; i--) {

            theta = getTheta(i);

            px = innerRadius * cos(theta);
            py = innerRadius * sin(theta);

            app.vertex(px, py);
        }

        app.endShape();
    }



    private void handleDrag() {
        float vDiff = app.mouseY - dragStartPosition.y;
        float delta = vDiff / (dragRatio * radius);

        value = dragStartValue - delta;

        value = min(value, 1f);
        value = max(value, 0f);
    }

    @Override
    public void mouseMoved() {

        float mouseX = app.mouseX - absX();
        float mouseY = app.mouseY - absY();

        if (mouseX > 0 && mouseX < radius * 2 &&
                mouseY > 0 && mouseY < radius * 2) {
            mouseOver = true;
        } else {
            mouseOver = false;
        }

        if (mouseDown) {
            handleDrag();
        }
    }

    @Override
    public void mouseClicked() {

    }

    @Override
    public void mousePressed() {
        if (mouseOver) {
            mouseDown = true;
            dragStartPosition = new PVector(app.mouseX, app.mouseY);
            dragStartValue = value;
        }
    }

    @Override
    public void mouseReleased() {
        mouseDown = false;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
