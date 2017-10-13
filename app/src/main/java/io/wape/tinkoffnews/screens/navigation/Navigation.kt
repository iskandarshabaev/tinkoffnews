package io.wape.tinkoffnews.screens.navigation

import android.support.v4.app.FragmentManager
import io.wape.tinkoffnews.R
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.screens.MainActivity
import io.wape.tinkoffnews.screens.newsdetails.NewsContentFragment
import io.wape.tinkoffnews.screens.newslist.NewsListFragment

class Navigation constructor(var mainActivity: MainActivity) {

    private val containerId: Int = R.id.container
    private val fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    fun navigateToNewsList() {
        val fragment = NewsListFragment()
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss()
        showBackButton(false)
    }

    fun navigateToNewsContent(news: NewsEntity) {
        val fragment = NewsContentFragment.forNewsID(news.id)
        val tag = "news/${news.id}"
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        showBackButton(true)
    }

    private fun showBackButton(show: Boolean) {
        val actionBar = mainActivity.supportActionBar
        if (actionBar != null) {
            if (show) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setDisplayShowHomeEnabled(true)
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false)
                actionBar.setDisplayShowHomeEnabled(false)
            }
        }
    }
}