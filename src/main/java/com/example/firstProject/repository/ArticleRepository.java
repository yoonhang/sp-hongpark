package com.example.firstProject.repository;

import com.example.firstProject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> { //CRUD 를 제공해주는 Repository 를 상속받음

    @Override
    ArrayList<Article> findAll();
}
