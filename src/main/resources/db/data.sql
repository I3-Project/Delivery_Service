create table p_cart (
                        id bigint not null auto_increment,
                        quantity integer not null,
                        product_id bigint not null,
                        store_id bigint not null,
                        user_id bigint not null,
                        primary key (id),
                        constraint FK_cart_product foreign key (product_id) references p_products (id),
                        constraint FK_cart_store foreign key (store_id) references p_stores (id),
                        constraint FK_cart_user foreign key (user_id) references p_users (id)
)

create table p_categories (
                              id bigint not null auto_increment,
                              description varchar(255),
                              category_name varchar(255) not null,
                              uuid binary(16),
                              primary key (id)
)

create table p_order (
                         id bigint not null auto_increment,
                         create_at datetime(6),
                         create_by varchar(255),
                         update_at datetime(6),
                         update_by varchar(255),
                         address varchar(50),
                         deleted_at datetime(6),
                         deleted_by varchar(255),
                         order_request varchar(60),
                         order_status enum ('CANCELED','COMPLETED','DELETED','PENDING') not null,
                         order_type enum ('OFFLINE','ONLINE') not null,
                         product_name varchar(255),
                         total_price decimal(38,2),
                         uuid binary(16) not null,
                         store_id bigint not null,
                         user_id bigint not null,
                         primary key (id),
                         constraint FK_order_store foreign key (store_id) references p_stores (id),
                         constraint FK_order_user foreign key (user_id) references p_users (id)
)

create table p_payment (
                           id bigint not null auto_increment,
                           create_at datetime(6),
                           create_by varchar(255),
                           update_at datetime(6),
                           update_by varchar(255),
                           deleted_at datetime(6),
                           deleted_by varchar(255),
                           payment_status enum ('CANCELED','COMPLETED','DELETED','PENDING') not null,
                           paymentuid binary(16),
                           pg_id varchar(255) not null,
                           total_price decimal(38,2),
                           order_id bigint not null,
                           user_id bigint not null,
                           primary key (id),
                           constraint FK_payment_order foreign key (order_id) references p_order (id),
                           constraint FK_payment_user foreign key (user_id) references p_users (id)
)

create table p_products (
                            id bigint not null auto_increment,
                            create_at datetime(6),
                            create_by varchar(255),
                            update_at datetime(6),
                            update_by varchar(255),
                            deleted_at datetime(6),
                            deleted_by varchar(255),
                            description varchar(255),
                            name varchar(255),
                            price decimal(38,2),
                            status enum ('DELETED','EXIST'),
                            stock integer,
                            uuid binary(16),
                            store_id bigint not null,
                            user_id bigint not null,
                            primary key (id),
                            constraint FK_product_store foreign key (store_id) references p_stores (id),
                            constraint FK_product_user foreign key (user_id) references p_users (id)
)

create table p_review (
                          id bigint not null auto_increment,
                          create_at datetime(6),
                          create_by varchar(255),
                          update_at datetime(6),
                          update_by varchar(255),
                          content varchar(100) not null,
                          deleted_at datetime(6),
                          deleted_by varchar(255),
                          rating integer not null,
                          review_status enum ('DELETED','FIXED','UPLOADED') not null,
                          uuid binary(16),
                          order_id bigint,
                          store_id bigint not null,
                          user_id bigint,
                          primary key (id),
                          constraint FK_review_order foreign key (order_id) references p_order (id),
                          constraint FK_review_store foreign key (store_id) references p_stores (id),
                          constraint FK_review_user foreign key (user_id) references p_users (id)
)

create table p_stores (
                          id bigint not null auto_increment,
                          create_at datetime(6),
                          create_by varchar(255),
                          update_at datetime(6),
                          update_by varchar(255),
                          address varchar(50),
                          deleted_at datetime(6),
                          deleted_by varchar(255),
                          description varchar(500) not null,
                          name varchar(20) not null,
                          phone_number varchar(20),
                          rating_avg integer not null check ((rating_avg <= 5) and (rating_avg >= -1)),
                          status enum ('CLOSE','DAYOFF','DELETED','OPEN'),
                          total_reviews integer not null,
                          uuid binary(16),
                          category_id bigint not null,
                          owner_id bigint not null,
                          primary key (id),
                          constraint FK_store_category foreign key (category_id) references p_categories (id),
                          constraint FK_store_owner foreign key (owner_id) references p_users (id)
)

create table p_users (
                         id bigint not null auto_increment,
                         create_at datetime(6),
                         create_by varchar(255),
                         update_at datetime(6),
                         update_by varchar(255),
                         address varchar(255),
                         deleted_at datetime(6),
                         deleted_by varchar(255),
                         email varchar(255),
                         is_deleted bit,
                         nickname varchar(255),
                         password varchar(255),
                         phone varchar(255),
                         role enum ('MANAGER','MASTER','OWNER','USER') not null,
                         username varchar(255),
                         primary key (id),
                         constraint UK_users_email unique (email),
                         constraint UK_users_nickname unique (nickname),
                         constraint UK_users_phone unique (phone),
                         constraint UK_users_username unique (username)
)
