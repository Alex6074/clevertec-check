# Check Application
## Консольное приложение, реализующее функционал формирования чека в магазине

## Требования
- Java 21

## Инструкция по запуску

1. Клонируйте репозиторий.
2. Убедитесь, что у вас установлен Java 21.
3. Перейдите в корневую директорию проекта.
4. Запустите команду:

```sh
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx
```
Где:
- id - идентификатор товара (см. products.csv)
- quantity - количество товара
- discountCard=xxxx - название и номер дисконтной карты (см. discountCards.csv)
- balanceDebitCard=xxxx - баланс на дебетовой карте

Например
```sh
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 3-1 2-5 5-1 discountCard=1111 balanceDebitCard=100
```