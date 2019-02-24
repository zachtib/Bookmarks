package com.zachtib.typedpreferences

import kotlin.properties.ReadWriteProperty

interface DelegatedPreference<T> : ReadWriteProperty<Any?, T>