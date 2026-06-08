# 📚 Meus Livros — Catálogo Pessoal de Livros

<div align="center">
  <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img alt="Firebase" src="https://img.shields.io/badge/Firebase-%23039BE5.svg?style=for-the-badge&logo=firebase" />
  <img alt="Firestore" src="https://img.shields.io/badge/Firestore-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" />
  <img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img alt="Material Design" src="https://img.shields.io/badge/Material%20Design%203-757575?style=for-the-badge&logo=material-design&logoColor=white" />
</div>

<br/>

<div align="center">
  <strong>Trabalho Final de Análise e Desenvolvimento de Sistemas — IMEPAC</strong><br/>
  Aplicativo Android nativo para gerenciamento de catálogo de livros pessoal,<br/>
  com autenticação Firebase, CRUD completo no Firestore e design "Dark Library".
</div>

---

## 📋 Sobre o Projeto

**Meus Livros** é um aplicativo mobile nativo Android desenvolvido como **Trabalho Final** da disciplina de desenvolvimento mobile do curso de Análise e Desenvolvimento de Sistemas (ADS) — IMEPAC.

O projeto foi entregue em **duas partes**:

| Parte | Foco | Status |
|---|---|---|
| **Parte 1** | Arquitetura base: autenticação Firebase, estrutura de Activities, navegação | ✅ Concluída |
| **Parte 2** | CRUD completo, listas paginadas, design profissional "Dark Library" | ✅ Concluída |

---

## ✨ Funcionalidades

### 🔒 Autenticação
- Cadastro de usuário com nome, e-mail e senha via **Firebase Authentication**
- Login com validação de credenciais e tratamento de erros específicos (`FirebaseAuthInvalidUserException`, `FirebaseAuthInvalidCredentialsException`)
- Sessão persistente: se o usuário já está autenticado, é redirecionado direto ao menu
- Logout seguro com limpeza de sessão

### 📚 Gerenciamento de Livros (CRUD Completo)
- **Create:** Cadastro de livros com título, autor e ano de publicação
- **Read:** Listagem paginada com `RecyclerView` + paginação incremental no Firestore
- **Update:** Edição completa dos dados de qualquer livro da lista
- **Delete:** Exclusão com diálogo de confirmação (`AlertDialog`)

### 📱 Telas (8 Activities)
| # | Tela | Descrição |
|---|---|---|
| 1 | `SplashActivity` | Tela de abertura com logo animado |
| 2 | `FormLogin` | Login com e-mail e senha |
| 3 | `FormCadastro` | Criação de conta de usuário |
| 4 | `TelaPrincipal` | Menu principal com navegação |
| 5 | `TelaListaLivros` | Lista paginada de livros cadastrados |
| 6 | `TelaDetalheLivro` | Visualização, edição e exclusão de livro |
| 7 | `TelaPerfil` | Dados do usuário logado e opção de logout |
| 8 | `FormCadastroLivro` | Formulário para adicionar novo livro |

### ⚙️ Extras
- **Seed de dados:** Botão para popular o banco com 15 livros clássicos de teste
- **Paginação:** Carregamento incremental da lista com `ProgressBar` de rodapé

---

## 🎨 Design System — "Dark Library"

O app adota o tema **"Dark Library"**, inspirado na estética premium de apps literários como Kindle e Apple Books, implementado com **Material Design 3** e recursos XML nativos do Android — sem bibliotecas de UI externas.

### Paleta de Cores

| Token | Nome | Hex | Uso |
|---|---|---|---|
| `fundo_escuro` | Azul Meia-Noite | `#12122A` | Fundo de todas as telas |
| `fundo_card` | Azul Escuro Suave | `#1E1E3F` | Cards e containers |
| `primaria` | Âmbar Dourado | `#F5A623` | Botões, títulos, destaques |
| `primaria_escura` | Âmbar Queimado | `#C17D0F` | Gradiente (fim) |
| `texto_principal` | Branco Quente | `#F0EDE6` | Textos em geral |
| `texto_secundario` | Cinza Lavanda | `#9E9EC0` | Subtítulos e hints |
| `perigo` | Vermelho Coral | `#E05252` | Excluir e Sair |
| `sucesso` | Verde Menta | `#4ECDC4` | Confirmações |

