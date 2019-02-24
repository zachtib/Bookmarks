package com.zachtib.bookmarks.converters

import com.zachtib.bookmarks.api.models.Bookmark as ApiBookmark
import com.zachtib.bookmarks.db.models.Bookmark as DbBookmark


fun ApiBookmark.toDbModel() = DbBookmark(id, url, title)

fun DbBookmark.toApiModel() = ApiBookmark(url, title, "", "", "", 0, 0, 0, id, listOf(), listOf())
