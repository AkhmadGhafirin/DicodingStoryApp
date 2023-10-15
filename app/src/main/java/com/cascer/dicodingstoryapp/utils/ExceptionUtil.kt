package com.cascer.dicodingstoryapp.utils

import com.cascer.dicodingstoryapp.utils.network.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

object ExceptionUtil {
    private fun ResponseBody?.convertErrorBody(): ErrorResponse {
        return try {
            this?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            } ?: ErrorResponse(true, "Unknown error has occurred. Please try again later")
        } catch (exception: Exception) {
            ErrorResponse(true, exception.message)
        }
    }

    private fun ErrorResponse.getMessage(): String {
        return message ?: "Unknown error has occurred. Please try again later"
    }

    fun ResponseBody?.toException(): Exception {
        return Exception(this.convertErrorBody().getMessage())
    }
}