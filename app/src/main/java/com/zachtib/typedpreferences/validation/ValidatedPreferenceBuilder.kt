package com.zachtib.typedpreferences.validation

import com.zachtib.typedpreferences.DelegatedPreference


fun <T> DelegatedPreference<T>.validate(validator: (T) -> Boolean) =
    ValidatedPreference(this, validator)