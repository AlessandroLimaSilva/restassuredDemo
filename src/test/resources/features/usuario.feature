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
    When envio um novo usuario do tipo <tipo>
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
    When envio um novo usuario sem o nome de usuario
    Then o novo usuario nao e cadastrado com sucesso

  @automatizado @[Usuario-005]-NaoCriarNovoUsuarioComNomeJaUtilizado
  Scenario Outline: Nao criar novo usuario com nome ja utilizado
    And ja possuo um usuario com o nome <tipo> cadastrado
    When envio um usuario do tipo <tipo> ja cadastrado
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
    And possuo um usuario com o email <tipo> ja cadastrado
    When envio um usuario <tipo> com o email ja cadastrado
    Then o usuario com email ja utilizado nao e cadastrado
    And o usuario com email ja cadastrado e deletado da aplicacao
    Examples:
      | tipo          |
      | administrador |
      | gerente       |
      | desenvolvedor |
      | atualizador   |
      | relator       |
      | visualizador  |

  @automatizado @[Usuario-007]-ValidarJsonSchemaDeCriarNovoUsuarioComSucesso
  Scenario Outline: validar o json schema de criar novo usuario com sucesso
    When envio um novo usuario do tipo <tipo>
    Then o schema de criar novo usuario e validado com sucesso
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
    When envio um novo usuario minimo
    Then o usuario minimo e criado com sucesso
    And o usuario cadastrado e deletado da aplicacao

  @automatizado @[Usuario-009]-NaoCriarUsuarioMinimoSemNomeDeUsuario
  Scenario: nao criar usuario minimo sem nome de usuario
    When envio um novo usuario minimo sem o nome de usuario
    Then o usuario minimo nao e criado com sucesso

  @automatizado @[Usuario-010]-ValidarJsonSchemaDeCriarNovoUsuarioMinimoComSucesso
  Scenario: validar json schema de criar novo usuario minimo com sucesso
    When envio um novo usuario minimo
    Then json schema criar novo usuario minimo e validado com sucesso
    And o usuario cadastrado e deletado da aplicacao

  @automatizado @[Usuario-011]-DeletarUsuarioComSucesso
  Scenario Outline: deletar usuario com sucesso
    And que ja possuo o usuario <tipo> cadastrado na aplicacao
    When envio a requisicao com o id do usuario a ser deletado
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

  @CriarToken
  Scenario: criar toke para automacao
    When ennvio a requisicao

