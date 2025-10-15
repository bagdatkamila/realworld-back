# RealWorld Back

Это backend-реализация проекта [RealWorld](https://github.com/gothinkster/realworld) на Spring Boot 3, реализующая REST API для платформы публикации статей с поддержкой пользователей, профилей, комментариев, тегов и избранного.

## Технологии

- Java 21
- Spring Boot 3.4.5 (Web, Data JPA, Security, Validation)
- PostgreSQL
- MinIO (S3-совместимое хранилище)
- MapStruct (маппинг DTO)
- JWT (аутентификация)
- Docker, Docker Compose

## Структура проекта

```
src/
  main/
    java/sdu/project/realworldback/
      controllers/   # REST-контроллеры
      services/      # Сервисный слой
      repositories/  # JPA-репозитории
      models/        # JPA-сущности
      dto/           # DTO-объекты
      config/        # Конфигурация Spring
      security/      # Безопасность и JWT
      exceptions/    # Обработка ошибок
      utill/         # Утилиты
      validation/    # Кастомные валидаторы
    resources/
      application.yml # Основная конфигурация
  test/
    java/sdu/project/realworldback/
      RealworldBackApplicationTests.java
```

## Быстрый старт

### 1. Клонирование и подготовка

```bash
git clone <repo_url>
cd realworld-back
```

### 2. Настройка переменных окружения

Создайте файл `.env` в корне проекта и заполните переменные:

```
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
POSTGRES_DB=realworld
HOST=db
JWT_SECRET=your_jwt_secret
MINIO_BUCKET=realworld-bucket
MINIO_URL=http://minio:9000
MINIO_ACCESS_KEY=minio_access_key
MINIO_SECRET_KEY=minio_secret_key
```

### 3. Запуск через Docker Compose

```bash
docker-compose up --build
```

- Приложение будет доступно на [http://localhost:8080](http://localhost:8080)
- PostgreSQL: порт 5432
- MinIO: [http://localhost:9001](http://localhost:9001) (консоль), [http://localhost:9000](http://localhost:9000) (S3 API)

### 4. Локальный запуск (без Docker)

- Установите PostgreSQL и MinIO локально, настройте переменные окружения.
- Соберите и запустите приложение:

```bash
./mvnw spring-boot:run
```

## API

Полная спецификация API — в файле [`openapi.yml`](openapi.yml).

Примеры эндпоинтов:

- `POST /api/users` — регистрация пользователя
- `POST /api/users/login` — вход
- `GET /api/user` — текущий пользователь (JWT)
- `GET /api/articles` — список статей (фильтрация по тегу, автору, избранному)
- `POST /api/articles` — создать статью
- `GET /api/articles/feed` — лента подписок (JWT)
- `GET /api/articles/{slug}` — получить статью
- `PUT /api/articles/{slug}` — обновить статью
- `DELETE /api/articles/{slug}` — удалить статью

Для авторизованных запросов используйте заголовок:
```
Authorization: Token <jwt>
```

## Тестирование

```bash
./mvnw test
```

## Лицензия

MIT (или укажите свою) 

## Проверка