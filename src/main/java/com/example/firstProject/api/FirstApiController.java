package com.example.firstProject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
// 일반 컨트롤러는 GET 요청 시 뷰페이지의 html 코드를 반환한다.
// REST 컨드롤러는 GET 요청 시 JSON, DATA 를 반환한다.

@RestController // 얘는 json 을 반환 (REST api 용 컨트롤러)
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello world";
    }

}
