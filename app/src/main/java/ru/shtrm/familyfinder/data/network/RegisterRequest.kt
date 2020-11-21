package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterRequest {

    data class ServerRegisterRequest internal constructor(@Expose
                                                          @SerializedName("email") internal val email: String,
                                                          @Expose
                                                          @SerializedName("password") internal val password: String)
}