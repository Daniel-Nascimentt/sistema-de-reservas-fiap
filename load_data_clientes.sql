DROP DATABASE IF EXISTS clientes;
CREATE DATABASE clientes;


\connect clientes;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'ENDERECOS') THEN
        CREATE TABLE ENDERECOS (
            id BIGSERIAL PRIMARY KEY,
            cep VARCHAR(255),
            estado VARCHAR(255),
            nome_rua VARCHAR(255),
            numero INTEGER NOT NULL
        );

        INSERT INTO ENDERECOS (cep, estado, nome_rua, numero) VALUES
            ('12345-678', 'SP', 'Rua das Flores', 123),
            ('98765-432', 'RJ', 'Avenida Principal', 456),
            ('11111-111', 'SC', 'Rua da Praia', 789);

    END IF;
END $$;

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'CLIENTES') THEN
        CREATE TABLE CLIENTES (
            id BIGSERIAL PRIMARY KEY,
            cpf VARCHAR(255),
            data_nascimento DATE NOT NULL,
            email VARCHAR(255),
            estrangeiro BOOLEAN NOT NULL,
            nome_completo VARCHAR(255),
            pais_origem VARCHAR(255),
            passaporte VARCHAR(255),
            telefone VARCHAR(255),
            endereco_id BIGINT NOT NULL,
            CONSTRAINT FK_CLIENTES_ENDERECO FOREIGN KEY (endereco_id) REFERENCES ENDERECOS(id)
        );

         INSERT INTO CLIENTES (id, cpf, data_nascimento, email, estrangeiro, nome_completo, pais_origem, passaporte, telefone, endereco_id) VALUES
            (1, '02565853009', '1990-05-15', 'joao@example.com', false, 'João Silva', 'Brasil', NULL, '(11) 98765-4321', 1),
            (2, '86185552078', '1985-08-20', 'john@example.com', true, 'John Doe', 'EUA', 'ABC123', '(123) 456-7890', 2),
            (3, '45653775002', '1992-11-25', 'anna@example.com', true, 'Anna Müller', 'Alemanha', 'XYZ789', '+49 1234 5678', 3);

    END IF;
END $$;