package io.wape.tinkoffnews.screens;

import android.app.Activity;

import io.wape.tinkoffnews.repository.NewsRepository;
import io.wape.tinkoffnews.repository.RepositoryProvider;
import io.wape.tinkoffnews.screens.newsdetails.NewsContentViewModel;
import io.wape.tinkoffnews.screens.newslist.NewsListViewModel;

/**
 * Провайдер для получения фабрики ViewModel
 */
public class ViewModelFactoryProvider {

    private static volatile NewsListViewModel.Factory sNewsListViewModel;
    private static volatile NewsContentViewModel.Factory sNewsContentViewModel;

    /**
     * Предоставляет инстанс фабрики NewsListViewModel
     * @param activity любое активити
     * @return инстанс фабрики NewsListViewModel
     */
    public static NewsListViewModel.Factory provideNews(Activity activity) {
        if (sNewsListViewModel == null) {
            NewsRepository repository = RepositoryProvider.INSTANCE.provideNews(activity);
            sNewsListViewModel = new NewsListViewModel.Factory(repository);
        }
        return sNewsListViewModel;
    }

    /**
     * Предоставляет инстанс фабрики NewsContentViewModel
     * @param activity любое активити
     * @return инстанс фабрики NewsContentViewModel
     */
    public static NewsContentViewModel.Factory provideNewsContent(Activity activity) {
        if (sNewsContentViewModel == null) {
            NewsRepository repository = RepositoryProvider.INSTANCE.provideNews(activity);
            sNewsContentViewModel = new NewsContentViewModel.Factory(repository);
        }
        return sNewsContentViewModel;
    }

}
