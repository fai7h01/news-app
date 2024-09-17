package com.localnews.bootstrap;

import com.localnews.client.NewsAPIClient;
import com.localnews.dto.response.newsapi.Article;
import com.localnews.dto.response.newsapi.NewsResponse;
import com.localnews.entity.News;
import com.localnews.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${news.api.key}")
    private String newsApiKey;
    private final NewsAPIClient newsclient;
    private final NewsRepository newsRepository;

    public DataLoader(NewsAPIClient newsclient, NewsRepository newsRepository) {
        this.newsclient = newsclient;
        this.newsRepository = newsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //import us news in db
        NewsResponse usNews = newsclient.getNewsByCountry("us", newsApiKey);
        List<Article> articles = usNews.getArticles();
        articles.forEach(article -> {
            News news = new News();
            news.setAuthor(article.getAuthor());
            news.setTitle(article.getTitle());
            news.setDescription(article.getDescription());
            news.setContent(article.getContent());
            newsRepository.save(news);
        });
    }
}
