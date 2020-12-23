package ru.shtrm.familyfinder.data.database.repository.migration

import android.util.Log

import io.realm.DynamicRealm
import io.realm.RealmObjectSchema


class Migration3 : IMigration {

    override fun migration(realm: DynamicRealm) {
        Log.d(this.javaClass.simpleName, "from version 2")
        val schema = realm.schema
        val obj = schema.get("User")
        if (obj != null) {
            obj.addField("isSent", Boolean::class.java)
            obj.transform(RealmObjectSchema.Function { objt -> objt.set("isSent", true) })
        }
    }
}
