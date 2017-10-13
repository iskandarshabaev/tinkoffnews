package io.wape.tinkoffnews.screens.navigation

import io.wape.tinkoffnews.screens.MainActivity

object NavigationProvider {

    fun provideNavigation(activity: MainActivity): Navigation {
        return Navigation(activity)
    }

}