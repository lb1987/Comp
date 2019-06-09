package bai.wip.wheel.timer;


import bai.wip.wheel.view.Wheel;

import java.util.TimerTask;


public final class InertiaTimerTask extends TimerTask {

    private float mCurrentVelocityY; //当前滑动速度
    private final float mFirstVelocityY;//手指离开屏幕时的初始速度
    private final Wheel mWheel;

    /**
     * @param wheel 滚轮对象
     * @param velocityY Y轴滑行速度
     */
    public InertiaTimerTask(Wheel wheel, float velocityY) {
        super();
        this.mWheel = wheel;
        this.mFirstVelocityY = velocityY;
        mCurrentVelocityY = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {

        //防止闪动，对速度做一个限制。
        if (mCurrentVelocityY == Integer.MAX_VALUE) {
            if (Math.abs(mFirstVelocityY) > 2000F) {
                mCurrentVelocityY = mFirstVelocityY > 0 ? 2000F : -2000F;
            } else {
                mCurrentVelocityY = mFirstVelocityY;
            }
        }

        //发送handler消息 处理平顺停止滚动逻辑
        if (Math.abs(mCurrentVelocityY) >= 0.0F && Math.abs(mCurrentVelocityY) <= 20F) {
            mWheel.cancelFuture();
            mWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
            return;
        }

        int dy = (int) (mCurrentVelocityY / 100F);
        mWheel.setTotalScrollY(mWheel.getTotalScrollY() - dy);
        if (!mWheel.isLoop()) {
            float itemHeight = mWheel.getItemHeight();
            float top = (-mWheel.getInitPosition()) * itemHeight;
            float bottom = (mWheel.getItemsCount() - 1 - mWheel.getInitPosition()) * itemHeight;
            if (mWheel.getTotalScrollY() - itemHeight * 0.25 < top) {
                top = mWheel.getTotalScrollY() + dy;
            } else if (mWheel.getTotalScrollY() + itemHeight * 0.25 > bottom) {
                bottom = mWheel.getTotalScrollY() + dy;
            }

            if (mWheel.getTotalScrollY() <= top) {
                mCurrentVelocityY = 40F;
                mWheel.setTotalScrollY((int) top);
            } else if (mWheel.getTotalScrollY() >= bottom) {
                mWheel.setTotalScrollY((int) bottom);
                mCurrentVelocityY = -40F;
            }
        }

        if (mCurrentVelocityY < 0.0F) {
            mCurrentVelocityY = mCurrentVelocityY + 20F;
        } else {
            mCurrentVelocityY = mCurrentVelocityY - 20F;
        }

        //刷新UI
        mWheel.getHandler().sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
    }
}
