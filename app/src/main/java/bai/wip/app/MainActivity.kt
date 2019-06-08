package bai.wip.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomBTN.setOnClickListener {

            dateBottomDialog("请选择时间") { date ->
                Log.i("bail", "date is $date")
            }

            //alert(this)
            /* dialog(
                 this,
                 isBottom = true,
                 layout = R.layout.date_bottom_dialog
             ) { dialog, view ->

             }*/
            /*dialog(
                this,
                layout = R.layout.bottom_dialog
            ) { dialog, view ->
                with(view) {
                    titleTV.text = "dialog"
                    positiveBTN.text = "确定"
                    positiveBTN.setOnClickListener {
                        Toast.makeText(this@MainActivity, "click sure btn", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    }
                    negativeBTN.text = "取消"
                    negativeBTN.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }*/

        }
    }


}
