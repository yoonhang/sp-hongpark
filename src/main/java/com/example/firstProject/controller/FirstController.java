package com.example.firstProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// 일반 컨트롤러는 GET 요청 시 뷰페이지의 html 코드를 반환한다.
// REST 컨드롤러는 GET 요청 시 JSON, DATA 를 반환한다.

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("userName", "minmi");
        return "greeting"; // templetes/greeting.mustache 파일을 찾아서 브라우저로 전송해줌
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickName","minmi");
        return "goodbye";
    }
}
