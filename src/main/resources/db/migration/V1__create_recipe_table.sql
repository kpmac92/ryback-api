create table recipes (
    id UUID default gen_random_uuid() primary key,
    name varchar(100),
    description text,
    time integer
);