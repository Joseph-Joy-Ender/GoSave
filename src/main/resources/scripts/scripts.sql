SET FOREIGN_KEY_CHECKS = 0;
truncate table user;
truncate table wallet;
SET FOREIGN_KEY_CHECKS = 1;

insert into user(id, username, password, email, amount) values
(201,'joyender', 'password', 'joyender@gmail.com',1000 * 100);

insert into wallet(id, balance) values
(101,1000),
(103, 4030),
(104, 4040),
(105, 4050);


