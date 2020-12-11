package ru.shtrm.familyfinder.data.network

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
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

    override fun performSendRoutes(request: SendRoutesRequest.SendRoutesRequest): Observable<SendResponse> =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_ROUTES_SEND)
                    .addHeaders(apiHeader.publicApiHeader)
                    .addBodyParameter(request)
                    .addQueryParameter("XDEBUG_SESSION_START", "xdebug")
                    .build()
                    .getObjectObservable(SendResponse::class.java)
}