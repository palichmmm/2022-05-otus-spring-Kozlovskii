## Домашнее задание (dz4-27-28)

---
Ввести авторизацию на основе URL и/или доменных сущностей

### Цель:
Научиться защищать приложение с помощью полноценной авторизации и разграничением прав доступа

### Результат:
Полноценное приложение с безопасностью на основе Spring Security

### Пошаговая инструкция выполнения домашнего задания:

Внимание! Задание выполняется на основе нереактивного приложения Sping MVC!

1. Минимум: настроить в приложении авторизацию на уровне URL.
2. Максимум: настроить в приложении авторизацию на основе доменных сущностей и методов сервиса.
3. В случае авторизации на основе доменных сущностей и PostgreSQL не используйте GUID для сущностей.
4. Написать тесты контроллеров, которые проверяют, что все необходимые ресурсы действительно защищены

Данное задание НЕ засчитывает предыдущие!

### Рекомендации:

Не рекомендуется выделять пользователей с разными правами в разные классы - т.е. просто один класс пользователя.

## Домашнее задание (dz4-27-28)

---
На основе Spring Batch разработать процедуру миграции данных из реляционного хранилища в NoSQL или наоборот

### Цель:
Мигрировать данные с помощью Spring Batch

### Результат:
Приложение для пакетных обработок данных на Spring Batch

### Пошаговая инструкция выполнения домашнего задания:

Задание может быть выполнено в отдельном репозитории, с сущностями из ДЗ JPA и MongoDB.

1. Вы можете выбрать другую доменную модель
2. Необязательно добавлять процесс миграции в веб-приложение. Приложение может быть оформлено в виде отдельной утилиты.
3. Используя Spring Batch, следите, чтобы связи сущностей сохранились.
4. Опционально: Сделать restart задачи с помощью Spring Shell.

Данное задание НЕ засчитывает предыдущие!
