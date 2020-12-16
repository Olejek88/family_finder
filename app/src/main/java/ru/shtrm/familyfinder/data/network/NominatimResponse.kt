package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NominatimResponse(@Expose
                        @SerializedName("display_name")
                        var displayName: String? = null,
                             @Expose
                        @SerializedName("name")
                        var name: String? = null)


