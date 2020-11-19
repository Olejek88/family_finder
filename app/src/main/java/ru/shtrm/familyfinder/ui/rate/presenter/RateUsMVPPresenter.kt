package ru.shtrm.familyfinder.ui.rate.presenter

import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.rate.interactor.RateUsMVPInterator
import ru.shtrm.familyfinder.ui.rate.view.RateUsDialogMVPView

/**
 * Created by jyotidubey on 15/01/18.
 */
interface RateUsMVPPresenter<V : RateUsDialogMVPView, I : RateUsMVPInterator> : MVPPresenter<V, I> {

    fun onLaterOptionClicked(): Unit?
    fun onSubmitOptionClicked(): Unit?
}