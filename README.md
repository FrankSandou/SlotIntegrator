Выполнение тестового задания - отчет
====================
стек cucumber-java-rest assured

Предварительно -

1. Установить cucmber for java, gherkin plugin
2. Установить версию java java 8 or higher
   Отчет по найденным ошибкам
1. Орфорграфические в swagger Sed - надо Send , pleas - надо please
2. В 1 задании при получении токена- вместо ожидаемого кода ответа 200 - получаем 201 код
3. В ответе при получени токена приходит DTO c другим набором (должен быть TokenDTO)
4. В методе - создание игрока в ответе приходит неверная DTO-- должна приходить PlayerResponseDTO , а приходит
   PlayerRequestDTO
5. После создания игрока id в linkedHashMap не сохраняются и сбрасываются в null
6. Поэтому метод удаления игрока по id* обязательный параметр значение - перестал работать
7. Сортировка по имени array of linkedhashmap тоже невозможна- потому что удалось записать в поля null значения .
   comparator перестал работать.
8. В методе getOne приходит 1 лишний параметр -currency_code в json ответе чем надо в PlayerResponseDTO
9. Также не ясен принцип работы getOne - должен ли он возвращать ArrayList если у многих такая почта
10. Некоторые assert отключил чтобы можно было проверить остальное