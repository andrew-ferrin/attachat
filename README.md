# Atta[chat]

Playground for experimenting with OpenAI Tools API in the context of documents containing attachments.

## Usage:

### Running Locally:

```sh
./gradlew clean bootRun
```

This will run the application listening on port `80`.
The port can be configured with the `server.port` property (see [`application.yml`](/src/main/resources/application.yml))

### Containerized:

Build the application `.jar`:
```sh
./gradlew clean bootJar
```

Build the container image (uses the `.jar` you just built):
```sh
docker build -t attachat .
```

Run the container:
```sh
docker run -p 8080:80 attachat
```

The application running within the container is listening on port `80`.
When running, the `-p 8080:80` maps your [`localhost:8080`](http://localhost:8080) to container port `80`.

## Configuration:
API key is expected to be provided via application configuration property `llm.api-key`.
This can be done by creating a file `src/main/resources/secrets.yml` with the following structure:

```yml
llm:
  api-key: YOUR_API_KEY
```