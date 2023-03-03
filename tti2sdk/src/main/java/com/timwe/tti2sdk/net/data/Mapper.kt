package com.timwe.tti2sdk.net.data

abstract class Mapper<CLASS_IN, CLASS_OUT> {
    abstract fun transform(item: CLASS_IN): CLASS_OUT
}
