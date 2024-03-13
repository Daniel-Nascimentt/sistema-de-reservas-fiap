DROP DATABASE IF EXISTS quartos;
CREATE DATABASE quartos;

\connect quartos;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'banheiros') THEN
        -- Tabela: banheiros
        CREATE TABLE banheiros (
            id BIGSERIAL NOT NULL,
            descricao_banheiro VARCHAR(255),
            tipo_banheiro VARCHAR(255),
            PRIMARY KEY (id)
        );
    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'localidades') THEN
        -- Tabela: localidades
        CREATE TABLE localidades (
            id BIGSERIAL NOT NULL,
            nome_localidade VARCHAR(255),
            PRIMARY KEY (id)
        );
    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'propriedades') THEN
        -- Tabela: propriedades
        CREATE TABLE propriedades (
            id BIGSERIAL NOT NULL,
            descricao_amenidades TEXT,
            nome_propriedade VARCHAR(255),
            localidade_id BIGINT,
            PRIMARY KEY (id),
            FOREIGN KEY (localidade_id) REFERENCES localidades
        );
    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'quartos') THEN
        -- Tabela: quartos
        CREATE TABLE quartos (
            id BIGSERIAL NOT NULL,
            descricao_quarto VARCHAR(255),
            tipo_quarto VARCHAR(255),
            banheiro_id BIGINT,
            PRIMARY KEY (id),
            UNIQUE (banheiro_id),
            FOREIGN KEY (banheiro_id) REFERENCES banheiros
        );
    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'propriedades_quartos') THEN
        -- Tabela: propriedades_quartos
        CREATE TABLE propriedades_quartos (
            propriedade_id BIGINT NOT NULL,
            quarto_id BIGINT NOT NULL,
            PRIMARY KEY (propriedade_id, quarto_id),
            FOREIGN KEY (quarto_id) REFERENCES quartos,
            FOREIGN KEY (propriedade_id) REFERENCES propriedades
        );
    END IF;
END $$;