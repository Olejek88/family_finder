package ru.shtrm.familyfinder.data.database

import android.content.Context
import android.util.Log

import io.realm.DynamicRealm
import io.realm.RealmMigration

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
/*
        if (oldVersion == 0L) {
            toVersion1(realm)
            oldVersion++
        }
*/
    }
}
