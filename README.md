# Framework de Teste de API com RestAssured em um Ambiente de Testes Docker

Neste tutorial, vamos explorar como configurar um ambiente de testes completo com Docker que contém os contêineres do MantisBT, MySQL e Jenkins, juntamente com o uso do framework de teste de API RestAssured. O ambiente de testes Docker fornecerá uma infraestrutura completa e escalável para executar testes de API do MantisBT API de forma automatizada e controlada.

## Ambiente de Testes Docker

O ambiente de testes Docker contém os seguintes contêineres:

- **MantisBT**: um sistema de gerenciamento de problemas e rastreamento de defeitos, usado para acompanhar e resolver problemas em um projeto de software.
- **MySQL**: um sistema de gerenciamento de banco de dados relacional, utilizado para armazenar e recuperar dados necessários para os testes.
- **Jenkins**: uma plataforma de automação de CI/CD (Integração Contínua/Entrega Contínua), que permite criar e executar pipelines de testes automatizados.

A combinação do RestAssured com o ambiente de testes Docker nos permite realizar testes completos de ponta a ponta em APIs RESTful, desde a interação com o banco de dados até a validação da interface do usuário.

Vamos começar a aproveitar os benefícios deste ambiente de teste poderoso para automatizar e validar suas APIs RESTful usando o framework RestAssured!


# Framework de Teste de API com RestAssured

Este é um projeto incrível de um framework de teste de API altamente eficiente, desenvolvido para simplificar e agilizar o processo de validação de APIs RESTful. Com base nas bibliotecas mais poderosas e populares do ecossistema Java, este framework oferece uma estrutura robusta e flexível para a automação de testes de API.

## Recursos Destacados

- **Cucumber**: Aproveite o poder do BDD (Behavior-Driven Development) com o Cucumber, uma biblioteca que permite escrever testes em uma linguagem de domínio específica (Gherkin) e executá-los em um formato legível para não desenvolvedores. Isso facilita a colaboração entre equipes técnicas e não técnicas.

- **RestAssured**: Simplifique a validação de APIs RESTful com o RestAssured, uma biblioteca Java poderosa e intuitiva que oferece uma sintaxe amigável para a criação de testes de integração de API. Através do RestAssured, você pode facilmente enviar requisições HTTP, validar respostas, extrair dados e muito mais.

- **JUnit 4**: Aproveite os recursos do JUnit 4, um framework de teste unitário amplamente utilizado para Java. Ele permite que você defina casos de teste individuais, organize-os em suítes de testes e execute-os com eficiência. O JUnit 4 também oferece recursos avançados, como anotações de configuração e assertivas poderosas.

- **Extent Reporter**: Desfrute de relatórios de testes detalhados e atraentes com o Extent Reporter, uma biblioteca que oferece recursos avançados de geração de relatórios. Com o Extent Reporter, você terá uma visão clara sobre o status dos testes executados, métricas de desempenho e resultados.

- **Jackson Databind**: O Jackson Databind é uma biblioteca de serialização e desserialização JSON muito popular no ecossistema Java. Com ele, você pode converter facilmente objetos Java em formato JSON e vice-versa. Ele fornece uma maneira simples e eficiente de trabalhar com dados JSON em seus testes de API.

## Organização do Projeto

Este projeto está estruturado de forma organizada para facilitar o desenvolvimento e a manutenção dos testes de API:

```
|-- src
|   |-- globalParameters.properties
|   |-- test
|       |-- java
|           |-- br.com.ale.restassuredDemo
|               |--  Base
|                   |-- RestBase.java
|               |--  Body
|                   |-- Body.java
|               |--  DAO
|                   |-- DAO.java
|               |--  Hooks
|                   |-- Hook.java
|               |--  Requests
|                   |-- Request.java
|               |-- StepDefinitions
|                   |-- StepDefinitions.java
|               |--  Tests
|                   |-- Test.java
|               |--  Types
|                   |-- Type.java
|               |-- utils
|                   |-- ApiUtils.java
|           |-- resources
|               |-- Schema.json
|               |-- extent.properties
|               |-- extentConfig
|                   |-- extent-html.xml 
|               |-- features
|                   |-- .features
|            

_tests.feature
|-- reports
```

