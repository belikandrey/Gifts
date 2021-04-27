

insert into tag(name) values ('#cool');
insert into tag(name) values ('#relax');
insert into tag(name) values ('#rich');
insert into tag(name) values ('#beautiful');
insert into tag(name) values ('#love');
insert into tag(name) values ('#cute');
insert into tag(name) values ('#friends');
insert into tag(name) values ('#summer');
insert into tag(name) values ('#fun');
insert into tag(name) values ('#smile');
insert into tag(name) values ('#family');

insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Relax.by', 'Certificate for one trip with family', 10000, 150, timestamp '2021-11-11 11:11',timestamp '2021-03-31 22:11');
insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Cars.by', 'Certificate for one car Kia Rio', 25000, 20, timestamp '2021-03-31 20:11', timestamp '2021-03-31 00:11');
insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Litres.com', 'Certificate for five random books', 100, 10, current_timestamp, current_timestamp);
insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Apple.com', 'Certificate for one Macbook pro', 2500, 7, timestamp '2021-04-01 11:11:35', timestamp '2021-04-01 11:11:35');
insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Jetbrains.com', 'Certificate for all products', 300, 30, timestamp '2021-04-01 21:11:35', timestamp '2021-04-01 21:11:35');
insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Xiaomipro.by', 'Certificate for one mobile phone', 300, 20, timestamp '2021-04-01 21:11:35', timestamp '2021-04-01 21:11:35');
insert into certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('BMW.com', 'Certificate for one car', 25000, 20, timestamp '2021-03-31 21:11:35', timestamp '2021-03-31 21:11:35');


insert into certificate_tag(certificate_id, tag_id) values(1, 3);
insert into certificate_tag(certificate_id, tag_id) values(1, 7);
insert into certificate_tag(certificate_id, tag_id) values(1,6);
insert into certificate_tag(certificate_id, tag_id) values(2,7);
insert into certificate_tag(certificate_id, tag_id) values(2,5);
insert into certificate_tag(certificate_id, tag_id) values(2,1);
insert into certificate_tag(certificate_id, tag_id) values(3,8);
insert into certificate_tag(certificate_id, tag_id) values(3,11);
insert into certificate_tag(certificate_id, tag_id) values(3,9);
insert into certificate_tag(certificate_id, tag_id) values(4,2);
insert into certificate_tag(certificate_id, tag_id) values(4,4);
insert into certificate_tag(certificate_id, tag_id) values(4,10);
insert into certificate_tag(certificate_id, tag_id) values(5,2);
insert into certificate_tag(certificate_id, tag_id) values(5,1);
insert into certificate_tag(certificate_id, tag_id) values(5,4);
insert into certificate_tag(certificate_id, tag_id) values(6,7);
insert into certificate_tag(certificate_id, tag_id) values(6,8);
insert into certificate_tag(certificate_id, tag_id) values(6,10);
insert into certificate_tag(certificate_id, tag_id) values(7,4);
insert into certificate_tag(certificate_id, tag_id) values(7,2);
insert into certificate_tag(certificate_id, tag_id) values(7,8);

/*
drop table user;
drop table user_order;

create table user
(
    id    int         not null primary key auto_increment,
    login varchar(35) not null
);

create table user_order
(
    id          int      not null primary key auto_increment,
    price       double   not null,
    create_date datetime not null default NOW(),
    user_id     int      not null,
    foreign key (user_id)
        references user (id)
);

create table order_certificate
(
    order_id       int not null,
    certificate_id int not null,
    foreign key (order_id)
        references user_order (id),
    foreign key (certificate_id)
        references certificate (id)
);


insert into user(login) values ('root'), ('andrey')

insert into user_order(price, user_id)
values (12.3, 1);

insert into user_order(price, user_id)
    value (27, 1);

insert into user_order(price, user_id)
values (25.3, 2);


insert into user_order(price, user_id)
values (211.3, 2);

insert into order_certificate(order_id, certificate_id)
values (1, 2);

insert into order_certificate(order_id, certificate_id)
values (1, 3);

insert into order_certificate(order_id, certificate_id)
values (2, 4);

insert into order_certificate(order_id, certificate_id)
values (4, 5);

insert into order_certificate(order_id, certificate_id)
values (5, 7);
*/


