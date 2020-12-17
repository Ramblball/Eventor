# О деплое проекта на Heroku

#### Подготовка проекта:

##### Создать файл procfile в корне проекта со следующим содержанием
```
worker: java $JAVA_OPTS -cp target/classes:target/dependency/* Main
```
worker - Не требует прослушивания порта от приложения

java $JAVA_OPTS -cp target/classes:target/dependency/* - компиляция с указанием директории с зависимостями

Main - Входной класс приложения

##### Создать файл system.properties в корне проекта с указанием версии JDK
```
java.runtime.version=11
```
##### Добавить в pom.xml файл проекта плагин maven-dependency-plugin
##### Задать copy-dependencies, что бы хранить классы внешних зависимостей

``` xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>3.0.1</version>
    <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>package</phase>
            <goals><goal>copy-dependencies</goal></goals>
        </execution>
    </executions>
</plugin>
```

##### Заменить все личные данные в коде на взятие значений переменных среды

```java
String SecretValue = System.getenv("ENV_VAR");
```

#### Настройка Heroku:

Heroku предоставляет удобный вэб интерфейс для создания серверного приложения и
деплою проекта как с устройства так и с git репозитория

#### Деплой базы данных

Так же с помощью вэб интерфейса создаем datastore. В нем dataclip, который добавляем к приложению

Чтобы отослать базу данных на Хероку с устройства, надо очистить базу данных

```bash
heroku pg:reset
```

Потом сделать push на сервер

```bash
heroku pg:push DATABASE_LOCAL_URL DATABASE_REMOTE_URL --app APP_NAME
```