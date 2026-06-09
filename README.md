<div align="center">

# 📚 Livraria Pessoal

<img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
<img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
<img alt="Firebase" src="https://img.shields.io/badge/Firebase-%23039BE5.svg?style=for-the-badge&logo=firebase" />
<img alt="Firestore" src="https://img.shields.io/badge/Firestore-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" />
<img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
<img alt="Material Design" src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=material-design&logoColor=white" />

<br/>

> **Livraria Pessoal** — Catálogo pessoal de livros com autenticação Firebase, CRUD completo no Firestore,  
> busca em tempo real, foto de perfil circular, navegação ergonômica e design premium *"Dark Library"*.

<br/>

_Trabalho Final de Análise e Desenvolvimento de Sistemas — IMEPAC_

</div>

---

## 📋 Sobre o Projeto

**Meus Livros** é um aplicativo Android nativo desenvolvido como **Trabalho Final** da disciplina de desenvolvimento mobile do curso de Análise e Desenvolvimento de Sistemas (ADS) — IMEPAC.

O projeto evoluiu em três fases:

|   Fase   | Foco                                                                          | Status |
| :------: | ----------------------------------------------------------------------------- | :----: |
| **v1.0** | Arquitetura base: autenticação Firebase, Activities, navegação | ✅ |
| **v2.0** | CRUD completo, listas paginadas e design profissional *"Dark Library"* | ✅ |
| **v3.0** | Melhorias UX/UI: busca de livros, foto de perfil, botão de voltar e nova logo | ✅ |
| **v3.1** | Rename para *"Livraria Pessoal"*, correção da foto circular, segurança e validações | ✅ |

---

## ✨ Funcionalidades

### 🔒 Autenticação

- Cadastro de usuário com nome, e-mail e senha via **Firebase Authentication**
- Login com validação de credenciais e tratamento de erros específicos
- Sessão persistente: usuário autenticado é redirecionado direto ao menu
- Logout seguro com limpeza de sessão e foto de perfil

### 📚 Gerenciamento de Livros (CRUD Completo)

- **Create** — Cadastro de livros com título, autor e ano de publicação
- **Read** — Listagem paginada com `RecyclerView` + paginação incremental no Firestore
- **Update** — Edição completa dos dados de qualquer livro da lista
- **Delete** — Exclusão com diálogo de confirmação (`AlertDialog`)

### 🔍 Busca de Livros _(novo — v3.0)_

- Campo de busca em **tempo real** no topo da lista de livros
- Filtra simultaneamente por **título**, **autor** ou **ano** (case-insensitive)
- Botão **"X"** para limpar a busca instantaneamente
- Mensagem "Nenhum livro encontrado" exibida quando não há resultados
- Paginação pausada automaticamente durante a busca

### 📸 Foto de Perfil _(novo — v3.0)_

- Avatar clicável na tela de perfil com **badge de câmera**
- Seleção de foto diretamente da **galeria nativa** do Android
- Foto salva localmente com **SharedPreferences** — persiste entre sessões
- Compatível com **Android 13+** (`READ_MEDIA_IMAGES`) e versões anteriores
- Foto removida automaticamente ao fazer logout

### 🔙 Navegação Ergonômica _(novo — v3.0)_

- **Botão de voltar** em todas as telas secundárias
- Posicionamento inteligente seguindo as diretrizes de UX:
  - **Formulários com scroll** → canto superior esquerdo (fixo sobre o conteúdo)
  - **Listas e Perfil** → canto inferior esquerdo (zona do polegar — ergonomia de uma mão)
- Área de toque mínima de **48dp** conforme Material Design

### 🎨 Nova Logo _(novo — v3.0)_

- Livro aberto dourado com páginas e linhas de texto estilizadas
- Mesma identidade visual no **ícone do launcher** (tela inicial do dispositivo) e **dentro do app**
- Vetor nativo — sem perda de qualidade em nenhuma densidade de tela

### ⚙️ Extras

- **Seed de dados** — botão para popular o banco com 15 livros clássicos de teste
- **Paginação** — carregamento incremental com `ProgressBar` de rodapé

---

## 📱 Telas do App

