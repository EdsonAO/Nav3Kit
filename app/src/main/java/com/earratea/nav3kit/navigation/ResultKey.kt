package com.earratea.nav3kit.navigation

open class ResultKey<T>(val key: String) where T : ResultValue

sealed interface ResultValue

@JvmInline
value class IntResult(val value: Int) : ResultValue {
    companion object {
        fun Int.asResult(): IntResult = IntResult(this)
    }
}

@JvmInline
value class BoolResult(val value: Boolean) : ResultValue {
    companion object {
        fun Boolean.asResult(): BoolResult = BoolResult(this)
    }
}

@JvmInline
value class StringResult(val value: String) : ResultValue {
    companion object {
        fun String.asResult(): StringResult = StringResult(this)
    }
}

@JvmInline
value class FloatResult(val value: Float) : ResultValue {
    companion object {
        fun Float.asResult(): FloatResult = FloatResult(this)
    }
}
