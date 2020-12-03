package ru.shtrm.familyfinder.ui.profile.presenter

import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.profile.interactor.ProfileMVPInterator
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragmentMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class ProfilePresenter<V : ProfileFragmentMVPView, I : ProfileMVPInterator> @Inject internal constructor(interator: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interator, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), ProfileMVPPresenter<V, I> {

    override fun onImageClicked() = getView()?.let{
        //it.dismissDialog()
    }

    override fun onSubmitClicked() = interactor?.let {
/*
        it.submitRating()
        getView()?.let {
            it.showRatingSubmissionSuccessMessage()
            it.dismissDialog()
        }
*/
    }
}
