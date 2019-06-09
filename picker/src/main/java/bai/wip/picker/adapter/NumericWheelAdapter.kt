package bai.wip.picker.adapter

import bai.wip.wheel.adapter.WheelAdapter

class NumericWheelAdapter(private var min: Int, private var max: Int) : WheelAdapter<Int> {

    override fun getItem(index: Int): Int {
        return if (index in 0 until itemsCount) {
            min + index
        } else
            0
    }

    override fun getItemsCount(): Int = max - min + 1

    override fun indexOf(element: Int): Int = element - min
}