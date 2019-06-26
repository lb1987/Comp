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
    init {
        View.inflate(context, R.layout.radio, this)
        upperIV.setOnClickListener {
            // 点击启动动画
        }
        middleIV.setOnClickListener { }
        underIV.setOnClickListener { }

        radio.setTransitionListener(object : SimpleTransitionListener() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

            }

        })
    }

}