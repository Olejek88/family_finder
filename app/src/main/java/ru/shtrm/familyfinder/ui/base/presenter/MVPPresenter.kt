package ru.shtrm.familyfinder.ui.base.presenter

import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import ru.shtrm.familyfinder.ui.base.view.MVPView

/**
 * Created by jyotidubey on 04/01/18.
 */
interface MVPPresenter<V : MVPView, I : MVPInteractor> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?

}