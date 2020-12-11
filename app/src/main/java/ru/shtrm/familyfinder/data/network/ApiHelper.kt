package ru.shtrm.familyfinder.data.network

import io.reactivex.Observable

interface ApiHelper {

    fun performServerLogin(request: LoginRequest.ServerLoginRequest): Observable<LoginResponse>

    fun performServerRegister(request: RegisterRequest.ServerRegisterRequest): Observable<RegisterResponse>

    fun performLogoutApiCall(): Observable<LogoutResponse>

    fun performSendRoutes(request: SendRoutesRequest.SendRoutesRequest, bearer: String): Observable<SendResponse>

    fun performTokenRequest(request: TokenRequest.SendTokenRequest): Observable<TokenResponse>
}