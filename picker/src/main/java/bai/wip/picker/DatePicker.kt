package bai.wip.picker

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import bai.wip.picker.adapter.NumericWheelAdapter
import bai.wip.wheel.listener.OnItemChangedListener
import bai.wip.wheel.view.Wheel
import kotlinx.android.synthetic.main.date_picker.view.*
import java.text.SimpleDateFormat
import java.util.*


class DatePicker : LinearLayout {

    private var mStartYear = 1900
    private var mEndYear = 2100
    private var mStartMonth = 1
    private var mEndMonth = 12
    private var mStartDay = 1
    private var mEndDay = 31
    private var mCurrentYear: Int = 2019

    private var isLoop = false
    private var mType = PICKER_TYPE_DATE
    private var mTextSize: Int = 17
    private var mTextColorMinor: Int = Color.GRAY
    private var mTextColorMajor: Int = Color.BLACK
    private var mLineSpaceFactor = 1.6f
    private var mDividerHeight = 1.0f
    private var mDividerColor = Color.GRAY
    private var mDividerType = DIVIDER_TYPE_FILL
    private var mListener: ((time: Long) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        View.inflate(context, R.layout.date_picker, this)
        initAttr(attrs!!, defStyle)
        initView()
        initWheelView()
    }

    private fun initAttr(attrs: AttributeSet, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DatePicker, defStyle, 0)
        mType = a.getInt(R.styleable.DatePicker_type, PICKER_TYPE_DATE)
        isLoop = a.getBoolean(R.styleable.DatePicker_isLoop, false)
        mLineSpaceFactor = a.getFloat(R.styleable.DatePicker_lineSpaceFactor, 1.6f)

        mTextColorMajor = a.getColor(R.styleable.DatePicker_textColorCenter, Color.BLACK)
        mTextColorMinor = a.getColor(R.styleable.DatePicker_textColorSide, Color.GRAY)
        mTextSize = a.getDimensionPixelSize(R.styleable.DatePicker_textSize, 17)

        mDividerColor = a.getColor(R.styleable.DatePicker_dividerColor, Color.GRAY)
        mDividerHeight = a.getFloat(R.styleable.DatePicker_dividerHeight, 1.0f)
        mDividerType = a.getInt(R.styleable.DatePicker_dividerType, DIVIDER_TYPE_FILL)

