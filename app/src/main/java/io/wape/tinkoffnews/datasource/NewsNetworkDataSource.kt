package io.wape.tinkoffnews.datasource

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.wape.tinkoffnews.api.API
import io.wape.tinkoffnews.api.models.ApiResponse
import io.wape.tinkoffnews.api.models.NewsContent
import io.wape.tinkoffnews.db.entity.NewsEntity
import io.wape.tinkoffnews.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Реализация сетевого источника данных
 * Загружает данные с сервера и в зависимости от результата возвращает нужный ресурс
 */
class NewsNetworkDataSource : NewsDataSource {

    override fun getNews(): LiveData<Resource<List<NewsEntity>>> {
        Log.d("request", "getNews")
        val data = MutableLiveData<Resource<List<NewsEntity>>>()
        API.news().loadAllNews().enqueue(object : Callback<ApiResponse<List<NewsEntity>>> {
            override fun onResponse(call: Call<ApiResponse<List<NewsEntity>>>?, response: Response<ApiResponse<List<NewsEntity>>>?) {
                if (response != null && response.body() != null) {
                    val resp = response.body()
                    if (resp!!.resultCode == "OK") {
                        data.postValue(Resource.success(resp.payload))
                    } else {
                        data.postValue(Resource.error(Throwable(""), null))
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<NewsEntity>>>?, t: Throwable?) {
                data.postValue(Resource.error(t!!, null))
            }
        })

        return data
    }

    override fun getNewsContent(id: Long): LiveData<Resource<NewsEntity>> {
        Log.d("request", "getNewsContent $id")
        val data = MutableLiveData<Resource<NewsEntity>>()
        API.news().loadNewsContent(id).enqueue(object : Callback<ApiResponse<NewsContent>> {
            override fun onResponse(call: Call<ApiResponse<NewsContent>>?, response: Response<ApiResponse<NewsContent>>?) {
                if (response != null && response.body() != null) {
                    val resp = response.body()
                    if (resp!!.resultCode == "OK") {
                        data.postValue(Resource.success(resp.payload!!.newsEntity()))
                    } else {
                        data.postValue(Resource.error(Throwable(""), null))
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse<NewsContent>>?, t: Throwable?) {
                data.postValue(Resource.error(t!!, null))
            }
        })
        return data
    }
}
