package ru.shtrm.familyfinder.data.database

interface ISend {
    val _id: Long

    fun setSent(sent: Boolean)
}
