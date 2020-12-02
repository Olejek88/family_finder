package ru.shtrm.familyfinder.ui.map

import dagger.Module
import dagger.Provides
import ru.shtrm.familyfinder.ui.map.interactor.MapInteractor
import ru.shtrm.familyfinder.ui.map.interactor.MapMVPInteractor
import ru.shtrm.familyfinder.ui.map.presenter.MapMVPPresenter
import ru.shtrm.familyfinder.ui.map.presenter.MapPresenter
import ru.shtrm.familyfinder.ui.map.view.MapMVPView

@Module
abstract class MapActivityModule {
/*
    @Provides
    internal fun provideMapInteractor(mapInteractor: MapInteractor): MapMVPInteractor = mapInteractor

    @Provides
    internal fun provideMapPresenter(mapPresenter: MapPresenter<MapMVPView, MapMVPInteractor>)
            : MapMVPPresenter<MapMVPView, MapMVPInteractor> = mapPresenter
*/
}
