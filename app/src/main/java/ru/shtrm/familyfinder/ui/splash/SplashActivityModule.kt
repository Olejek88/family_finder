package ru.shtrm.familyfinder.ui.splash

import dagger.Module
import dagger.Provides
import ru.shtrm.familyfinder.ui.splash.interactor.SplashInteractor
import ru.shtrm.familyfinder.ui.splash.interactor.SplashMVPInteractor
import ru.shtrm.familyfinder.ui.splash.presenter.SplashMVPPresenter
import ru.shtrm.familyfinder.ui.splash.presenter.SplashPresenter
import ru.shtrm.familyfinder.ui.splash.view.SplashMVPView

@Module
class SplashActivityModule {

    @Provides
    internal fun provideSplashInteractor(splashInteractor: SplashInteractor): SplashMVPInteractor = splashInteractor

    @Provides
    internal fun provideSplashPresenter(splashPresenter: SplashPresenter<SplashMVPView, SplashMVPInteractor>)
            : SplashMVPPresenter<SplashMVPView, SplashMVPInteractor> = splashPresenter
}