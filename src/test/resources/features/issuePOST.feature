@issuePOST
Feature: Issues POST

  Background:
    Given que possuo um novo projeto cadastrado

  @automatizado @[ISP-001]-CriarUmaNovaTarefaComSucesso
  Scenario: Criar uma nova tarefa com sucesso
    When envio uma requisicao para criar uma nova tarefa
    Then a nova tarefa e criada com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-002]-ValidarJsonSchemaDeCriarUmaNovaTarefaComSucesso
  Scenario: Validar json schema de criar uma tarefa com sucesso
    When envio uma requisicao para criar uma nova tarefa
    Then o schema de criar uma nova tarefa e validado com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-003]-CriarUmaNovaTarefaComDadosMinimosComSucesso
  Scenario: Criar uma nova tarefa com dados minimos com sucesso
    When envio uma requisicao informando apenas os dados minimos para criar uma nova tarefa
    Then a nova tarefa com dados minimos e criada com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-004]-ValidarJsonSchemaDeCriarUmaNovaTarefComDadosMinimosComSucesso
  Scenario: Validar json schema de criar nova tarefa com dados minimo com sucesso
    When envio uma requisicao informando apenas os dados minimos para criar uma nova tarefa
    Then o schema de criar uma nova tarefa com dados minimos e validado com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-005]-CriarUmaNovaTarefaDeCopiaComSucesso
  Scenario: Criar uma nova tarefa de copia com sucesso
    When envio uma requisicao para criar uma nova tarefa de copia
    Then a nova tarefa de copia e criada com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-006]-ValidarJsonSchemaDeCriarUmaNovaTarefaDeCopiaComSucesso
  Scenario: Validar json schema de nova tarefea de copia com sucesso
    When envio uma requisicao para criar uma nova tarefa de copia
    Then o schema de criar uma nova tarefa de copia e validado com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-007]-CriarNovaTarefaComArquivosComSucesso
  Scenario: Criar nova tarefa com arquivos com sucesso
    When envio uma requisicao para criar uma nova tarefa com arquivos
    Then a nova tarefa com arquivos e validada com sucesso
    And deleto o projeto e tarefa inseridos

  @automatizado @[ISP-008]-ValidarJsonSchemaDeCriarUmaNovaTarefaComArquivosComSucesso
  Scenario: validar json schema de criar uma nova tarefa com arquivos com sucesso
    When envio uma requisicao para criar uma nova tarefa com arquivos
    Then o schema de criar uma nova tarefa com arquivos e validado com sucesso
    And deleto o projeto e tarefa inseridos