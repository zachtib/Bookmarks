package com.zachtib.typedpreferences.validation

import com.zachtib.typedpreferences.DelegatedPreference
import kotlin.reflect.KProperty

class ValidatedPreference<T>(
    private val delegate: DelegatedPreference<T>,
    private val validate: (T) -> Boolean
) : DelegatedPreference<T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = delegate.getValue(thisRef, property)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (validate(value)) {
            delegate.setValue(thisRef, property, value)
        } else {
            throw PreferencesValidationException("Value \"$value\" for \"${property.name}\" failed validation")
        }
    }
}