        a.recycle()
    }

    private fun initView() {
        setTextSize()
        setMajorTextColor()
        setMinorTextColor()

        setType()
        setLoop()
        setLineSpaceFactor()

        setDividerColor()
        setDividerType()
        setDividerHeight()
    }

    private fun setDividerHeight() {
        yearWheel.dividerHeight = mDividerHeight
        monthWheel.dividerHeight = mDividerHeight
        dayWheel.dividerHeight = mDividerHeight
        hourWheel.dividerHeight = mDividerHeight
        minuteWheel.dividerHeight = mDividerHeight
        secondWheel.dividerHeight = mDividerHeight
    }

    private fun setType() {
        when (mType) {
            PICKER_TYPE_DATE -> {
                yearWheel.isVisible = true
                monthWheel.isVisible = true
                dayWheel.isVisible = true
                hourWheel.isVisible = false
                minuteWheel.isVisible = false
                secondWheel.isVisible = false
            }
            PICKER_TYPE_TIME -> {
                yearWheel.isVisible = false
                monthWheel.isVisible = false
                dayWheel.isVisible = false
                hourWheel.isVisible = true
                minuteWheel.isVisible = true
                secondWheel.isVisible = true
            }

            PICKER_TYPE_ALL -> {
                yearWheel.isVisible = true
                monthWheel.isVisible = true
                dayWheel.isVisible = true
                hourWheel.isVisible = true
                minuteWheel.isVisible = true
                secondWheel.isVisible = true
            }
        }
    }

    private fun setDividerType() {
        val type = when (mDividerType) {
            DIVIDER_TYPE_FILL -> {
                Wheel.DividerType.FILL
            }
            else -> {
                Wheel.DividerType.WRAP
            }
        }

        yearWheel.setDividerType(type)
        monthWheel.setDividerType(type)
        dayWheel.setDividerType(type)
        hourWheel.setDividerType(type)
        minuteWheel.setDividerType(type)
        secondWheel.setDividerType(type)
    }

    private fun setDividerColor() {
        yearWheel.setDividerColor(mDividerColor)
        monthWheel.setDividerColor(mDividerColor)
        dayWheel.setDividerColor(mDividerColor)
        hourWheel.setDividerColor(mDividerColor)
        minuteWheel.setDividerColor(mDividerColor)
        secondWheel.setDividerColor(mDividerColor)
    }

    private fun setLineSpaceFactor() {
        yearWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        monthWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        dayWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        hourWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        minuteWheel.setLineSpacingMultiplier(mLineSpaceFactor)
        secondWheel.setLineSpacingMultiplier(mLineSpaceFactor)
    }

    private fun setLoop() {
        yearWheel.setCyclic(isLoop)
        monthWheel.setCyclic(isLoop)
        dayWheel.setCyclic(isLoop)
        hourWheel.setCyclic(isLoop)
        minuteWheel.setCyclic(isLoop)
        secondWheel.setCyclic(isLoop)
    }

    private fun setMajorTextColor() {
        yearWheel.setTextColorCenter(mTextColorMajor)
        monthWheel.setTextColorCenter(mTextColorMajor)
        dayWheel.setTextColorCenter(mTextColorMajor)
        hourWheel.setTextColorCenter(mTextColorMajor)
        minuteWheel.setTextColorCenter(mTextColorMajor)
        secondWheel.setTextColorCenter(mTextColorMajor)
    }

    private fun setMinorTextColor() {
        yearWheel.setTextColorOut(mTextColorMinor)
        monthWheel.setTextColorOut(mTextColorMinor)
        dayWheel.setTextColorOut(mTextColorMinor)
        hourWheel.setTextColorOut(mTextColorMinor)
        minuteWheel.setTextColorOut(mTextColorMinor)
        secondWheel.setTextColorOut(mTextColorMinor)
    }

    private fun setTextSize() {
        yearWheel.setTextSize(mTextSize)
        monthWheel.setTextSize(mTextSize)
        dayWheel.setTextSize(mTextSize)
        hourWheel.setTextSize(mTextSize)
        minuteWheel.setTextSize(mTextSize)
        secondWheel.setTextSize(mTextSize)
    }

    private fun initWheelView() {
        setDate(Calendar.getInstance())
    }

    fun setDate(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        mCurrentYear = year

        // 年
        yearWheel.adapter = NumericWheelAdapter(mStartYear, mEndYear)
        yearWheel.currentItem = yearWheel.adapter.indexOf(mCurrentYear)

        // 月
        monthWheel.adapter = when {
            mStartYear == mEndYear -> {
                NumericWheelAdapter(mStartMonth, mEndMonth)
            }
            mCurrentYear == mStartYear -> {
                NumericWheelAdapter(mStartMonth, 12)
            }
            mCurrentYear == mEndYear -> {
                NumericWheelAdapter(1, mEndMonth)
            }
            else -> {
                NumericWheelAdapter(1, 12)
            }
        }
        monthWheel.currentItem = monthWheel.adapter.indexOf(month)

        // 日
        when {
            mStartYear == mEndYear && mStartMonth == mEndMonth -> {
                when (month) {
                    in month31 -> {
                        if (mEndDay > 31) {
                            mEndDay = 31
                        }
                        dayWheel.adapter = NumericWheelAdapter(mStartDay, mEndDay)
                    }

                    in month30 -> {
                        if (mEndDay > 30) {
                            mEndDay = 30
                        }
                        dayWheel.adapter = NumericWheelAdapter(mStartDay, mEndDay)
                    }

                    else -> {
                        // 闰年
                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                            if (mEndDay > 29) {
                                mEndDay = 29
                            }
                            dayWheel.adapter = NumericWheelAdapter(mStartDay, mEndDay)
                        } else {
                            if (mEndDay > 28) {
                                mEndDay = 28
                            }
                            dayWheel.adapter = NumericWheelAdapter(mStartDay, mEndDay)
                        }
                    }
                }
                dayWheel.currentItem = dayWheel.adapter.indexOf(day)
            }

            year == mStartYear && month == mStartMonth -> {
                when (month) {
                    in month31 -> dayWheel.adapter = NumericWheelAdapter(mStartDay, 31)
                    in month30 -> dayWheel.adapter = NumericWheelAdapter(mStartDay, 30)
                    else -> {
                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                            dayWheel.adapter = NumericWheelAdapter(mStartDay, 29)
                        } else {
                            dayWheel.adapter = NumericWheelAdapter(mStartDay, 28)
                        }
                    }
                }
                dayWheel.currentItem = dayWheel.adapter.indexOf(day)

            }
            // 终止日期的天数控制
            year == mEndYear && month == mEndMonth -> {
                when (month) {
                    in month31 -> {
                        if (mEndDay > 31) {
                            mEndDay = 31
                        }
                        dayWheel.adapter = NumericWheelAdapter(1, mEndDay)
                    }

                    in month30 -> {
                        if (mEndDay > 30) {
                            mEndDay = 30
                        }
                        dayWheel.adapter = NumericWheelAdapter(1, mEndDay)
                    }

                    else -> {
                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                            if (mEndDay > 29) {
                                mEndDay = 29
                            }
                            dayWheel.adapter = NumericWheelAdapter(1, mEndDay)
                        } else {
                            if (mEndDay > 28) {
                                mEndDay = 28
                            }
                            dayWheel.adapter = NumericWheelAdapter(1, mEndDay)
                        }
                    }

                }
                dayWheel.currentItem = dayWheel.adapter.indexOf(day)
            }

            else -> {
                when (month) {
                    in month31 -> {
                        dayWheel.adapter = NumericWheelAdapter(1, 31)
                    }

                    in month30 -> {
                        dayWheel.adapter = NumericWheelAdapter(1, 30)
                    }

                    else -> {
                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                            dayWheel.adapter = NumericWheelAdapter(1, 29)
                        } else {
                            dayWheel.adapter = NumericWheelAdapter(1, 28)
                        }
                    }
                }
                dayWheel.currentItem = dayWheel.adapter.indexOf(day)
            }
        }

        //时
        hourWheel.adapter = NumericWheelAdapter(0, 23)
        hourWheel.currentItem = hour

        //分
        minuteWheel.adapter = NumericWheelAdapter(0, 59)
        minuteWheel.currentItem = minute

        //秒
        secondWheel.adapter = NumericWheelAdapter(0, 59)
        secondWheel.currentItem = second

        // 添加"年"监听
        yearWheel.setOnItemChangedListener(object : OnItemChangedListener {
            override fun onChanged(index: Int) {
                @Suppress("NAME_SHADOWING")
                val year = index + mStartYear
                mCurrentYear = year

                val currentMonthItemIndex = monthWheel.currentItem
                val adapter = monthWheel.adapter as NumericWheelAdapter
                val currentMonth = adapter.getItem(currentMonthItemIndex)

                when {
                    mStartYear == mEndYear -> {
                        monthWheel.adapter = NumericWheelAdapter(mStartMonth, mEndMonth)

                        when {
                            currentMonth == mStartMonth && currentMonth == mEndMonth -> {
                                monthWheel.currentItem = monthWheel.adapter.indexOf(currentMonth)
                                resetDay(year, currentMonth, mStartDay, mEndDay)
                            }

                            currentMonth <= mStartMonth -> {
                                monthWheel.currentItem = monthWheel.adapter.indexOf(mStartMonth)
                                resetDay(year, mStartMonth, mStartDay, 31)
                            }

                            currentMonth >= mEndMonth -> {
                                monthWheel.currentItem = monthWheel.adapter.indexOf(mEndMonth)
                                resetDay(year, mEndMonth, 1, mEndDay)
                            }

                            else -> {
                                monthWheel.currentItem = monthWheel.adapter.indexOf(currentMonth)
                                resetDay(year, currentMonth, 1, 31)
                            }
                        }
                    }

                    year == mStartYear -> {
                        monthWheel.adapter = NumericWheelAdapter(mStartMonth, 12)
                        if (currentMonth > mStartMonth) {
                            monthWheel.currentItem = monthWheel.adapter.indexOf(currentMonth)
                            resetDay(year, currentMonth, 1, 31)
                        } else {
                            monthWheel.currentItem = monthWheel.adapter.indexOf(mStartMonth)
                            resetDay(year, mStartMonth, mStartDay, 31)
                        }
                    }

                    year == mEndYear -> {
                        monthWheel.adapter = NumericWheelAdapter(1, mEndMonth)
                        if (currentMonth >= mEndMonth) {
                            monthWheel.currentItem = monthWheel.adapter.indexOf(mEndMonth)
                            resetDay(year, mEndMonth, 1, mEndDay)
                        } else {
                            monthWheel.currentItem = monthWheel.adapter.indexOf(currentMonth)
                            resetDay(year, currentMonth, 1, 31)
                        }
                    }

                    else -> {
                        monthWheel.adapter = NumericWheelAdapter(1, 12)
                        monthWheel.currentItem = monthWheel.adapter.indexOf(currentMonth)
                        resetDay(year, currentMonth, 1, 31)
                    }
                }
                mListener?.invoke(getTime())
            }
        })

        // 添加"月"监听
        monthWheel.setOnItemChangedListener(object : OnItemChangedListener {
            override fun onChanged(index: Int) {
                @Suppress("NAME_SHADOWING")
                val wheel = monthWheel.adapter as NumericWheelAdapter
                val month = wheel.getItem(index)

                when {
                    mStartYear == mEndYear -> when {
                        mStartMonth == mEndMonth ->
                            resetDay(mCurrentYear, month, mStartDay, mEndDay)
                        mStartMonth == month ->    //重新设置日
                            resetDay(mCurrentYear, month, mStartDay, 31)
                        mEndMonth == month -> resetDay(mCurrentYear, month, 1, mEndDay)
                        else -> resetDay(mCurrentYear, month, 1, 31)
                    }

                    mCurrentYear == mStartYear -> when (month) {
                        mStartMonth -> resetDay(mCurrentYear, month, mStartDay, 31)
                        else -> resetDay(mCurrentYear, month, 1, 31)
                    }


                    mCurrentYear == mEndYear -> when (month) {
                        mEndMonth -> resetDay(mCurrentYear, monthWheel.currentItem + 1, 1, mEndDay)
                        else -> resetDay(mCurrentYear, monthWheel.currentItem + 1, 1, 31)
                    }

                    else -> resetDay(mCurrentYear, month, 1, 31)
                }

                mListener?.invoke(getTime())

            }
        })

        setChangedListener(dayWheel)
        setChangedListener(hourWheel)
        setChangedListener(minuteWheel)
        setChangedListener(secondWheel)

    }

    private fun setChangedListener(wheel: Wheel) {
        wheel.setOnItemChangedListener(object : OnItemChangedListener {
            override fun onChanged(index: Int) {
                mListener?.invoke(getTime())
            }
        })
    }

    private fun resetDay(
        year: Int,
        month: Int,
        startDay: Int,
        endDay: Int
    ) {
        var end = endDay
        val currentItem = dayWheel.currentItem
        val adapter = dayWheel.adapter as NumericWheelAdapter
        val day = adapter.getItem(currentItem)

        when (month) {
            in month31 -> {
                if (end > 31) {
                    end = 31
                }
                dayWheel.adapter = NumericWheelAdapter(startDay, end)
                when {
                    day <= startDay -> {
                        dayWheel.currentItem = dayWheel.adapter.indexOf(startDay)
                    }
                    day >= end -> {
                        dayWheel.currentItem = dayWheel.adapter.indexOf(end)
                    }

                    else -> {
                        dayWheel.currentItem = dayWheel.adapter.indexOf(day)
                    }
                }
            }

            in month30 -> {
                if (end > 30) {
                    end = 30
                }
                dayWheel.adapter = NumericWheelAdapter(startDay, end)
                when {
                    day <= startDay -> {
                        dayWheel.currentItem = dayWheel.adapter.indexOf(startDay)
                    }

                    day >= end -> {
                        dayWheel.currentItem = dayWheel.adapter.indexOf(end)
                    }

                    else -> {
                        dayWheel.currentItem = dayWheel.adapter.indexOf(day)
                    }
                }
            }

            else -> {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    if (end > 29) {
                        end = 29
                    }
                    dayWheel.adapter = NumericWheelAdapter(startDay, end)
                    when {
                        day <= startDay -> {
                            dayWheel.currentItem = dayWheel.adapter.indexOf(startDay)
                        }

                        day >= end -> {
                            dayWheel.currentItem = dayWheel.adapter.indexOf(end)
                        }

                        else -> {
                            dayWheel.currentItem = dayWheel.adapter.indexOf(day)
                        }
                    }

                } else {
                    if (end > 28) {
                        end = 28
                    }
                    dayWheel.adapter = NumericWheelAdapter(startDay, end)
                    when {
                        day <= startDay -> {
                            dayWheel.currentItem = dayWheel.adapter.indexOf(startDay)
                        }

                        day >= end -> {
                            dayWheel.currentItem = dayWheel.adapter.indexOf(end)
                        }

                        else -> {
                            dayWheel.currentItem = dayWheel.adapter.indexOf(day)
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取时间
     */
    fun getTime(): Long {
        val time = if (mCurrentYear == mStartYear) {
            if ((monthWheel.currentItem + mStartMonth) == mStartMonth) {
                val year = yearWheel.currentItem + mStartYear
                val month = monthWheel.currentItem + mStartMonth
                val day = dayWheel.currentItem + mStartDay
                val hour = hourWheel.currentItem
                val minute = minuteWheel.currentItem
                val second = secondWheel.currentItem
                "$year-$month-$day $hour:$minute:$second"
            } else {
                val year = yearWheel.currentItem + mStartYear
                val month = monthWheel.currentItem + mStartMonth
                val day = dayWheel.currentItem + 1
                val hour = hourWheel.currentItem
                val minute = minuteWheel.currentItem
                val second = secondWheel.currentItem
                "$year-$month-$day $hour:$minute:$second"
            }

        } else {
            val year = yearWheel.currentItem + mStartYear
            val month = monthWheel.currentItem + 1
            val day = dayWheel.currentItem + 1
            val hour = dayWheel.currentItem
            val minute = minuteWheel.currentItem
            val second = secondWheel.currentItem
            "$year-$month-$day $hour:$minute:$second"
        }

        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time).time

    }


    fun setTimeChangeListener(listener: (time: Long) -> Unit) {
        mListener = listener
    }

    fun setStartCalender(calender: Calendar): DatePicker {
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH) + 1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        when {
            year < mEndYear -> {
                mStartYear = year
                mStartMonth = month
                mStartDay = day
            }
            year == mEndYear -> when {
                month < mEndMonth -> {
                    mStartYear = year
                    mStartMonth = month
                    mStartDay = day
                }
                month == mEndMonth -> when {
                    day < mEndDay -> {
                        mStartYear = year
                        mStartMonth = month
                        mStartDay = day
                    }
                }
            }
        }
        return this
    }


    fun setEndCalendar(calender: Calendar): DatePicker {
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH) + 1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        when {
            year > mStartYear -> {
                mEndYear = year
                mEndMonth = month
                mEndDay = day
            }
            year == mStartYear -> when {
                month > mStartMonth -> {
                    mEndYear = year
                    mEndMonth = month
                    mEndDay = day
                }
                month == mStartMonth -> when {
                    day > mStartDay -> {
                        mEndYear = year
                        mEndMonth = month
                        mEndDay = day
                    }
                }
            }
        }
        return this
    }
}
