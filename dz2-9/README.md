## Домашнее задание (dz2-9)

---
Создать приложение хранящее информацию о книгах в библиотеке

### Цель:
использовать возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных

### Результат:
приложение с хранением данных в реляционной БД, которое в дальнейшем будем развивать

### Пошаговая инструкция выполнения домашнего задания:

---

Это домашнее задание выполняется НЕ на основе предыдущего.

### Необходимо:

1. Использовать Spring JDBC и реляционную базу (H2 или настоящую реляционную БД). 
Настоятельно рекомендуем использовать NamedParametersJdbcTemplate
2. Предусмотреть таблицы авторов, книг и жанров.
3. Предполагается отношение многие-к-одному (у книги один автор и жанр). 
Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров).
4. Интерфейс выполняется на Spring Shell (CRUD книги обязателен, операции с авторами и жанрами - как будет удобно).
5. Скрипт создания таблиц и скрипт заполнения данными должны автоматически запускаться с помощью spring-boot-starter-jdbc.
6. Написать тесты для всех методов DAO и сервиса работы с книгами. 
### Рекомендации к выполнению работы:
* НЕ делать AbstractDao.
* НЕ делать наследования в тестах.