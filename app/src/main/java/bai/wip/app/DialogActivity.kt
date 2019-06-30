package bai.wip.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import bai.wip.picker.tag.PrimitiveType
import bai.wip.wheel.interfaces.Tag
import kotlinx.android.synthetic.main.activity_dialog.*


class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)


        dateBTN.setOnClickListener {
            dateBottomDialog("请选择时间") { date ->
                Log.i("bail", "date is $date")
            }
        }

        cascadeBTN.setOnClickListener {
            val xOptions = listOf(
                    PrimitiveType("x"),
                    PrimitiveType("y"),
                    PrimitiveType("z"),
                    PrimitiveType("l"),
                    PrimitiveType("m"),
                    PrimitiveType("n"))

            val yOptions = listOf(
                    listOf(PrimitiveType("x1"),
                            PrimitiveType("x2"),
                            PrimitiveType("x3"),
                            PrimitiveType("x4")),

                    listOf(PrimitiveType("y1"),
                            PrimitiveType("y2"),
                            PrimitiveType("y3"),
                            PrimitiveType("y4"),
                            PrimitiveType("y5"),
                            PrimitiveType("y6")),

                    listOf(PrimitiveType("z1"),
                            PrimitiveType("z2"),
                            PrimitiveType("z3"),
                            PrimitiveType("z4"),
                            PrimitiveType("z5"))
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

            cascadeOptionDialog<PrimitiveType<String>, PrimitiveType<String>, PrimitiveType<String>>(
                    title = "请选择",
                    xList = xOptions,
                    yList = yOptions
            ) { x, y, z ->
                Log.i("bail", "x is $x, y is $y")
            }
        }

        parallelBTN.setOnClickListener {
            val intOptions =
                    listOf(
                            PrimitiveType(0L),
                            PrimitiveType(1L),
                            PrimitiveType(2L),
                            PrimitiveType(3L),
                            PrimitiveType(4L),
                            PrimitiveType(5L)
                    )
            parallelOptionDialog<PrimitiveType<Long>, PrimitiveType<Long>, PrimitiveType<Long>>(
                    "请选择",
                    PrimitiveType(2L),
                    xOptions = intOptions) { x, y, z ->
                Log.i("bail", "x is ${x.t}, y is $y, z is $z")
            }
        }
    }
}
