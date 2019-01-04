package com.app.movieapp.model

/**
 * Created by Rahul Sadhu
 */

data class ResponseData<T>(
    var message: String? = null,
    var results: T? = null,
    var success: Boolean? = null,
    var key: String? = null

)