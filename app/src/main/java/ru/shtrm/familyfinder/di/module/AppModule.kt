package ru.shtrm.familyfinder.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.data.network.ApiHeader
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.network.AppApiHelper
import ru.shtrm.familyfinder.data.preferences.AppPreferenceHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.di.ApiKeyInfo
import ru.shtrm.familyfinder.di.PreferenceInfo
import ru.shtrm.familyfinder.util.AppConstants
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @ApiKeyInfo
    internal fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @PreferenceInfo
    internal fun provideprefFileName(): String = AppConstants.PREF_NAME

    @Provides
    @Singleton
    internal fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    @Singleton
    internal fun provideProtectedApiHeader(@ApiKeyInfo apiKey: String, preferenceHelper: PreferenceHelper)
            : ApiHeader.ProtectedApiHeader = ApiHeader.ProtectedApiHeader(apiKey = apiKey,
            userId = preferenceHelper.getCurrentUserId(),
            accessToken = preferenceHelper.getAccessToken())

    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper = appApiHelper

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()


}