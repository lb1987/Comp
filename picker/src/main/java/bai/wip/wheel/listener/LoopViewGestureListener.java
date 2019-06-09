package bai.wip.wheel.listener;

import android.view.MotionEvent;
import bai.wip.wheel.view.Wheel;


public final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    private final Wheel wheel;


    public LoopViewGestureListener(Wheel wheel) {
        this.wheel = wheel;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheel.scrollBy(velocityY);
        return true;
    }
}
