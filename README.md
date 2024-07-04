# Check Application
## Консольное приложение, реализующее функционал формирования чека в магазине

## Требования
- Java 21

## Инструкция по запуску

1. Клонируйте репозиторий.
2. Убедитесь, что у вас установлен Java 21.
3. Перейдите в корневую директорию проекта.
4. Запустите последовательно команды для создания папки, компиляции Java файлов и запуска программы:

```sh
mkdir build\classes
javac -d build\classes -cp src src\main\java\ru\clevertec\check\*.java
java -cp build\classes ru.clevertec.check.CheckRunner id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=xxxx saveToFile=xxxx
```

Где:
- id - идентификатор товара (см. products.csv)
- quantity - количество товара
- discountCard=xxxx - название и номер дисконтной карты (см. discountCards.csv)
- balanceDebitCard=xxxx - баланс на дебетовой карте
- pathToFile=xxxx - относительный(от корневой директории проекта) путь + название файла с расширением содержащего список продуктов
- saveToFile=xxxx - относительный(от корневой директории проекта) путь + название файла с расширением для сохранения результата

Например
```sh
java -cp build\classes ru.clevertec.check.CheckRunner 3-1 2-5 5-1 discountCard=1111 balanceDebitCard=100 pathToFile=src/main/resources/products.csv saveToFile=src/main/resources/result.csv
```