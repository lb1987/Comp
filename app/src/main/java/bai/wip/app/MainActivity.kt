package bai.wip.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        optionBottomBTN2.setOnClickListener {
            val xOptions = listOf("x", "y", "z", "l", "m", "n")
            val yOptions = listOf("x", "y", "z", "l", "m", "n")
            val zOptions = listOf("x", "y", "z", "l", "m", "n")

            optionUnRelatedDialog("请选择", xOptions, yOptions, zOptions) { x, y, z ->
                Log.i("bail", "x is $x, y is $y, z is $z")
            }
        }

        //  底部选项选择器
        optionBottomBTN.setOnClickListener {
            val xOptions = listOf("x", "y", "z", "l", "m", "n")

            val yOptions = listOf(
                    listOf("x1", "x2", "x3", "x4"),
                    listOf("y1", "y2", "y3", "y4", "y5", "y6"),
                    listOf("z1", "z2", "z3", "z4", "z5")
            )

            val zOptions =
                    listOf(
                            listOf(
                                    listOf("x11", "x12", "x13", "x14"),
                                    listOf("x21", "x22", "x23", "x24", "x25"),
                                    listOf("x31", "x32", "x33"),
                                    listOf("x41", "x42", "x43", "x44")
                            ),
                            listOf(
                                    listOf("y11", "y12", "y13", "y14"),
                                    listOf("y21", "y22", "y23", "y24", "y25"),
                                    listOf("y31", "y32", "y33"),
                                    listOf("y41", "y42", "y43", "y44"),
                                    listOf("y51", "y52", "y53", "y54", "y55", "y56")
                            ),
                            listOf(
                                    listOf("z11", "z12", "z13", "z14"),
                                    listOf("z21", "z22", "z23", "z24", "z25"),
                                    listOf("z31", "z32", "z33"),
                                    listOf("z41", "z42", "z43", "z44"),
                                    listOf("z51", "z52", "z53", "z54", "z55", "z56")
                            )
                    )

            optionRelatedDialog(
                    title = "请选择",
                    xOptions = xOptions,
                    yOptions = yOptions,
                    zOptions = zOptions
            ) { x, y, z ->
                Log.i("bail", "x is $x, y is $y, z is $z")
            }
        }

        // 底部日期选择器
        dateBottomBTN.setOnClickListener {

            dateBottomDialog("请选择时间") { date ->
                Log.i("bail", "date is $date")
            }
        }
    }


}
