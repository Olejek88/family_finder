package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TokenRequest {

    data class SendTokenRequest internal constructor(@Expose
                                                     @SerializedName("userLogin") internal val userLogin: String)

}