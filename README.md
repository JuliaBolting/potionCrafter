# 🧪 PotionCrafter: Simulador de Poções

## 📌 Sobre o Projeto

**PotionCrafter** é um aplicativo Android onde o jogador assume o papel de um alquimista. Ele coleta ingredientes com diferentes raridades, combina-os para criar poções mágicas, e salva suas criações em um grimório. O app foi desenvolvido como parte da disciplina **Laboratório de Desenvolvimento de Sistemas (LDS)** do curso de Ciência da Computação do IFSC - Campus Lages.

## 🚀 Funcionalidades Implementadas

- Cadastro de novo jogador.
- Coleta e exibição de ingredientes com imagem e raridade.
- Criação de poções a partir de até 3 ingredientes.
- Salvamento automático de receitas criadas no banco de dados.
- Grimório de receitas com visualização de poções e ingredientes utilizados.
- Geração de relatório em **PDF** com as poções criadas.
- Internacionalização: suporte a **português**, **inglês** e **espanhol**.
- Interface responsiva e estilizada com **Jetpack Compose**.
- Persistência local com **Room (ORM)**.

## 🛠️ Tecnologias Utilizadas

- **Kotlin**
- **Android Studio**
- **Jetpack Compose**
- **Room (Jetpack ORM)**
- **Material Design 3**
- **Arquitetura MVVM**

## 📥 Como Instalar

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/PotionCrafter.git
   ```
2. Abra o projeto no Android Studio.
3. Conecte um dispositivo físico ou emululador.
4. Clique em **Run > Run 'app'** para executar o app.

> Obs.: O app requer permissões de armazenamento (para salvar o PDF em dispositivos com Android 10 ou inferior).

## 📌 Funcionalidades Desejadas e Ainda Não Implementadas (ToDo)

- Compartilhar poções entre jogadores.
- Sistema de ranking baseado em raridade das poções.
- Modo história ou desafios diários.
- Nuvem para salvar poções online.