|  #  | Activity            | Descrição                                                  |
| :-: | ------------------- | ---------------------------------------------------------- |
|  1  | `SplashActivity`    | Tela de abertura com nova logo animada                     |
|  2  | `FormLogin`         | Login com e-mail e senha                                   |
|  3  | `FormCadastro`      | Criação de conta de usuário                                |
|  4  | `TelaPrincipal`     | Menu principal com navegação por cards                     |
|  5  | `TelaListaLivros`   | Lista paginada com busca em tempo real e botão de voltar   |
|  6  | `TelaDetalheLivro`  | Visualização, edição e exclusão de livro + botão de voltar |
|  7  | `TelaPerfil`        | Dados do usuário, foto de perfil e botão de voltar         |
|  8  | `FormCadastroLivro` | Formulário de cadastro de livro + botão de voltar          |

---

## 🎨 Design System — _"Dark Library"_

O app adota o tema **"Dark Library"**, inspirado na estética premium de apps literários como Kindle e Apple Books, implementado com **Material Design 3** e recursos XML nativos — sem bibliotecas de UI externas.

### Paleta de Cores

| Token              | Nome              |    Hex    | Uso                                |
| ------------------ | ----------------- | :-------: | ---------------------------------- |
| `fundo_escuro`     | Azul Meia-Noite   | `#12122A` | Fundo de todas as telas            |
| `fundo_card`       | Azul Escuro Suave | `#1E1E3F` | Cards e containers                 |
| `primaria`         | Âmbar Dourado     | `#F5A623` | Botões, títulos, destaques, logo   |
| `primaria_escura`  | Âmbar Queimado    | `#C17D0F` | Gradiente e lombada da logo        |
| `texto_principal`  | Branco Quente     | `#F0EDE6` | Textos em geral                    |
| `texto_secundario` | Cinza Lavanda     | `#9E9EC0` | Subtítulos, hints, ícones inativos |
| `perigo`           | Vermelho Coral    | `#E05252` | Excluir e Sair                     |
| `sucesso`          | Verde Menta       | `#4ECDC4` | Confirmações                       |

### Componentes de Design

- **Botão primário** — Gradiente âmbar horizontal com `selector` (3 estados: normal, pressionado, desabilitado)
- **Botão de perigo** — Gradiente vermelho coral para ações destrutivas
- **Cards** — Fundo escuro + borda âmbar semitransparente (efeito glassmorphism sem biblioteca)
- **Card da lista** — Faixa âmbar vertical (4dp) na borda esquerda + ícone de capa
- **Campos de texto** — Fundo escuro com borda âmbar intensificada ao focar
- **Avatar** — Círculo com borda âmbar de 3dp, suporte a foto personalizada
- **Ícones** — 10 VectorDrawables criados nativamente (sem assets externos)

---

## 🛠️ Tecnologias

| Tecnologia             | Detalhe                                                     |
| ---------------------- | ----------------------------------------------------------- |
| **Linguagem**          | Kotlin                                                      |
| **IDE**                | Android Studio                                              |
| **Min SDK**            | API 24 (Android 7.0)                                        |
| **Target SDK**         | API 34 (Android 14)                                         |
| **Autenticação**       | Firebase Authentication                                     |
| **Banco de Dados**     | Cloud Firestore (NoSQL)                                     |
| **Persistência Local** | SharedPreferences (foto de perfil)                          |
| **Design System**      | Material Design 3 (`Theme.Material3.Dark`)                  |
| **Layouts**            | ConstraintLayout, LinearLayout, CardView, FrameLayout       |
| **Lista**              | RecyclerView com Adapter customizado e filtro em tempo real |
| **Galeria**            | `ActivityResultContracts.GetContent`                        |
| **Feedback UI**        | Snackbar, ProgressBar, AlertDialog                          |
| **Controle de Versão** | Git & GitHub                                                |

---

## 📁 Estrutura do Projeto

