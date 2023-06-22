@usuario
Feature: Usuario

  @automatizado @[Usuario-001]-ObterMinhasInformacoesDeUsuarioComSucesso
  Scenario: obter minhas informacoes de usuario com sucesso
    When envio a requisicao sobre minhas informacoes de usuario
    Then recebo minhas informacoes de usuario com sucesso

  @automatizado @[Usuario-002]-ValidarJsonSchemaDeMinhasInformacoesDeUsuarioComSucesso
    Scenario: validar json schema de minhas informacoes de usuario
    When envio a requisicao sobre minhas informacoes de usuario
    Then json schema de obter minhas informacoes de usuario e validado com sucesso

  @automatizado @[Usuario-003]-CriarNovoUsuarioComSucesso
  Scenario Outline: Criar novo usuario com sucesso
    When um usuario tenha enviado a requisição de um novo usuario do tipo <tipo>
    Then o usuario e criado com sucesso
    And o usuario cadastrado e deletado da aplicacao
    Examples:
      | tipo          |
      | administrador |
      | gerente       |
      | desenvolvedor |
      | atualizador   |
      | relator       |
      | visualizador  |

  @automatizado @[Usuario-004]-NaoCriarUsuarioSemNomeDeUsuario
  Scenario: nao criar usuario sem nome de usuario
    When um usuario tenha enviado uma requisicao de novo usuario sem o nome de usuario
    Then o novo usuario nao e cadastrado com sucesso

  @automatizado @[Usuario-005]-NaoCriarNovoUsuarioComNomeJaUtilizado
  Scenario Outline: Nao criar novo usuario com nome ja utilizado
    Given que exista um usuario com o nome <tipo> cadastrado
    When uma requisição para novo usuario com o nome <tipo> ja utilizado é enviada
    Then o usuario com o nome de usuario ja utilizado nao e cadastrado
    And o usuario cadastrado e deletado da aplicacao
    Examples:
      | tipo          |
      | administrador |
      | gerente       |
      | desenvolvedor |
      | atualizador   |
      | relator       |
      | visualizador  |

  @automatizado @[Usuario-006]-NaoCriarNovoUsuarioComEmailJaUtilizado
  Scenario Outline: Nao criar novo usuario com email ja utilizado
    Given que exista um usuario com o email <email> cadastrado
    When uma requisição para novo usuario com o email <email> ja utilizado é enviada
    Then o usuario com email ja utilizado nao e cadastrado
    And o usuario com email ja cadastrado e deletado da aplicacao
    Examples:
      | email                       |
      | UserAdministrador@teste.com |
      | UserGerente@teste.com       |
      | UserDesenvolvedor@teste.com |
      | UserAtualizador@teste.com   |
      | UserRelator@teste.com       |
      | UserVisualizador@teste.com  |

  @automatizado @[Usuario-007]-ValidarJsonSchemaDeCriarNovoUsuarioComSucesso
  Scenario Outline: validar o json schema de criar novo usuario com sucesso
    When um usuario tenha enviado a requisição de um novo usuario do tipo <tipo>
    Then o json schema ao criar novo usuario e validado com sucesso
    And o usuario cadastrado e deletado da aplicacao
    Examples:
      | tipo          |
      | administrador |
      | gerente       |
      | desenvolvedor |
      | atualizador   |
      | relator       |
      | visualizador  |

  @automatizado @[Usuario-008]-CriarNovoUsuarioMinimoComSucesso
  Scenario: criar novo usuario minimo com sucesso
    When um usuario envia uma requisicao de um novo usuario "minimo"
    Then o usuario minimo e criado com sucesso
    And o usuario cadastrado e deletado da aplicacao

  @automatizado @[Usuario-009]-NaoCriarUsuarioMinimoSemNomeDeUsuario
  Scenario: nao criar usuario minimo sem nome de usuario
    When um usuario envia uma requisicao de um novo usuario minimo sem o nome de usuario
    Then o usuario minimo nao e criado com sucesso

  @automatizado @[Usuario-010]-ValidarJsonSchemaDeCriarNovoUsuarioMinimoComSucesso
  Scenario: validar json schema de criar novo usuario minimo com sucesso
    When um usuario envia uma requisicao de um novo usuario "minimo"
    Then o json schema criar novo usuario minimo e validado com sucesso
    And o usuario cadastrado e deletado da aplicacao

  @automatizado @[Usuario-011]-DeletarUsuarioComSucesso
  Scenario Outline: deletar usuario com sucesso
    Given que exista um usuario <tipo> cadastrado na aplicacao
    When um usuario envia uma requisicao com o id do usuario a ser deletado
    Then o usuario e deletado com sucesso
    And o usuario cadastrado e deletado da aplicacao
    Examples:
      | tipo          |
      | administrador |
      | gerente       |
      | desenvolvedor |
      | atualizador   |
      | relator       |
      | visualizador  |
