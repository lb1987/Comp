package bai.wip.radio

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
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
        initAttr(attrs, defStyleAttr)
        initUI()
        setListener()
    }

    /*
       <attr name="centerBackgroundColor" format="color|reference" />
        <attr name="upperBackgroundColor" format="color|reference" />
        <attr name="middleBackgroundColor" format="color|reference" />
        <attr name="underBackgroundColor" format="color|reference" />
        <attr name="centerSrc" format="reference" />
        <attr name="upperSrc" format="reference" />
        <attr name="middleSrc" format="reference" />
        <attr name="underSrc" format="reference" />
        <attr name="centerSize" format="dimension|reference" />
        <attr name="upperSize" format="dimension|reference" />
        <attr name="middleSize" format="dimension|reference" />
        <attr name="underSize" format="dimension|reference" />
        <attr name≤="radioSize" format="dimension|reference" />
     */
    private var mCenterBackgroundColor =
            ResourcesCompat.getDrawable(context.resources, R.drawable.shape_oval_blue_56dp, null)

    private fun initAttr(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.Radio, defStyle, 0)
        mCenterBackgroundColor = a.getDrawable(R.styleable.Radio_centerBackgroundColor)
        a.recycle()
    }

    private fun initUI() {
        if (mCenterBackgroundColor != null) {
            centerIV.background = mCenterBackgroundColor
        }
    }

    private fun setListener() {
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

    fun setClickListener(
            upperClickListener: (() -> Unit)? = null,
            middleClickListener: (() -> Unit)? = null,
            underClickListener: (() -> Unit)? = null
    ) {
        mUpperClickListener = upperClickListener
        mMiddleClickListener = middleClickListener
        mUnderClickListener = underClickListener
    }


}