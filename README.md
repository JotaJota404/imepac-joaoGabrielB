# 🚀 Projeto Base Android - Arquitetura de Autenticação e Nuvem

<div align="center">
  <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="Kotlin" src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img alt="Firebase" src="https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase" />
  <img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
</div>

<br>

## 📋 Sobre o Projeto

Este projeto é uma aplicação mobile nativa Android desenvolvida como parte das avaliações práticas do curso de Análise e Desenvolvimento de Sistemas (ADS). 

O objetivo central desta fase inicial foi construir um **"Motor Base"** com uma arquitetura sólida, segura e otimizada. Em vez de focar imediatamente na regra de negócio, a prioridade foi estabelecer uma fundação de excelência em autenticação, design de interface (UI/UX) e persistência de dados em nuvem. 

Este repositório encontra-se **em desenvolvimento contínuo**. A base construída aqui servirá como ponto de partida escalável para uma futura aplicação comercial completa, cujo escopo principal (regras de negócio e funcionalidades centrais) ainda está sendo definido.

## ✨ O que já está funcionando (Fase Core)

A infraestrutura atual garante a jornada segura e fluida do usuário:

* **🔒 Autenticação Robusta:** Sistema de Login e Cadastro utilizando `Firebase Authentication`.
* **☁️ Persistência em Tempo Real:** Integração com `Cloud Firestore` para salvar e recuperar informações do usuário de forma assíncrona (Nome, E-mail e UID).
* **🛡️ Tratamento de Erros Profissional:** Validação de campos vazios, verificação de requisitos de senha e tratamento nativo de exceções do Firebase (ex: e-mail já em uso) com feedback visual via `Snackbar`.
* **📱 UI/UX Otimizada:** Layouts construídos inteiramente com `ConstraintLayout`, remoção de `ActionBar` nativa e campos estilizados com *Vector Assets*.
* **🧭 Navegação Segura:** Gestão inteligente do ciclo de vida das Activities (`finish()`), prevenindo o retorno acidental a telas de login após a autenticação.

## 🚀 Próximos Passos (Roadmap Aberto)

Como o projeto está em evolução, o roadmap foca na expansão modular a partir desta base de segurança:

- [ ] 💡 Definição do escopo e nicho da aplicação principal.
- [ ] 🎨 Criação do Dashboard (Tela Inicial) pós-login.
- [ ] 🗄️ Expansão da modelagem de dados no Firestore.
- [ ] 🏗️ Refatoração do código para arquitetura MVVM (Model-View-ViewModel).

## 🛠️ Tecnologias e Ferramentas

As seguintes tecnologias foram utilizadas na construção deste projeto base:

* **Linguagem:** Kotlin
* **IDE:** Android Studio
* **Backend as a Service (BaaS):** Firebase (Auth & Firestore)
* **Design de Interface:** XML, ConstraintLayout, Material Components
* **Controle de Versão:** Git & GitHub

## ⚙️ Como Executar o Projeto

Para rodar esta base na sua máquina local:

1. Clone o repositório:
   ```bash
   git clone [https://github.com/JotaJota404/imepac-joaoGabrielB.git](https://github.com/JotaJota404/imepac-joaoGabrielB.git)
2.Abra a pasta do projeto no Android Studio.

3.Aguarde a sincronização do Gradle.

4.Conecte um emulador ou dispositivo físico via Depuração USB e clique em Run (Shift + F10).

Aviso Importante: Para que os serviços de nuvem funcionem, é necessário configurar o seu próprio banco de dados no Firebase e adicionar o arquivo google-services.json válido na pasta app/ do projeto.

👨‍💻 Desenvolvido por João Gabriel | Graduando em Análise e Desenvolvimento de Sistemas
