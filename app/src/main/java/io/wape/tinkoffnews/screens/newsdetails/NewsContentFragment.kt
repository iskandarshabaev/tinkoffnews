package io.wape.tinkoffnews.screens.newsdetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.wape.tinkoffnews.R
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.screens.MainActivity
import io.wape.tinkoffnews.screens.ViewModelFactoryProvider
import io.wape.tinkoffnews.screens.handleError
import io.wape.tinkoffnews.utils.Resource
import io.wape.tinkoffnews.utils.Status

/**
 * Фрагмент для отображения контента новостей
 */
class NewsContentFragment : Fragment {

    private lateinit var title: TextView
    private lateinit var content: TextView

    constructor()

    private constructor(args: Bundle) {
        arguments = args
    }

    companion object {
        fun forNewsID(id: Long) = NewsContentFragment(Bundle(1).apply { putLong(ID_KEY, id) })
        const val ID_KEY = "id"
    }

    private lateinit var viewModel: NewsContentViewModel
    private lateinit var progressBar: ProgressBar
    private var snackBar: Snackbar? = null
    private var id: Long = -1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_news_details, container, false)
        findViews(view)
        initViews()
        return view
    }

    private fun findViews(view: View) {
        title = view.findViewById(R.id.title)
        content = view.findViewById(R.id.content)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun initViews() {
        val actionBar = (activity as MainActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    /**
     * После создания активити достается id из аргументов,
     * создается ViewModel(либо возвращается старый если он уже есть).
     * Затем фрагмент подписывается на изменения ViewModel и вызывается метод загрузки даных
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        id = arguments.getLong(ID_KEY, -1)
        val factory = ViewModelFactoryProvider.provideNewsContent(activity)
        viewModel = ViewModelProviders.of(this, factory).get(NewsContentViewModel::class.java)
        subscribeUI(viewModel)
        loadData()
    }

    /**
     * Загрузка данных для отображения страницы
     */
    private fun loadData() {
        progressBar.visibility = View.VISIBLE
        viewModel.load(id)
    }

    /**
     * Перезагрузка данных.
     */
    private fun reloadData() {
        progressBar.visibility = View.VISIBLE
        viewModel.reload(id)
    }

    /**
     * Попдисывает фрагмент на изменения ViewModel
     */
    private fun subscribeUI(viewModel: NewsContentViewModel) {
        viewModel.newsContent.observe(this, Observer { observe(it) })
    }

    /**
     * Сюда приходят изменения ViewMddel
     * @param resource Класс контейнер содержащий данные для отображения страницы
     */
    private fun observe(resource: Resource<NewsEntity>?) {
        if (resource != null) {
            when (resource.status) {
                Status.SUCCESS -> updateData(resource.data)
                Status.ERROR -> showError(resource)
            }
        }
    }

    /**
     * Отображает контент новости
     */
    private fun updateData(news: NewsEntity?) {
        progressBar.visibility = View.GONE
        if (news != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                title.text = Html.fromHtml(news.text, Html.FROM_HTML_MODE_COMPACT)
                content.text = Html.fromHtml(news.content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                title.text = Html.fromHtml(news.text)
                content.text = Html.fromHtml(news.content)
            }
        }
    }

    /**
     * Показывает SnackBar с текстом ошибки и кнопкой перезагрузки
     */
    private fun showError(resource: Resource<NewsEntity>) {
        progressBar.visibility = View.GONE
        if (resource.throwable != null) {
            snackBar = handleError(resource.throwable, R.string.reload,
                    View.OnClickListener { reloadData() })
        }
        if (resource.data != null) {
            updateData(resource.data)
        }
    }
}