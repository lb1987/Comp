package bai.wip.radio

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import java.util.zip.Inflater

class Radio(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.radio, this)

    }
}