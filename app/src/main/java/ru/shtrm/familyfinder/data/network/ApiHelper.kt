package ru.shtrm.familyfinder.data.network

import io.reactivex.Observable

interface ApiHelper {

    fun performServerLogin(request: LoginRequest.ServerLoginRequest): Observable<LoginResponse>

    fun performServerRegister(request: RegisterRequest.ServerRegisterRequest): Observable<RegisterResponse>

    fun performLogoutApiCall(): Observable<LogoutResponse>

    fun getBlogApiCall(): Observable<BlogResponse>

    fun getOpenSourceApiCall(): Observable<OpenSourceResponse>

}