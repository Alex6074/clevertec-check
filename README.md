# Check Application
## Консольное приложение, реализующее функционал формирования чека в магазине

## Требования
- Java 21

## Инструкция по запуску

1. Клонируйте репозиторий.
2. Убедитесь, что у вас установлен Java 21.
3. Перейдите в корневую директорию проекта.
4. Запустите последовательно команды для сборки и запуска программы:

```sh
gradlew build
java -jar build/libs/clevertec-check-1.0.jar id-quantity discountCard=xxxx balanceDebitCard=xxxx saveToFile=xxxx datasource.url=ххxх datasource.username=ххxх datasource.password=хxхх
```

Где:
- id - идентификатор товара (см. products.csv)
- quantity - количество товара
- discountCard=xxxx - название и номер дисконтной карты (см. discountCards.csv)
- balanceDebitCard=xxxx - баланс на дебетовой карте
- saveToFile=xxxx - относительный(от корневой директории проекта) путь + название файла с расширением для сохранения результата
- datasource.url=xxxx - url адресс базы данных
- datasource.username=хххx - имя пользователя для подключения к базе данных
- datasource.password=хххx - пароль для подключения к базе данных

Например
```sh
java -jar build/libs/clevertec-check-1.0.jar 3-1 2-5 5-1 discountCard=1111 balanceDebitCard=100 saveToFile=src/main/resources/result.csv datasource.url=jdbc:postgresql://localhost:5432/clevertec_check datasource.username=for_java_connection datasource.password=password
```

## Изменения
- Улучшена структура проекта