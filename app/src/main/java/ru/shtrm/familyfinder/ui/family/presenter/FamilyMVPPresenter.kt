package ru.shtrm.familyfinder.ui.family.presenter

import io.realm.RealmResults
import ru.shtrm.familyfinder.data.database.repository.user.User
import ru.shtrm.familyfinder.ui.base.presenter.MVPPresenter
import ru.shtrm.familyfinder.ui.family.interactor.FamilyMVPInterator
import ru.shtrm.familyfinder.ui.family.view.FamilyFragmentMVPView

interface FamilyMVPPresenter<V : FamilyFragmentMVPView, I : FamilyMVPInterator> : MVPPresenter<V, I> {
    fun onImageClicked(): Unit?
    fun onSubmitClicked(): Unit?
    fun getUsers(): RealmResults<User>?
}