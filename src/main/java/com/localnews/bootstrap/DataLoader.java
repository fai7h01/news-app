package com.localnews.bootstrap;

import com.localnews.client.NewsAPIClient;
import com.localnews.dto.CityDto;
import com.localnews.dto.response.newsapi.Article;
import com.localnews.dto.response.newsapi.NewsResponse;
import com.localnews.entity.News;
import com.localnews.repository.NewsRepository;
import com.localnews.service.CityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${news.api.key}")
    private String newsApiKey;
    private static String path = "src/main/resources/uscities.csv";
    private final NewsAPIClient newsclient;
    private final NewsRepository newsRepository;
    private final CityService cityService;

    public DataLoader(NewsAPIClient newsclient, NewsRepository newsRepository, CityService cityService) {
        this.newsclient = newsclient;
        this.newsRepository = newsRepository;
        this.cityService = cityService;
    }

    @Override
    public void run(String... args) {
        //import us cities
        readCsv(path);
        //import us news in db
        NewsResponse usNews = newsclient.getNewsByCountry("us", newsApiKey); // by country
        importNews(usNews.getArticles());
        usNews = newsclient.getAllNewsByKeyword("United States", newsApiKey); // by keyword
        importNews(usNews.getArticles());
    }


    private void importNews(List<Article> articles){
        articles.forEach(article -> {
            News news = new News();
            news.setAuthor(article.getAuthor());
            news.setTitle(article.getTitle());
            news.setDescription(article.getDescription());
            news.setContent(article.getContent());
            news.setUrl(article.getUrl());
            newsRepository.save(news);
        });
    }


    private void readCsv(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CityDto city = new CityDto();
                city.setName(values[0]);
                city.setState(values[2]);
                city.setPopulation(values[8]);
                cityService.save(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
