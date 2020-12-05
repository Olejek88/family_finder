package ru.shtrm.familyfinder.ui.family

import dagger.Module
import dagger.Provides
import ru.shtrm.familyfinder.ui.family.interactor.FamilyInteractor
import ru.shtrm.familyfinder.ui.family.interactor.FamilyMVPInterator
import ru.shtrm.familyfinder.ui.family.presenter.FamilyMVPPresenter
import ru.shtrm.familyfinder.ui.family.presenter.FamilyPresenter
import ru.shtrm.familyfinder.ui.family.view.FamilyFragmentMVPView

@Module
class FamilyFragmentModule {

    @Provides
    internal fun provideFamilyInteractor(interactor: FamilyInteractor): FamilyMVPInterator = interactor

    @Provides
    internal fun provideFamilyPresenter(presenter: FamilyPresenter<FamilyFragmentMVPView, FamilyMVPInterator>)
            : FamilyMVPPresenter<FamilyFragmentMVPView, FamilyMVPInterator> = presenter
}