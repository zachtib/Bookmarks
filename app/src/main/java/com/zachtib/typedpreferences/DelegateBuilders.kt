package com.zachtib.typedpreferences

import android.content.SharedPreferences
import kotlin.reflect.KProperty

private fun <T> SharedPreferences.delegateBuilder(
    defaultValue: T,
    getter: SharedPreferences.(String, T) -> T,
    setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): DelegatedPreference<T> {
    return object : DelegatedPreference<T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return getter(property.name, defaultValue)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            if (thisRef is TypedPreferences && thisRef.isEditing) {
                thisRef.addAction {
                    setter(property.name, value)
                }
            } else {
                return edit()
                    .setter(property.name, value)
                    .apply()
            }
        }
    }
}

fun SharedPreferences.int(defaultValue: Int = 0) = delegateBuilder(
    defaultValue = defaultValue,
    getter = SharedPreferences::getInt,
    setter = SharedPreferences.Editor::putInt
)

fun SharedPreferences.string(defaultValue: String = "") = delegateBuilder(
    defaultValue = defaultValue,
    getter = SharedPreferences::getString,
    setter = SharedPreferences.Editor::putString
)

fun SharedPreferences.long(defaultValue: Long = 0L) = delegateBuilder(
    defaultValue = defaultValue,
    getter = SharedPreferences::getLong,
    setter = SharedPreferences.Editor::putLong
)

fun SharedPreferences.boolean(defaultValue: Boolean = false) = delegateBuilder(
    defaultValue = defaultValue,
    getter = SharedPreferences::getBoolean,
    setter = SharedPreferences.Editor::putBoolean
)

