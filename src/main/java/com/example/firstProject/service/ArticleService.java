package com.example.firstProject.service;

import com.example.firstProject.dto.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // log 찍을 수 있게
@Service // 서비스 선언! 요리사임 ( 서비스 객체를 스프링부트에 생성)
public class ArticleService {

    @Autowired // springboot 가 미리 생성해놓은 객체를 가져다가 자동 연결해줌 DI(Dependency Injection)
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        // 만약에 유저가 데이터를 새로 생성할 때! id 값이 이미 있는걸로 입력하면은! 안된다고 하셈
        if (article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }


    public Article update(Long id, ArticleForm dto) {

        // 1. 수정용 entity 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id,article.toString());

        // 2. 대상 entity 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리 (응답은 안해줘도됨. 요리사는 요리만 하라구!)
        if (target ==null || id != article.getId()){
            // 잘못된 요청 응답
            log.info("잘못된 요청! id: {}, article: {}",id,article.toString());
            return null;
        }

        // 4. 업데이트 (응답은 안해줘도 됨. 요리사는 요리만 하라구!)
        target.patch(article); // 수정데이터를 입력하지 않은 항목이 null 로 설정되는 것을 막음 (기존에 있는 target 에 새로운 patch(article)을 붙인다.)
        Article updated = articleRepository.save(target);
        return updated;

    }

    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if ( target == null) {
            return null;
        }
        // 대상 삭제 후 데이터 반환
        articleRepository.delete(target);
        return target;
    }

    @Transactional // 해당 메소드를 트랜젝션으로 -> 이러면 롤백이 가능하다
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 믂음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect((Collectors.toList()));

        // 위의 코드는 stream 문법이구, 아래는 똑같은걸 for 문으로 적은거
//        List<Article> articleList = new ArrayList<>();
//        for (int i = 0; i < dtos.size(); i++) {
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }

        // entity 묶음을 db 로 변환
        articleList.stream()
                .forEach(article-> articleRepository.save(article));

        // 위의 코드는 stream 문법이구, 아래는 똑같은걸 for 문으로 적은거
//        for (int i = 0; i < articleList.size(); i++) {
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }


        // 롤백을 위해 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결재 실패!")
        );

        // 결과값 반환
        return articleList;
    }
}