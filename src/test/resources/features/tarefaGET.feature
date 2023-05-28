Feature: Tarefa GET

  @automatizado @[ISG-001]ObterUmaTarefaComSucesso
  Scenario: obter uma tarefa com sucesso
    And que possuo uma tarefa cadastrada
    When envio uma requisicao com o id da tarefa
    Then a tarefa e retornada com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISG-002]NaoObterUmaTarefaComSucesso
  Scenario: nao obter uma tarefa com sucesso
    When que envio uma requisicao com o id 99999 de uma tarefa que nao existe
    Then a aplicacao retorna que a tarefa com o id 99999 nao foi encontrado com sucesso

  #Bug a requisicao so deveria aceitar o id um int como elemento de busca !
  @automatizado @[ISG-003]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosDeObterUmatarefaComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de obter uma tarefa com sucesso
    When que informo um dado <dado> invalido na requisicao de obter uma tarefa
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[ISG-004]ValidarJsonSchemaDeObterUmaTarefaComSucesso
  Scenario: validar json schema de obter uma tarefa com sucesso
    And que possuo uma tarefa cadastrada
    When envio uma requisicao com o id da tarefa
    Then o json schema de obter uma tarefa com sucesso e validado com sucesso

  @automatizado @[ISG-005]ObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario: obter uma tarefa com arquivos cadastrados com sucesso
    And que possuo um projeto com uma tarefa com arquivos cadastrados
    When envio uma requisicao de tarefa que possua arquivos
    Then a tarefa com arquivos e retornada com sucesso

  #Bug nao retorna 404 e que não encontrou os arquivos
  @automatizado @[ISG-006]NaoObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario: nao obter uma tarefa com arquivos cadastrados com sucesso
    And que possuo um projeto cadastrado que nao contem arquivos
    When envio uma requisicao com o id de uma tarefa que nao contem arquivo
    Then e retornado que a tarefa nao existe com sucesso

  #Bug nao reconhece a bad request,
  @automatizado @[ISG-007]ValidarStatusCodeDeObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario Outline: validar status code de obter uma tarefa com arquivos cadastrados com sucesso
    When envio uma requisicao com id <dado> invalido de obter uma tarefa com arquivos cadastrados
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[ISG-008]ValidarJsonSchemaDeObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario: validar json schema de obter uma tarefa com arquivos cadastrados com sucesso
    And que possuo um projeto com uma tarefa com arquivos cadastrados
    When envio uma requisicao de tarefa que possua arquivos
    Then o json schema de obter uma tarefa com arquivos cadastrados e validada com sucesso

  @automatizado @[ISG-009]ObterUmArquivoDeUmaTarefaComSucesso
  Scenario: obter um arquivo de uma tarefa com sucesso
    And que possuo um projeto com uma tarefa com arquivos cadastrados
    When envio uma requisicao com o id da tarefa e id do arquivo
    Then a tarefa com arquivo e retornada com sucesso

  @automatizado @[ISG-010]NaoObterUmArquivoDeUmaTarefaComSucesso
  Scenario: nao obter um arquivo de uma tarefa com sucesso
    And que possuo um projeto cadastrado que nao contem arquivos
    When envio uma requisicao com o id de uma tarefa que nao possue arquivo e id de arquivo invalido
    Then e retornado que a tarefa nao existe com sucesso

  #Bug menssagem retornada não é compativel com o tipo de erro
  #{
  #    "message": "'issue_id' must be >= 1",
  #    "code": 29,
  #    "localized": "Invalid value for 'issue_id'"
  #}
  @automatizado @[ISG-011]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosDeObterUmArquivoDeUmaTarefaComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de obter um arquivo de uma tarefa com sucesso
    When envio uma requisicao com id <dado> de tarefa e id <id> arquivo invalido
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return | id |
      | adminstrador | 400    | 1  |
      | 9+*/1adeedff | 400    | 2  |
      | ???????????9 | 400    | 3  |

  @automatizado @[ISG-012]ValidarJsonSchemaDeObterUmArquivoDeUmaTarefaComSucesso
  Scenario: validar json schema de obter um arquivo de uma tarefa com sucesso
    And que possuo um projeto com uma tarefa com arquivos cadastrados
    When envio uma requisicao com o id da tarefa e id do arquivo
    Then o json schema de obter um arquivo de uma tarefa e validado com sucesso

  @automatizado @[ISG-013]ObterTodasAsTarefasComSucesso
  Scenario: obter todas as tarefas com sucesso
    And que possuo uma tarefa cadastrada
    When envio uma requisicao informando a quantidade de tarefas e paginas
    Then as tarefas sao retornadas com sucesso

  @automatizado @[ISG-014]ValidarOJsonSchemaDeObterTodasAsTarefasComSucesso
  Scenario: validar o json schema de obter todas as tarefas com sucesso
    And que possuo uma tarefa cadastrada
    When envio uma requisicao informando a quantidade de tarefas e paginas
    Then o json schema de obter todas as tarefas e validado com sucesso

  @automatizado @[ISG-015]ObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario: obter todas as tarefas de um projeto com sucesso
    And que possuo uma tarefa cadastrada
    When envio uma requisicao de um projeto informando o id
    Then as tarefas do projeto sao retornadas com sucesso

  @automatizado @[ISG-016]NaoObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario: nao obter todas as tarefas de um projeto com sucesso
    When envio uma requisicao com o id 99999999999 de um projeto que nao existe
    Then e retornado que o projeto nao existe com sucesso

  @automatizado @[ISG-017]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosDeObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de obter todas as tarefas de um projeto com sucesso
    When envio uma requisicao de obter todas as tarefas de um projeto com id <dado> invalido
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[ISG-018]ValidarJsonSchemaDeObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario: validar json schemas de obter todas as tarefas de um projeto com sucesso
    And que possuo uma tarefa cadastrada
    When envio uma requisicao de um projeto informando o id
    Then o json schema de obter todas as tarefas de um projeto e validado com sucesso

  @automatizado @[ISG-019]ObterTarefasRelacionadasAUmFiltro
  Scenario: obter as tarefas relacionadas a um filtro
    And que possuo uma tarefa cadastrada
    When envio uma requisicao informando o id do filtro de tarefas
    Then as tarefas pertencentes ao filtro sao retornadas com sucesso

  @automatizado @[ISG-020]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosNaRequisicaoComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos na requisicao com sucesso
    When envio uma requisicao informando um dado <dado> invalido do filtro
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | 999999999999 | 404    |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |