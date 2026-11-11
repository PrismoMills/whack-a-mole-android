package com.nine.project_2_finalver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public final class ResultArtwork extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path sparkle = new Path();
    private boolean cleared;

    public ResultArtwork(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    void setCleared(boolean cleared) {
        this.cleared = cleared;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float size = Math.min(getWidth(), getHeight());
        canvas.save();
        canvas.translate((getWidth() - size) / 2, (getHeight() - size) / 2);
        canvas.scale(size / 100, size / 100);
        paint.setColor(cleared ? 0xFF98C85C : 0xFFEBD8AC);
        canvas.drawCircle(50, 50, 46, paint);
        paint.setColor(0xFF85502D);
        canvas.drawOval(15, 67, 85, 90, paint);
        paint.setColor(0xFF39251D);
        canvas.drawOval(20, 69, 80, 86, paint);
        canvas.save();
        canvas.clipRect(20, 12, 80, 85);
        BurrowView.drawMole(canvas, paint, !cleared);
        canvas.restore();
        if (cleared) {
            drawSparkle(canvas, 19, 34, 8);
            drawSparkle(canvas, 75, 22, 9);
            drawSparkle(canvas, 85, 47, 6);
        } else {
            paint.setColor(0xFF85502D);
            canvas.drawRoundRect(77, 30, 86, 33, 2, 2, paint);
            canvas.drawRoundRect(77, 37, 82, 40, 2, 2, paint);
        }
        canvas.restore();
    }

    private void drawSparkle(Canvas canvas, float x, float y, float size) {
        float inset = size * .27f;
        sparkle.reset();
        sparkle.moveTo(x, y - size);
        sparkle.lineTo(x + inset, y - inset);
        sparkle.lineTo(x + size, y);
        sparkle.lineTo(x + inset, y + inset);
        sparkle.lineTo(x, y + size);
        sparkle.lineTo(x - inset, y + inset);
        sparkle.lineTo(x - size, y);
        sparkle.lineTo(x - inset, y - inset);
        sparkle.close();
        paint.setColor(0xFFFFD65C);
        canvas.drawPath(sparkle, paint);
    }
}
