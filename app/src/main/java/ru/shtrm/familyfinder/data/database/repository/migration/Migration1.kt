package ru.shtrm.familyfinder.data.database.repository.migration

import android.util.Log

import io.realm.DynamicRealm

class Migration1 : IMigration {

    override fun migration(realm: DynamicRealm) {
        Log.d(this.javaClass.simpleName, "from version 0")
        val schema = realm.schema
        val obj = schema.get("Route")
        obj!!.removePrimaryKey()
        obj.removeField("userUuid")
        obj.removeField("_id")
        obj.addField("userId",Long::class.java)
    }
}
