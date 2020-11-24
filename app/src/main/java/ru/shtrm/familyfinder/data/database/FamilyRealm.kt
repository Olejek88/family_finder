package ru.shtrm.familyfinder.data.database

import io.realm.Realm
import android.content.Context
import io.realm.RealmConfiguration
import com.facebook.stetho.Stetho

class FamilyRealm {
    private val VERSION: Long = 1

    fun init(context: Context, dbName: String = "toir.realm") {
        Realm.init(context)
        val realmConfig = RealmConfiguration.Builder()
                .name(dbName)
                .schemaVersion(VERSION)
                .build()
        Realm.migrateRealm(realmConfig, FamilyRealmMigration(context));
        Realm.setDefaultConfiguration(realmConfig);

        // инициализируем интерфейс для отладки через Google Chrome
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                        .build());
    }
}
