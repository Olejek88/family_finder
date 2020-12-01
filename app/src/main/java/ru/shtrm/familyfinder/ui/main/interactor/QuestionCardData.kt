package ru.shtrm.familyfinder.ui.main.interactor

import ru.shtrm.familyfinder.data.database.repository.options.Options
import ru.shtrm.familyfinder.data.database.repository.questions.Question

data class QuestionCardData(val option: List<Options>, val question: Question)