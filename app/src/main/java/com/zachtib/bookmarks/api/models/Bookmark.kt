package com.zachtib.bookmarks.api.models

import com.google.gson.annotations.SerializedName

// Example record;
// {
//     "url":"https:\/\/github.com\/nextcloud\/bookmarks\/blob\/master\/API.md",
//     "title":"bookmarks\/API.md at master \u00b7 nextcloud\/bookmarks",
//     "user_id":"zach",
//     "description":"",
//     "public":"0",
//     "added":"1550844008",
//     "lastmodified":"1550844008",
//     "clickcount":"0",
//     "id":"11",
//     "tags":[],
//     "folders":["6"]
// }

data class Bookmark(
    val url: String,
    val title: String,
    val userId: String,
    val description: String,
    val public: String,
    val added: Long,
    @SerializedName("lastmodified")
    val lastModified: Long,
    @SerializedName("clickcount")
    val clickCount: Int,
    val id: Int,
    val tags: List<String>,
    val folders: List<String>
)