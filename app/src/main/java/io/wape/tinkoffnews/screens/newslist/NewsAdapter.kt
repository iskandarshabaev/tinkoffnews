package io.wape.tinkoffnews.screens.newslist

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.wape.tinkoffnews.R
import io.wape.tinkoffnews.db.entity.NewsEntity

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val text: TextView = itemView.findViewById<TextView>(R.id.text)
    private val date: TextView = itemView.findViewById<TextView>(R.id.date)

    fun bind(news: NewsEntity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.text = Html.fromHtml(news.text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            text.text = Html.fromHtml(news.text)
        }
        date.text = news.publicationDate.date()
    }
}

class NewsAdapter(
        private val news: MutableList<NewsEntity>
) : RecyclerView.Adapter<NewsViewHolder>(), View.OnClickListener {

    var listener: ((NewsEntity) -> Unit)? = null

    fun changeDataSet(news: List<NewsEntity>?) {
        this.news.clear()
        if (news != null) {
            this.news.addAll(news)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_news, parent, false)
        view.setOnClickListener(this)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
        holder.itemView.tag = news[position]
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onClick(v: View?) {
        listener?.invoke(v!!.tag as NewsEntity)
    }
}
