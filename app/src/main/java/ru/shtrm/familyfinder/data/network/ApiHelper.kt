package ru.shtrm.familyfinder.data.network

import android.content.Context
import io.reactivex.Observable

//@Component(modules = {NetworkingClientModule.class})
interface ApiHelper {

    fun performServerLogin(request: LoginRequest.ServerLoginRequest): Observable<LoginResponse>

    fun performServerRegister(request: RegisterRequest.ServerRegisterRequest): Observable<RegisterResponse>

    fun performLogoutApiCall(): Observable<LogoutResponse>

    fun performSendRoutes(request: SendRoutesRequest.SendRoutesRequest, bearer: String): Observable<SendResponse>

    fun performTokenRequest(request: TokenRequest.SendTokenRequest): Observable<TokenResponse>

    fun performUserSendRequest(request: UserRequest.SendUserRequest, bearer: String): Observable<SendResponse>

    fun performUserImageSendRequest(request: UserRequest.SendImageRequest, context: Context, bearer: String): Observable<SendResponse>
}