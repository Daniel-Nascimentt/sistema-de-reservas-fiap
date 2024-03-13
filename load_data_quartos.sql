DROP DATABASE IF EXISTS quartos;
CREATE DATABASE quartos;

\connect quartos;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'banheiros') THEN
        -- Tabela: banheiros
        CREATE TABLE banheiros (
            id BIGSERIAL NOT NULL,
            descricao_banheiro TEXT NOT NULL,
            tipo_banheiro VARCHAR(255) NOT NULL,
            PRIMARY KEY (id)
        );


        INSERT INTO banheiros (descricao_banheiro, tipo_banheiro) 
        VALUES
        ('Banheiro Privativo com Chuveiro', 'SIMPLES'), --1
        ('Banheiro Privativo com Hidromassagem', 'LUXO'), --2
        ('Banheiro privativo com chuveiro e vaso sanitário', 'SIMPLES'), --3
        ('Banheiro com chuveiro, banheira e amenities de luxo', 'LUXO'), --4
        ('Banheiro de luxo com banheira de hidromassagem e sauna integrada', 'PREMIUM'), --5
        ('Banheiro privativo com chuveiro, vaso sanitário e pia com bancada de mármore', 'SIMPLES'), --6
        ('Banheiro de luxo com chuveiro de cascata, banheira de hidromassagem e sistema de iluminação LED', 'LUXO'), --7
        ('Banheiro premium com banheira de hidromassagem, sauna seca e ducha de teto', 'PREMIUM'), --8
        ('Banheiro com chuveiro, vaso sanitário e pia com bancada de granito', 'SIMPLES'), --9
        ('Banheiro de luxo com banheira de imersão, ducha de chuva e espelho de aumento', 'LUXO'), --10
        ('Banheiro Privativo de Luxo com Banheira de Hidromassagem', 'PREMIUM'), -- 11
        ('Banheiro premium com sauna a vapor, banheira de hidromassagem dupla e sistema de som integrado', 'PREMIUM'); --12


    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'localidades') THEN
        -- Tabela: localidades
        CREATE TABLE localidades (
            id BIGSERIAL NOT NULL,
            nome_localidade VARCHAR(255) NOT NULL,
            PRIMARY KEY (id)
        );

        INSERT INTO localidades (nome_localidade) 
        VALUES
        ('São Paulo - Centro'),
        ('São Paulo - Zona Sul'),
        ('São Paulo - Zona Oeste');

    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'propriedades') THEN
        -- Tabela: propriedades
        CREATE TABLE propriedades (
            id BIGSERIAL NOT NULL,
            descricao_amenidades TEXT NOT NULL,
            nome_propriedade VARCHAR(255) NOT NULL,
            localidade_id BIGINT,
            endereco_propriedade VARCHAR(255) NOT NULL,
            PRIMARY KEY (id),
            FOREIGN KEY (localidade_id) REFERENCES localidades
        );

        INSERT INTO propriedades (descricao_amenidades, nome_propriedade, localidade_id, endereco_propriedade) 
        VALUES
        ('Piscina, Academia', 'Hotel Estrela', 1, 'Av. Paulista, 100, São Paulo - Centro'), --1
        ('Wi-Fi, Estacionamento', 'Pousada Paraíso', 2, 'Rua dos Jardins, 200, São Paulo - Zona Sul'), --2
        ('Restaurante, Spa', 'Resort Luxo', 3, 'Av. das Margaridas, 300, São Paulo - Zona Oeste'), --3
        ('Piscina, Sala de conferências, Café da manhã incluso', 'Hotel Metrópole', 1, 'Av. São João, 500, São Paulo - Centro'), --4
        ('Estacionamento, Wi-Fi gratuito, Serviço de lavanderia', 'Pousada das Artes', 1, 'Rua da Consolação, 200, São Paulo - Centro'), --5
        ('Academia, Restaurante, Serviço de quarto', 'Hotel Sol Poente', 2, 'Av. Faria Lima, 300, São Paulo - Zona Sul'); -- 6


    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'quartos') THEN
        -- Tabela: quartos
        CREATE TABLE quartos (
            id BIGSERIAL NOT NULL,
            descricao_quarto TEXT NOT NULL,
            tipo_quarto VARCHAR(255) NOT NULL,
            total_hospedes INT NOT NULL,
            valor_diaria NUMERIC NOT NULL,
            banheiro_id BIGINT,
            PRIMARY KEY (id),
            UNIQUE (banheiro_id),
            FOREIGN KEY (banheiro_id) REFERENCES banheiros
        );

        INSERT INTO quartos (descricao_quarto, tipo_quarto, total_hospedes, valor_diaria, banheiro_id)
        VALUES
        ('Quarto Standard Simples com TV a cabo e cama de casal', 'STANDARD_SIMPLES', 2, 150.00, 1),
        ('Quarto Standard Duplo com Ar Condicionado e vista panorâmica', 'STANDARD_DUPLO', 2, 200.00, 2),
        ('Suíte Luxo Simples com Banheira de Hidromassagem e amenities exclusivos', 'LUXO_SIMPLES', 1, 300.00, 3),
        ('Suíte Luxo Duplo com Cama King Size, Varanda e Sala de Estar', 'LUXO_DUPLO', 2, 400.00, 4),
        ('Suíte Premium Simples com Sala de Jantar, Vista para o Mar e Sauna Privativa', 'PREMIUM_SIMPLES', 1, 500.00, 5),
        ('Suíte Premium Duplo com Terraço Privativo, Jacuzzi e Serviço de Mordomo', 'PREMIUM_DUPLO', 2, 600.00, 6),
        ('Quarto Standard Simples com TV de tela plana, cama de casal e minibar abastecido', 'STANDARD_SIMPLES', 2, 200.00, 7),
        ('Quarto Standard Duplo com Ar Condicionado, varanda privativa e vista para o jardim', 'STANDARD_DUPLO', 2, 250.00, 8),
        ('Suíte Luxo Simples com Banheira de Hidromassagem, área de estar e decoração elegante', 'LUXO_SIMPLES', 1, 400.00, 9),
        ('Suíte Luxo Duplo com Cama King Size, Terraço com vista para o mar e Jacuzzi', 'LUXO_DUPLO', 2, 500.00, 10),
        ('Suíte Premium Simples com Sala de Estar, Bar privativo e varanda com vista panorâmica', 'PREMIUM_SIMPLES', 1, 600.00, 11),
        ('Suíte Premium Duplo com Sala de Jantar, Home Theater, Sauna Privativa e Piscina Privada', 'PREMIUM_DUPLO', 2, 700.00, 12);

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

        INSERT INTO propriedades_quartos (propriedade_id, quarto_id) VALUES
        (1, 1),  
        (1, 2),  
        (2, 3),
        (2, 4),  
        (3, 5),  
        (3, 6),
        (4, 7),
        (4, 8),
        (5, 9),
        (5, 10),
        (5, 11),
        (6, 12); 

    END IF;
END $$;