package ru.shtrm.familyfinder.ui.register

import dagger.Module
import dagger.Provides
import ru.shtrm.familyfinder.ui.register.interactor.RegisterInteractor
import ru.shtrm.familyfinder.ui.register.interactor.RegisterMVPInteractor
import ru.shtrm.familyfinder.ui.register.presenter.RegisterMVPPresenter
import ru.shtrm.familyfinder.ui.register.presenter.RegisterPresenter
import ru.shtrm.familyfinder.ui.register.view.RegisterMVPView

@Module
class RegisterActivityModule {

    @Provides
    internal fun provideRegisterInteractor(interactor: RegisterInteractor): RegisterMVPInteractor = interactor

    @Provides
    internal fun provideRegisterPresenter(presenter: RegisterPresenter<RegisterMVPView, RegisterMVPInteractor>)
            : RegisterMVPPresenter<RegisterMVPView, RegisterMVPInteractor> = presenter

}