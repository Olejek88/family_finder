package ru.shtrm.familyfinder.data.database

import android.content.Context
import android.util.Log

import io.realm.DynamicRealm
import io.realm.RealmMigration
import ru.shtrm.familyfinder.data.database.repository.migration.Migration1

internal class FamilyRealmMigration(private val context: Context) : RealmMigration {
    private val TAG = "realm"

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var oldVersion = oldVersion
        val schema = realm.getSchema()
        Log.d(TAG, "oldVersion = $oldVersion")
        Log.d(TAG, "newVersion = $newVersion")

        if (oldVersion == newVersion) {
            return
        }
        if (oldVersion == 0L) {
            Migration1().migration(realm)
            oldVersion++
        }
    }
}
