package com.maks.musicapp.utils

class Resource<T>(
    val state: State,
    val value: T?,
    val message: String?
) {
    companion object {
        fun <T> success(value: T?) = Resource(State.SUCCESS, value, null)
        fun <T> loading(value:T?) = Resource(State.LOADING, value, null)
        fun <T> error(value:T?,message: String?) = Resource(State.ERROR, value, message)
    }
}

enum class State {
    ERROR, LOADING, SUCCESS
}