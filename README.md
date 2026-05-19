🏥 App de Gestão Clínica - Módulo de Autenticação
📋 Sobre o Projeto
Este projeto é uma aplicação mobile nativa Android desenvolvida como parte das avaliações práticas do curso de Análise e Desenvolvimento de Sistemas (ADS). O objetivo central desta primeira etapa ("Progresso do trabalho da faculdade") foi construir uma arquitetura de base sólida, segura e otimizada para um futuro Sistema de Gestão Clínica, garantindo as melhores práticas de UI/UX e persistência de dados em nuvem.

A aplicação foi projetada com foco absoluto em performance, utilizando ConstraintLayout para interfaces fluidas e integrando os serviços do Google Cloud (Firebase) com tratamento rigoroso de exceções e null-safety.

✨ Funcionalidades Atuais (Fase 1)
A infraestrutura inicial foca na jornada segura do usuário:

🔒 Autenticação Robusta: Sistema de Login e Cadastro utilizando Firebase Authentication.

☁️ Persistência de Dados em Tempo Real: Integração com Cloud Firestore para salvar e recuperar informações do usuário de forma assíncrona (Nome, E-mail e UID).

🛡️ Tratamento de Erros Profissional: Validação de campos vazios, verificação de requisitos de senha e tratamento nativo de exceções do Firebase (ex: e-mail já em uso ou falha de rede) com feedback visual claro via Snackbar.

📱 UI/UX Otimizada: Layouts construídos inteiramente com ConstraintLayout, remoção de ActionBar nativa e campos estilizados com Vector Assets internos.

🧭 Navegação Segura: Gestão inteligente do ciclo de vida das Activities (finish()), prevenindo o retorno acidental a telas de login ou cadastro após a autenticação.

🛠️ Tecnologias Utilizadas
Linguagem: Kotlin

IDE: Android Studio

BaaS (Backend as a Service): Firebase (Auth & Firestore)

Design e UI: XML (ConstraintLayout, Material Components)

⚙️ Como Executar o Projeto
Para testar a aplicação na sua máquina, siga os passos abaixo:

1. Clone este repositório:
git clone https://github.com/JotaJota404/imepac-joaoGabrielB.git

2. Abra o projeto:
Inicie o Android Studio e selecione a pasta do projeto clonado.

3. Sincronize o Gradle:
Aguarde o Android Studio baixar as dependências ou clique em Sync Project with Gradle Files.

4. Execute o App:
Conecte um emulador configurado para performance (ex: Pixel 2, API 30, sem Google Play) ou um dispositivo físico via Depuração USB e clique em Run (Shift + F10).

Aviso Importante: Certifique-se de ter o arquivo google-services.json válido na pasta app/ para que a conexão com os servidores do Firebase seja estabelecida corretamente.

👨‍💻 Desenvolvido com dedicação por João Gabriel Graduando em Análise e Desenvolvimento de Sistemas
