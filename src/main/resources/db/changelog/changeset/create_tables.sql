create table "customer" (
  id bigserial primary key,
  customer_str_id varchar(50),
  name varchar(50) not null,
  password varchar(50) not null,
  phone varchar(15),
  encryptedPassword varchar(255) not null
);

create table "products" (
  id bigserial primary key,
  sku varchar(50) not null,
  name varchar(255) not null,
  price numeric not null
);

create table "orders" (
  id bigserial primary key,
  customer_id bigint not null references customer(id),
  date timestamp without time zone not null,
  amount numeric not null
);

create table "order_products" (
  id bigserial primary key,
  order_id bigint references orders(id),
  product_id bigint references products(id)
);