## Configuração e Execução
    
### Pré-requisitos
### Hardware:
- Processador: Intel Core i7-7700HQ ou AMD Ryzen 5600G
- Memória RAM: 16 GB
- Armazenamento: 20GB de espaço livre
- Conectividade: Acesso à Internet estável

### Software:

#### S.O Ubuntu 22.04 : 
<details>
  <summary>Como instalar o Ubuntu 22.04</summary>

## Passo a passo para instalar o Ubuntu 22.04:

1. Faça o download da imagem ISO do Ubuntu 22.04 no [site oficial do Ubuntu](https://releases.ubuntu.com/22.04/).

2. Grave a imagem ISO em um disco ou crie um pendrive inicializável. Você pode usar ferramentas como o [Etcher](https://etcher.io/) ou o [Rufus](https://rufus.ie/) para criar um pendrive inicializável.

3. Insira o disco ou o pendrive inicializável no computador onde deseja instalar o Ubuntu 22.04.

4. Reinicie o computador e acesse a configuração de inicialização (geralmente pressionando "F2" ou "Del" durante a inicialização, mas pode variar dependendo do fabricante do seu computador).

5. Na configuração de inicialização, defina a opção de inicialização para o disco ou o pendrive inicializável.

6. Salve as configurações e saia da configuração de inicialização. O computador reiniciará e será inicializado a partir do disco ou do pendrive.

7. Siga as instruções na tela para instalar o Ubuntu 22.04. Você poderá escolher o idioma, definir as configurações regionais e particionar o disco, se necessário.

8. Durante a instalação, você também precisará fornecer informações como nome de usuário, senha e outros detalhes específicos.

9. Após a conclusão da instalação, reinicie o computador e inicialize no Ubuntu 22.04 a partir do disco rígido.

10. Parabéns! Agora você tem o Ubuntu 22.04 instalado no seu computador. Você pode começar a explorar e usar o sistema operacional.

</details>

#### OpenJDK 1.8 :
<details>
  <summary>Como instalar o OpenJDK 1.8 e configurar as variáveis de ambiente</summary>

## Passo a passo para instalar o OpenJDK 1.8 e configurar as variáveis de ambiente:

1. Abra o terminal pressionando "Ctrl + Alt + T" no teclado.

2. Execute o seguinte comando para instalar o OpenJDK 1.8:

   ```
   sudo apt update
   sudo apt install openjdk-8-jdk
   ```

3. Após a instalação, verifique se o Java foi instalado corretamente executando o seguinte comando:

   ```
   java -version
   ```

   Isso exibirá a versão do Java instalada no seu sistema. Deve mostrar algo semelhante a:

    ```
    openjdk version "1.8.x_xxx"
    OpenJDK Runtime Environment (build 1.8.x_xxx-ubuntu0.xx.xx.xx)
    OpenJDK 64-Bit Server VM (build 25.x_xxx-ubuntu0.xx.xx.xx, mixed mode)
    ```

4. Agora, vamos configurar as variáveis de ambiente para o OpenJDK 1.8. Abra o arquivo `/etc/environment` com um editor de texto usando o seguinte comando:

   ```
   sudo nano /etc/environment
   ```

5. Adicione a seguinte linha ao arquivo, substituindo `/usr/lib/jvm/java-8-openjdk-amd64` pelo caminho correto da instalação do OpenJDK 1.8 em seu sistema:

   ```
   JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
   ```

6. Pressione "Ctrl + X" para sair do editor de texto, pressione "Y" para confirmar as alterações e pressione Enter para salvar o arquivo com o mesmo nome.

7. Execute o seguinte comando para recarregar as variáveis de ambiente:

   ```
   source /etc/environment
   ```

8. Agora, vamos adicionar o diretório do Java ao PATH do sistema. Abra o arquivo `~/.bashrc` com um editor de texto usando o seguinte comando:

   ```
   nano ~/.bashrc
   ```

9. Adicione a seguinte linha ao final do arquivo:

   ```
   export PATH="$PATH:$JAVA_HOME/bin"
   ```

10. Pressione "Ctrl + X" para sair do editor de texto, pressione "Y" para confirmar as alterações e pressione Enter para salvar o arquivo com o mesmo nome.

11. Execute o seguinte comando para recarregar o arquivo `~/.bashrc`:

    ```
    source ~/.bashrc
    ```

12. Agora você tem o OpenJDK 1.8 instalado e as variáveis de ambiente configuradas corretamente. Você pode começar a usar o Java 8 em seu sistema.

</details>


#### Docker 20.10.24
<details>
  <summary>Como instalar o Docker no Ubuntu 22.04</summary>

## Passo a passo para instalar o Docker no Ubuntu 22.04:

1. Abra o terminal pressionando "Ctrl + Alt + T" no teclado.

2. Remova versões antigas do Docker, se existirem, executando o seguinte comando:

   ```
   sudo apt remove docker docker-engine docker.io containerd runc
   ```

3. Atualize a lista de pacotes com o seguinte comando:

   ```
   sudo apt update
   ```

4. Instale os pacotes necessários para permitir que o apt utilize repositórios via HTTPS, executando o seguinte comando:

   ```
   sudo apt install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
   ```

5. Adicione a chave GPG oficial do Docker executando o seguinte comando:

   ```
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
   ```

6. Adicione o repositório do Docker às fontes do APT, executando o seguinte comando:

   ```
   echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
   ```

7. Atualize a lista de pacotes novamente, agora incluindo o repositório do Docker, com o seguinte comando:

   ```
   sudo apt update
   ```

8. Agora, você pode instalar o Docker executando o seguinte comando:

   ```
   sudo apt install docker-ce docker-ce-cli containerd.io
   ```

9. Após a conclusão da instalação, verifique se o Docker foi instalado corretamente executando o comando:

   ```
   docker --version
   ```

   Isso exibirá a versão do Docker instalada no seu sistema.

10. Parabéns! Agora você tem o Docker instalado no Ubuntu 22.04. Você pode começar a usar o Docker para criar e gerenciar contêineres.

</details>

#### Intellij Idea Community version 2023.1.2
<details>
  <summary>Como instalar o IntelliJ IDEA Community</summary>

## Passo a passo para instalar o IntelliJ IDEA Community:

1. Abra o terminal pressionando "Ctrl + Alt + T" no teclado.

2. Faça o download do arquivo de instalação do IntelliJ IDEA Community Edition no [site oficial da JetBrains](https://www.jetbrains.com/idea/download).

3. Navegue até o local onde o arquivo de instalação foi baixado.

4. Extraia o arquivo de instalação usando o seguinte comando:

   ```
   tar -xzf nome_do_arquivo.tar.gz
   ```

5. Acesse o diretório extraído usando o seguinte comando:

   ```
   cd nome_do_diretorio
   ```

6. Execute o script de instalação usando o seguinte comando:

   ```
   ./bin/idea.sh
   ```

7. O IntelliJ IDEA Community será iniciado e você será guiado pelo assistente de configuração inicial.

8. Siga as instruções na tela para configurar suas preferências, como temas, plugins, esquemas de teclado, etc.

9. Após a configuração inicial, o IntelliJ IDEA Community Edition estará pronto para uso.

10. Parabéns! Agora você tem o IntelliJ IDEA Community Edition instalado no seu sistema. Você pode começar a desenvolver aplicativos usando o IntelliJ IDEA.

</details>

#### Firefox ou qualquer navegador de sua preferencia

#### Instalação do Firefox no Ubuntu 22.04
 <details>
        <summary>Clique aqui para ver o passo a passo de instalação</summary>

- #### Passo 1: Abrir o Terminal

    #### Abra o terminal no Ubuntu. Você pode fazer isso pressionando `Ctrl + Alt + T` no teclado ou pesquisando por "Terminal" no menu de aplicativos.

- #### Passo 2: Verificar a disponibilidade do Snap

    #### O Ubuntu 22.04 inclui o Snap por padrão. Para verificar se o Snap está instalado, execute o seguinte comando no terminal:
    ```bash
    snap version
    ```
    #### Se o Snap estiver instalado, você verá a versão do Snap instalada no seu sistema. Caso contrário, você pode instalar o Snap executando o seguinte comando :
    ```bash
    sudo apt update
    sudo apt install snapd
    ```
- #### Passo 3: Instalar o Firefox via Snap
  
    #### Para instalar o Firefox usando o Snap, execute o seguinte comando no terminal:
    ```bash
    sudo snap install firefox
    ```
    #### Digite Y ou S quando solicitado para confirmar a instalação. O sistema irá baixar e instalar o Firefox via Snap.  

- #### Passo 4: Verificar a Instalação

  #### Após a instalação, você pode verificar se o Firefox foi instalado corretamente executando o seguinte comando no terminal:    
  ```bash
    sudo snap install firefox
    ```
    
</details>

#### Portas de rede : 80, 8989, 3606, 8080, 8081, 50000

#### Usuario root no Ubuntu

## Configuração do ambiente de teste
Neste passo iremos configurar a estrutura de pastas do nosso ambiente, junto a isso iremos subir o nosso ambiente de teste.

Para começar a utilizar este framework de teste de API, siga estas etapas:
- ### Configurando a estrutura de arquivos e clonando o repositorio Git 
  1. Abra o terminal pressionando "Ctrl + Alt + T" no teclado.

     - #### crie uma pasta com o nome git em Documentos
        ```bash
        mkdir ~/Documentos/git/
        ```
     - #### crie uma pasta com o nome docker em Documentos
        ```bash
        mkdir ~/Documentos/docker/
        ``` 
     - #### Navegue até o diretório, no terminal
        ```bash
        cd ~/Documentos/git/
        ```
     - #### No terminal realize o clone do repositorio 
        ```bash
        git clone https://github.com/AlessandroLimaSilva/restassuredDemo.git
       ```
     - #### No terminal acesse a pasta
        ```bash
        cd ~/Documentos/git/restassuredDemo
        ```
     - #### Copie os arquivo docker-compose.yml, jenkinsFile, plugins.txt para a pasta Documentos/docker  
        ```bash
        cp ~/Documentos/git/restassuredDemo/docker-compose.yml ~/Documentos/docker/
        ```
     - #### Copie os arquivo docker-compose.yml, jenkinsFile, plugins.txt para a pasta Documentos/docker
        ```bash 
        cp ~/Documentos/git/restassuredDemo/jenkinsFile ~/Documentos/docker/
        ```
     - #### Copie os arquivo docker-compose.yml, jenkinsFile, plugins.txt para a pasta Documentos/docker
        ```bash
        cp ~/Documentos/git/restassuredDemo/plugins.txt ~/Documentos/docker/
        ```
- ### Iniciando o ambiente de teste  
  #### Agora que ja temos nossa estrutura de arquivos esta configurada podemos subir o ambiente
  1. Abra o terminal pressionando "Ctrl + Alt + T" no teclado.

      - #### Acesse a pasta Documentos/docker
         ```bash
         cd ~/Documentos/git/
         ```
      - #### Iniciando o Docker
         ```bash
         sudo docker-compose -f docker-compose.yml up -d
         ```
      - #### Aguarde o docker realizar o download dos container e suas dependencias, ao finalizar voce ira ver no terminal.
         ```bash
         [+] Running 4/4
          ✔ Network docker_my-networks   Created                                    0.1s
          ✔ Container docker-mysql-1     Started                                    2.7s
          ✔ Container jenkins            Started                                    2.9s
          ✔ Container docker-mantisbt-1  Started                                    1.3s
         ```
        
      - #### conferindo se o ambiente de teste foi iniciado corretamente
         ```bash
        sudo docker ps -a
        CONTAINER ID   IMAGE                      COMMAND                  CREATED              STATUS              PORTS                                                                                      NAMES
        e8b5c06810f2   vimagick/mantisbt:latest   "docker-php-entrypoi…"   About a minute ago   Up About a minute   0.0.0.0:8989->80/tcp, :::8989->80/tcp                                                      docker-mantisbt-1
        48a433718ca3   docker-jenkins             "/usr/bin/tini -- /u…"   About a minute ago   Up About a minute   0.0.0.0:50000->50000/tcp, :::50000->50000/tcp, 0.0.0.0:8081->8080/tcp, :::8081->8080/tcp   jenkins
        352cf52ae2a3   mysql:5.7                  "docker-entrypoint.s…"   About a minute ago   Up About a minute   0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp                                       docker-mysql-1
        ```
      - #### Ao final desse passo nosso ambiente ja esta online.

- ### Configurando as aplicações
    Agora que terminamos de configurar nossa estrutura de arquivos podemos configurar as aplicações em nosso ambiente de teste.
- ### Configurando o MantisBT
  #### 1. Abra um navegador e digite na barra de endereço
  #### [http://0.0.0.0:8989/admin/install.php](http://0.0.0.0:8989/admin/install.php)

  #### ou

  #### [http://localhost:8989/admin/install.php](http://localhost:8989/admin/install.php)

  #### 2. Configurando o banco de dados do MantisBT
  A tela de configuração da aplicação MantisBT sera apresentada  
  ![Texto alternativo](src/test/resources/readmeImg/mantisBT01.png)
  - Preencha os seguintes campos
  - #### Em type of database Selecione
    ```bash
    MySQL Improved
    ```
  - #### Em Hostname (for Database Server) informe
    ```bash
    mysql
    ```
  - #### Em Username (for Database) informe
    ```bash
    root
    ``` 
  - #### Em Password (for Database) informe
    ```bash
    root
    ```
  - #### Em Database name (for Database) informe
    ```bash
    root
    ``` 
  - #### Em Admin Username (to create Database if required) informe
    ```bash
    root
    ```
  - #### Em Admin Password (to create Database if required) informe
    ```bash
    root
    ```     
  - #### Em Default Time Zone selecione
    ```bash
    Sao Paulo
    ```   
  - #### Realize um click em Install/Upgrade Database
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT02.png)
  
  - #### A seguinte tela sera apresentada
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT03.png)
  - #### Agora clique em Back to administration
    A tela de configuração da aplicação MantisBT sera apresentada
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT04.png)
  
  #### 3. Obtendo o token de acesso a API do MantisBT
  Agora iremos obter o token de acesso que ira garantir que nossos testes tenha acesso a API do MantisBT.
  
  - #### Informe o seu nome de usuario 
    ```bash
    Administrator
    ``` 
  - #### Clique em entrar
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT05.png)  

  - #### Informe a sua senha 
    ```bash
    root
    ``` 
  - #### Clique em entrar
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT06.png)

  - #### Clique em Minha Conta
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT07.png) 

  - #### Clique em Tokens API
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT08.png)

  - #### Preencha o nome do token com Token, clique em criar token
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT09.png)

  - #### Copie e guarde o token, este token so é exibido uma vez se voce perder o token é necessario criar outro.
  - vYpgsCjNSf2AE-dVR2Xtqs9asBvXp2Qd
    ![Texto alternativo](src/test/resources/readmeImg/mantisBT10.png)
  - - #### Lembre-se iremos precisar do token nos passos seguintes

