-- Create prices table for catalog
create table if not exists prices (
                                      id bigserial primary key,
                                      store varchar(50) not null,
    query varchar(200) not null,
    name varchar(400) not null,
    brand varchar(200),
    quantity varchar(100),
    packaging varchar(100),
    regular_price numeric(10,2),
    discount_price numeric(10,2),
    currency varchar(10) default 'EUR',
    unit_price numeric(10,4),
    source_type varchar(30),
    confidence smallint default 80,
    last_updated timestamp not null default now()
    );

-- Unique key to avoid duplicates per (query,store,name,quantity)
create unique index if not exists uq_prices_qsnq
    on prices (lower(query), lower(store), lower(name), coalesce(lower(quantity), ''));