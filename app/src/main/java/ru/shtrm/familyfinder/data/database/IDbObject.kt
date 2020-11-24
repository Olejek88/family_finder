package ru.shtrm.familyfinder.data.database

import java.util.Date

interface IDbObject {
    val imageFile: String

    val imageFilePath: String

    val createdAt: Date

    val changedAt: Date

    fun getImageFileUrl(userName: String): String
}
