package com.app.movieapp.retrofit

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}


data class NetworkState(val status: Status, val msg: String) {
    companion object {
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
    }
}
