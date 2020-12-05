package ru.shtrm.familyfinder.ui.family.presenter

import io.reactivex.disposables.CompositeDisposable
import ru.shtrm.familyfinder.ui.base.presenter.BasePresenter
import ru.shtrm.familyfinder.ui.family.interactor.FamilyMVPInterator
import ru.shtrm.familyfinder.ui.family.view.FamilyFragmentMVPView
import ru.shtrm.familyfinder.util.SchedulerProvider
import javax.inject.Inject

class FamilyPresenter<V : FamilyFragmentMVPView, I : FamilyMVPInterator> @Inject internal constructor(interator: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BasePresenter<V, I>(interactor = interator, schedulerProvider = schedulerProvider, compositeDisposable = compositeDisposable), FamilyMVPPresenter<V, I> {

    override fun onImageClicked() = getView()?.let{
    }

    override fun onSubmitClicked() = interactor?.let {
    }
}
