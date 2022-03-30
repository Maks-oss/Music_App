package com.maks.musicapp.utils

class Resource<T> private constructor(
    val state: State,
    val value: T?,
    val tabIndex: Int?,
    val message: String?
) {
    companion object {
        fun <T> success(value: T?, tabIndex: Int?) = Resource(State.SUCCESS, value, tabIndex, null)
        fun <T> loading( tabIndex: Int?,value: T? = null) =
            Resource(State.LOADING, value, tabIndex, null)

        fun <T> error(value: T?, message: String?) = Resource(State.ERROR, value, null, message)
    }
}

enum class State {
    ERROR, LOADING, SUCCESS
}