Feature: Tarefa GET

  @automatizado @[ISG-001]ObterUmaTarefaComSucesso
  Scenario: obter uma tarefa com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisição é enviada com o id da tarefa
    Then a tarefa e retornada com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISG-002]NaoObterUmaTarefaComSucesso
  Scenario: nao obter uma tarefa com sucesso
    When uma requisição é enviada com o id 99999 de uma tarefa que nao existe
    Then é retornado que a tarefa com id 99999 invalido não foi encontrado com sucesso

  @automatizado @[ISG-003]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosDeObterUmatarefaComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de obter uma tarefa com sucesso
    When uma requisição para obter uma tarefa é enviada informando um dado <dado> invalido
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[ISG-004]ValidarJsonSchemaDeObterUmaTarefaComSucesso
  Scenario: validar json schema de obter uma tarefa com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisição é enviada com o id da tarefa
    Then o json schema de obter uma tarefa com sucesso e validado com sucesso

  @automatizado @[ISG-005]ObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario: obter uma tarefa com arquivos cadastrados com sucesso
    Given que exista um projeto com uma tarefa com arquivos cadastrados
    When uma requisição de tarefa com arquivos é enviada
    Then a tarefa com arquivos e retornada com sucesso

  @automatizado @[ISG-006]NaoObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario: nao obter uma tarefa com arquivos cadastrados com sucesso
    Given que exista uma tarefa cadastrada que nao contem arquivos
    When uma requisição com o id de uma tarefa que nao contem arquivo é enviada
    Then e retornado que a tarefa nao existe com sucesso

  #Bug nao reconhece a bad request,
  @automatizado @[ISG-007]ValidarStatusCodeDeObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario Outline: validar status code de obter uma tarefa com arquivos cadastrados com sucesso
    When uma requisicao de obter uma tarefa com arquivos cadastrados com id <dado> invalido é enviada
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[ISG-008]ValidarJsonSchemaDeObterUmaTarefaComArquivosCadastradosComSucesso
  Scenario: validar json schema de obter uma tarefa com arquivos cadastrados com sucesso
    Given que exista um projeto com uma tarefa com arquivos cadastrados
    When uma requisição de tarefa com arquivos é enviada
    Then o json schema de obter uma tarefa com arquivos cadastrados e validada com sucesso

  @automatizado @[ISG-009]ObterUmArquivoDeUmaTarefaComSucesso
  Scenario: obter um arquivo de uma tarefa com sucesso
    Given que exista um projeto com uma tarefa com arquivos cadastrados
    When uma requisicao de obter um arquivo de uma tarefa com o id da tarefa e id do arquivo e enviada
    Then a tarefa com arquivo e retornada com sucesso

  @automatizado @[ISG-010]NaoObterUmArquivoDeUmaTarefaComSucesso
  Scenario: nao obter um arquivo de uma tarefa com sucesso
    Given que exista uma tarefa cadastrada que nao contem arquivos
    When uma requisição de obter um arquivo de uma tarefa é enviada com o id da tarefa que nao possue arquivo e o id do arquivo invalido
    Then e retornado que a tarefa nao existe com sucesso

  @automatizado @[ISG-011]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosDeObterUmArquivoDeUmaTarefaComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de obter um arquivo de uma tarefa com sucesso
    When uma requisição de obter um arquivo de uma tarefa é enviada com o id <dado> da tarefa invalido e id <id> do arquivo invalido
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return | id |
      | adminstrador | 400    | 1  |
      | 9+*/1adeedff | 400    | 2  |
      | ???????????9 | 400    | 3  |

  @automatizado @[ISG-012]ValidarJsonSchemaDeObterUmArquivoDeUmaTarefaComSucesso
  Scenario: validar json schema de obter um arquivo de uma tarefa com sucesso
    Given que exista um projeto com uma tarefa com arquivos cadastrados
    When uma requisicao de obter um arquivo de uma tarefa com o id da tarefa e id do arquivo e enviada
    Then o json schema de obter um arquivo de uma tarefa e validado com sucesso

  @automatizado @[ISG-013]ObterTodasAsTarefasComSucesso
  Scenario: obter todas as tarefas com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisicao de obter todas as tarefas informando a quantidade de tarefas e paginas e enviada
    Then as tarefas sao retornadas com sucesso

  @automatizado @[ISG-014]ValidarOJsonSchemaDeObterTodasAsTarefasComSucesso
  Scenario: validar o json schema de obter todas as tarefas com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisicao de obter todas as tarefas informando a quantidade de tarefas e paginas e enviada
    Then o json schema de obter todas as tarefas e validado com sucesso

  @automatizado @[ISG-015]ObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario: obter todas as tarefas de um projeto com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisicao de obter todas as tarefas de um projeto informando o id do projeto e enviada
    Then as tarefas do projeto sao retornadas com sucesso

  @automatizado @[ISG-016]NaoObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario: nao obter todas as tarefas de um projeto com sucesso
    When uma requisicao de obter todas as tarefas de um projeto e enviada com o id 99999999999 de um projeto que nao existe
    Then e retornado que o projeto nao existe com sucesso

  @automatizado @[ISG-017]ValidarStatusCodeDeUmaRequisicaoComDadosInvalidosDeObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario Outline: validar status code de uma requisicao com dados invalidos de obter todas as tarefas de um projeto com sucesso
    When uma requisicao de obter todas as tarefas de um projeto com id <dado> invalido e enviado
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |

  @automatizado @[ISG-018]ValidarJsonSchemaDeObterTodasAsTarefasDeUmProjetoComSucesso
  Scenario: validar json schemas de obter todas as tarefas de um projeto com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisicao de obter todas as tarefas de um projeto informando o id do projeto e enviada
    Then o json schema de obter todas as tarefas de um projeto e validado com sucesso

  @automatizado @[ISG-019]ObterTarefasRelacionadasAUmFiltroComSucesso
  Scenario: obter as tarefas relacionadas a um filtro com sucesso
    Given que exista uma tarefa cadastrada
    When uma requisicao obter as tarefas informando o id do filtro de tarefas e enviada
    Then as tarefas pertencentes ao filtro sao retornadas com sucesso

  @automatizado @[ISG-020]NaoObterTodasAsTarefasDeUmProjetoinformandoIDDoFiltroComSucesso
  Scenario: nao obter todas as tarefas de um projeto informando id do filtro com sucesso
    When uma requisicao de obter todas as tarefas informando o id 99999999999 de um projeto que nao existe
    Then e retornado que as tarefas nao existem com sucesso

  @automatizado @[ISG-021]ValidarStatusCodeDeUmaRequisicaoDeObterAsTarefasComIDInvalidoComSucesso
  Scenario Outline: validar status code de uma requisicao de obter as tarefas com id invalido com sucesso
    When uma requisicao de obter as tarefas informando o id <dado> do filtro invalido e enviado
    Then e retornado um status code de erro <return> com sucesso
    Examples:
      | dado         | return |
      | 999999999999 | 404    |
      | adminstrador | 400    |
      | 9+*/1adeedff | 400    |
      | ???????????9 | 400    |