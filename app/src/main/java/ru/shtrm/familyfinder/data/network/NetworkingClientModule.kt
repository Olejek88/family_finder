package ru.shtrm.familyfinder.data.network

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit


@Module
class NetworkingClientModule {
    @Provides
    fun okHttpClient(cache: Cache) : OkHttpClient? {
        return OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000) //10 MB
    }
}