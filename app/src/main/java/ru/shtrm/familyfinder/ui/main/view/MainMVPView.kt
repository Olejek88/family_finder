package ru.shtrm.familyfinder.ui.main.view

import ru.shtrm.familyfinder.ui.base.view.MVPView
import ru.shtrm.familyfinder.ui.main.interactor.QuestionCardData

/**
 * Created by jyotidubey on 08/01/18.
 */
interface MainMVPView : MVPView {

    fun inflateUserDetails(userDetails: Pair<String?, String?>)
    fun displayQuestionCard(questionCard: List<QuestionCardData>)
    fun openLoginActivity()
    fun openFeedActivity()
    fun openAboutFragment()
    fun openRateUsDialog(): Unit?
    fun lockDrawer(): Unit?
    fun unlockDrawer(): Unit?
}