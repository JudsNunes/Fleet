# 🚛 Jornada Fleet Evolutech

Bem-vindo ao repositório oficial da **Plataforma de Gestão de Frota Veicular - Fleet Evolutech**.

## 📖 A História do Projeto
A Jornada Fleet Evolutech representa a evolução arquitetural de uma plataforma de gestão de frotas. Nascido como um MVP (Produto Mínimo Viável), o sistema atingiu um ponto de maturidade em que exigia maior escalabilidade, resiliência e independência de times. 

O objetivo deste projeto é realizar a transição desse monolito para uma **Arquitetura de Microserviços** moderna, orientada a eventos, contemplando um painel web administrativo e um aplicativo mobile para os motoristas em campo.

## 🎯 O Que Resolvemos?
Gerir uma frota envolve múltiplas variáveis críticas: veículos, manutenção, motoristas, viagens e despesas. Nossa plataforma centraliza essa operação, oferecendo:
* **Para o Gestor:** Controle total sobre custos, inventário, e manutenções preditivas/corretivas.
* **Para o Motorista:** Um app simples para registrar o início e fim de viagens.
* **Para o Negócio:** Rastreabilidade, conformidade legal (alertas de CNH e documentos) e relatórios operacionais.

## 🏗️ Visão Arquitetural
Nossa arquitetura foi desenhada utilizando os seguintes padrões e tecnologias:
* **Padrões:** Bounded Contexts, Database per Service, API Gateway, Transactional Outbox e CQRS.
* **Backend:** Java Spring Boot, Spring Cloud Gateway.
* **Mensageria:** RabbitMQ para eventos de domínio (comunicação assíncrona).
* **Banco de Dados:** PostgreSQL (isolado por contexto).
* **Segurança:** Keycloak (OAuth2 / RBAC).
* **Frontend:** React (Web Admin) e React Native/Expo (Mobile).

## 🚀 Como este repositório está organizado?
Este projeto segue a metodologia ágil, gerenciado através do **GitHub Projects**. Todo o escopo está dividido em 5 Épicos principais:
* **Épico 0:** Fundação, estabilização do monolito e testes.
* **Épico 1:** Plataforma, Gateway, Auth e mensageria.
* **Épico 2:** Domínio Core (Veículos, Manutenções, Motoristas, Viagens).
* **Épico 3:** Experiência UX (Notificações, Web Admin, Mobile).
* **Épico 4:** Inteligência e Integrações (Despesas, Relatórios).
