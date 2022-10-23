create table orders
(
    id        uuid primary key,
    client_id uuid         not null,
    status    varchar2(10) not null
);

create table products
(
    id         uuid primary key,
    product_id uuid,
    order_id   uuid,
    quantity   number not null,
    unique (product_id, order_id)
);

create table order_operations
(
    id             uuid          not null,
    order_id       uuid          not null,
    operation_type varchar2(20)  not null,
    change_set     varchar2(200) not null,
    primary key (id, order_id),
    foreign key (order_id) references orders (id)
);
