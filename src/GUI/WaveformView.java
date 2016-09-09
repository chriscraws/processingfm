package GUI;


import static java.lang.Float.min;


/**
 * Class for drawing the interactive waveform.
 *
 * Modes:
 * 1) Show one period of carrier
 * 2) Show entire waveform
 *
 * Todo: display the mode
 *
 * 1) show full wavelength compared to whats shown
 * 2)
 *
 */
public class WaveformView extends View {

  private float viewWidth;
  private float viewHeight;
  private int waveColor = 1;
  private int backgroundColor = 0;
  private int backgroundHoverColor = 0x28FFFFFF;
  private boolean mouseOver = false;
  private float[] waveBuffer;


  public WaveformView(float x, float y, float width, float height){
    super(x, y);
    viewHeight = height;
    viewWidth = width;
    waveBuffer = new float[(int) width];
  }

  public void writeWave(float[] waveBuffer) {
    for (int i = 0; i < min(waveBuffer.length, this.waveBuffer.length); i++) {
      this.waveBuffer[i] = waveBuffer[i];
    }
  }

  public int getBufferLength() {
    return waveBuffer.length;
  }

  public void draw() {

    // detect mouse
    float x = absX() + this.x;
    float y = absY() + this.y;

    if (app.mouseX > x && app.mouseX < x + viewWidth &&
            app.mouseY > y && app.mouseY < y + viewHeight) {
      mouseOver = true;
    } else {
      mouseOver = false;
    }

    // draw background
    app.pushStyle();
    app.noStroke();
    if (mouseOver) {
      app.fill(backgroundHoverColor);
    } else {
      app.fill(backgroundColor);
    }
    app.rect(0, 0, viewWidth, viewHeight);
    app.popStyle();


    // draw waveform
    app.pushStyle();
    app.stroke(waveColor);
    app.noFill();
    app.beginShape();

    for (int i = 0; i < waveBuffer.length; i++) {
      app.vertex(i, viewHeight * (waveBuffer[i] + 1) / 2);
    }

    app.endShape();
  }

  public boolean isMouseOver() {
    return mouseOver;
  }

  public float getViewWidth() {
    return viewWidth;
  }

  public void setViewWidth(float viewWidth) {
    this.viewWidth = viewWidth;
  }

  public float getViewHeight() {
    return viewHeight;
  }

  public void setViewHeight(float viewHeight) {
    this.viewHeight = viewHeight;
  }
}
