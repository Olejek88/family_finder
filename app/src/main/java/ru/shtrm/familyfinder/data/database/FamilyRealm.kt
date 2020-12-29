package ru.shtrm.familyfinder.data.database

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.shtrm.familyfinder.R

object FamilyRealm {
    private const val VERSION: Long = 3

    fun init(context: Context) {
        init(context, "default.realm")
    }

    private fun init(context: Context, dbName: String = "default.realm"): Boolean {
        var success: Boolean
        val realmDB: Realm?
        val realmConfig = RealmConfiguration.Builder()
                .name(dbName)
                .schemaVersion(VERSION)
                .build()
        try {
            if (VERSION>0) Realm.migrateRealm(realmConfig, FamilyRealmMigration(context))
            Realm.setDefaultConfiguration(realmConfig)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            realmDB = Realm.getDefaultInstance()
            Log.d("realm", "Realm DB schema version = " + realmDB.version)
            Log.d("realm", "db.version=" + realmDB.version)
            if (realmDB.version == 0.toLong()) {
                success = true
            } else {
                val toast = Toast.makeText(context, context.getString(R.string.toast_db_actual), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                success = true
            }
            realmDB.close()
        } catch (e: Exception) {
            val toast = Toast.makeText(context, context.getString(R.string.toast_db_error),
                    Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
            success = false
            return success
        }

        // инициализируем интерфейс для отладки через Google Chrome
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                        .build())
        return success
    }
}
