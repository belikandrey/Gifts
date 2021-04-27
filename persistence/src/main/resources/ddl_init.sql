SET MODE MySQL;


create table tag(
                    id int not null primary key auto_increment,
                    name varchar(50) not null unique ,
                    index tag_name_ind(name)
);

create table certificate(
                            id int not null primary key auto_increment,
                            name varchar(50) not null ,
                            description varchar(150),
                            price double not null ,
                            duration int not null ,
                            create_date datetime not null ,
                            last_update_date datetime not null ,
                            index certificate_name_ind(name)
);


create table certificate_tag(
                                certificate_id int not null ,
                                tag_id int not null ,
                                foreign key (certificate_id)
                                    references certificate(id) on update cascade ,
                                foreign key (tag_id)
                                    references tag(id) on update cascade ,
                                primary key (certificate_id, tag_id)
);