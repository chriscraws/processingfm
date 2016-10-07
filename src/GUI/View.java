package GUI;

import Controllers.AppController;

import java.util.ArrayList;

/**
 * Created by Christopher on 8/26/16.
 */
public class View {

    float x;
    float y;

    private View parent = null;
    private ArrayList<View> children = new ArrayList<>();

    protected AppController app;
    protected float mouseX;
    protected float mouseY;

    public View(float x, float y) {
        this.x = x;
        this.y = y;
        app = AppController.getRunningInstance();
    }

    public void draw() {

    }

    public final void render() {
        app.pushMatrix();
        app.resetMatrix();
        app.pushStyle();
        app.translate(absX(), absY());

        mouseX = app.mouseX - absX();
        mouseY = app.mouseY - absY();

        draw();

        app.popStyle();
        app.popMatrix();

        renderChildren();
    }

    protected boolean isMouseOverRect(float x, float y, float width, float height) {
        float absx = absX() + x;
        float absy = absY() + y;

        return app.mouseX >= absx && app.mouseX <= absx + width && app.mouseY >= absy && app.mouseY <= absy + height;
    }

    private void renderChildren() {
        children.forEach(View::render);
    }

    public void addChild(View child) {

        if (child.parent != null) {
            child.parent.removeChild(child);
        }

        child.parent = this;

        if (!children.contains(child)) {
            children.add(child);
        }

    }

    public void removeChild(View child) {
        children.remove(child);
    }

    public void reset() {
        parent.removeChild(this);
        x = 0;
        y = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    protected float absX() {
        if (parent == null) {
            return x;
        }
        return parent.absX() + x;
    }

    protected float absY() {
        if (parent == null) {
            return y;
        }
        return parent.absY() + y;
    }
}
