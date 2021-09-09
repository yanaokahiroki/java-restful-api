create table if not exists products
(
    id bigint unsigned not null primary key auto_increment,
    title varchar(50) not null unique,
    body varchar(100) not null,
    price int unsigned not null,
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp
) engine = innodb char set = utf8mb4;
