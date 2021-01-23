package ru.shtrm.familyfinder.ui.base.interactor

import android.app.Application
import org.acra.ACRA
import org.acra.config.CoreConfigurationBuilder
import org.acra.config.HttpSenderConfigurationBuilder
import org.acra.data.StringFormat
import org.acra.sender.HttpSender
import ru.shtrm.familyfinder.BuildConfig
import ru.shtrm.familyfinder.data.database.AuthorizedUser
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.util.AppConstants

open class BaseInteractor() : MVPInteractor {

    protected lateinit var preferenceHelper: PreferenceHelper
    protected lateinit var apiHelper: ApiHelper
    protected lateinit var application: Application

    constructor(preferenceHelper: PreferenceHelper, apiHelper: ApiHelper, application: Application) : this() {
        this.preferenceHelper = preferenceHelper
        this.apiHelper = apiHelper
        this.application = application
    }

    override fun isUserLoggedIn() = this.preferenceHelper.getCurrentUserLoggedInMode() != AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type

    override fun performUserLogout() = preferenceHelper.let {
        it.setCurrentUserId(null)
        it.setAccessToken(null)
        it.setCurrentUserEmail(null)
        it.setCurrentUserLoggedInMode(AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT)

        val authUser = AuthorizedUser.instance
        authUser.login = ""
        authUser.username = ""
        authUser.token = ""
        authUser._id = 0
    }

    override fun initAcra() {
        val token = AuthorizedUser.instance.token
        val login = AuthorizedUser.instance.login
        if (token == null || login == null) {
            return
        }

        val builder = CoreConfigurationBuilder(application)
                .setBuildConfigClass(BuildConfig::class.java)
                .setReportFormat(StringFormat.JSON)
        builder.getPluginConfigurationBuilder(HttpSenderConfigurationBuilder::class.java)
                .setUri(BuildConfig.BASE_URL.plus("crash?XDEBUG_SESSION_START=xdebug&token=").plus(token).plus("&apiuser=").plus(login))
                .setHttpMethod(HttpSender.Method.POST)
                .setEnabled(true)
        ACRA.init(application, builder)
    }
}