Feature: Tarefa POST

  Background:
    Given que exista um novo projeto cadastrado

  @automatizado @[ISP-001]-CriarUmaNovaTarefaComSucesso
  Scenario: Criar uma nova tarefa com sucesso
    When uma requisição e enviada para criar uma nova tarefa
    Then a nova tarefa e criada com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-002]-ValidarJsonSchemaDeCriarUmaNovaTarefaComSucesso
  Scenario: Validar json schema de criar uma tarefa com sucesso
    When uma requisição e enviada para criar uma nova tarefa
    Then o json schema ao criar uma nova tarefa e validado com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-003]-CriarUmaNovaTarefaComDadosMinimosComSucesso
  Scenario: Criar uma nova tarefa com dados minimos com sucesso
    When uma requisição e enviada informando apenas os dados minimos para criar uma nova tarefa
    Then a nova tarefa com dados minimos e criada com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-004]-ValidarJsonSchemaDeCriarUmaNovaTarefaComDadosMinimosComSucesso
  Scenario: Validar json schema de criar nova tarefa com dados minimo com sucesso
    When uma requisição e enviada informando apenas os dados minimos para criar uma nova tarefa
    Then o json schema de criar uma nova tarefa com dados minimos e validado com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-005]-CriarUmaNovaTarefaDeCopiaComSucesso
  Scenario: Criar uma nova tarefa de copia com sucesso
    When uma requisição e enviada para criar uma nova tarefa de copia
    Then a nova tarefa de copia e criada com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-006]-ValidarJsonSchemaDeCriarUmaNovaTarefaDeCopiaComSucesso
  Scenario: Validar json schema de nova tarefea de copia com sucesso
    When uma requisição e enviada para criar uma nova tarefa de copia
    Then o json schema de criar uma nova tarefa de copia e validado com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-007]-CriarNovaTarefaComArquivosComSucesso
  Scenario: Criar nova tarefa com arquivos com sucesso
    When uma requisição e enviada para criar uma nova tarefa com arquivos
    Then a nova tarefa com arquivos e validada com sucesso
    And o projeto e tarefa inseridos são deletados

  @automatizado @[ISP-008]-ValidarJsonSchemaDeCriarUmaNovaTarefaComArquivosComSucesso
  Scenario: validar json schema de criar uma nova tarefa com arquivos com sucesso
    When uma requisição e enviada para criar uma nova tarefa com arquivos
    Then o schema de criar uma nova tarefa com arquivos e validado com sucesso
    And o projeto e tarefa inseridos são deletados