### Componentes de Design
- **Botão primário:** Gradiente âmbar horizontal com `selector` (3 estados: normal, pressionado, desabilitado)
- **Botão de perigo:** Gradiente vermelho coral para ações destrutivas
- **Cards:** Fundo escuro + borda âmbar semitransparente (efeito glassmorphism sem biblioteca)
- **Card da lista:** Faixa âmbar vertical (4dp) na borda esquerda + ícone placeholder de capa
- **Campos de texto:** Fundo escuro com borda âmbar que intensifica ao focar
- **Avatar:** Círculo com borda âmbar de 3dp
- **Ícones:** 8 VectorDrawables criados nativamente (sem assets externos)

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão / Detalhe |
|---|---|
| **Linguagem** | Kotlin |
| **IDE** | Android Studio |
| **Autenticação** | Firebase Authentication |
| **Banco de Dados** | Cloud Firestore (NoSQL) |
| **Design System** | Material Design 3 (`Theme.Material3.Dark`) |
| **Layouts** | ConstraintLayout, LinearLayout, CardView |
| **Lista** | RecyclerView com Adapter customizado |
| **Feedback UI** | Snackbar, ProgressBar, AlertDialog |
| **Controle de Versão** | Git & GitHub |

---

## 📁 Estrutura do Projeto

```
app/src/main/
├── java/br/com/faculdade/imepac/
│   ├── SplashActivity.kt       # Tela de abertura
│   ├── FormLogin.kt            # Tela de login
│   ├── FormCadastro.kt         # Tela de cadastro de usuário
│   ├── TelaPrincipal.kt        # Menu principal
│   ├── TelaListaLivros.kt      # Lista paginada de livros
│   ├── TelaDetalheLivro.kt     # Detalhe/edição/exclusão de livro
│   ├── FormCadastroLivro.kt    # Formulário de cadastro de livro
│   ├── TelaPerfil.kt           # Perfil do usuário
│   └── AdapterLivros.kt        # Adapter da RecyclerView
│
└── res/
    ├── layout/                 # 8 layouts de telas + 1 item de lista
    ├── drawable/               # Shapes, gradientes e 8 ícones vetoriais
    └── values/
        ├── colors.xml          # Paleta "Dark Library"
        ├── styles.xml          # Estilos globais (button, edit_text, container)
        └── themes.xml          # Tema Material3 Dark
```

---

## ⚙️ Como Executar

### Pré-requisitos
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17+
- Dispositivo físico ou emulador com API 24+

### Passos

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/JotaJota404/imepac-joaoGabrielB.git
   cd imepac-joaoGabrielB
   ```

2. **Configure o Firebase:**
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
   - Ative **Authentication** (método: E-mail/Senha)
   - Ative **Cloud Firestore**
   - Baixe o arquivo `google-services.json` e coloque em `app/`

3. **Abra no Android Studio** e aguarde a sincronização do Gradle

4. **Execute:** conecte um dispositivo ou inicie um emulador e pressione **Shift + F10**

> ⚠️ **Importante:** O arquivo `google-services.json` não está versionado por segurança. É obrigatório configurar seu próprio projeto Firebase para executar o app.

---

## 🗄️ Estrutura do Banco de Dados (Firestore)

```
Firestore
├── Usuarios/
│   └── {documentId}
│       ├── nome: String
│       ├── email: String
│       └── uid: String
│
└── Livros/
    └── {documentId}
        ├── titulo: String
        ├── autor: String
        ├── ano: String
        └── timestamp: Date
```

---

## 📸 Capturas de Tela

> *Tema "Dark Library" — Âmbar Dourado sobre Azul Meia-Noite*

| Splash | Login | Menu | Lista de Livros |
|---|---|---|---|
| Logo vetorial âmbar | Card escuro + campos | Cards de navegação | RecyclerView paginada |

---

## 👨‍💻 Autor

**João Gabriel B.**
Graduando em Análise e Desenvolvimento de Sistemas — IMEPAC

---

*Projeto acadêmico — Trabalho Final ADS — IMEPAC 2025/2026*
