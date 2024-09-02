package com.example.firstProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id // id 삽입!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 id 넘버링 + db가 id를 자동으로 생성해줌
    private Long id;

    @ManyToOne // 여러 개의 코멘트가 하나의 게시글에 달린다. (댓글 엔티티 여러 개가 하나의 Article 에 연관됨)
    @JoinColumn(name = "article_id") // 테이블에서 연결된 대상의 정보! 그니까 외래키의 이름
    private Article article;

    @Column // 이 어노테이션이 있어야 DB 에서 컬럼으로 인식 가능
    private String nickname;

    @Column
    private String body;

}
