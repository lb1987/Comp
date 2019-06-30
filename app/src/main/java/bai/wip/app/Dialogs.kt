package bai.wip.app

import android.app.Activity
import bai.wip.dialog.dialog
import bai.wip.picker.OptionPicker
import bai.wip.wheel.interfaces.Tag
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.date_bottom_dialog.view.*
import kotlinx.android.synthetic.main.date_bottom_dialog.view.cancelBTN
import kotlinx.android.synthetic.main.date_bottom_dialog.view.sureBTN
import kotlinx.android.synthetic.main.date_bottom_dialog.view.titleTV
import kotlinx.android.synthetic.main.option_bottom_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*

fun Activity.alert(
        msg: String = "警告",
        layout: Int = R.layout.alert
) {
    dialog(layout) { _, view ->
        with(view) {
            msgTV.text = msg
        }
    }
}

fun Activity.dateBottomDialog(
        title: String,
        current: Calendar = Calendar.getInstance(),
        start: Calendar? = null,
        end: Calendar? = null,
        format: String = "yyyy-MM-dd",
        callback: (date: String) -> Unit
) = dialog(
        layout = R.layout.date_bottom_dialog,
        isBottom = true
) { dialog, view ->
    val sdf = SimpleDateFormat(format, Locale.CHINA)
    var result = sdf.format(current.time)
    with(view) {

        titleTV.text = title
        datePK.setDate(current)
        if (start != null) {
            datePK.setStartCalender(start)
        }
        if (end != null) {
            datePK.setEndCalendar(end)
        }

        datePK.setTimeChangeListener { time ->
            result = sdf.format(Date(time))
        }
        sureBTN.setOnClickListener {
            callback.invoke(result)
            dialog.dismiss()
        }
        cancelBTN.setOnClickListener {
            dialog.dismiss()
        }
    }
}


fun <X : Tag, Y : Tag, Z : Tag> Activity.cascadeOptionDialog(
        title: String,
        x: X? = null,
        y: Y? = null,
        z: Z? = null,
        xList: List<X>,
        yList: List<List<Y>>? = null,
        zList: List<List<List<Z>>>? = null,
        callback: (x: X, y: Y?, z: Z?) -> Unit
) = dialog(
        layout = R.layout.option_bottom_dialog,
        isBottom = true
) { dialog, view ->
    var xResult: X = x ?: xList[0]
    var yResult: Y? = y ?: yList?.get(0)?.get(0)
    var zResult: Z? = z ?: zList?.get(0)?.get(0)?.get(0)
    with(view) {
        titleTV.text = title
        val adapter =
                OptionPicker.CascadeAdapter(xList, yList, zList) { x: X, y: Y?, z: Z? ->
                    xResult = x
                    yResult = y
                    zResult = z
                }
        optionPK.setCascadeAdapter(adapter)
        sureBTN.setOnClickListener {
            callback(xResult, yResult, zResult)
            dialog.dismiss()
        }

        cancelBTN.setOnClickListener {
            dialog.dismiss()
        }
    }
}

fun <X : Tag, Y : Tag, Z : Tag> Activity.parallelOptionDialog(
        title: String,
        x: X? = null,
        y: Y? = null,
        z: Z? = null,
        xOptions: List<X>,
        yOptions: List<Y>? = null,
        zOptions: List<Z>? = null,
        callback: (x: X, y: Y?, z: Z?) -> Unit
) = dialog(
        layout = R.layout.option_bottom_dialog,
        isBottom = true
) { dialog, view ->
    var xResult = x ?: xOptions[0]
    var yResult = y ?: yOptions?.get(0)
    var zResult = z ?: zOptions?.get(0)
    with(view) {
        titleTV.text = title
        val adapter = OptionPicker.ParallelAdapter(xOptions, yOptions, zOptions) { x, y, z ->
            xResult = x
            yResult = y
            zResult = z
        }

        optionPK.setParallelAdapter(adapter)
        sureBTN.setOnClickListener {
            callback(xResult, yResult, zResult)
            dialog.dismiss()
        }

        cancelBTN.setOnClickListener {
            dialog.dismiss()
        }

        if (x != null) {
            optionPK.setOption<X, Y, Z>(x)
        }
    }

}

