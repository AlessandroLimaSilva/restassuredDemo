@projeto
Feature: Projeto

  @automatizado @[PR-001]ValidarOStatusCodeDeObterTodosOsProjetosCadastradosComSucesso
  Scenario: validar o status code de obter todos os projetos cadastrados com sucesso
    And que possuo um projeto cadastrado
    When envio a requisicao para obter todos os projetos
    Then o status code 200 e validado com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-002]ValidarSeTodosOsProjetosCadastradosForamRetornadosComSucesso
  Scenario: validar se todos os projetos cadastrados foram retornados com sucesso
    And que possuo um projeto cadastrado
    When envio a requisicao para obter todos os projetos
    Then todos os projetos sao retornados com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-003]ValidarJsonSchemaDeObterTodosOsProjetos
  Scenario: validar json schema de obter todos os projetos
    And que possuo um projeto cadastrado
    When envio a requisicao para obter todos os projetos
    Then json schema para obter todos os projetos e validado com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-004]ObterInformacoesDeUmProjetoComSucesso
  Scenario: obter informacoes de um projeto com sucesso
    And que possuo um projeto cadastrado
    When envio a requisicao com o id do projeto ser pesquisado
    Then o projeto e retornado com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-005]NaoObterUmProjetoComSucesso
  Scenario: nao obter um projeto com sucesso
    When que envio o id 999999 de um projeto que nao existe
    Then e retornado com sucesso que o projeto nao existe

  @automatizado @[PR-006]validarJsonSchemaDeObterInformacoesDeUmProjetoComSucesso
  Scenario: validar json schema de obter informacoes de um projeto com sucesso
    And que possuo um projeto cadastrado
    When envio a requisicao com o id do projeto ser pesquisado
    Then json schema para obter um projeto e validado com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-007]CriarUmNovoProjetoComSucesso
  Scenario: criar um novo projeto com sucesso
    When informo os dados necessarios para criar um novo projeto na requisicao
    Then o projeto e criado com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-008]AlterarUmProjetoComSucesso
  Scenario: alterar um projeto com sucesso
    And que possuo um projeto cadastrado
    When informo os dados para alterar o projeto
    Then o projeto e alterado com sucesso
    And deleto o projeto que foi cadastrado

  @automatizado @[PR-009]AlterarUmProjetoComSucesso
  Scenario: nao alterar um projeto com sucesso
    When informo na requisicao um id 999999 de projeto que nao existe
    Then e retornado com sucesso que o projeto nao existe

  @automatizado @[PR-010]ValidarStatusCodeDeUmarequisicaoComDadosInvalidosDeAlterarUmPropjetoNaRequisicaoComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de alterar um projeto na requisicao com sucesso
    When informo o id <dado> invalido na requisicao alterar um projeto
    Then e retornado o status code <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[PR-011]DeletarProjetoComSucesso
  Scenario: deletar projeto com sucesso
    And que possuo um projeto cadastrado
    When envio a requisicao com o id do projeto a ser excluido
    Then o projeto e deletado com sucesso

  @automatizado @[PR-012]NaoDeletarUmProjetoComSucesso
  Scenario: nao deletar um projeto nao cadastrado com sucesso
    When informo o id 999999 de um projeto nao cadastrado na requisicao
    Then e informado que nao existe um projeto com esse id com sucesso

  @automatizado @[PR-013]ValidarStatusCodeDeUmRequisicaoComDadosInvalidosDeExcluirProjetoComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de excluir projeto com sucesso
    When informo id <dado> invalido na requisicao de excluir um projeto
    Then e retornado status code <return> de requisicao invalida em excluir projeto com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @MontaOBancoDeDados
  Scenario: retorno todos os projetos
    And monto o banco de dados do teste
    When consulto todos os id's dos projetos
    Then os ids sao retornados com sucesso
