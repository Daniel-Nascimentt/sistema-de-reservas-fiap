DROP DATABASE IF EXISTS servicos_opcionais;
CREATE DATABASE servicos_opcionais;


\connect servicos_opcionais;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'ITENS') THEN
        CREATE TABLE ITENS (
            id SERIAL PRIMARY KEY,
            nome_item VARCHAR(255) NOT NULL,
            descricao_item VARCHAR(255) NOT NULL,
            valor_item NUMERIC NOT NULL,
            id_propriedade BIGINT NOT NULL
        );

        INSERT INTO ITENS (nome_item, descricao_item, valor_item, id_propriedade)
        VALUES
        ('Coca-Cola', 'Refrigerante Coca-Cola lata 350ml', 4.50, 1),
        ('Suco de Laranja', 'Suco Natural de Laranja 500ml', 7.99, 1),
        ('Skol', 'Cerveja Skol 350ml', 5.50, 1),
        ('Caipirinha de Limão', 'Caipirinha de Limão com Cachaça', 12.00, 1),
        ('Heineken', 'Cerveja Heineken 500ml', 8.99, 1),
        -- Propriedade 2
        ('Suco de Uva Integral', 'Suco de Uva Integral 1L', 10.50, 2),
        ('Brahma', 'Cerveja Brahma 355ml', 6.00, 2),
        ('Caipirinha de Morango', 'Caipirinha de Morango com Vodka', 14.50, 2),
        ('Guaraná Antarctica', 'Refrigerante Guaraná Antarctica 2L', 9.99, 2),
        ('Stella Artois', 'Cerveja Stella Artois 330ml', 7.75, 2),
        -- Propriedade 3
        ('Água Mineral', 'Água Mineral sem Gás 500ml', 2.50, 3),
        ('Caipirinha de Abacaxi', 'Caipirinha de Abacaxi com Rum', 13.50, 3),
        ('Suco de Maçã', 'Suco de Maçã 1L', 8.99, 3),
        ('Skol Beats Senses', 'Cerveja Skol Beats Senses 313ml', 11.75, 3),
        ('Sprite', 'Refrigerante Sprite lata 350ml', 4.25, 3),
        -- Propriedade 4
        ('Vinho Tinto', 'Vinho Tinto Seco 750ml', 29.99, 4),
        ('Martini', 'Martini Seco', 18.50, 4),
        ('Coca-Cola Zero', 'Refrigerante Coca-Cola Zero lata 350ml', 5.00, 4),
        ('Eisenbahn Pilsen', 'Cerveja Eisenbahn Pilsen 355ml', 9.25, 4),
        ('Suco de Pêssego', 'Suco de Pêssego 500ml', 6.50, 4),
        -- Propriedade 5
        ('Red Bull', 'Energético Red Bull lata 250ml', 8.00, 5),
        ('Jack Daniels', 'Whiskey Jack Daniels 750ml', 59.99, 5),
        ('Chá Gelado de Limão', 'Chá Gelado de Limão 1L', 4.75, 5),
        ('Original', 'Cerveja Original 600ml', 12.90, 5),
        ('Fanta Uva', 'Refrigerante Fanta Uva lata 350ml', 4.75, 5),
        -- Propriedade 6
        ('Caipiroska de Frutas Vermelhas', 'Caipiroska de Frutas Vermelhas com Vodka', 15.75, 6),
        ('Água de Coco', 'Água de Coco 330ml', 3.99, 6),
        ('Budweiser', 'Cerveja Budweiser 355ml', 7.50, 6),
        ('Margarita', 'Cocktail Margarita com Tequila', 16.50, 6),
        ('Chá de Hibisco', 'Chá de Hibisco Gelado 500ml', 5.25, 6);
    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'SERVICOS') THEN
        CREATE TABLE SERVICOS (
            id SERIAL PRIMARY KEY,
            nome_servico VARCHAR(255) NOT NULL,
            descricao_servico VARCHAR(255) NOT NULL,
            valor_servico NUMERIC NOT NULL,
            id_propriedade BIGINT NOT NULL
        );

        INSERT INTO SERVICOS (nome_servico, descricao_servico, valor_servico, id_propriedade)
        VALUES
        -- Propriedade 1
        ('Serviço de Limpeza Diária', 'Limpeza diária do quarto e troca de toalhas', 50.00, 1),
        ('Estacionamento Valet', 'Estacionamento com manobrista', 25.00, 1),
        ('Wi-Fi Premium', 'Acesso à internet de alta velocidade', 15.99, 1),
        ('Spa Relaxante', 'Sessão de massagem relaxante', 80.00, 1),
        ('Café da Manhã Premium', 'Buffet de café da manhã premium', 35.00, 1),
        -- Propriedade 2
        ('Piscina Aquecida', 'Piscina aquecida com área de descanso', 40.00, 2),
        ('Serviço de Concierge 24h', 'Assistência personalizada a qualquer hora', 30.00, 2),
        ('Sala de Conferências', 'Espaço equipado para reuniões e conferências', 60.00, 2),
        ('Pet-Friendly', 'Acomodações pet-friendly com mimos especiais', 20.00, 2),
        ('Bar Lounge', 'Bar no lounge com coquetéis exclusivos', 18.50, 2),
        -- Propriedade 3
        ('Aulas de Ioga', 'Aulas de Ioga no jardim do Propriedade', 30.00, 3),
        ('Transfer do Aeroporto', 'Serviço de transfer do aeroporto para o Propriedade', 45.00, 3),
        ('Serviço de Lavanderia', 'Lavagem e passagem de roupas', 20.00, 3),
        ('Aluguel de Bicicletas', 'Aluguel de bicicletas para explorar a cidade', 15.00, 3),
        ('Café da Manhã Vegano', 'Opções de café da manhã vegano', 25.00, 3),
        -- Propriedade 4
        ('Serviço de Quarto 24h', 'Serviço de quarto disponível a qualquer hora', 40.00, 4),
        ('Trilha Guiada', 'Trilha guiada nas montanhas próximas', 55.00, 4),
        ('Serviço de Babá', 'Serviço de babá para os hóspedes com crianças', 35.00, 4),
        ('Cinema ao Ar Livre', 'Exibição de filmes ao ar livre à noite', 10.00, 4),
        ('Brunch de Domingo', 'Brunch especial aos domingos', 28.50, 4),
        -- Propriedade 5
        ('Quadra de Tênis', 'Quadra de tênis profissional disponível', 50.00, 5),
        ('Jantar Temático', 'Jantar temático com pratos exclusivos', 75.00, 5),
        ('Heliponto Privativo', 'Heliponto exclusivo para hóspedes', 100.00, 5),
        ('Biblioteca Privativa', 'Acesso a uma biblioteca exclusiva', 15.00, 5),
        ('Serviço de Carro de Luxo', 'Serviço de motorista com carro de luxo', 90.00, 5),
        -- Propriedade 6
        ('Aula de Mergulho', 'Aulas de mergulho na piscina do Propriedade', 60.00, 6),
        ('Café da Tarde', 'Café da tarde com chás e doces especiais', 22.50, 6),
        ('Tour Gastronômico', 'Tour guiado pelos melhores restaurantes locais', 40.00, 6),
        ('Serviço de Manicure e Pedicure', 'Serviço de manicure e pedicure no quarto', 25.00, 6),
        ('Sala de Jogos', 'Sala de jogos com diversos entretenimentos', 18.75, 6);

    END IF;
END $$;


