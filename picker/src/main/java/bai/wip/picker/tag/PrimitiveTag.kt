package bai.wip.picker.tag

import bai.wip.wheel.interfaces.Tag

data class PrimitiveType<T>(val t: T) : Tag {
    override fun tag(): String = when (t) {
        is Number -> {
            t.toString()
        }

        is String -> {
            t
        }

        else -> {
            "param 'T' must be Primitive or String type !!!"
        }
    }
}