- ### Configurando o Jenkins
    #### 1. Abra o terminal pressionando "Ctrl + Alt + T" no teclado e digite.
    ```bash
    sudo docker ps -a
    ```
    #### Sera retornado as informações dos container
    ```bash
    code@Nitro:~$ sudo docker ps -a
    CONTAINER ID   IMAGE                      COMMAND                  CREATED       STATUS       PORTS                                                                                      NAMES
    e8b5c06810f2   vimagick/mantisbt:latest   "docker-php-entrypoi…"   4 hours ago   Up 4 hours   0.0.0.0:8989->80/tcp, :::8989->80/tcp                                                      docker-mantisbt-1
    48a433718ca3   docker-jenkins             "/usr/bin/tini -- /u…"   4 hours ago   Up 4 hours   0.0.0.0:50000->50000/tcp, :::50000->50000/tcp, 0.0.0.0:8081->8080/tcp, :::8081->8080/tcp   jenkins
    352cf52ae2a3   mysql:5.7                  "docker-entrypoint.s…"   4 hours ago   Up 4 hours   0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp                                       docker-mysql-1
    ```    
    #### Iremos copiar o CONTAINER ID do nosso container do Jenkins e no terminal e iremos digitar.
    #### sudo docker exec -it CONTAINER ID bash
    ```bash
    sudo docker exec -it 48a433718ca3 bash 
    ```
    #### Agora estamos acessando o container do jenkins pelo terminal, com isso iremos copiar o password para acessar o jenkins no terminal e digitamos.
    ```bash
    cat /var/jenkins_home/secrets/initialAdminPassword 
    ```
    ![Texto alternativo](src/test/resources/readmeImg/jenkins01.png)
    #### Copie o password, precisaremos dele para acessar o jenkins

    #### 2. Abra um navegador e digite na barra de endereço
    #### [http://0.0.0.0:8081/login?from=%2F](http://0.0.0.0:8081/login?from=%2F)

    #### ou

    #### [http://localhost:8081/login?from=%2F](http://localhost:8081/login?from=%2F)
    #### Vamos informar a senha de administrador e clicar em Continuar
    ![Texto alternativo](src/test/resources/readmeImg/jenkins02.png)
    
    #### Clique em Instalar as extensões sugeridas, as extensões necessarias ja foram adicionadas no container.
    ![Texto alternativo](src/test/resources/readmeImg/jenkins03.png)
    ![Texto alternativo](src/test/resources/readmeImg/jenkins04.png)
    #### Agora iremos preencher os campos para criar um usuario administrativo
    - #### Em Nome de usuario informe
    ```bash
    root
    ```
    - #### Em Senha informe
    ```bash
    root
    ```
    - #### Em Confirmar Senha informe
    ```bash
    root
    ```
    - #### Em Nome Completo informe
    ```bash
    root root
    ```
    - #### Em Endereço de Email informe
    ```bash
    root@root.com
    ```
    - #### Clique em Salvar e continuar
    ![Texto alternativo](src/test/resources/readmeImg/jenkins05.png)
    - #### Clique em Gravar e Concluir
    ![Texto alternativo](src/test/resources/readmeImg/jenkins06.png)
    - #### Clique em Reiniciar
    ![Texto alternativo](src/test/resources/readmeImg/jenkins07.png)
    - #### Aguarde o jenkins realizar as configurações, se dentro de 5 minutos a pagina não recarregar sozinha aperte F5 para recarregar a pagina.
    ![Texto alternativo](src/test/resources/readmeImg/jenkins08.png)

  #### 3. Agora iremos configurar o jenkins para executar os nossos testes.

    

