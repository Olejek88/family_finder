package ru.shtrm.familyfinder.data.network

import android.content.Context
import com.androidnetworking.interfaces.UploadProgressListener
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import ru.shtrm.familyfinder.util.FileUtils
import java.io.File
import javax.inject.Inject


class AppApiHelper @Inject constructor(private val apiHeader: ApiHeader) : ApiHelper {
    override fun performServerLogin(request: LoginRequest.ServerLoginRequest): Observable<LoginResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                    .addHeaders(apiHeader.publicApiHeader)
                    .addBodyParameter(request)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .build()
                    .getObjectObservable(LoginResponse::class.java)

    override fun performServerRegister(request: RegisterRequest.ServerRegisterRequest): Observable<RegisterResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_REGISTER)
                    .addHeaders(apiHeader.publicApiHeader)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .addBodyParameter(request)
                    .build()
                    .getObjectObservable(RegisterResponse::class.java)

    override fun performLogoutApiCall(): Observable<LogoutResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                    .addHeaders(apiHeader.protectedApiHeader)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .build()
                    .getObjectObservable(LogoutResponse::class.java)

    override fun performSendRoutes(request: SendRoutesRequest.SendRoutesRequest, bearer: String): Observable<SendResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_ROUTES_SEND)
                    .addHeaders(apiHeader.publicApiHeader)
                    .addHeaders("Authorization", bearer)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .addApplicationJsonBody(request)
                    .build()
                    .getObjectObservable(SendResponse::class.java)

    override fun performTokenRequest(request: TokenRequest.SendTokenRequest): Observable<TokenResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_TOKEN)
                    .addHeaders(apiHeader.protectedApiHeader)
                    .addBodyParameter(request)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .build()
                    .getObjectObservable(TokenResponse::class.java)

    override fun performUserSendRequest(request: UserRequest.SendUserRequest, bearer: String): Observable<SendResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_USER_SEND)
                    .addHeaders(apiHeader.protectedApiHeader)
                    .addHeaders("Authorization", bearer)
                    .addApplicationJsonBody(request)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .build()
                    .getObjectObservable(SendResponse::class.java)

    override fun performUserImageSendRequest(request: UserRequest.SendImageRequest, context: Context, bearer: String): Observable<SendResponse> {
        val path = FileUtils.getPicturesDirectory(context)
        val file = File(path.plus("/").plus(request.user.image))
        return Rx2AndroidNetworking.upload(ApiEndPoint.ENDPOINT_SERVER_USER_IMAGE_SEND)
                .addHeaders(apiHeader.protectedApiHeader)
                .addHeaders("Authorization", bearer)
                .addMultipartFile("image", file)
                .addMultipartParameter("userLogin",request.user.login.toString())
                .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                .build()
                .setUploadProgressListener(UploadProgressListener { bytesUploaded, totalBytes ->
                    // do anything with progress
                })
                .getObjectObservable(SendResponse::class.java)
    }
}