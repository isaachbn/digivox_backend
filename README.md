# Desafio - Digivox

## Package the application

- Package the application

`$ ./mvnw clean package`

`$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)`

## Run

`$ docker-compose up --build -d`

## Verify the application is running

> Application listens on port 3000.


## Usage [Insomnia](https://insomnia.rest/)

[Documentation Export](insomnia/documentation.json)