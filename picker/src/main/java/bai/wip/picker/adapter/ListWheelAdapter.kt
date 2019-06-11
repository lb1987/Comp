package bai.wip.picker.adapter

import bai.wip.wheel.adapter.WheelAdapter

class ListWheelAdapter<T>(
    private val items: List<T>
) : WheelAdapter<T> {

    override fun getItemsCount(): Int = items.size

    override fun getItem(index: Int): T? =
        if (index in 0 until items.size) {
            items[index]
        } else {
            null
        }

    override fun indexOf(item: T): Int = items.indexOf(item)
}