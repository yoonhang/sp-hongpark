package com.example.firstProject.controller;

import com.example.firstProject.dto.ArticleForm;
import com.example.firstProject.entity.Article;
import com.example.firstProject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller // 얘는 일반 controller, 뷰 템플릿을 반환함
@Slf4j // 로깅을 위한 어노테이션 (log 찍을 수 있게)
public class ArticleController {

    @Autowired // 이 어노테이션은 springboot 가 미리 생성해놓은 객체를 가져다가 자동 연결해줌
    private ArticleRepository articleReopsitory; // = newArticleRepo~~~를 안해도 됨. springboot 가 알아서해줌

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { //  post 방식으로 던져지는 데이터(DTO)를 파라미터로 받음
//        System.out.println(form.toString()); --> 로깅 기능으로 대체
        log.info(form.toString());

        // 이제 dto로 받아온 form 데이터를 db 로 잘 넣어줄거이다.
        // 1. form 데이터(DTO) 를 Entity 로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository 에게 Entity 를 DB 안에 넣으라고 시킴.
        Article saved = articleReopsitory.save(article);
        log.info(saved.toString());

        // 데이터를 전송받았을 때 데이터 목록을 볼 수 있게 return 값에 redirect 해줌
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){ // 이 골뱅이는 id 값의 경로를 인식시키기 위해 있는거임
        log.info("id = " + id);

        // 1. id 를 이용해서 데이터를 불러와라
//        Optional<Article> articleEntity = articleReopsitory.findById(id); // 제레닉! 선택적 제네릭 ㄱㄱ
        Article articleEntity = articleReopsitory.findById(id).orElse(null); // 자바8 버전보다 밑인 사람들을 위해서

       // 2. 가져온 데이터를 모델에 등록하기
        model.addAttribute("article",articleEntity);

        // 3. 보여줄 뷰 페이지를 설정하기
        return "articles/show";
    }


    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 article을 가져온다.


        // 1-1. 타입캐스팅하기(iterator<-list)
//        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
        // 1-2. 반환하는 타입으로 그냥 적어주기 iterator
//        Iterable<Article> articleEntityList = articleRepository.findAll();
        // 1-3. ArticleRepository 에서 ArrayList 를 메소드오버라이딩해서 List 로 업케스팅하기
        List<Article> articleEntityList = articleReopsitory.findAll();


        // 2. 가져온 article 묶음을 모델에 등록하기 (뷰로 전달)
        model.addAttribute("articleList",articleEntityList);

        // 3. 보여줄 뷰 페이지를 설정하기
        return"articles/index";
    }


    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터 가져오기
        Article articleEntity = articleReopsitory.findById(id).orElse(null);

        // model 에 데이터 등록하기 (뷰로 전달)
        model.addAttribute("article",articleEntity);

        // 뷰 페이지 설정
       return "articles/edit";
    }

// PATCH
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        // 1. 찍어보기 (검증)
        log.info(form.toString());

        // 2. DTO -> Entity 로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 3. Entity -> DB 에 저장
        //3-1 DB에서 기존 데이터를 가져온다. (articleEntity 의 id 값을 찾아서 기존 데이터의 id 값을 찾을려고)
//        Optional<Article> target = articleReopsitory.findById(articleEntity.getId());
        Article target = articleReopsitory.findById(articleEntity.getId()).orElse(null);

        //3-2 기존데이터의 값을 수정한다. (찾은 id 값을 기준으로)
        if (target !=null){
            Article saved = articleReopsitory.save(articleEntity); // Entity 가 DB 로 갱신됨됨
            log.info(saved.toString());
        }

        // 4. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

// DELETE
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){ // RedirectAttributes 는 메세지 찍어줌
        log.info("삭제요청이 들어왔습니다.");

        // 1. 삭제 대상을 가져온다.
        Article target = articleReopsitory.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 그 대상을 삭제한다.
        if (target != null){
            articleReopsitory.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다."); // Flash : 휘발성임! 한번만 쓰고 사라짐
        }

        // 3. 결과 페이지로 리다이렉트한다.
        return "redirect:/articles";
    }
}
