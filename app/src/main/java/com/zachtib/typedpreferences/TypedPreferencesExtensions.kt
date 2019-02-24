package com.zachtib.typedpreferences

import com.zachtib.typedpreferences.validation.PreferencesValidationException


fun <T : TypedPreferences> T.edit(block: T.() -> Unit) {
    try {
        beginEditing()
        block()
        commit()
    } catch (e: PreferencesValidationException) {
        rollback()
    }
}