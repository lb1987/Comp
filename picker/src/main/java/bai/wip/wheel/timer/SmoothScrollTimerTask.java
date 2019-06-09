package bai.wip.wheel.timer;


import bai.wip.wheel.view.Wheel;

import java.util.TimerTask;

public final class SmoothScrollTimerTask extends TimerTask {

    private int realTotalOffset;
    private int realOffset;
    private int offset;
    private final Wheel wheel;

    public SmoothScrollTimerTask(Wheel wheel, int offset) {
        this.wheel = wheel;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滚动的范围细分成10小份，按10小份单位来重绘
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        if (Math.abs(realTotalOffset) <= 1) {
            wheel.cancelFuture();
            wheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            wheel.setTotalScrollY(wheel.getTotalScrollY() + realOffset);

            //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的 情况
            if (!wheel.isLoop()) {
                float itemHeight = wheel.getItemHeight();
                float top = (float) (-wheel.getInitPosition()) * itemHeight;
                float bottom = (float) (wheel.getItemsCount() - 1 - wheel.getInitPosition()) * itemHeight;
                if (wheel.getTotalScrollY() <= top || wheel.getTotalScrollY() >= bottom) {
                    wheel.setTotalScrollY(wheel.getTotalScrollY() - realOffset);
                    wheel.cancelFuture();
                    wheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                    return;
                }
            }
            wheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
