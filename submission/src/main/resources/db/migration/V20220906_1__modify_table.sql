DROP TABLE IF EXISTS `problem`;
create table problem
(
    id              bigint auto_increment
        primary key,
    is_del          tinyint default 0 null comment '软删除',
    create_time     datetime          null,
    update_time     datetime          null,
    show_id         varchar(255)      null comment '显示的id',
    problem         longtext          null comment '问题主干',
    output_describe text              null comment '输出描述',
    input_describe  text              null comment '输入描述',
    sample       json              null comment '输入输出样例',
    tag             varchar(255)      null comment '标签',
    visible         tinyint           null comment '是否可见',
    difficulty      varchar(255)      null comment '题目难度',
    data_range      varchar(255)      null comment '数据范围',
    memory_limit    int               null comment '时空限制',
    time_limit      int               null comment '时间限制',
    problem_name    varchar(100)      null comment '题目名字'
);
DROP TABLE IF EXISTS `result`;
create table result
(
    id                 bigint       not null
        primary key,
    is_del             tinyint      null comment '软删除',
    create_time        timestamp    null comment '创建时间',
    result_of_case     json         null comment '评测机评测结果',
    related_user       bigint       null comment '谁提交了该份代码',
    related_problem    bigint       null comment '与哪个问题相关联',
    related_submission bigint       null comment '与哪一次提交相关联',
    case_id            int unsigned null comment '当前测试样例在是在问题的io文件中的序号'
);
DROP TABLE IF EXISTS `submission`;
create table submission
(
    id              bigint auto_increment
        primary key,
    is_del          int          null,
    create_time     timestamp    null,
    code            text         not null comment '提交的代码',
    status          varchar(50)  null comment '提交状态 failed or AC or RE or 内部错误',
    language        varchar(10)  null comment '代码的类别',
    error           text         null comment '如果为RE or 内部错误, 错误信息',
    related_user    varchar(255) null comment '谁提交了该份代码',
    related_problem bigint       null comment '与哪个问题相关联'
);



