# Challenge - Digivox

## Package the application

- Package the application

      $ ./mvnw clean package

      $ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

## Run

    $ docker-compose up --build -d


## Verify the application is running

> Application listens on port 3001.

## Documentation

> [json](http://localhost:3001/api-docs)

> [html](http://localhost:3001/api-ui.html)

### Usage Requests [Insomnia](https://insomnia.rest/)

> [Workspace import](insomnia/documentation.json)
