###Проэкт библиотека.
Для запуска проекта необходимо настроить БД. Схема БД
представлена ```/db/db.png```
MySQL бд с дефолтными настройками пользователя и пароля ```login: root  password:1```
Необходимо запустить на исполнение скрипт ```/db/db_create.sql``` и
```/db/library_Status.sql``` Так же необходимо создать одного библиотекаря в Таблице User
и установить ему роль библиотекаря. Пример запроса:
```SQL
INSERT INTO library.User (id, email, password, firstName, lastName, isLibrarian) VALUES (2, 'github@gmail.com', '61fd06bc7bc863dd7efc7263f0486a23', 'Kilop', 'Molo', true);
```
