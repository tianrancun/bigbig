CREATE TABLE student (
	student_id int identity(1,1),
	student_name varchar(20),
	student_nbr varchar(10),
	class_nbr varchar(10),
	age varchar(2),
	email varchar(50)
);
insert into student values
('xiaoming','001','4','15','xiaoming@samsclub.com'),
('xiaozhang','002','4','15','xiaozhang@samsclub.com'),
('xiaohong','003','4','15','xiaohong@samsclub.com');

update student set class_nbr=4;