package ru.shtrm.familyfinder.ui.map

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.ui.map.view.MapFragment

@Module
abstract class MapFragmentProvider {
    @ContributesAndroidInjector(modules = [MapActivityModule::class])
    internal abstract fun provideMapFragment(): MapFragment
}