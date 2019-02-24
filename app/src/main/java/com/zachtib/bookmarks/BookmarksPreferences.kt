package com.zachtib.bookmarks

import android.content.SharedPreferences
import com.zachtib.typedpreferences.TypedPreferences

class BookmarksPreferences(preferences: SharedPreferences) : TypedPreferences(preferences) {
    var serverUrl: String by preference("")
    var username: String by preference("")
    var password: String by preference("")
}