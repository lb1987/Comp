package bai.wip.wheel.timer;

import android.os.Handler;
import android.os.Message;
import bai.wip.wheel.view.Wheel;


public final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_ITEM_SELECTED = 3000;

    private final Wheel wheel;

    public MessageHandler(Wheel wheel) {
        this.wheel = wheel;
    }

    @Override
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                wheel.invalidate();
                break;

            case WHAT_SMOOTH_SCROLL:
                wheel.smoothScroll(Wheel.ACTION.FLING);
                break;

            case WHAT_ITEM_SELECTED:
                wheel.onItemSelected();
                break;
        }
    }

}
