package com.bitoasis.data.common

interface Preferences {
    fun putValue(key: String, value: String)
    fun putBoolean(key: String, value: Boolean)

    fun getValue(key: String): String

    fun getBoolean(key: String): Boolean

    fun putInt(key: String, value: Int)

    fun getInt(key: String, default: Int): Int
}