package ru.shtrm.familyfinder.ui.login

import ru.shtrm.familyfinder.ui.login.interactor.LoginInteractor
import ru.shtrm.familyfinder.ui.login.interactor.LoginMVPInteractor
import ru.shtrm.familyfinder.ui.login.presenter.LoginMVPPresenter
import ru.shtrm.familyfinder.ui.login.presenter.LoginPresenter
import ru.shtrm.familyfinder.ui.login.view.LoginMVPView
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {

    @Provides
    internal fun provideLoginInteractor(interactor: LoginInteractor): LoginMVPInteractor = interactor

    @Provides
    internal fun provideLoginPresenter(presenter: LoginPresenter<LoginMVPView, LoginMVPInteractor>)
            : LoginMVPPresenter<LoginMVPView, LoginMVPInteractor> = presenter

}