3. Importe o projeto em sua IDE preferida como um projeto Maven existente.
3. Certifique-se de que todas as dependências do Maven sejam baixadas corretamente.
4. Explore os arquivos de teste e personalização para atender às necessidades específicas do seu projeto.
5. Execute os testes usando a opção de execução de teste fornecida pela sua IDE ou via linha de comando com o comando `mvn test`.
6. Desfrute dos relatórios detalhados gerados automaticamente no diretório `reports`.

## Personalização e Expansão

Este framework de teste de API é altamente personalizável e pode ser facilmente expandido para atender às necessidades exclusivas do seu projeto. Você pode adicionar novos casos de teste, criar classes utilitárias adicionais e integrar bibliotecas adicionais conforme necessário.

## Suporte

Se você precisar de suporte ou quiser contribuir para aprimorar este framework de teste de API, não hesite em entrar em contato com [nome_do_contato] em [email_do_contato].

Aproveite o uso deste framework de teste de API com RestAssured, Cucumber, JUnit 4, Extent Reporter e Jackson Databind, e obtenha resultados eficientes e confiáveis em seus testes de API!

### Teste automatizado de API da aplicação MantisBT
#### - [ ] Tarefa concluída
#### - [x] Tarefas pendentes
#### - [x] Implementar no minimo 50 Casos de Testes
#### - [x] Implementação de Data-Driven pelo Cucumber
#### - [x] Autenticação da API atraves do headers
#### - [] pelo menos uma validação por regex
#### - [x] groovy nos asserts pegar o nome da biblioteca
#### - [x] Relatorio de testes extent reporter
#### - [x] pelo menos dois ambientes local, jenkins
#### - [x] Massa de teste condicionada No Mysql, inserida antes dos testes
#### - [x] Testes em paralelo dividos em 4 threads,
#### - [x] Execução ambiente montado em docker com execução no jenkins