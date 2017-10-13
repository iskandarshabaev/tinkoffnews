package io.wape.tinkoffnews.datasource

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.wape.tinkoffnews.Resource
import io.wape.tinkoffnews.api.API
import io.wape.tinkoffnews.api.models.NewsContent
import io.wape.tinkoffnews.api.models.Resp
import io.wape.tinkoffnews.db.entity.NewsEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsNetworkDataSource : NewsDataSource {

    override fun getNews(): LiveData<Resource<List<NewsEntity>>> {
        val data = MutableLiveData<Resource<List<NewsEntity>>>()

        API.news().loadAllNews().enqueue(object : Callback<Resp<List<NewsEntity>>> {
            override fun onResponse(call: Call<Resp<List<NewsEntity>>>?, response: Response<Resp<List<NewsEntity>>>?) {
                if (response != null && response.body() != null) {
                    val resp = response.body()
                    if (resp!!.resultCode == "OK") {
                        data.postValue(Resource.success(resp.payload))
                    } else {
                        data.postValue(Resource.error(Throwable(""), null))
                    }
                }
            }

            override fun onFailure(call: Call<Resp<List<NewsEntity>>>?, t: Throwable?) {
                data.postValue(Resource.error(t!!, null))
            }
        })

        return data
    }

    override fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>> {
        val data = MutableLiveData<Resource<NewsEntity>>()
        API.news().loadNewsContent(id).enqueue(object : Callback<Resp<NewsContent>> {
            override fun onResponse(call: Call<Resp<NewsContent>>?, response: Response<Resp<NewsContent>>?) {
                if (response != null && response.body() != null) {
                    val resp = response.body()
                    if (resp!!.resultCode == "OK") {
                        data.postValue(Resource.success(resp.payload!!.newsEntity()))
                    } else {
                        data.postValue(Resource.error(Throwable(""), null))
                    }
                }
            }

            override fun onFailure(call: Call<Resp<NewsContent>>?, t: Throwable?) {
                data.postValue(Resource.error(t!!, null))
            }
        })
        return data
    }
}
