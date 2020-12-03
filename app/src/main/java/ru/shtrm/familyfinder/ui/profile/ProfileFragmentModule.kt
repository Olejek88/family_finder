package ru.shtrm.familyfinder.ui.profile

import dagger.Module
import dagger.Provides
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileInteractor
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.presenter.ProfileMVPPresenter
import ru.shtrm.familyfinder.ui.profile.presenter.ProfilePresenter
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragmentMVPView

@Module
class ProfileFragmentModule {

    @Provides
    internal fun provideProfileInteractor(interactor: ProfileInteractor): ProfileMVPInterator = interactor

    @Provides
    internal fun provideProfilePresenter(presenter: ProfilePresenter<ProfileFragmentMVPView, ProfileMVPInterator>)
            : ProfileMVPPresenter<ProfileFragmentMVPView, ProfileMVPInterator> = presenter
}