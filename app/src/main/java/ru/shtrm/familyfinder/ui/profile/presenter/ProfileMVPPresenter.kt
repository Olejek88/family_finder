package ru.shtrm.familyfinder.ui.profile.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragmentMVPView

interface ProfileMVPPresenter<V : ProfileFragmentMVPView, I : ProfileMVPInterator> : MVPPresenter<V, I> {
    fun onImageClicked(): Unit?
    fun onSubmitClicked(): Unit?
}