--初期ユーザー
INSERT INTO users(username, email, password, bio, images) values
('田中太郎','taro@exam.com','taro123','ゲームが好きです','images/icon1.png');


--初期投稿
INSERT INTO posts(user_id, post) values
('1','最初に触ったゲームはマリ男64です');

--初期FF関係
INSERT INTO follows(following_id, followed_id) values
('1','3');