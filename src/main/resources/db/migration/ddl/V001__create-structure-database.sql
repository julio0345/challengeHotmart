/* Creation of structure database*/

create table buyer(
	id bigint not null auto_increment,
    name varchar(100) not null,
    primary key(id)
);

create table seller(
	id bigint not null auto_increment,
    name varchar(100) not null,
    primary key(id)
);

create table category(
	id bigint not null auto_increment,
    name varchar(100) not null,
    primary key(id)
);

create table product(
	id bigint not null auto_increment,
	id_category bigint not null,
    name varchar(100) not null,
    description text,
    creation_date datetime not null,
    primary key(id)
);

alter table product
	add constraint fk_product_category
    foreign key (id_category) references category(id);
    
create table sale(
	id bigint not null auto_increment,
    id_seller bigint not null,
    id_buyer bigint not null,
    id_product bigint not null,    
    sale_date datetime not null,
    evaluation int,
    primary key(id)
);

alter table sale
	add constraint fk_sale_seller
    foreign key (id_seller) references seller(id),
    
    add constraint fk_sale_buyer
    foreign key (id_buyer) references buyer(id),
    
    add constraint fk_sale_product
    foreign key (id_product) references product(id);
    
create table news(
	id bigint not null auto_increment,
    id_category bigint not null,    
    date datetime not null,
    quantity bigint not null,
    primary key(id)
);

alter table news
	add constraint fk_news_category
    foreign key (id_category) references category(id);
