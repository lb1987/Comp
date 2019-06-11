package bai.wip.picker

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import bai.wip.picker.adapter.ListWheelAdapter
import bai.wip.wheel.view.Wheel
import kotlinx.android.synthetic.main.option_picker.view.*

class OptionPicker : LinearLayout {

    private var isLoop = false
    private var isLink = true
    private var mTextSize: Int = 17
    private var mTextColorSide: Int = Color.GRAY
    private var mTextColorCenter: Int = Color.BLACK
    private var mLineSpaceFactor = 1.6f
    private var mDividerHeight = 1.0f
    private var mDividerColor = Color.GRAY
    private var mDividerType = DIVIDER_TYPE_FILL

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        View.inflate(context, R.layout.option_picker, this)
        initAttr(attrs!!, defStyle)
        initWheelAttr()
    }

    private fun initAttr(attrs: AttributeSet, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.OptionPicker, defStyle, 0)
        mTextColorCenter = a.getColor(R.styleable.OptionPicker_textColorCenter, Color.BLACK)
        mTextColorSide = a.getColor(R.styleable.OptionPicker_textColorSide, Color.GRAY)
        mTextSize = a.getDimensionPixelSize(R.styleable.OptionPicker_textSize, 17)
        isLoop = a.getBoolean(R.styleable.OptionPicker_isLoop, false)
        mLineSpaceFactor = a.getFloat(R.styleable.OptionPicker_lineSpaceFactor, 1.6f)
        mDividerColor = a.getColor(R.styleable.OptionPicker_dividerColor, Color.GRAY)
        mDividerHeight = a.getFloat(R.styleable.OptionPicker_dividerHeight, 1.0f)
        mDividerType = a.getInt(R.styleable.OptionPicker_dividerType, DIVIDER_TYPE_FILL)

        isLink = a.getBoolean(R.styleable.OptionPicker_isLink, true)
        a.recycle()
    }

    private fun initWheelAttr() {
        setWheelTextColorCenter()
        setWheelTextColorSide()
        setWheelTextSize()
        setWheelLoop()
        setWheelLineSpaceFactor()
        setWheelDividerColor()
        setWheelDividerType()
        setDividerHeight()
    }

    private fun setDividerHeight() {
        xWheel.dividerHeight = mDividerHeight
        yWheel.dividerHeight = mDividerHeight
        zWheel.dividerHeight = mDividerHeight
    }

    private fun setWheelDividerType() {
        val type = when (mDividerType) {
            DIVIDER_TYPE_FILL -> {
                Wheel.DividerType.FILL
            }
            else -> {
                Wheel.DividerType.WRAP
            }
        }

        xWheel.setDividerType(type)
        yWheel.setDividerType(type)
        zWheel.setDividerType(type)
    }

    private fun setWheelDividerColor() {
        xWheel.setDividerColor(mDividerColor)
        yWheel.setDividerColor(mDividerColor)
        zWheel.setDividerColor(mDividerColor)
    }

    private fun setWheelLineSpaceFactor() {
        xWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        yWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        zWheel.setLineSpacingMultiplier(mLineSpaceFactor)
    }

    private fun setWheelLoop() {
        xWheel.setCyclic(isLoop)
        yWheel.setCyclic(isLoop)
        zWheel.setCyclic(isLoop)
    }

    private fun setWheelTextColorCenter() {
        xWheel.setTextColorCenter(mTextColorCenter)
        yWheel.setTextColorCenter(mTextColorCenter)
        zWheel.setTextColorCenter(mTextColorCenter)
    }

    private fun setWheelTextColorSide() {
        xWheel.setTextColorOut(mTextColorSide)
        yWheel.setTextColorOut(mTextColorSide)
        zWheel.setTextColorOut(mTextColorSide)
    }

    private fun setWheelTextSize() {
        xWheel.setTextSize(mTextSize)
        yWheel.setTextSize(mTextSize)
        zWheel.setTextSize(mTextSize)
    }

    //<editor-fold desc = "related adapter">
    fun <T> setRelatedAdapter(adapter: RelatedAdapter<T>) {
        val xList = adapter.xList
        val yList = adapter.yList
        val zList = adapter.zList
        initRelatedData(xList, yList, zList, adapter.listener)
    }

    private fun <T> initRelatedData(
        xList: List<T>,
        yList: List<List<T>>? = null,
        zList: List<List<List<T>>>? = null,
        listener: ((x: T, y: T?, z: T?) -> Unit)? = null
    ) {
        // x 滚轮
        xWheel.adapter = ListWheelAdapter(xList)
        xWheel.setOnItemChangedListener { index ->
            when {
                yList != null -> {
                    yWheel.isVisible = true
                    val yData = yList[index]
                    yWheel.adapter = ListWheelAdapter(yData)

                    when {

                        zList != null -> {
                            zWheel.isVisible = true
                            val yIndex = yWheel.currentItem
                            val zData = zList[index][yIndex]
                            zWheel.adapter = ListWheelAdapter(zData)
                        }

                        else -> {
                            zWheel.isVisible = false
                        }
                    }
                }

                else -> {
                    yWheel.isVisible = false
                }
            }

            val triple = getOption<T>()
            listener?.invoke(triple.first, triple.second, triple.third)
        }

        // y 滚轮
        if (yList == null) {
            yWheel.isVisible = false
        } else {
            yWheel.isVisible = true
            yWheel.adapter = ListWheelAdapter(yList[0])
            yWheel.setOnItemChangedListener { index ->
                when {
                    zList != null -> {
                        zWheel.isVisible = true
                        val xIndex = xWheel.currentItem
                        val zData = zList[xIndex][index]
                        zWheel.adapter = ListWheelAdapter(zData)
                    }

                    else -> {
                        zWheel.isVisible = false
                    }
                }

                val triple = getOption<T>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }

        // z 滚轮
        if (zList == null) {
            zWheel.isVisible = false
        } else {
            zWheel.isVisible = true
            zWheel.adapter = ListWheelAdapter(zList[0][0])
            zWheel.setOnItemChangedListener {
                val triple = getOption<T>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }
    }
//</editor-fold>

    //<editor-fold desc = "unrelated adapter">
    fun <T> setUnrelatedAdapter(adapter: UnRelatedAdapter<T>) {
        val xList = adapter.xList
        val yList = adapter.yList
        val zList = adapter.zList
        initUnRelatedData(xList, yList, zList, adapter.listener)
    }

    private fun <T> initUnRelatedData(
        xList: List<T>,
        yList: List<T>?,
        zList: List<T>?,
        listener: ((x: T, y: T?, z: T?) -> Unit)?
    ) {
        // x 滚轮
        xWheel.adapter = ListWheelAdapter(xList)
        xWheel.setOnItemChangedListener {
            val triple = getOption<T>()
            listener?.invoke(triple.first, triple.second, triple.third)
        }

        // y 滚轮
        if (yList == null) {
            yWheel.isVisible = false
        } else {
            yWheel.isVisible = true
            yWheel.adapter = ListWheelAdapter(yList)
            yWheel.setOnItemChangedListener {
                val triple = getOption<T>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }

        // z 滚轮
        if (zList == null) {
            zWheel.isVisible = false
        } else {
            zWheel.isVisible = true
            zWheel.adapter = ListWheelAdapter(zList)
            zWheel.setOnItemChangedListener {
                val triple = getOption<T>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }

    }
//</editor-fold>


    @Suppress("UNCHECKED_CAST")
    private fun <T> getOption(): Triple<T, T?, T?> {
        val x = xWheel.adapter.getItem(xWheel.currentItem) as T
        val y = yWheel.adapter?.getItem(yWheel.currentItem) as? T?
        val z = zWheel.adapter?.getItem(zWheel.currentItem) as? T?
        return Triple(x, y, z)
    }

    data class RelatedAdapter<T>(
        val xList: List<T>,
        val yList: List<List<T>>? = null,
        val zList: List<List<List<T>>>? = null,
        val listener: ((x: T, y: T?, z: T?) -> Unit)? = null
    )

    data class UnRelatedAdapter<T>(
        val xList: List<T>,
        val yList: List<T>? = null,
        val zList: List<T>? = null,
        val listener: ((x: T, y: T?, z: T?) -> Unit)? = null
    )

}