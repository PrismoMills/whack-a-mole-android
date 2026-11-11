package com.nine.project_2_finalver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.View;

public final class HomeArtwork extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path badge = new Path();
    private float revealProgress;

    public HomeArtwork(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        setContentDescription("Whack-a-Mole");
        paint.setTypeface(Typeface.create("serif", Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        badge.moveTo(180, 12);
        badge.lineTo(270, 63);
        badge.lineTo(270, 96);
        badge.lineTo(348, 96);
        badge.lineTo(348, 156);
        badge.lineTo(270, 156);
        badge.lineTo(270, 169);
        badge.lineTo(180, 221);
        badge.lineTo(90, 169);
        badge.lineTo(90, 156);
        badge.lineTo(12, 156);
        badge.lineTo(12, 96);
        badge.lineTo(90, 96);
        badge.lineTo(90, 63);
        badge.close();
    }

    void setRevealProgress(float progress) {
        revealProgress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scale = Math.min(getWidth() / 360f, getHeight() / 354f);
        canvas.save();
        canvas.translate((getWidth() - 360 * scale) / 2, (getHeight() - 354 * scale) / 2);
        canvas.scale(scale, scale);

        float rise = Math.max(0f, (revealProgress - .15f) / .85f);
        canvas.save();
        canvas.translate(0, 82 * (1 - rise));
        float titleScale = 1 + .05f * (1 - rise);
        canvas.scale(titleScale, titleScale, 180, 116);

        paint.setColor(0xFFF3EDDA);
        canvas.drawPath(badge, paint);
        paint.setColor(0xFF679942);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5);
        canvas.drawPath(badge, paint);
        paint.setStyle(Paint.Style.FILL);

        drawMallet(canvas, 136, -42);
        drawMallet(canvas, 224, 42);
        paint.setColor(0xFF2D3B2B);
        paint.setTextSize(25);
        canvas.drawText("Whack", 70, 134, paint);
        canvas.drawText("Mole", 296, 134, paint);
        paint.setTextSize(26);
        canvas.drawText("a", 180, 63, paint);

        canvas.restore();
        if (revealProgress > 0) drawBurrow(canvas, rise);
        canvas.restore();
    }

    private void drawBurrow(Canvas canvas, float rise) {
        canvas.save();
        float opening = Math.min(1f, revealProgress / .22f);
        canvas.scale(opening, opening, 180, 322);
        paint.setColor(0xFF679942);
        canvas.drawOval(101, 301, 259, 343, paint);
        paint.setColor(0xFF85502D);
        canvas.drawOval(108, 302, 252, 338, paint);
        paint.setColor(0xFF39251D);
        canvas.drawOval(116, 306, 244, 331, paint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(115, 224, 245, 330);
        canvas.translate(105, 198 + 101 * (1 - rise));
        canvas.scale(1.5f, 1.5f);
        BurrowView.drawMole(canvas, paint, false);
        canvas.restore();
    }

    private void drawMallet(Canvas canvas, float x, float angle) {
        canvas.save();
        canvas.translate(180 + (x - 180) * .9f, 91);
        canvas.rotate(angle);
        canvas.scale(1.035f, 1.485f);
        BurrowView.drawMallet(canvas, paint);
        canvas.restore();
    }
}




