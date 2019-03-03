package com.zachtib.bookmarks.framework

fun <K, V> List<V>.collect(collector: (V) -> K): Map<K, V> {
    return mapOf(*map { collector(it) to it }.toTypedArray())
}