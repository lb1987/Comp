package bai.wip.picker

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import bai.wip.picker.adapter.ListWheelAdapter
import bai.wip.wheel.interfaces.Tag
import bai.wip.wheel.view.Wheel
import kotlinx.android.synthetic.main.option_picker.view.*

class OptionPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(
    context,
    attrs,
    defStyle
) {

    private var isLoop = false
    private var isCascade = true
    private var mTextSize: Int = 17
    private var mTextColorSide: Int = Color.GRAY
    private var mTextColorCenter: Int = Color.BLACK
    private var mLineSpaceFactor = 1.6f
    private var mDividerHeight = 1.0f
    private var mDividerColor = Color.GRAY
    private var mDividerType = DIVIDER_TYPE_FILL

    init {
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

    @Suppress("UNCHECKED_CAST")
    private fun <X : Tag, Y : Tag, Z : Tag> getOption(): Triple<X, Y?, Z?> {
        val x = xWheel.adapter.getItem(xWheel.currentItem) as X
        val y = yWheel.adapter?.getItem(yWheel.currentItem) as? Y?
        val z = zWheel.adapter?.getItem(zWheel.currentItem) as? Z?
        return Triple(x, y, z)
    }

    //<editor-fold desc = "Cascade adapter">
    fun <T : Tag, R : Tag, V : Tag> setCascadeAdapter(adapter: CascadeAdapter<T, R, V>) = with(adapter) {
        isCascade = true
        setCascadeScene(xList, yList, zList, listener)
    }

    private fun <X : Tag, Y : Tag, Z : Tag> setCascadeScene(
        xList: List<X>,
        yList: List<List<Y>>? = null,
        zList: List<List<List<Z>>>? = null,
        listener: ((x: X, y: Y?, z: Z?) -> Unit)? = null
    ) {
        // x 滚轮
        xWheel.adapter = ListWheelAdapter(xList)
        xWheel.currentItem = xWheel.currentItem
        xWheel.setOnItemChangedListener { index ->
            if (yList == null || index > yList.size - 1) {
                yWheel.isVisible = false
                zWheel.isVisible = false
            } else {
                yWheel.isVisible = true

                yWheel.adapter = ListWheelAdapter(yList[index])

                val yIndex = yWheel.currentItem
                yWheel.currentItem = if (yIndex >= yList[index].size - 1) {
                    yList[index].size - 1
                } else {
                    yIndex
                }

                if (zList == null) {
                    zWheel.isVisible = false
                } else {
                    zWheel.isVisible = true
                    val yCurrent = yWheel.currentItem
                    val zData = zList[index][yCurrent]
                    zWheel.adapter = ListWheelAdapter(zData)

                    val zIndex = zWheel.currentItem
                    zWheel.currentItem = if (zIndex >= zData.size - 1) {
                        zData.size - 1
                    } else {
                        zIndex
                    }
                }
            }

            val triple = getOption<X, Y, Z>()
            listener?.invoke(triple.first, triple.second, triple.third)
        }

        // y 滚轮
        if (yList == null) {
            yWheel.isVisible = false
        } else {
            yWheel.isVisible = true
            yWheel.adapter = ListWheelAdapter(yList[0])
            yWheel.currentItem = yWheel.currentItem
            yWheel.setOnItemChangedListener { index ->
                val xIndex = xWheel.currentItem
                if (zList == null || index > zList[xIndex].size - 1) {
                    zWheel.isVisible = false
                } else {
                    zWheel.isVisible = true
                    val zData = zList[xIndex][index]
                    zWheel.adapter = ListWheelAdapter(zData)

                    val zIndex = zWheel.currentItem
                    zWheel.currentItem = if (zIndex >= zData.size - 1) {
                        zData.size - 1
                    } else {
                        zIndex
                    }
                }

                val triple = getOption<X, Y, Z>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }

        // z 滚轮
        if (zList == null) {
            zWheel.isVisible = false
        } else {
            zWheel.isVisible = true
            zWheel.adapter = ListWheelAdapter(zList[0][0])
            zWheel.currentItem = zWheel.currentItem
            zWheel.setOnItemChangedListener {
                val triple = getOption<X, Y, Z>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc = "Parallel adapter">
    fun <X : Tag, Y : Tag, Z : Tag> setParallelAdapter(adapter: ParallelAdapter<X, Y, Z>) = with(adapter) {
        isCascade = false
        setParallelScene(xList, yList, zList, listener)
    }

    private fun <X : Tag, Y : Tag, Z : Tag> setParallelScene(
        xList: List<X>,
        yList: List<Y>?,
        zList: List<Z>?,
        listener: ((x: X, y: Y?, z: Z?) -> Unit)?
    ) {
        // x 滚轮
        xWheel.adapter = ListWheelAdapter(xList)
        xWheel.setOnItemChangedListener {
            val triple = getOption<X, Y, Z>()
            listener?.invoke(triple.first, triple.second, triple.third)
        }

        // y 滚轮
        if (yList == null) {
            yWheel.isVisible = false
        } else {
            yWheel.isVisible = true
            yWheel.adapter = ListWheelAdapter(yList)
            yWheel.setOnItemChangedListener {
                val triple = getOption<X, Y, Z>()
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
                val triple = getOption<X, Y, Z>()
                listener?.invoke(triple.first, triple.second, triple.third)
            }
        }

    }
    //</editor-fold>

    fun <X : Tag, Y : Tag, Z : Tag> setOption(x: X, y: Y? = null, z: Z? = null) {
        if (xWheel.adapter != null) {
            val xIndex = xWheel.adapter.indexOf(x)
            xWheel.currentItem = xIndex

            if (!isCascade) {
                if (y != null && yWheel.adapter != null) {
                    val yIndex = yWheel.adapter.indexOf(y)
                    yWheel.currentItem = yIndex
                }

                if (z != null && zWheel.adapter != null) {
                    val zIndex = zWheel.adapter.indexOf(z)
                    zWheel.currentItem = zIndex
                }
            }
        }
    }

    data class CascadeAdapter<X : Tag, Y : Tag, Z : Tag>(
        val xList: List<X>,
        val yList: List<List<Y>>? = null,
        val zList: List<List<List<Z>>>? = null,
        val listener: ((x: X, y: Y?, z: Z?) -> Unit)? = null
    )

    data class ParallelAdapter<X : Tag, Y : Tag, Z : Tag>(
        val xList: List<X>,
        val yList: List<Y>? = null,
        val zList: List<Z>? = null,
        val listener: ((x: X, y: Y?, z: Z?) -> Unit)? = null
    )
}