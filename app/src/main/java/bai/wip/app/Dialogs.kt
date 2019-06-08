package bai.wip.app

import android.annotation.SuppressLint
import android.app.Activity
import bai.wip.dialog.dialog
import bai.wip.picker.OptionPicker
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
    dialog(
        layout
    ) { _, view ->
        with(view) {
            msgTV.text = msg
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Activity.dateBottomDialog(
    title: String,
    current: Calendar = Calendar.getInstance(),
    start: Calendar? = null,
    end: Calendar? = null,
    format: String = "yyyy-MM-dd",
    callback: (date: String) -> Unit
) {
    dialog(
        layout = R.layout.date_bottom_dialog,
        isBottom = true
    ) { dialog, view ->
        val sdf = SimpleDateFormat(format)
        var result = sdf.format(current.time)
        with(view) {

            titleTV.text = title
            datePK.setDate(Calendar.getInstance())
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
}

fun <T> Activity.optionBottomDailog(
    title: String,
    xOptions: List<T>,
    yOptions: List<List<T>>?,
    zOptions: List<List<List<T>>>?,
    callback: (x: T, y: T?, z: T?) -> Unit
) {
    dialog(
        layout = R.layout.option_bottom_dialog,
        isBottom = true
    ) { dialog, view ->
        var xResult: T = xOptions[0]
        var yResult: T? = yOptions?.get(0)?.get(0)
        var zResult: T? = zOptions?.get(0)?.get(0)?.get(0)
        with(view) {
            titleTV.text = title
            val adapter =
                OptionPicker.RelatedAdapter(xOptions, yOptions, zOptions) { x: T, y: T?, z: T? ->
                    xResult = x
                    yResult = y
                    zResult = z
                }
            optionPK.setRelatedAdapter(adapter)
            sureBTN.setOnClickListener {
                callback(xResult, yResult, zResult)
                dialog.dismiss()
            }

            cancelBTN.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}
