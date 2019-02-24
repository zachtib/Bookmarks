package com.zachtib.typedpreferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.zachtib.typedpreferences.validation.validate

abstract class TypedPreferences(private val sharedPreferences: SharedPreferences) {

    protected fun <T> preference(default: T): DelegatedPreference<T> {
        @Suppress("UNCHECKED_CAST")
        return when (default) {
            is String -> sharedPreferences.string(default)
            is Int -> sharedPreferences.int(default)
            is Long -> sharedPreferences.long(default)
            is Boolean -> sharedPreferences.boolean(default)
            else -> throw RuntimeException("Unsupported preference type")
        } as DelegatedPreference<T>
    }

    protected fun <T> preference(default: T, validator: T.() -> Boolean) = preference(default).validate(validator)

    private var editor: SharedPreferences.Editor? = null

    internal val isEditing: Boolean
        get() = editor != null

    @SuppressLint("CommitPrefEdits")
    internal fun beginEditing() {
        editor = sharedPreferences.edit()
    }

    internal fun addAction(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
        editor?.block()
    }

    internal fun rollback() {
        editor = null
    }

    internal fun commit() {
        editor?.commit()
    }
}
