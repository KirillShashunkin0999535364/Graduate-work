
<h1 align="center"><strong><em>Онлайн Магазин</strong></em></h1>

<p align="center"><img src="https://static.vecteezy.com/system/resources/previews/009/848/288/original/verified-shop-online-store-3d-illustration-for-ecommerce-icon-free-png.png" alt="логотип магазину" height=225 width=225></p>

Онлайн магазин - це проект-хобі, створений за допомогою Spring Boot і React. Він використовує базу даних MySQL для зберігання даних про користувачів, продавців та продукцію. Онлайн магазин використовує JWT для системи авторизації та підтримує 3 мови:
* Англійська
* Російська
* Українська

## Передумови:
* Java 17+
* Node.js 21+
* Maven
* База даних MySQL
  <br>

## Для запуску серверної програми Spring:

* Виконайте команду `mvn clean install` у директорії `online-shop\online-shop-server`.
* Змініть файл `online-shop-server\src\main\resources\application.properties`.
```properties
# Властивості бази даних
spring.jpa.hibernate.ddl-auto=create
spring.datasource.url=jdbc:mysql://localhost:3306/online_shop
spring.datasource.username=root
spring.datasource.password=1234

# JWT
jwt.secret=640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316

# Algolia
algolia.usage=true
algolia.app.id=KLFWXPOEHY
algolia.api.key=87c939ac9269c88a17beeaacca28567a
algolia.index.name=online-shop-dev
```
* Виконайте команду `mvn spring-boot:run` у директорії `online-shop\online-shop-server`.
  <br>

# Для запуску клієнтської програми React:

* Виконайте команду `npm install` у директорії `online-shop\online-shop-ui`.
* Змініть файл `online-shop-ui\src\env-config.ts`.
```ts
export const API_BASE_URL = "http://localhost:8080";
export const JWT_SECRET = "640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13E9ED20316640762F165320F52408DAFED313C106346575273C66013DE94B8D13
