package com.example.firstProject.repository;

import com.example.firstProject.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글 조회
    @Query(value =
            "SELECT *" +
            "FROM comment" +
            "WHERE article_id = :articleId",
            nativeQuery = true)
//    List<Comment> findByArticleId(Long articleId); // articleId를 입력했을 때 Comment(List 묶음인)를 반환했으면 좋겠구만!!
    List<Comment> finByArticleId(@Param("articleId") Long articleId);


    // 특정 닉네임의 모든 댓글 조회
//    List<Comment> findByNickname(String nickname); // nickname을 입력했을 때 Comment(List 묶음인)를 반환했으면 좋겠구만!!
    List<Comment> findByNickname(@Param("nickname") String nickname);

}
