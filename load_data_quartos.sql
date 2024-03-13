DROP DATABASE IF EXISTS quartos;
CREATE DATABASE quartos;

\connect quartos;

-- Tabela: banheiros
create table banheiros (
    id bigserial not null,
    descricao_banheiro varchar(255),
    tipo_banheiro varchar(255),
    primary key (id)
);

-- Tabela: localidades
create table localidades (
    id bigserial not null,
    nome_localidade varchar(255),
    primary key (id)
);

-- Tabela: propriedades
create table propriedades (
    id bigserial not null,
    descricao_amenidades varchar(255),
    nome_propriedade varchar(255),
    localidade_id bigint,
    primary key (id),
    foreign key (localidade_id) references localidades
);

-- Tabela: quartos
create table quartos (
    id bigserial not null,
    descricao_quarto varchar(255),
    tipo_quarto varchar(255),
    banheiro_id bigint,
    primary key (id),
    unique (banheiro_id),
    foreign key (banheiro_id) references banheiros
);

-- Tabela: propriedades_quartos
create table propriedades_quartos (
    propriedade_id bigint not null,
    quarto_id bigint not null,
    primary key (propriedade_id, quarto_id),
    foreign key (quarto_id) references quartos,
    foreign key (propriedade_id) references propriedades
);