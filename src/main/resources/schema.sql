-- 各種テーブル削除
DROP TABLE users CASCADE;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS follows;

-- ユーザテーブル
CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	username varchar(255) NOT NULL,
	email varchar(255) NOT NULL CONSTRAINT unique_email UNIQUE,
	password varchar(255) NOT NULL,
	bio varchar(400), 
	images varchar(255) NOT NULL DEFAULT 'images/icon1.png',
	created_at timestamp NOT NULL DEFAULT current_timestamp,
	updated_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

-- 投稿テーブル
CREATE TABLE posts(
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
	post varchar(400) NOT NULL,
	created_at timestamp NOT NULL DEFAULT current_timestamp,
	updated_at timestamp NOT NULL DEFAULT current_timestamp
	);

-- フォローテーブル
CREATE TABLE follows(
	id SERIAL PRIMARY KEY,
	following_id INTEGER NOT NULL,
	followed_id INTEGER NOT NULL,
	created_at timestamp NOT NULL DEFAULT current_timestamp,
	updated_at timestamp NOT NULL DEFAULT current_timestamp
);