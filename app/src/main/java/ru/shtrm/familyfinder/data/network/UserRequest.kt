package ru.shtrm.familyfinder.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.shtrm.familyfinder.data.database.repository.user.User

class UserRequest {

    data class SendUserRequest internal constructor(@Expose
                                                    @SerializedName("user") internal val user: User)

    data class SendImageRequest internal constructor(@Expose
                                                     @SerializedName("user") internal val user: User)

}