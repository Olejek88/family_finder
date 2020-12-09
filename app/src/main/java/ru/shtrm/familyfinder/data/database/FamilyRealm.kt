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
    private val VERSION: Long = 1

    fun init(context: Context, dbName: String = "family.realm"): Boolean {
        var success = false
        var realmDB: Realm? = null
        Realm.init(context)
        val realmConfig = RealmConfiguration.Builder()
                .name(dbName)
                .schemaVersion(VERSION)
                .build()

        if (VERSION>0) Realm.migrateRealm(realmConfig, FamilyRealmMigration(context));
        Realm.setDefaultConfiguration(realmConfig);
        try {
            realmDB = Realm.getDefaultInstance()
            Log.d("realm", "Realm DB schema version = " + realmDB.getVersion())
            Log.d("realm", "db.version=" + realmDB.getVersion())
            if (realmDB.getVersion() == 0.toLong()) {
                success = true
            } else {
                val toast = Toast.makeText(context, context.getString(R.string.toast_db_actual), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                success = true
            }
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
                        .build());
        return success
    }
}
