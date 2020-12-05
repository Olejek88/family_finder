package ru.shtrm.familyfinder.ui.family

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.ui.family.view.FamilyFragment

@Module
abstract class FamilyFragmentProvider {

    @ContributesAndroidInjector(modules = [FamilyFragmentModule::class])
    internal abstract fun provideFamilyFragment(): FamilyFragment
}