package com.example.firstProject.service;

import com.example.firstProject.dto.ArticleForm;
import com.example.firstProject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링 부트와 연동되어 테스팅된다!
class ArticleServiceTest {

    @Autowired ArticleService articleService;

    @Test
    void index() {
        // 예상 시나리오
        Article a = new Article(1L,"가가가가","1111");
        Article b = new Article(2L,"나나나나","2222");
        Article c = new Article(3L,"다다다다","3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); // a,b,c 를 리스트화시킴

        // 실제 결과
        List<Article> articles= articleService.index();

        // 예상과 실제 비교 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        // 예상
         Long id = 1L;
         Article expected = new Article(id, "가가가가","1111");

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패_존재하지않는_id_입력() {
        // 예상
        Long id = -1L;
        Article expected = null; // articleRepository.findById(id).orElse(null); 이여서 null 임

        // 실제
        Article article = articleService.show(id);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional // create 되면 id4 데이터가 생기기 때문에 다른 test 메소드에서 검증 오류가 발생할 수 있다. 그래서 롤백으로 초기화시켜준다.
    void create_성공_Title과_Content_만_있는_dto_입력() { // Title과_Content_만_있는_dto_입력한 경우 create 에 성공
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null,title,content); // 위의 title, content 를 통해서 dto 가 만들어져야 한다.
        Article expected = new Article(4L,title,content); // 위에서 만든 dto 를 service 에 집어넣었을 때 4번 데이터가 튀어나오겠죠

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() { // 이미 있는 id 인 경우는 create 실패함
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L,title,content); // 위의 title, content 를 통해서 dto 가 만들어져야 한다.
        Article expected = null; //  if (article.getId() != null){ return null; } 이니까

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }


//    @Test
//    void update_성공_id_title_content_가_있는_dto_입력() {
//    }
//    @Test
//    void update_성공_id_title_이_있는_dto_입력() {
//    }
//    @Test
//    void update_실패_없는_id_의_dto_입력() {
//    }
//    @Test
//    void update_실패_id_만_있는_dto_입력() {
//    }
//
//    @Test
//    void delete_성공_존재하는_id_입력() {
//    }
//    @Test
//    void delete_실패_존재하지않는_id_입력() {
//    }
}