package bai.wip.radio

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.radio.view.*

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

    private lateinit var mCenterBackground: Drawable
    private lateinit var mUpperBackground: Drawable
    private lateinit var mMiddleBackground: Drawable
    private lateinit var mUnderBackground: Drawable
    private lateinit var mCenterIcon: Drawable
    private lateinit var mUpperIcon: Drawable
    private lateinit var mMiddleIcon: Drawable
    private lateinit var mUnderIcon: Drawable
    private var mCenterSize = 0
    private var mUpperSize = 0
    private var mMiddleSize = 0
    private var mUnderSize = 0


    private fun initAttr(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.Radio, defStyle, 0)
        mCenterBackground = a.getDrawable(R.styleable.Radio_centerBackground)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.shape_oval_blue_56dp, null)!!

        mUpperBackground = a.getDrawable(R.styleable.Radio_upperBackground)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.shape_oval_purple_48dp, null)!!

        mMiddleBackground = a.getDrawable(R.styleable.Radio_middleBackground)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.shape_oval_orange_48dp, null)!!

        mUnderBackground = a.getDrawable(R.styleable.Radio_underBackground)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.shape_oval_green_48dp, null)!!

        mCenterIcon = a.getDrawable(R.styleable.Radio_centerIcon)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.ic_round_center_24px, null)!!

        mUpperIcon = a.getDrawable(R.styleable.Radio_upperIcon)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.ic_round_upper_24px, null)!!

        mMiddleIcon = a.getDrawable(R.styleable.Radio_middleIcon)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.ic_round_middle_24px, null)!!

        mUnderIcon = a.getDrawable(R.styleable.Radio_underIcon)
                ?: ResourcesCompat.getDrawable(context.resources, R.drawable.ic_round_under_24px, null)!!

        mCenterSize = a.getDimensionPixelSize(R.styleable.Radio_centerSize, 56)
        mUpperSize = a.getDimensionPixelSize(R.styleable.Radio_centerSize, 48)
        mMiddleSize = a.getDimensionPixelSize(R.styleable.Radio_centerSize, 48)
        mUnderSize = a.getDimensionPixelSize(R.styleable.Radio_centerSize, 48)

        a.recycle()
    }

    private fun initUI() {
        centerIV.background = mCenterBackground
        upperIV.background = mUpperBackground
        middleIV.background = mMiddleBackground
        underIV.background = mUnderBackground
        centerIV.setImageDrawable(mCenterIcon)
        upperIV.setImageDrawable(mUpperIcon)
        middleIV.setImageDrawable(mMiddleIcon)
        underIV.setImageDrawable(mUnderIcon)
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