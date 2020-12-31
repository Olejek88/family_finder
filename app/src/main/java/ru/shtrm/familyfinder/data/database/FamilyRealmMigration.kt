package ru.shtrm.familyfinder.data.database

import android.content.Context
import android.util.Log

import io.realm.DynamicRealm
import io.realm.RealmMigration
import ru.shtrm.familyfinder.data.database.repository.migration.Migration1
import ru.shtrm.familyfinder.data.database.repository.migration.Migration2
import ru.shtrm.familyfinder.data.database.repository.migration.Migration3

internal class FamilyRealmMigration(private val context: Context) : RealmMigration {
    private val tag = "realm"

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersionCheck = oldVersion
        Log.d(tag, "oldVersion = $oldVersion")
        Log.d(tag, "newVersion = $newVersion")

        if (oldVersionCheck == newVersion) {
            return
        }
        if (oldVersionCheck == 0L) {
            Migration1().migration(realm)
            oldVersionCheck++
        }

        if (oldVersionCheck == 1L) {
            Migration2().migration(realm)
            oldVersionCheck++
        }

        if (oldVersionCheck == 2L) {
            Migration3().migration(realm)
            oldVersionCheck++
        }

    }
}
