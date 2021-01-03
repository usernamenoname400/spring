drop table if exists books;

create table books (
    id int auto_increment primary key,
    author varchar(250) not null,
    title varchar(250) not null,
    size int default null
);

