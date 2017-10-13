package io.wape.tinkoffnews.screens;

import android.app.Activity;

import io.wape.tinkoffnews.repository.NewsRepository;
import io.wape.tinkoffnews.repository.RepositoryProvider;
import io.wape.tinkoffnews.screens.newsdetails.NewsContentViewModel;
import io.wape.tinkoffnews.screens.newslist.NewsListViewModel;

public class ViewModelFactoryProvider {

    private static volatile NewsListViewModel.Factory sNewsListViewModel;
    private static volatile NewsContentViewModel.Factory sNewsContentViewModel;

    public static NewsListViewModel.Factory provideNews(Activity activity) {
        if (sNewsListViewModel == null) {
            NewsRepository repository = RepositoryProvider.INSTANCE.provideNews(activity);
            sNewsListViewModel = new NewsListViewModel.Factory(repository);
        }
        return sNewsListViewModel;
    }

    public static NewsContentViewModel.Factory provideNewsContent(Activity activity) {
        if (sNewsContentViewModel == null) {
            NewsRepository repository = RepositoryProvider.INSTANCE.provideNews(activity);
            sNewsContentViewModel = new NewsContentViewModel.Factory(repository);
        }
        return sNewsContentViewModel;
    }

}
