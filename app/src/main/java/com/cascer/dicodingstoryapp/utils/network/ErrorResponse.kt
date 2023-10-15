package com.cascer.dicodingstoryapp.utils.network

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") var error: Boolean?,
    @SerializedName("message") val message: String?
) {
    override fun toString(): String {
        return "ErrorResponse(error=$error, message=$message)"
    }
}