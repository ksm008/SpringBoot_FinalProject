INSERT INTO users (username, password) VALUES
('ksm008','1234'),
('ksm123','1234'),
('ksm456','1234'),
('ksm789','1234'),
('ksm101','1234');

INSERT INTO article (user_Id, content, location, date_Posted) VALUES
('1', '내용1','장소 이름 1|127.123456789|37.123456789','2024-11-19'),
('2', '내용1','장소 이름 2|127.123456789|37.123456789','2024-11-19'),
('3', '내용1','장소 이름 3|127.123456789|37.123456789','2024-11-19'),
('4', '내용1','장소 이름 4|127.123456789|37.123456789','2024-11-19'),
('5', '내용1','장소 이름 5|127.123456789|37.123456789','2024-11-19');

INSERT INTO media (article_id, upload_time, file_type, file_url) VALUES
('1','2024-11-26 11:41:04.95','image/jpeg','/uploads/ksm008/1.jpg');