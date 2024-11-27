INSERT INTO users (username, password) VALUES
('ksm008','1234'),
('ksm123','1234');

INSERT INTO article (user_Id, content, location, date_Posted) VALUES
('1', '2023년 월디페에 갔었던 날이다.','서울랜드|127.01870212137163|37.43390283191043','2024-11-19'),
('2', '딱히 찍은 사진은 없다.',null,'2024-11-19'),
('1', '아침에 찍은 사진','래미안블레스티지아파트|127.06397918908269|37.481287460486016','2024-11-27');

INSERT INTO media (article_id, upload_time, file_type, file_url) VALUES
('1','2024-11-26 11:41:04.95','image/jpeg','/uploads/ksm008/1.jpg'),
('3','2024-11-26 11:41:04.95','image/jpeg','/uploads/ksm008/2.jpg');