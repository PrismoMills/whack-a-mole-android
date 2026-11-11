package com.nine.project_2_finalver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

/** Draws one burrow in a 100-by-100 coordinate system. */
final class BurrowView extends View {
    private static final int SOIL = 0xFF85502D;
    private static final int FUR = 0xFFAE683B;
    private static final int MUZZLE = 0xFFF1BA79;
    private static final int INK = 0xFF352318;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private boolean emerged;
    private long motionStart;
    private long impactStart = -1000;
    private float from;

    BurrowView(Context context) {
        super(context);
        setFocusable(true);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(Button.class.getName());
    }

    void setEmerged(boolean visible) {
        if (emerged == visible) return;
        long now = SystemClock.uptimeMillis();
        from = liftAt(now);
        motionStart = now;
        emerged = visible;
        invalidate();
    }

    void showImpact() {
        impactStart = SystemClock.uptimeMillis();
        setEmerged(false);
        invalidate();
    }

    private float liftAt(long now) {
        float progress = Math.min(1f, (now - motionStart) / (emerged ? 150f : 120f));
        float eased = 1f - (1f - progress) * (1f - progress);
        return from + ((emerged ? 1f : 0f) - from) * eased;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float size = Math.min(getWidth(), getHeight());
        canvas.save();
        canvas.translate((getWidth() - size) / 2f, (getHeight() - size) / 2f);
        canvas.scale(size / 100f, size / 100f);

        oval(canvas, paint, 7, 63, 93, 96, 0xFF75A742);
        oval(canvas, paint, 10, 60, 90, 92, SOIL);
        oval(canvas, paint, 15, 63, 85, 86, 0xFF39251D);

        long now = SystemClock.uptimeMillis();
        float lift = liftAt(now);
        long impactAge = now - impactStart;
        if (lift > 0f) {
            path.reset();
            path.moveTo(15, 0);
            path.lineTo(85, 0);
            path.lineTo(85, 74);
            path.cubicTo(85, 90, 15, 90, 15, 74);
            path.close();
            canvas.save();
            canvas.clipPath(path);
            canvas.translate(0, 62 * (1f - lift));
            drawMole(canvas, paint, impactAge < 200);
            canvas.restore();
        }

        oval(canvas, paint, 37, 88, 47, 91, 0xFFB57944);
        oval(canvas, paint, 67, 87, 72, 90, 0xFFB57944);
        if (impactAge < 210) drawImpact(canvas, impactAge / 210f);
        canvas.restore();

        if (now - motionStart < 150 || impactAge < 210) postInvalidateOnAnimation();
    }

    static void drawMole(Canvas canvas, Paint paint, boolean hit) {
        paint.setColor(FUR);
        canvas.drawRoundRect(26, 23, 74, 93, 23, 23, paint);
        oval(canvas, paint, 35, 51, 65, 70, MUZZLE);
        oval(canvas, paint, 44, 48, 56, 56, INK);
        oval(canvas, paint, 46, 49, 50, 51, 0xFFFFF0CF);
        if (hit) {
            line(canvas, paint, 36, 40, 42, 43, INK, 2.4f);
            line(canvas, paint, 42, 43, 36, 46, INK, 2.4f);
            line(canvas, paint, 64, 40, 58, 43, INK, 2.4f);
            line(canvas, paint, 58, 43, 64, 46, INK, 2.4f);
        } else {
            oval(canvas, paint, 36, 38, 43, 47, INK);
            oval(canvas, paint, 57, 38, 64, 47, INK);
            oval(canvas, paint, 37, 39, 40, 42, 0xFFFFF4D8);
            oval(canvas, paint, 58, 39, 61, 42, 0xFFFFF4D8);
        }
        line(canvas, paint, 32, 55, 39, 57, INK, 1.4f);
        line(canvas, paint, 32, 61, 39, 60, INK, 1.4f);
        line(canvas, paint, 68, 55, 61, 57, INK, 1.4f);
        line(canvas, paint, 68, 61, 61, 60, INK, 1.4f);
        oval(canvas, paint, 26, 72, 40, 83, MUZZLE);
        oval(canvas, paint, 60, 72, 74, 83, MUZZLE);
    }

    private void drawImpact(Canvas canvas, float progress) {
        canvas.save();
        canvas.translate(0, -12 * progress);
        canvas.translate(52, 25);
        canvas.rotate(-62 + 46 * progress);
        canvas.scale(.42f, .42f);
        drawMallet(canvas, paint);
        canvas.restore();
        int gold = 0xFFFFD65C;
        line(canvas, paint, 29, 27, 23, 19, gold, 3);
        line(canvas, paint, 24, 34, 14, 32, gold, 3);
        line(canvas, paint, 35, 22, 34, 12, gold, 3);
    }

    // The horizontal head and vertical handle share the same center axis.
    static void drawMallet(Canvas canvas, Paint paint) {
        paint.setColor(0xFF694329);
        canvas.drawRoundRect(-6, 6, 6, 78, 5, 5, paint);
        paint.setColor(0xFFEF6D3B);
        canvas.drawRoundRect(-30, -15, 30, 15, 7, 7, paint);
        paint.setColor(0xFFFFAD4E);
        canvas.drawRoundRect(-32, -17, -22, 17, 4, 4, paint);
        canvas.drawRoundRect(22, -17, 32, 17, 4, 4, paint);
    }

    private static void oval(Canvas canvas, Paint paint, float left, float top, float right, float bottom, int color) {
        paint.setColor(color);
        canvas.drawOval(left, top, right, bottom, paint);
    }

    private static void line(Canvas canvas, Paint paint, float x1, float y1, float x2, float y2, int color, float width) {
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }
}

