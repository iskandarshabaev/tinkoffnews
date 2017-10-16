package io.wape.tinkoffnews.screens.navigation

import android.support.v4.app.FragmentManager
import io.wape.tinkoffnews.R
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.screens.MainActivity
import io.wape.tinkoffnews.screens.newsdetails.NewsContentFragment
import io.wape.tinkoffnews.screens.newslist.NewsListFragment

/**
 * Класс для навигации между активити и фрагментами
 */
class Navigation constructor(mainActivity: MainActivity) {

    private val containerId: Int = R.id.container
    private val fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    /**
     * Запуск фрагмента со списком новостей
     */
    fun navigateToNewsList() {
        val fragment = NewsListFragment()
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    /**
     * Запуск фрагмента с контентом новости
     */
    fun navigateToNewsContent(news: NewsEntity) {
        val fragment = NewsContentFragment.forNewsID(news.id)
        val tag = "news/${news.id}"
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
                        R.anim.slide_in_left, R.anim.slide_out_left)
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }
}