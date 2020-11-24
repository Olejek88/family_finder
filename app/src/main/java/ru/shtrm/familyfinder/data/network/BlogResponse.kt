package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BlogResponse(@Expose
                        @SerializedName("status_code")
                        private var statusCode: String? = null,

                        @Expose
                        @SerializedName("message")
                        private var message: String? = null,

                        @Expose
                        @SerializedName("data")
                        var data: List<Blog>? = null)
