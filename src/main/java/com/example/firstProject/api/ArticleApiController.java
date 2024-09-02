package com.example.firstProject.api;

import com.example.firstProject.dto.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import com.example.firstProject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // REST 컨드롤러는 GET 요청 시 JSON, DATA 를 반환한다.
@Slf4j // 로깅을 위한 어노테이션 (log 찍을 수 있게)
public class ArticleApiController {

    @Autowired // springboot 가 미리 생성해놓은 객체를 가져다가 자동 연결해줌 DI(Dependency Injection)
    private ArticleService articleService;


    // GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index(); // 이제 service 를 통해서 데이터를 받아오게 함
    }


    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }
//
//    // POST
//    @PostMapping("/api/articles")
//    public Article index(@RequestBody ArticleForm dto){ // @가 json 데이터를 받게 해줌. 안하면 인식을 못해서 null
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create (@RequestBody ArticleForm dto){ // @가 json 데이터를 받게 해줌. 안하면 인식을 못해서 null
        Article created = articleService.create(dto);
        return (created != null)
               ? ResponseEntity.status(HttpStatus.OK).body(created)
               : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }





//    // PATCH
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){ // @가 json 데이터를 받게 해줌. 안하면 인식을 못해서 null
//            // Article 데이터가 ResponseEntity 에 담겨져서 json 으로 보내진다.
//
//        // 1. 수정용 entity 생성
//        Article article = dto.toEntity();
//        log.info("id: {}, article: {}",id,article.toString());
//
//        // 2. 대상 entity 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리 (null, id 다를때) 400
//        if (target ==null || id != article.getId()){
//            // 잘못된 요청 응답
//            log.info("잘못된 요청! id: {}, article: {}",id,article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 4. 업데이트 및 정상 응답 200
//        target.patch(article); // 수정데이터를 입력하지 않은 항목이 null 로 설정되는 것을 막음 (기존에 있는 target 에 새로운 patch(article)을 붙인다.
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }
//

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        Article updated = articleService.update(id,dto);
        return (updated != null)
                ? ResponseEntity.status(HttpStatus.OK).body(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }



//    // DELETE
//    @DeleteMapping("/api/articles/{id}")
//    public  ResponseEntity<Article> delete(@PathVariable Long id){ // @가 json 데이터를 받게 해줌. 안하면 인식을 못해서 null
//
//        // 대상 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 잘못된 요청 처리
//        if ( target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 대상 삭제
//        articleRepository.delete(target);
//
//        // 데이터 반환
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

    @DeleteMapping("/api/articles/{id}")
    public  ResponseEntity<Article> delete(@PathVariable Long id){ // @가 json 데이터를 받게 해줌. 안하면 인식을 못해서 null

        // 요리는 요리사한테 시키고 웨이터는 주문만 받으셈
        Article deleted = articleService.delete(id);

        // 데이터 반환
        return (deleted !=null)
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 트랜젝션이 -> 실패한다면 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) { // @가 json 데이터를 받게 해줌. 안하면 인식을 못해서 null
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null)
                ? ResponseEntity.status(HttpStatus.OK).body(createdList)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
