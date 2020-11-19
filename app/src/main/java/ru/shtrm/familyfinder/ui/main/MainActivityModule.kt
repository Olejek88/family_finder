package ru.shtrm.familyfinder.ui.main

import ru.shtrm.familyfinder.ui.main.interactor.MainInteractor
import ru.shtrm.familyfinder.ui.main.interactor.MainMVPInteractor
import ru.shtrm.familyfinder.ui.main.presenter.MainMVPPresenter
import ru.shtrm.familyfinder.ui.main.presenter.MainPresenter
import ru.shtrm.familyfinder.ui.main.view.MainMVPView
import dagger.Module
import dagger.Provides

/**
 * Created by jyotidubey on 09/01/18.
 */
@Module
class MainActivityModule {

    @Provides
    internal fun provideMainInteractor(mainInteractor: MainInteractor): MainMVPInteractor = mainInteractor

    @Provides
    internal fun provideMainPresenter(mainPresenter: MainPresenter<MainMVPView, MainMVPInteractor>)
            : MainMVPPresenter<MainMVPView, MainMVPInteractor> = mainPresenter

}