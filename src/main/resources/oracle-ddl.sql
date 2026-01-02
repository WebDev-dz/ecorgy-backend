
    create sequence location_seq start with 1 increment by 1;

    create sequence product_item_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence task_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table admin_permissions (
        admin_id bigint not null,
        permission varchar(255)
    );

    create table location (
        latitude float(53) not null,
        longitude float(53) not null,
        id bigint not null,
        city varchar(255) not null,
        country varchar(255) not null,
        street varchar(255) not null,
        zip_code varchar(255) not null,
        primary key (id)
    );

    create table product (
        id bigint not null,
        seller_id bigint not null,
        description CLOB,
        name varchar(255) not null,
        serial_number varchar(255) not null unique,
        primary key (id)
    );

    create table product_item (
        price float(53) not null,
        id bigint not null,
        product_id bigint not null,
        seller_id bigint not null unique,
        primary key (id)
    );

    create table task (
        achieved_at timestamp(6),
        client_id bigint not null,
        confirmed_at timestamp(6),
        created_at timestamp(6) not null,
        id bigint not null,
        location_id bigint not null unique,
        product_id bigint not null,
        worker_id bigint not null,
        description CLOB not null,
        status varchar(255) not null,
        primary key (id)
    );

    create table users (
        birth_date date,
        phone_number integer,
        id bigint not null,
        location_id bigint unique,
        user_type varchar(31) not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists admin_permissions 
       add constraint FK31vh5bv0kynmm7alfuefa3mlt 
       foreign key (admin_id) 
       references users;

    alter table if exists product 
       add constraint FKnuvtfgcf3ohskgoyi6v1eh1jr 
       foreign key (seller_id) 
       references users;

    alter table if exists product_item 
       add constraint FKa9mjpi98ark8eovbtnnreygbb 
       foreign key (product_id) 
       references product;

    alter table if exists product_item 
       add constraint FK7lcsnss1f96tf908hemtcbi2r 
       foreign key (seller_id) 
       references users;

    alter table if exists task 
       add constraint FKfajcgals1atfo8abj18378id4 
       foreign key (client_id) 
       references users;

    alter table if exists task 
       add constraint FK3g10rvjt2p0aswg0c3ihct9fc 
       foreign key (location_id) 
       references location;

    alter table if exists task 
       add constraint FKnx6gbgoxm5v9n0a15n7vetp0f 
       foreign key (product_id) 
       references product;

    alter table if exists task 
       add constraint FKqn6o460g63qdfbf7d3oqrfaq6 
       foreign key (worker_id) 
       references users;

    alter table if exists users 
       add constraint FKscan6s3oplch90k26jpvied7b 
       foreign key (location_id) 
       references location;

    create sequence location_seq start with 1 increment by 1;

    create sequence product_item_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence task_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table admin_permissions (
        admin_id bigint not null,
        permission varchar(255)
    );

    create table location (
        latitude float(53) not null,
        longitude float(53) not null,
        id bigint not null,
        city varchar(255) not null,
        country varchar(255) not null,
        street varchar(255) not null,
        zip_code varchar(255) not null,
        primary key (id)
    );

    create table product (
        id bigint not null,
        seller_id bigint not null,
        description CLOB,
        name varchar(255) not null,
        serial_number varchar(255) not null unique,
        primary key (id)
    );

    create table product_item (
        price float(53) not null,
        id bigint not null,
        product_id bigint not null,
        seller_id bigint not null unique,
        primary key (id)
    );

    create table task (
        achieved_at timestamp(6),
        client_id bigint not null,
        confirmed_at timestamp(6),
        created_at timestamp(6) not null,
        id bigint not null,
        location_id bigint not null unique,
        product_id bigint not null,
        worker_id bigint not null,
        description CLOB not null,
        status varchar(255) not null,
        primary key (id)
    );

    create table users (
        birth_date date,
        phone_number integer,
        id bigint not null,
        location_id bigint unique,
        user_type varchar(31) not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists admin_permissions 
       add constraint FK31vh5bv0kynmm7alfuefa3mlt 
       foreign key (admin_id) 
       references users;

    alter table if exists product 
       add constraint FKnuvtfgcf3ohskgoyi6v1eh1jr 
       foreign key (seller_id) 
       references users;

    alter table if exists product_item 
       add constraint FKa9mjpi98ark8eovbtnnreygbb 
       foreign key (product_id) 
       references product;

    alter table if exists product_item 
       add constraint FK7lcsnss1f96tf908hemtcbi2r 
       foreign key (seller_id) 
       references users;

    alter table if exists task 
       add constraint FKfajcgals1atfo8abj18378id4 
       foreign key (client_id) 
       references users;

    alter table if exists task 
       add constraint FK3g10rvjt2p0aswg0c3ihct9fc 
       foreign key (location_id) 
       references location;

    alter table if exists task 
       add constraint FKnx6gbgoxm5v9n0a15n7vetp0f 
       foreign key (product_id) 
       references product;

    alter table if exists task 
       add constraint FKqn6o460g63qdfbf7d3oqrfaq6 
       foreign key (worker_id) 
       references users;

    alter table if exists users 
       add constraint FKscan6s3oplch90k26jpvied7b 
       foreign key (location_id) 
       references location;

    create sequence location_seq start with 1 increment by 1;

    create sequence product_item_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence task_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table admin_permissions (
        admin_id bigint not null,
        permission varchar(255)
    );

    create table location (
        latitude float(53) not null,
        longitude float(53) not null,
        id bigint not null,
        city varchar(255) not null,
        country varchar(255) not null,
        street varchar(255) not null,
        zip_code varchar(255) not null,
        primary key (id)
    );

    create table product (
        id bigint not null,
        seller_id bigint not null,
        description CLOB,
        name varchar(255) not null,
        serial_number varchar(255) not null unique,
        primary key (id)
    );

    create table product_item (
        price float(53) not null,
        id bigint not null,
        product_id bigint not null,
        seller_id bigint not null,
        primary key (id)
    );

    create table task (
        achieved_at timestamp(6),
        client_id bigint not null,
        confirmed_at timestamp(6),
        created_at timestamp(6) not null,
        id bigint not null,
        location_id bigint not null unique,
        product_id bigint not null,
        worker_id bigint not null,
        description CLOB not null,
        status varchar(255) not null,
        primary key (id)
    );

    create table users (
        birth_date date,
        phone_number integer,
        id bigint not null,
        location_id bigint unique,
        user_type varchar(31) not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists admin_permissions 
       add constraint FK31vh5bv0kynmm7alfuefa3mlt 
       foreign key (admin_id) 
       references users;

    alter table if exists product 
       add constraint FKnuvtfgcf3ohskgoyi6v1eh1jr 
       foreign key (seller_id) 
       references users;

    alter table if exists product_item 
       add constraint FKa9mjpi98ark8eovbtnnreygbb 
       foreign key (product_id) 
       references product;

    alter table if exists product_item 
       add constraint FK7lcsnss1f96tf908hemtcbi2r 
       foreign key (seller_id) 
       references users;

    alter table if exists task 
       add constraint FKfajcgals1atfo8abj18378id4 
       foreign key (client_id) 
       references users;

    alter table if exists task 
       add constraint FK3g10rvjt2p0aswg0c3ihct9fc 
       foreign key (location_id) 
       references location;

    alter table if exists task 
       add constraint FKnx6gbgoxm5v9n0a15n7vetp0f 
       foreign key (product_id) 
       references product;

    alter table if exists task 
       add constraint FKqn6o460g63qdfbf7d3oqrfaq6 
       foreign key (worker_id) 
       references users;

    alter table if exists users 
       add constraint FKscan6s3oplch90k26jpvied7b 
       foreign key (location_id) 
       references location;

    create sequence location_seq start with 1 increment by 1;

    create sequence product_item_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence task_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table admin_permissions (
        admin_id bigint not null,
        permission varchar(255)
    );

    create table location (
        latitude float(53) not null,
        longitude float(53) not null,
        id bigint not null,
        city varchar(255) not null,
        country varchar(255) not null,
        street varchar(255) not null,
        zip_code varchar(255) not null,
        primary key (id)
    );

    create table product (
        id bigint not null,
        seller_id bigint not null,
        description CLOB,
        name varchar(255) not null,
        serial_number varchar(255) not null unique,
        primary key (id)
    );

    create table product_item (
        price float(53) not null,
        id bigint not null,
        product_id bigint not null,
        seller_id bigint not null,
        primary key (id)
    );

    create table task (
        achieved_at timestamp(6),
        client_id bigint not null,
        confirmed_at timestamp(6),
        created_at timestamp(6) not null,
        id bigint not null,
        location_id bigint not null unique,
        product_id bigint not null,
        worker_id bigint not null,
        description CLOB not null,
        status varchar(255) not null,
        primary key (id)
    );

    create table users (
        birth_date date,
        phone_number integer,
        id bigint not null,
        location_id bigint unique,
        user_type varchar(31) not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists admin_permissions 
       add constraint FK31vh5bv0kynmm7alfuefa3mlt 
       foreign key (admin_id) 
       references users;

    alter table if exists product 
       add constraint FKnuvtfgcf3ohskgoyi6v1eh1jr 
       foreign key (seller_id) 
       references users;

    alter table if exists product_item 
       add constraint FKa9mjpi98ark8eovbtnnreygbb 
       foreign key (product_id) 
       references product;

    alter table if exists product_item 
       add constraint FK7lcsnss1f96tf908hemtcbi2r 
       foreign key (seller_id) 
       references users;

    alter table if exists task 
       add constraint FKfajcgals1atfo8abj18378id4 
       foreign key (client_id) 
       references users;

    alter table if exists task 
       add constraint FK3g10rvjt2p0aswg0c3ihct9fc 
       foreign key (location_id) 
       references location;

    alter table if exists task 
       add constraint FKnx6gbgoxm5v9n0a15n7vetp0f 
       foreign key (product_id) 
       references product;

    alter table if exists task 
       add constraint FKqn6o460g63qdfbf7d3oqrfaq6 
       foreign key (worker_id) 
       references users;

    alter table if exists users 
       add constraint FKscan6s3oplch90k26jpvied7b 
       foreign key (location_id) 
       references location;

    create sequence location_seq start with 1 increment by 1;

    create sequence product_item_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence task_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table admin_permissions (
        admin_id bigint not null,
        permission varchar(255)
    );

    create table location (
        latitude float(53) not null,
        longitude float(53) not null,
        id bigint not null,
        city varchar(255) not null,
        country varchar(255) not null,
        street varchar(255) not null,
        zip_code varchar(255) not null,
        primary key (id)
    );

    create table product (
        id bigint not null,
        seller_id bigint not null,
        description CLOB,
        name varchar(255) not null,
        serial_number varchar(255) not null unique,
        primary key (id)
    );

    create table product_item (
        price float(53) not null,
        id bigint not null,
        product_id bigint not null,
        seller_id bigint not null,
        primary key (id)
    );

    create table task (
        achieved_at timestamp(6),
        client_id bigint not null,
        confirmed_at timestamp(6),
        created_at timestamp(6) not null,
        id bigint not null,
        location_id bigint not null unique,
        product_id bigint not null,
        worker_id bigint not null,
        description CLOB not null,
        status varchar(255) not null,
        primary key (id)
    );

    create table users (
        birth_date date,
        phone_number integer,
        id bigint not null,
        location_id bigint unique,
        user_type varchar(31) not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists admin_permissions 
       add constraint FK31vh5bv0kynmm7alfuefa3mlt 
       foreign key (admin_id) 
       references users;

    alter table if exists product 
       add constraint FKnuvtfgcf3ohskgoyi6v1eh1jr 
       foreign key (seller_id) 
       references users;

    alter table if exists product_item 
       add constraint FKa9mjpi98ark8eovbtnnreygbb 
       foreign key (product_id) 
       references product;

    alter table if exists product_item 
       add constraint FK7lcsnss1f96tf908hemtcbi2r 
       foreign key (seller_id) 
       references users;

    alter table if exists task 
       add constraint FKfajcgals1atfo8abj18378id4 
       foreign key (client_id) 
       references users;

    alter table if exists task 
       add constraint FK3g10rvjt2p0aswg0c3ihct9fc 
       foreign key (location_id) 
       references location;

    alter table if exists task 
       add constraint FKnx6gbgoxm5v9n0a15n7vetp0f 
       foreign key (product_id) 
       references product;

    alter table if exists task 
       add constraint FKqn6o460g63qdfbf7d3oqrfaq6 
       foreign key (worker_id) 
       references users;

    alter table if exists users 
       add constraint FKscan6s3oplch90k26jpvied7b 
       foreign key (location_id) 
       references location;

    create sequence location_seq start with 1 increment by 1;

    create sequence product_item_seq start with 1 increment by 1;

    create sequence product_seq start with 1 increment by 1;

    create sequence task_seq start with 1 increment by 1;

    create sequence user_seq start with 1 increment by 1;

    create table admin_permissions (
        admin_id bigint not null,
        permission varchar(255)
    );

    create table location (
        latitude float(53) not null,
        longitude float(53) not null,
        id bigint not null,
        city varchar(255) not null,
        country varchar(255) not null,
        street varchar(255) not null,
        zip_code varchar(255) not null,
        primary key (id)
    );

    create table product (
        id bigint not null,
        seller_id bigint not null,
        description CLOB,
        name varchar(255) not null,
        serial_number varchar(255) not null unique,
        primary key (id)
    );

    create table product_item (
        price float(53) not null,
        id bigint not null,
        product_id bigint not null,
        seller_id bigint not null,
        primary key (id)
    );

    create table task (
        achieved_at timestamp(6),
        client_id bigint not null,
        confirmed_at timestamp(6),
        created_at timestamp(6) not null,
        id bigint not null,
        location_id bigint not null unique,
        product_id bigint not null,
        worker_id bigint not null,
        description CLOB not null,
        status varchar(255) not null,
        primary key (id)
    );

    create table users (
        birth_date date,
        phone_number integer,
        id bigint not null,
        location_id bigint unique,
        user_type varchar(31) not null,
        email varchar(255) not null unique,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists admin_permissions 
       add constraint FK31vh5bv0kynmm7alfuefa3mlt 
       foreign key (admin_id) 
       references users;

    alter table if exists product 
       add constraint FKnuvtfgcf3ohskgoyi6v1eh1jr 
       foreign key (seller_id) 
       references users;

    alter table if exists product_item 
       add constraint FKa9mjpi98ark8eovbtnnreygbb 
       foreign key (product_id) 
       references product;

    alter table if exists product_item 
       add constraint FK7lcsnss1f96tf908hemtcbi2r 
       foreign key (seller_id) 
       references users;

    alter table if exists task 
       add constraint FKfajcgals1atfo8abj18378id4 
       foreign key (client_id) 
       references users;

    alter table if exists task 
       add constraint FK3g10rvjt2p0aswg0c3ihct9fc 
       foreign key (location_id) 
       references location;

    alter table if exists task 
       add constraint FKnx6gbgoxm5v9n0a15n7vetp0f 
       foreign key (product_id) 
       references product;

    alter table if exists task 
       add constraint FKqn6o460g63qdfbf7d3oqrfaq6 
       foreign key (worker_id) 
       references users;

    alter table if exists users 
       add constraint FKscan6s3oplch90k26jpvied7b 
       foreign key (location_id) 
       references location;
