package ru.shtrm.familyfinder.ui.rate

import dagger.Module
import dagger.Provides
import ru.shtrm.familyfinder.ui.rate.interactor.RateUsInteractor
import ru.shtrm.familyfinder.ui.rate.interactor.RateUsMVPInterator
import ru.shtrm.familyfinder.ui.rate.presenter.RateUsMVPPresenter
import ru.shtrm.familyfinder.ui.rate.presenter.RateUsPresenter
import ru.shtrm.familyfinder.ui.rate.view.RateUsDialogMVPView

@Module
class RateUsFragmentModule {

    @Provides
    internal fun provideRateUsInteractor(interactor: RateUsInteractor): RateUsMVPInterator = interactor

    @Provides
    internal fun provideRateUsPresenter(presenter: RateUsPresenter<RateUsDialogMVPView, RateUsMVPInterator>)
            : RateUsMVPPresenter<RateUsDialogMVPView, RateUsMVPInterator> = presenter
}