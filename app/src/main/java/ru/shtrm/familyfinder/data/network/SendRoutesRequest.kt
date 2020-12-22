package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.shtrm.familyfinder.data.database.repository.route.Route

class SendRoutesRequest {

    data class SendRoutesRequest internal constructor(@Expose
                                                      @SerializedName("userLogin") internal val userLogin: String,
                                                      @Expose
                                                      @SerializedName("routes") internal val routes: List<Route>)
}