package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendResponse(@Expose
                         @SerializedName("status_code")
                         var statusCode: String? = null)


