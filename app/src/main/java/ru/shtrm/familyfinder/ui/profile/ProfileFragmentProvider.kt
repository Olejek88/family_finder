package ru.shtrm.familyfinder.ui.profile

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.ui.profile.view.ProfileFragment

@Module
abstract class ProfileFragmentProvider {

    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    internal abstract fun provideProfileFragment(): ProfileFragment
}