package io.wape.tinkoffnews.screens

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.wape.tinkoffnews.R
import io.wape.tinkoffnews.screens.navigation.NavigationProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            NavigationProvider.provideNavigation(this).navigateToNewsList()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            return false
        } else {
            return super.onNavigateUp()
        }
    }
}
