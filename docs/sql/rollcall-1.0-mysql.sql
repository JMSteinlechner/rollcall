-- auto-generated definition
create table example_table
(
    id          int auto_increment
        primary key,
    column_name varchar(255)                          not null,
    created_at  timestamp default current_timestamp() null
);

