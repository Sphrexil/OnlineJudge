DROP TABLE IF EXISTS `result`;
create table onlinejudge.result
(
    id                   bigint    not null,
    result_of_submission text    null,
    related_user         long    null,
    related_problem      int     null,
    related_submission   int     null,
    is_del               tinyint null,
    create_time          int     null,
    primary key (`id`)
);

