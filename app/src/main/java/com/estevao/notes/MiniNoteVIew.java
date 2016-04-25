package com.estevao.notes;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by estevao on 24/04/16.
 */
public class MiniNoteVIew extends View {
    private final FrameLayout mFrameLayout;
    private WindowManager mWindowManager;

    public MiniNoteVIew(Context context) {
        super(context);
        mFrameLayout = new FrameLayout(context);
        addToWindowManager();
    }

    private void addToWindowManager() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.gravity = Gravity.TOP;

        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflate = layoutInflater.inflate(R.layout.mini_note, mFrameLayout);
        mWindowManager.addView(inflate, params);

        inflate.setOnTouchListener(
                new OnTouchListener() {
                    private int initialX;
                    private int initialY;
                    private float initialTouchX;
                    private float initialTouchY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                params.alpha = 0.5f;
                                initialX = params.x;
                                initialY = params.y;
                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();
                                return true;
                            case MotionEvent.ACTION_UP:
                                params.alpha = 1f;
                                v.setAlpha(1f);
                                mWindowManager.updateViewLayout(inflate, params);
                                return true;
                            case MotionEvent.ACTION_MOVE:
                                params.x = initialX
                                        + (int) (event.getRawX() - initialTouchX);
                                params.y = initialY
                                        + (int) (event.getRawY() - initialTouchY);
                                mWindowManager.updateViewLayout(inflate, params);
                                return true;
                            case MotionEvent.ACTION_CANCEL:
                                params.alpha = 1f;
                                v.setAlpha(1f);
                                return true;
                        }
                        return false;
                    }
                }
        );
    }
}
