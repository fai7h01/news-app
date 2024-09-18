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

import java.io.*;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${news.api.key}")
    private String newsApiKey;
    //private static String path = "/home/ubuntu/news-app/uscities.csv";
   // private String path = "/csv/uscities.csv";
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
        readCsv();
        //import us news in db
        NewsResponse usNews = newsclient.getNewsByCountry("us", newsApiKey); // by country
        importNews(usNews.getArticles());
        usNews = newsclient.getAllNewsByKeyword("United States", newsApiKey); // by keyword
        importNews(usNews.getArticles());
        newsRepository.findAll().stream().map(News::getContent).forEach(System.out::println);
    }


    private void importNews(List<Article> articles){
//        articles.forEach(article -> {
//            News news = new News();
//            news.setAuthor(article.getAuthor());
//            news.setTitle(article.getTitle());
//            news.setDescription(article.getDescription());
//            news.setContent(article.getContent());
//            news.setUrl(article.getUrl());
//            newsRepository.save(news);
//        });
        for (Article article : articles) {
            if (article.getTitle() == null || article.getContent() == null || article.getTitle().contains("Removed") || article.getContent().contains("Removed")){
                continue;
            }
            News news = new News();
            news.setAuthor(article.getAuthor());
            news.setTitle(article.getTitle());
            news.setDescription(article.getDescription());
            news.setContent(article.getContent());
            news.setUrl(article.getUrl());
            newsRepository.save(news);
        }
    }


    private void readCsv(){
        InputStream inputStream = getClass().getResourceAsStream("/csv/uscities.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
