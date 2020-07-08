package com.mn.data.common

interface Mapper<T, R> {
    operator fun invoke(t: T): R
}