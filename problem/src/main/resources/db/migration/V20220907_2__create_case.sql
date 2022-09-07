create table onlinejudge.testcase
(
    id              bigint primary key auto_increment,
    create_time     timestamp       null,
    update_time     timestamp        null,
    is_del          tinyint default 0 null,
    `std_in`            longtext   null,
    `std_out`           longtext   null,
    related_problem int        null
);

