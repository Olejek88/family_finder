package ru.shtrm.familyfinder.ui.rate

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.shtrm.familyfinder.ui.rate.view.RateUsDialog

@Module
abstract class RateUsDialogFragmentProvider {

    @ContributesAndroidInjector(modules = [RateUsFragmentModule::class])
    internal abstract fun provideRateUsFragment(): RateUsDialog
}