```
app/src/main/
├── java/br/com/faculdade/imepac/
│   ├── SplashActivity.kt         # Tela de abertura
│   ├── FormLogin.kt              # Tela de login
│   ├── FormCadastro.kt           # Tela de cadastro de usuário
│   ├── TelaPrincipal.kt          # Menu principal
│   ├── TelaListaLivros.kt        # Lista paginada + busca em tempo real  ← v3.0
│   ├── TelaDetalheLivro.kt       # Detalhe, edição e exclusão de livro
│   ├── FormCadastroLivro.kt      # Formulário de cadastro de livro
│   ├── TelaPerfil.kt             # Perfil + foto de perfil selecionável  ← v3.0
│   └── AdapterLivros.kt          # Adapter com filtro de busca            ← v3.0
│
└── res/
    ├── layout/
    │   ├── activity_splash.xml
    │   ├── activity_form_login.xml
    │   ├── activity_form_cadastro.xml
    │   ├── activity_main.xml
    │   ├── activity_tela_principal.xml
    │   ├── activity_tela_lista_livros.xml    ← v3.0 (busca + botão voltar)
    │   ├── activity_tela_detalhe_livro.xml   ← v3.0 (botão voltar)
    │   ├── activity_tela_perfil.xml          ← v3.0 (avatar clicável + botão voltar)
    │   ├── activity_form_cadastro_livro.xml  ← v3.0 (botão voltar)
    │   └── item_livro.xml
    │
    └── drawable/
        ├── ic_launcher_foreground.xml        ← v3.0 (nova logo vetorial)
        ├── ic_launcher_background.xml        ← v3.0 (cor alinhada ao tema)
        ├── ic_livro_splash.xml               ← v3.0 (mesma identidade da logo)
        ├── ic_arrow_back.xml                 ← v3.0 (ícone de voltar)
        ├── ic_camera.xml                     ← v3.0 (badge do avatar)
        └── ...                               # demais ícones e shapes
```

---

## ⚙️ Como Executar

### Pré-requisitos

- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17+
- Dispositivo físico ou emulador com **API 24+**

### Passos

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/JotaJota404/imepac-joaoGabrielB.git
   cd imepac-joaoGabrielB
   ```

2. **Configure o Firebase:**
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
   - Ative **Authentication** (método: E-mail/Senha)
   - Ative **Cloud Firestore** (modo de produção ou teste)
   - Baixe o `google-services.json` e coloque dentro de `app/`

3. **Abra no Android Studio** e aguarde a sincronização do Gradle

4. **Execute** — conecte um dispositivo ou inicie um emulador e pressione **Shift + F10**

> ⚠️ **Importante:** O arquivo `google-services.json` não está versionado por segurança. Configure seu próprio projeto Firebase para executar o app.

---

## 🗄️ Estrutura do Banco de Dados (Firestore)

```
Firestore
├── Usuarios/
│   └── {documentId}
│       ├── nome:   String
│       ├── email:  String
│       └── uid:    String
│
└── Livros/
    └── {documentId}
        ├── titulo:    String
        ├── autor:     String
        ├── ano:       String
        └── timestamp: Date        ← usado para ordenação e paginação
```

---

## 📝 Histórico de Versões

### v3.1 — Segurança & Polimento *(atual)*
- ✅ Rename do app: "Meus Livros" → **"Livraria Pessoal"** em todas as telas
- ✅ Correção da foto de perfil: `ShapeableImageView` com clip circular real e `imageTintList = null`
- ✅ Validação de ano nos formulários (range 1000–ano atual)
- ✅ R8/ProGuard habilitado em builds de release (ofuscação + minificação)
- ✅ Regras ProGuard para Firebase (Authentication + Firestore)
- ✅ Logout seguro com `FLAG_ACTIVITY_CLEAR_TASK` (impede volta sem autenticar)
- ✅ SharedPreferences com validação de URI antes de carregar a foto

### v3.0 — Melhorias UX/UI_

- ✅ Busca de livros em tempo real (título, autor, ano)
- ✅ Foto de perfil selecionável da galeria com persistência local
- ✅ Botão de voltar em todas as telas secundárias (ergonomia por zona de toque)
- ✅ Nova logo vetorial unificada (launcher + dentro do app)
- ✅ Permissões de mídia para Android 13+ e anteriores

### v2.0 — CRUD & Design

- ✅ CRUD completo com Firebase Firestore
- ✅ Lista paginada com scroll infinito
- ✅ Design system _"Dark Library"_ completo
- ✅ Seed de dados de teste

### v1.0 — Base

- ✅ Autenticação Firebase (cadastro e login)
- ✅ Estrutura de Activities e navegação
- ✅ Layout inicial das telas

---

## 👨‍💻 Autor

<div align="center">

**João Gabriel B.**  
Graduando em Análise e Desenvolvimento de Sistemas — IMEPAC

</div>

---

<div align="center">

_Projeto acadêmico — Trabalho Final ADS — IMEPAC 2024/2026_

</div>
