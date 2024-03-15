Relatório Técnico - Sistema de Reservas




Olá, meu nome é Daniel, e sou o responsável pelo desenvolvimento do Sistema de Reservas. Gostaria de compartilhar uma visão geral da arquitetura e dos microserviços que implementei.

Nessa arquitetura específica, optei pela abordagem de microserviços por diversos motivos. Primeiramente, os microserviços oferecem uma maior flexibilidade e escalabilidade, permitindo que cada componente do sistema seja desenvolvido, implantado e escalado de forma independente. Isso significa que posso atualizar ou expandir um serviço sem afetar os outros, o que facilita a manutenção e evolução do sistema ao longo do tempo.

Desenvolvi o projeto utilizando a IDE IntelliJ. Os microserviços foram implementados em Java e Spring Boot, com todos eles dockerizados. Utilizei o Docker para construir e gerenciar os containers. O projeto conta com um Docker Compose para facilitar a inicialização de todos os microserviços e alimentar a base de dados Postgres para testes.

O único microserviço exposto é o Cloud Gateway, fornecendo uma camada adicional de proteção aos demais microserviços dockerizados, permitindo até a implementação de endpoints privados.

Além disso, utilizei o Visual Studio Code e o Debeaver para interagir com o banco de dados. É importante ressaltar que ao usar o Postman, é necessário ficar atento aos IDs gerados e garantir que eles existam na base de dados antes de realizar operações de atualização ou busca. No vídeo de apresentação do projeto, farei uma explicação mais detalhada e testarei todas as funcionalidades. Essa ressalva visa garantir uma experiência de teste mais fluida e eficiente.


*1. Microserviço de Clientes:*
Desenvolvi um microserviço dedicado à gestão de clientes dentro da arquitetura de microserviços do Sistema de Reservas. Este microserviço permite operações CRUD (Create, Read, Update, Delete) para gerenciar clientes, com destaque para a validação de clientes estrangeiros, onde é necessário informar o passaporte se a opção de estrangeiro for marcada.

*2. Microserviço de Gestão de Serviços e Itens Opcionais da Reserva:*
Este microserviço é responsável pela gestão de serviços e itens opcionais da reserva, permitindo o cadastro de serviços e itens associados a propriedades específicas. Exemplos de serviços opcionais incluem serviço de quarto, serviço de lavanderia e serviço de transporte. Já os itens opcionais podem incluir itens como café da manhã, acesso à piscina, ou estacionamento.

*3. Microserviço de Gestão de Quartos:*
Este microserviço gerencia quartos, propriedades e localidades, permitindo operações CRUD para essas entidades. Optei por uma abordagem mais ampla na descrição dos quartos e banheiros, permitindo que os usuários descrevam detalhadamente suas características. Evitei a granularidade no cadastro de itens, deixando para o cliente definir na descrição tudo que aquele quarto tem, incluindo amenidades e serviços associados. O tipo de quarto é subjetivo, então dei liberdade para o cliente cadastrar da forma que preferir.

*4. Microserviço de E-mail:*
Implementei um microserviço para enviar e-mails de confirmação de reservas, incluindo informações detalhadas sobre a reserva, como valor total, itens selecionados e detalhes do quarto.

*5. Microserviço de Reservas:*
Este microserviço é responsável pela gestão das reservas, com operações CRUD e funcionalidades de busca de disponibilidade. Além disso, possui a funcionalidade de fazer pré-reservas e posterior confirmação de reserva, visando amenizar problemas de concorrência e volume de requisições. Optei por não utilizar serviços assíncronos, tornando todos os serviços síncronos, com exceção do envio de e-mail, que é realizado de forma assíncrona.

*6. Cloud Gateway do Spring:*
Utilizei o Cloud Gateway como ponto de entrada para os microserviços, direcionando as requisições para os serviços correspondentes. Todos os microserviços foram dockerizados e estão isolados em containers, com exceção do Cloud Gateway.

Além disso, implementei logs para rastreabilidade, testes unitários com cobertura mínima de 85% em cada microserviço, validações de dados de entrada e tratamento de exceções para garantir a robustez e estabilidade do sistema.




DIAGRAMA ARQUITETURA
![meudiag](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/b083a1ab-f053-45e2-8079-87a7b5707da3)


DIAGRAMA TABELAS

MS-CLIENTE

![db-ms-clientes](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/bd31ee14-68c5-4904-aeb1-08033b22548e)

MS-QUARTO

![db-ms-quartos](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/ebffa715-a932-46bb-8674-16037f364bb3)

MS-RESERVAS

![db-ms-reservas](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/59b14754-cbf0-42d5-afb9-bcc78fd0316a)

MS-SERVICOS

![db-ms-servicos](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/96470101-314a-48f0-a9c0-29877561e3f2)

EMAIL EXEMPLO

![email-exemplo](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/acbba258-459d-46f1-912f-93ef2184c532)


TESTE MS CLIENT
![teste-ms-clientes](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/50e50dff-4e6e-4da7-9474-8bbc759859f5)


TESTE MS QUARTO
![teste-ms-quarto](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/4513ba75-756b-4dbd-baf0-e6b53da54a58)


TESTE MS SERVICOS
![teste-ms-servicos](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/6e8fee19-30db-4111-b94f-4d0d54e556f4)


TESTE MS RESERVAS
![teste-ms-reservas](https://github.com/Daniel-Nascimentt/sistema-de-reservas-fiap/assets/65513073/e4426f8d-125c-40f5-8782-83d9431363a2)

