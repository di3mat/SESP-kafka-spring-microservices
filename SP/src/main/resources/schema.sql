create table if not exists pratica
(
    id            uuid      not null primary key,
    name          text      not null,
    surname       text      not null,
    date_of_birth date      not null,
    tax_code      text      not null,
    status        text      not null,
    file          blob(1 M) not null,
    created_by    text      not null
);
