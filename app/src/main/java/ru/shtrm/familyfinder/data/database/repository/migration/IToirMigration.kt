package ru.shtrm.familyfinder.data.database.repository.migration

import io.realm.DynamicRealm

interface IToirMigration {
    fun migration(realm: DynamicRealm)
}
