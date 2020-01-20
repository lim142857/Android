package fall2018.csc2017.GameCenter.Hanoi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * The class TextDrawable for displaying the player's score
 */
public class TextDrawable extends Drawable {

    /**
     * Message to display moves
     */
    private final String TEXT;

    /**
     * The attribute PAINT for the class TextDrawable
     */
    private final Paint PAINT;

    /**
     * A new Text containing the required information
     *
     * @param text The TEXT to display
     */
    TextDrawable(String text) {

        this.TEXT = text;

        this.PAINT = new Paint();
        PAINT.setColor(Color.BLACK);
        PAINT.setTextSize(92f);
        PAINT.setAntiAlias(true);
        PAINT.setFakeBoldText(true);
        PAINT.setShadowLayer(6f, 0, 0, Color.BLACK);
        PAINT.setStyle(Paint.Style.FILL);
        PAINT.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawText(TEXT, -850, -1300, PAINT);
    }

    @Override
    public void setAlpha(int alpha) {
        PAINT.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        PAINT.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
