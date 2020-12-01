package ru.shtrm.familyfinder.data.database.repository.migration

import io.realm.DynamicRealm

interface IMigration {
    fun migration(realm: DynamicRealm)
}
