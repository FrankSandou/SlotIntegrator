# language: ru

Функционал: Players

  Сценарий: Зарегистрировать игроков (12 штук) (/api/automationTask/create)
  Ожидаемый результат: HTTP response code 201, ответ соответствует документации

    И проверить токен пользователя
    И сбросить реквест
    И создать "12" игроков


