package ru.shtrm.familyfinder.data.database.repository.family

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

open class Family(
        @Index var _id: Long = 1,
        @PrimaryKey var uuid: String = "",
        var title: String = ""
) : RealmObject()
