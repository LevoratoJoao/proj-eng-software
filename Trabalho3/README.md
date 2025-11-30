# Modelagem e Desenvolvimento

- [Cenarios de testes](#Cenarios-de-testes)
- [Testes automatizados](#Testes-automatizados)
- [Novas funcionalidades e refatorações](#Novas-funcionalidades-e-refatorações)
- [Demonstração](#Demonstração)
- [Como Executar o Projeto](../Trabalho2/codigo/README.md)

## Cenarios de testes

### Cenário 1: Atividades (Levorato)

- Testar a criação de atividades com diferentes tipos de dados (texto, números, datas).

![teste1_atividade](../Imagens/teste1_atividade.png)

- Testar a criação de atividades com dados inválidos (ex: datas no formato errado, números negativos).

![teste2_atividade](../Imagens/teste2_atividade.png)

Teste 3 passou, será necessário corrigir este comportamento.
![teste3_atividade](../Imagens/teste3_atividade.png)

- Testar a criação de atividades com campos obrigatórios faltando.

![teste4_atividade](../Imagens/teste4_atividade.png)

- Testar a criação de atividades com campos nulos.

![teste5_atividade](../Imagens/teste5_atividade.png)

## Testes Automatizados

### Testes de Atividades (Levorato)

Para os testes automatizados, utilizamos o JUnit para garantir a funcionalidade correta do sistema de atividades. A seguir estão os casos de teste implementados:

- `postActivityWithStudentsCreatesActivitiesForAllStudents`: Testa a criação de atividades para múltiplos estudantes.
- `getAllActivitiesReturnsAllActivities`: Testa a recuperação de todas as atividades.
- `sendReminderNotificationsWithValidIdSendsNotifications`: Testa o envio de notificações de lembrete para uma atividade específica.
- `postActivityWithNoStudentsReturnsWarningMessage`: Testa a criação de uma atividade sem estudantes associados, esperando uma mensagem de erro.
- `postActivityWithNullDescriptionThrowsException`: Testa a criação de uma atividade com descrição nula, esperando uma exceção.
- `postActivityWithPastDueDateThrowsException`: Testa a criação de uma atividade com data de vencimento no passado, esperando uma exceção.
- `sendReminderNotificationsWithInvalidIdThrowsException`: Testa o envio de notificações de lembrete para uma atividade com ID inválido, esperando uma exceção.
- `sendReminderNotificationsWithNullIdThrowsException`: Testa o envio de notificações de lembrete para uma atividade com ID nulo, esperando uma exceção.

![testes_automatizados_atividade](../Imagens/autotest_atividade.png)

## Novas funcionalidades e refatorações

### Novas Funcionalidades (Levorato)

- Implementação do envio de notificações para uma atividade específica.

## Demonstração

[Slide](https://www.canva.com/design/DAG3BlAkJfg/NuW4hy0TVdLkqUESelTdlg/view?utm_content=DAG3BlAkJfg&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h2bd91cac84)

https://github.com/user-attachments/assets/aaa49d8c-0861-44c4-b0b1-d909ae3d76ee
