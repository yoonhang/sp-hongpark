INSERT INTO article(id, title, content) VALUES (1,'가가가가','1111');
INSERT INTO article(id, title, content) VALUES (2,'나나나나','2222');
INSERT INTO article(id, title, content) VALUES (3,'다다다다','3333');

-- article 더미 데이터
INSERT INTO article(id, title, content) VALUES (4,'당신의 인생 영화는?','댓글1');
INSERT INTO article(id, title, content) VALUES (5,'당신의 소울 푸드는?','댓글2');
INSERT INTO article(id, title, content) VALUES (6,'당신의 취미는?','댓글3');

-- comment 더미 데이터 (4번 게시글 댓글)
INSERT INTO comment(id, article_id, nickname, body) VALUES (1, 4,'Park','영화1');
INSERT INTO comment(id, article_id, nickname, body) VALUES (2, 4,'Kim','영화2');
INSERT INTO comment(id, article_id, nickname, body) VALUES (3, 4,'Choi','영화3');

-- comment 더미 데이터 (5번 게시글 댓글)
INSERT INTO comment(id, article_id, nickname, body) VALUES (4, 5,'Park','음식1');
INSERT INTO comment(id, article_id, nickname, body) VALUES (5, 5,'Kim','음식2');
INSERT INTO comment(id, article_id, nickname, body) VALUES (6, 5,'Choi','음식3');

-- comment 더미 데이터 (6번 게시글 댓글)
INSERT INTO comment(id, article_id, nickname, body) VALUES (7, 6,'Park','취미1');
INSERT INTO comment(id, article_id, nickname, body) VALUES (8, 6,'Kim','취미2');
INSERT INTO comment(id, article_id, nickname, body) VALUES (9, 6,'Choi','취미3');