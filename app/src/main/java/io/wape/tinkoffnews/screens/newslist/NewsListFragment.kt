package io.wape.tinkoffnews.screens.newslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.wape.tinkoffnews.R
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.screens.MainActivity
import io.wape.tinkoffnews.screens.ViewModelFactoryProvider
import io.wape.tinkoffnews.screens.handleError
import io.wape.tinkoffnews.screens.navigation.Navigation
import io.wape.tinkoffnews.screens.navigation.NavigationProvider
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.utils.Status
import java.util.*

/**
 * Фрагмент для отображения списка новостей
 */
class NewsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private var snackBar: Snackbar? = null
    private lateinit var viewModel: NewsListViewModel
    private lateinit var navigation: Navigation

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_news_list, container, false)
        findViews(view)
        initViews()
        navigation = NavigationProvider.provideNavigation(activity as MainActivity)
        return view
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById(R.id.news_list)
        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh)
    }

    private fun initViews() {
        adapter = NewsAdapter(ArrayList<NewsEntity>())
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        swipeToRefresh.setOnRefreshListener { reloadData() }
        adapter.listener = { showContent(it) }
        initToolbar()
    }

    private fun initToolbar() {
        val actionBar = (activity as MainActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowHomeEnabled(false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = ViewModelFactoryProvider.provideNews(activity)
        viewModel = ViewModelProviders.of(this, factory).get(NewsListViewModel::class.java)
        subscribeUI(viewModel)
        loadData()
    }

    /**
     * Попдисывает фрагмент на изменения ViewModel
     */
    private fun subscribeUI(viewModel: NewsListViewModel) {
        viewModel.news.observe(this, Observer { observe(it) })
    }

    /**
     * Загрузка данных необходимых для отображения страницы
     */
    private fun loadData() {
        swipeToRefresh.isRefreshing = true
        snackBar?.dismiss()
        viewModel.load()
    }

    /**
     * Перезагрузка данных.
     */
    private fun reloadData() {
        swipeToRefresh.isRefreshing = true
        snackBar?.dismiss()
        viewModel.reload()
    }

    /**
     * Сюда приходят изменения ViewMddel
     * @param resource Класс контейнер содержащий данные для отображения страницы
     */
    private fun observe(resource: Resource<List<NewsEntity>>?) {
        if (resource != null) {
            when (resource.status) {
                Status.SUCCESS -> updateData(resource.data)
                Status.LOADING -> swipeToRefresh.isRefreshing = true
                Status.ERROR -> showError(resource)
            }
        }
    }

    /**
     * Запуск фрагмента NewsContentFragment для отображения контента новости
     */
    private fun showContent(news: NewsEntity) {
        navigation.navigateToNewsContent(news)
    }

    /**
     * Обновление данных в адаптере
     */
    private fun updateData(news: List<NewsEntity>?) {
        adapter.changeDataSet(news)
        swipeToRefresh.isRefreshing = false
    }

    /**
     * Вывод текста ошибка в snackBar c кнопокой перезагрузки
     */
    private fun showError(resource: Resource<List<NewsEntity>>) {
        swipeToRefresh.isRefreshing = false
        if (resource.throwable != null) {
            snackBar = handleError(resource.throwable, R.string.reload,
                    View.OnClickListener { reloadData() })
        }
        if (resource.data != null) {
            updateData(resource.data)
        }
    }
}
