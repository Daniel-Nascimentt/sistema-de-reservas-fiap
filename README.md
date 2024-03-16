# HACKATHON - SISTEMA DE RESERVAS - FIAP - PÓS TECH


## Relatório Técnico

Olá, meu nome é Daniel, e sou o responsável pelo desenvolvimento do Sistema de Reservas. Gostaria de compartilhar uma visão geral da arquitetura e dos microserviços que implementei.

Nessa arquitetura específica, optei pela abordagem de ***microserviços*** por diversos motivos. Primeiramente, os microserviços oferecem uma maior *flexibilidade* e *escalabilidade*, permitindo que cada componente do sistema seja desenvolvido, implantado e escalado de forma independente. Isso significa que posso *atualizar ou expandir um serviço sem afetar os outros*, o que facilita a manutenção e evolução do sistema ao longo do tempo.

### Desenvolvi o projeto utilizando:
 * IDE IntelliJ. 
 * Os microserviços foram implementados em Java e Spring Boot
 * Todos eles dockerizados para construir e gerenciar os containers.. 
 * O projeto conta com um Docker Compose para facilitar a inicialização de todos os microserviços e alimentar a base de dados Postgres para testes.
 * Banco de dados postgres
 * Postman
 * Visual Studio Code
 * Debeaver para interagir com o banco de dados.


É importante ressaltar que ao usar o ***Postman***, é necessário ficar ***ATENTO*** aos IDs e garantir que eles existam na base de dados antes de realizar operações de atualização, busca, deleção, etc. No vídeo de apresentação do projeto, farei uma explicação mais detalhada e testarei todas as funcionalidades. Essa ressalva visa garantir uma experiência de teste mais fluida e eficiente.

# COMO EXECUTAR ?

Basta fazer o clone desse projeto e executar:
> docker-compose up

#### MAS ANTES...

Atente-se para que não tenha nada executando nas portas ***5432*** e ***8080***, pois são as portas que o postgres e o gateway executam respectivamente.

#

# DETALHAMENTO DO SISTEMA

## DIAGRAMA DE ARQUITETURA
![meudiag](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/b083a1ab-f053-45e2-8079-87a7b5707da3)


Para todos micro serviços, implementei logs para rastreabilidade, testes unitários com cobertura mínima de 85% em cada microserviço, validações de dados de entrada e tratamento de exceções para garantir a robustez e estabilidade do sistema.

### *1. Microserviço de Clientes:*
Desenvolvi um microserviço dedicado à gestão de clientes dentro da arquitetura de microserviços do Sistema de Reservas. Este microserviço permite operações CRUD (Create, Read, Update, Delete) para gerenciar clientes, com destaque para a validação de clientes estrangeiros, onde é necessário informar o passaporte se a opção de estrangeiro for marcada.

### *2. Microserviço de Gestão de Serviços e Itens Opcionais da Reserva:*
Este microserviço é responsável pela gestão de serviços e itens opcionais da reserva, permitindo o cadastro de serviços e itens associados a propriedades específicas. Exemplos de serviços opcionais incluem serviço de quarto, serviço de lavanderia e serviço de transporte. Já os itens opcionais podem incluir itens como café da manhã, acesso à piscina, ou estacionamento.

### *3. Microserviço de Gestão de Quartos:*
Este microserviço gerencia quartos, propriedades e localidades, permitindo operações CRUD para essas entidades. Optei por uma abordagem mais ampla na descrição dos quartos e banheiros, permitindo que os usuários descrevam detalhadamente suas características. Evitei a granularidade no cadastro de itens, deixando para o cliente definir na descrição tudo que aquele quarto tem, incluindo amenidades e serviços associados. O tipo de quarto é subjetivo, então dei liberdade para o cliente cadastrar da forma que preferir.

### *4. Microserviço de E-mail:*
Implementei um microserviço para enviar e-mails de confirmação de reservas, incluindo informações detalhadas sobre a reserva, como valor total, itens selecionados e detalhes do quarto.

### *5. Microserviço de Reservas:*
Este microserviço é responsável pela gestão das reservas, com operações CRUD e funcionalidades de busca de disponibilidade com implementação de filtros dinamicos que ajudam o cliente no caso de ele desejar uma quantidade X de hospedes e não conter quartos com aquela capacidade. Nesse caso a exibição favorece quartos no mesmo endereço para comportar todos os hospedes em uma unica propriedade. Além disso, possui a funcionalidade de fazer pré-reservas e posterior confirmação de reserva, visando amenizar problemas de concorrência por conta de volume de requisições. Optei por não utilizar serviços assíncronos, tornando todos os serviços síncronos, com exceção do envio de e-mail, que é realizado de forma assíncrona.

### *6. Cloud Gateway do Spring:*
Utilizei o Cloud Gateway como ponto de entrada para os microserviços, direcionando as requisições para os serviços correspondentes. Todos os microserviços foram dockerizados e estão isolados em containers, com exceção do Cloud Gateway. O único microserviço exposto é o Cloud Gateway, fornecendo uma camada adicional de proteção aos demais microserviços dockerizados, permitindo até a implementação de endpoints privados.



# DIAGRAMAS DAS TABELAS DE CADA DATABASE

## DATABASE MS-CLIENTE

![db-ms-clientes](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/bd31ee14-68c5-4904-aeb1-08033b22548e)

## DATABASE MS-QUARTO

![db-ms-quartos](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/ebffa715-a932-46bb-8674-16037f364bb3)

## DATABASE MS-RESERVAS

![db-ms-reservas](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/59b14754-cbf0-42d5-afb9-bcc78fd0316a)

## DATABASE MS-SERVICOS

![db-ms-servicos](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/96470101-314a-48f0-a9c0-29877561e3f2)

# EXEMPLO DE EMAIL GERADO PELO SISTEMA

![email-exemplo](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/acbba258-459d-46f1-912f-93ef2184c532)

# COBERTURA DOS TESTES

## TESTES MS CLIENTES
![teste-ms-clientes](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/50e50dff-4e6e-4da7-9474-8bbc759859f5)


## TESTES MS QUARTO
![teste-ms-quarto](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/4513ba75-756b-4dbd-baf0-e6b53da54a58)


## TESTES MS SERVICOS
![teste-ms-servicos](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/6e8fee19-30db-4111-b94f-4d0d54e556f4)


## TESTES MS RESERVAS
![teste-ms-reservas](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/e4426f8d-125c-40f5-8782-83d9431363a2)

# SWAGGER

>http://localhost:8080/ms-reservas/swagger-ui/index.html#/

>http://localhost:8080/ms-cliente/swagger-ui/index.html#/

>http://localhost:8080/ms-quartos/swagger-ui/index.html#/

>http://localhost:8080/ms-servicos/swagger-ui/index.html#/

# POSTMAN COM CURL'S DAS REQUESTS
[sistema-de-reserva.postman_collection.json](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/files/14621769/sistema-de-reserva.postman_collection.json)
