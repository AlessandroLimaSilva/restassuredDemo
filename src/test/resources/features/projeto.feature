Feature: Projeto

  @automatizado @[PR-001]ValidarOStatusCodeDeObterTodosOsProjetosCadastradosComSucesso
  Scenario: validar o status code de obter todos os projetos cadastrados com sucesso
    Given que exista um projeto cadastrado
    When uma requisição é enviada para obter todos os projetos
    Then o status code 200 e retornado com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-002]ValidarSeTodosOsProjetosCadastradosForamRetornadosComSucesso
  Scenario: validar se todos os projetos cadastrados foram retornados com sucesso
    Given que exista um projeto cadastrado
    When uma requisição é enviada para obter todos os projetos
    Then todos os projetos sao retornados com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-003]ValidarJsonSchemaDeObterTodosOsProjetos
  Scenario: validar json schema de obter todos os projetos
    Given que exista um projeto cadastrado
    When uma requisição é enviada para obter todos os projetos
    Then json schema para obter todos os projetos e validado com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-004]ObterInformacoesDeUmProjetoComSucesso
  Scenario: obter informacoes de um projeto com sucesso
    Given que exista um projeto cadastrado
    When uma requisição é enviada com o id do projeto a ser pesquisado
    Then o projeto e retornado com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-005]NaoObterUmProjetoComSucesso
  Scenario: nao obter um projeto com sucesso
    When uma requisição é enviada com o id 999999 de um projeto que nao existe
    Then e retornado com sucesso que o projeto nao existe

  @automatizado @[PR-006]validarJsonSchemaDeObterInformacoesDeUmProjetoComSucesso
  Scenario: validar json schema de obter informacoes de um projeto com sucesso
    Given que exista um projeto cadastrado
    When uma requisição é enviada com o id do projeto a ser pesquisado
    Then json schema para obter um projeto e validado com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-007]CriarUmNovoProjetoComSucesso
  Scenario: criar um novo projeto com sucesso
    When uma requisição é enviada com os dados necessarios para criar um novo projeto
    Then o projeto e criado com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-008]AlterarUmProjetoComSucesso
  Scenario: alterar um projeto com sucesso
    Given que exista um projeto cadastrado
    When uma requisição é enviada com os dados para alterar o projeto
    Then o projeto e alterado com sucesso
    And o projeto cadastrado é deletado

  @automatizado @[PR-009]NaoAlterarUmProjetoComSucesso
  Scenario: nao alterar um projeto com sucesso
    When uma requisição é enviada informando o id 999999 de um projeto que nao existe
    Then e retornado com sucesso que o projeto nao existe

  @automatizado @[PR-010]ValidarStatusCodeDeUmarequisicaoComDadosInvalidosDeAlterarUmProjeto
  Scenario Outline: validar status code de uma requisicao com dados invalidos de alterar um projeto
    When uma requisição para alterar um projeto é enviada informando o id <dado> invalido
    Then e retornado o status code <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[PR-011]DeletarProjetoComSucesso
  Scenario: deletar projeto com sucesso
    Given que exista um projeto cadastrado
    When uma requisição é enviada com o id do projeto a ser excluido
    Then o projeto e deletado com sucesso

  @automatizado @[PR-012]NaoDeletarUmProjetoComSucesso
  Scenario: nao deletar um projeto nao cadastrado com sucesso
    When uma requisição é enviada com o id 999999 de um projeto nao cadastrado
    Then e retornado que nao existe um projeto com esse id com sucesso

  @automatizado @[PR-013]ValidarStatusCodeDeUmRequisicaoComDadosInvalidosDeExcluirProjetoComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de excluir projeto com sucesso
    When uma requisição para excluir um projeto é enviada informando o id <dado> invalido
    Then e retornado status code <return> de requisicao invalida em excluir projeto com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @MontaOBancoDeDados
  Scenario: whats is a man
    When a miserable little pile of secrets
    Then Dracula Conde
