package bai.wip.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


fun Fragment.dialog(
    layout: Int,
    isCancelable: Boolean = true,
    isBottom: Boolean = false,
    init: (dialog: Dialog, view: View) -> Unit
) = activity?.dialog(layout, isCancelable, isBottom, init)

fun Activity.dialog(
    layout: Int,
    isCancelable: Boolean = true,
    isBottom: Boolean = false,
    init: (dialog: Dialog, view: View) -> Unit
) {
    val view = inflate(this, layout)
    val dialog = if (isBottom) {
        bottomDialog(this, view)
    } else {
        dialog(this, view)
    }
    init(dialog, view)
    dialog.setCancelable(isCancelable)
    dialog.show()
}

private fun inflate(activity: Context, layout: Int): View =
    View.inflate(activity, layout, null)

/*
 * 生成普通对话框
 */
private fun dialog(activity: Activity, view: View): Dialog = with(activity) {
    val dialog = Dialog(this)
    dialog.setContentView(view)
    val window = dialog.window!!
    window.setBackgroundDrawableResource(android.R.color.transparent)
    val p = Point()
    windowManager.defaultDisplay.getSize(p)
    val lp = window.attributes
    lp.width = (p.x * 0.75).toInt()
    window.attributes = lp
    return dialog
}

/*
 * 生成底部对话框
 */
private fun bottomDialog(activity: Activity, view: View): Dialog = with(activity) {
    val dialog = BottomSheetDialog(this)
    val window = dialog.window!!
    dialog.setContentView(view)

    val root = dialog.delegate.findViewById<View>(
        com.google.android.material.R.id.design_bottom_sheet
    )
    val behavior = BottomSheetBehavior.from(root)
    behavior.isHideable = false

    window.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        .setBackgroundResource(android.R.color.transparent)

    val p = Point()
    windowManager.defaultDisplay.getSize(p)
    val lp = window.attributes
    lp.dimAmount = 0.2f
    window.attributes = lp
    return dialog
}