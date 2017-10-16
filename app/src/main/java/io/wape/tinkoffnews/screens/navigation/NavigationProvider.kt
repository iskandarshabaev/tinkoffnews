package io.wape.tinkoffnews.screens.navigation

import io.wape.tinkoffnews.screens.MainActivity

/**
 * Провайдер навигации
 */
object NavigationProvider {

    fun provideNavigation(activity: MainActivity): Navigation {
        return Navigation(activity)
    }

}