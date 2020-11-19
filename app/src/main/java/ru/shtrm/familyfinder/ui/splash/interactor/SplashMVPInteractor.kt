package ru.shtrm.familyfinder.ui.splash.interactor

import ru.shtrm.familyfinder.data.database.repository.questions.Question
import ru.shtrm.familyfinder.ui.base.interactor.MVPInteractor
import io.reactivex.Observable

/**
 * Created by jyotidubey on 04/01/18.
 */
interface SplashMVPInteractor : MVPInteractor {

    fun seedQuestions(): Observable<Boolean>
    fun seedOptions(): Observable<Boolean>
    fun getQuestion(): Observable<List<Question>>
}