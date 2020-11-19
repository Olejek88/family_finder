package ru.shtrm.familyfinder.ui.splash.interactor

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.internal.`$Gson$Types`
import ru.shtrm.familyfinder.data.database.repository.options.Options
import ru.shtrm.familyfinder.data.database.repository.options.OptionsRepo
import ru.shtrm.familyfinder.data.database.repository.questions.Question
import ru.shtrm.familyfinder.data.database.repository.questions.QuestionRepo
import ru.shtrm.familyfinder.data.network.ApiHelper
import ru.shtrm.familyfinder.data.preferences.PreferenceHelper
import ru.shtrm.familyfinder.ui.base.interactor.BaseInteractor
import ru.shtrm.familyfinder.util.AppConstants
import ru.shtrm.familyfinder.util.FileUtils
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by jyotidubey on 04/01/18.
 */
class SplashInteractor @Inject constructor(private val mContext: Context, private val questionRepoHelper: QuestionRepo, private val optionsRepoHelper: OptionsRepo, preferenceHelper: PreferenceHelper, apiHelper: ApiHelper) : BaseInteractor(preferenceHelper, apiHelper), SplashMVPInteractor {

    override fun getQuestion(): Observable<List<Question>> {
        return questionRepoHelper.loadQuestions()
    }

    override fun seedQuestions(): Observable<Boolean> {
        val builder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        val gson = builder.create()
        return questionRepoHelper.isQuestionsRepoEmpty().concatMap { isEmpty ->
            if (isEmpty) {
                val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Question::class.java)
                val questionList = gson.fromJson<List<Question>>(
                        FileUtils.loadJSONFromAsset(
                                mContext,
                                AppConstants.SEED_DATABASE_QUESTIONS),
                        type)
                questionRepoHelper.insertQuestions(questionList)
            } else
                Observable.just(false)
        }
    }

    override fun seedOptions(): Observable<Boolean> {
        val builder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        val gson = builder.create()
        return optionsRepoHelper.isOptionsRepoEmpty().concatMap { isEmpty ->
            if (isEmpty) {
                val type = `$Gson$Types`.newParameterizedTypeWithOwner(null, List::class.java, Options::class.java)
                val optionsList = gson.fromJson<List<Options>>(
                        FileUtils.loadJSONFromAsset(
                                mContext,
                                AppConstants.SEED_DATABASE_OPTIONS),
                        type)
                optionsRepoHelper.insertOptions(optionsList)
            } else
                Observable.just(false)
        }
    }
}