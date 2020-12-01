package ru.shtrm.familyfinder.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.data.database.AppDatabase
import ru.shtrm.familyfinder.data.database.repository.options.OptionsRepo
import ru.shtrm.familyfinder.data.database.repository.options.OptionsRepository
import ru.shtrm.familyfinder.data.database.repository.questions.QuestionRepo
import ru.shtrm.familyfinder.data.database.repository.questions.QuestionRepository
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
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, AppConstants.APP_DB_NAME).build()

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
    @Singleton
    internal fun provideQuestionRepoHelper(appDatabase: AppDatabase): QuestionRepo = QuestionRepository(appDatabase.questionsDao())

    @Provides
    @Singleton
    internal fun provideOptionsRepoHelper(appDatabase: AppDatabase): OptionsRepo = OptionsRepository(appDatabase.optionsDao())

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()


}