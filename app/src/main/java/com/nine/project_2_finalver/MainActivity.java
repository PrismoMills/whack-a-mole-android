package com.nine.project_2_finalver;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int LEVELS = 3;
    private static final int POINTS_PER_LEVEL = 10;
    private static final long ROUND_MS = 30_000;
    private static final int[] MOVE_INTERVALS_MS = {1150, 1000, 850};
    private static final int DARK = Color.rgb(45, 59, 43);

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();
    private final BurrowView[] holes = new BurrowView[9];
    private final Runnable moveMole = this::showNextMole;
    private TextView scoreView;
    private TextView levelView;
    private TextView timeView;
    private TextView message;
    private View homeView;
    private View introTouch;
    private HomeArtwork homeArtwork;
    private Button playButton;
    private ValueAnimator homeReveal;
    private boolean homeRevealed;
    private View gameView;
    private boolean home = true;
    private CountDownTimer timer;
    private int levelHits;
    private int level = 1;
    private int activeHole = -1;
    private int lastHole = -1;
    private boolean running;
    private boolean finished;
    private long remainingMs = ROUND_MS;
    private long deadline;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(DARK);
        getWindow().setNavigationBarColor(DARK);
        scoreView = findViewById(R.id.score);
        levelView = findViewById(R.id.level);
        timeView = findViewById(R.id.time);
        message = findViewById(R.id.message);
        playButton = findViewById(R.id.play);
        playButton.setOnClickListener(view -> startGame());
        homeView = findViewById(R.id.home);
        homeArtwork = findViewById(R.id.home_art);
        introTouch = findViewById(R.id.intro_touch);
        introTouch.setOnClickListener(view -> revealHome());
        gameView = findViewById(R.id.game);
        buildBoard(findViewById(R.id.board));

        if (savedState != null) {
            home = savedState.getBoolean("home", true);
            homeRevealed = savedState.getBoolean("homeRevealed", !home);
            levelHits = savedState.getInt("levelHits", Math.max(0,
                    savedState.getInt("score") - (savedState.getInt("level", 1) - 1) * POINTS_PER_LEVEL));
            level = savedState.getInt("level", 1);
            running = savedState.getBoolean("running");
            finished = savedState.getBoolean("finished");
            remainingMs = savedState.getLong("remaining", ROUND_MS);
        }
        render();
        configureHome();
        if (!home && finished) showResult();
    }

    private void configureHome() {
        homeArtwork.setRevealProgress(homeRevealed ? 1f : 0f);
        playButton.setVisibility(homeRevealed ? View.VISIBLE : View.INVISIBLE);
        introTouch.setVisibility(home && !homeRevealed ? View.VISIBLE : View.GONE);
    }

    private void revealHome() {
        if (homeReveal != null) return;
        introTouch.setClickable(false);
        homeReveal = ValueAnimator.ofFloat(0f, 1f);
        homeReveal.setDuration(700);
        homeReveal.addUpdateListener(animation ->
                homeArtwork.setRevealProgress((float) animation.getAnimatedValue()));
        homeReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                homeReveal = null;
                homeRevealed = true;
                configureHome();
            }
        });
        homeReveal.start();
    }

    @Override
    protected void onDestroy() {
        if (homeReveal != null) {
            homeReveal.removeAllListeners();
            homeReveal.cancel();
        }
        super.onDestroy();
    }

    private void buildBoard(LinearLayout board) {
        int height = dp(getResources().getConfiguration().screenHeightDp < 520 ? 64 : 88);
        GradientDrawable field = new GradientDrawable();
        field.setColor(0xFF98C85C);
        field.setCornerRadius(dp(24));
        field.setStroke(dp(3), 0xFF679942);
        board.setBackground(field);
        board.setPadding(dp(5), dp(5), dp(5), dp(5));
        for (int row = 0; row < 3; row++) {
            LinearLayout line = new LinearLayout(this);
            board.addView(line, new LinearLayout.LayoutParams(-1, height));
            for (int col = 0; col < 3; col++) {
                int index = row * 3 + col;
                BurrowView hole = new BurrowView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -1, 1);
                params.setMargins(dp(4), dp(4), dp(4), dp(4));
                hole.setOnClickListener(view -> hit(index));
                line.addView(hole, params);
                holes[index] = hole;
            }
        }
    }

    private void startGame() {
        stopRound();
        home = false;
        levelHits = 0;
        level = 1;
        lastHole = -1;
        finished = false;
        running = true;
        remainingMs = ROUND_MS;
        startRound();
    }

    private void startRound() {
        stopRound();
        if (remainingMs <= 0) {
            finishGame();
            return;
        }
        deadline = SystemClock.elapsedRealtime() + remainingMs;
        render();
        timer = new CountDownTimer(remainingMs, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                renderTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                remainingMs = 0;
                finishGame();
            }
        }.start();
        showNextMole();
    }

    private void showNextMole() {
        if (!running) return;
        if (SystemClock.elapsedRealtime() >= deadline) {
            remainingMs = 0;
            finishGame();
            return;
        }
        int next = random.nextInt(holes.length - (lastHole < 0 ? 0 : 1));
        if (lastHole >= 0 && next >= lastHole) next++;
        activeHole = next;
        lastHole = next;
        renderBoard();
        handler.postDelayed(moveMole, MOVE_INTERVALS_MS[level - 1]);
    }

    private void hit(int index) {
        if (!running || index != activeHole) return;
        if (SystemClock.elapsedRealtime() >= deadline) {
            remainingMs = 0;
            finishGame();
            return;
        }
        activeHole = -1;
        holes[index].showImpact();
        levelHits++;
        if (levelHits == POINTS_PER_LEVEL) {
            remainingMs = millisLeft();
            finishGame();
        } else {
            renderScore();
            renderBoard();
        }
    }

    private void finishGame() {
        running = false;
        finished = true;
        stopRound();
        render();
        showResult();
    }

    private void showResult() {
        boolean cleared = levelHits >= POINTS_PER_LEVEL;
        boolean moreLevels = cleared && level < LEVELS;
        String title = moreLevels ? "Level " + level + " cleared!"
                : cleared ? "You cleared all three levels!" : "Time’s up!";
        String detail = moreLevels ? "The next level starts when you’re ready."
                : cleared ? "You cleared every round."
                : "Give it another go when you’re ready.";

        View card = getLayoutInflater().inflate(R.layout.round_result, null);
        ResultArtwork artwork = card.findViewById(R.id.result_art);
        artwork.setCleared(cleared);
        TextView heading = card.findViewById(R.id.result_title);
        heading.setText(title);
        TextView result = card.findViewById(R.id.result_score);
        result.setText("Score  " + levelHits + " / " + POINTS_PER_LEVEL);
        TextView description = card.findViewById(R.id.result_detail);
        description.setText(detail);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(card)
                .setCancelable(false)
                .create();
        Button next = card.findViewById(R.id.result_next);
        next.setText(moreLevels ? "Next level" : "Play again");
        next.setOnClickListener(view -> {
            dialog.dismiss();
            if (moreLevels) {
                level++;
                levelHits = 0;
                finished = false;
                running = true;
                remainingMs = ROUND_MS;
                startRound();
            } else {
                startGame();
            }
        });
        card.findViewById(R.id.result_exit).setOnClickListener(view -> {
            dialog.dismiss();
            returnHome();
        });
        dialog.show();
        android.view.Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            int availableWidth = getResources().getDisplayMetrics().widthPixels - dp(32);
            window.setLayout(Math.min(availableWidth, dp(420)), -2);
        }
    }

    private void returnHome() {
        running = false;
        finished = false;
        home = true;
        stopRound();
        render();
    }

    @Override
    public void onBackPressed() {
        if (home) {
            super.onBackPressed();
        } else {
            returnHome();
        }
    }

    private void stopRound() {
        handler.removeCallbacks(moveMole);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        activeHole = -1;
    }

    private long millisLeft() {
        return Math.max(0, deadline - SystemClock.elapsedRealtime());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (running) startRound();
    }

    @Override
    protected void onStop() {
        if (running) remainingMs = millisLeft();
        stopRound();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putBoolean("home", home);
        out.putBoolean("homeRevealed", homeRevealed || homeReveal != null);
        out.putInt("levelHits", levelHits);
        out.putInt("level", level);
        out.putBoolean("running", running);
        out.putBoolean("finished", finished);
        out.putLong("remaining", running && timer != null ? millisLeft() : remainingMs);
    }

    private void render() {
        renderScore();
        renderTime(remainingMs);
        renderBoard();
        homeView.setVisibility(home ? View.VISIBLE : View.GONE);
        gameView.setVisibility(home ? View.GONE : View.VISIBLE);
        message.setText(running ? "Tap the mole before it moves." : "Round complete.");
    }

    private void renderScore() {
        scoreView.setText("Score  " + levelHits + " / " + POINTS_PER_LEVEL);
        levelView.setText("Level  " + level + " / " + LEVELS);
    }

    private void renderTime(long ms) {
        timeView.setText("Time  " + (ms + 999) / 1000 + "s");
    }

    private void renderBoard() {
        for (int i = 0; i < holes.length; i++) {
            boolean active = i == activeHole;
            BurrowView hole = holes[i];
            hole.setEmerged(active);
            hole.setEnabled(active);
            hole.setContentDescription(active ? "Mole at hole " + (i + 1) : "Empty hole " + (i + 1));
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
