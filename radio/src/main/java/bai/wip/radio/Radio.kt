package bai.wip.radio

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.radio.view.*
import java.util.zip.Inflater

class Radio @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var mCurrentClickListener = -1
    private var mUpperClickListener: (() -> Unit)? = null
    private var mMiddleClickListener: (() -> Unit)? = null
    private var mUnderClickListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.radio, this)
        upperIV.setOnClickListener {
            mCurrentClickListener = 0
            radio.transitionToStart()
        }
        middleIV.setOnClickListener {
            mCurrentClickListener = 1
            radio.transitionToStart()
        }
        underIV.setOnClickListener {
            mCurrentClickListener = 2
            radio.transitionToStart()
        }

        radio.setTransitionListener(object : SimpleTransitionListener() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (mCurrentClickListener) {
                    0 -> {
                        mUpperClickListener?.invoke()
                    }

                    1 -> {
                        mMiddleClickListener?.invoke()
                    }

                    2 -> {
                        mUnderClickListener?.invoke()
                    }
                }
            }
        })
    }

    fun setListener(
        upperClickListener: (() -> Unit)? = null,
        middleClickListener: (() -> Unit)? = null,
        underClickListener: (() -> Unit)? = null
    ) {
        mUpperClickListener = upperClickListener
        mMiddleClickListener = middleClickListener
        mUnderClickListener = underClickListener
    }


}