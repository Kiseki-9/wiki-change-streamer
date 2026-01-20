# Wikimedia Change Streamer — kafka-consumer-database

Overview

- Spring Boot Kafka consumer that listens to Wikimedia recent changes events and persists them to PostgreSQL.
- Located in: springboot-kafka-project/kafka-consumer-database

Prerequisites

- Java 17+ (or matching project JVM)
- Maven (or Gradle if project uses it)
- Apache Kafka broker (localhost:9092 by default)
- PostgreSQL database (default DB: WikimedicaRecentChanges)

Configuration

- Main properties file: src/main/resources/application.properties
  - Kafka bootstrap: spring.kafka.consumer.bootstrap-servers (default: localhost:9092)
  - Kafka group id: spring.kafka.consumer.group-id
  - Postgres URL: spring.datasource.url (default: jdbc:postgresql://localhost:5432/WikimediaRecentChanges)
  - DB credentials: spring.datasource.username / spring.datasource.password
- Adjust these values for your environment or provide overrides via environment variables or application-\*.properties.

Run locally (Maven)

- Build: mvn clean package
- Run: mvn spring-boot:run
- Or run the built jar:
  java -jar target/<artifact>-<version>.jar

Notes

- The consumer uses JsonDeserializer for values; trusted packages and default type are set in application.properties.
- Check application logs to verify consumption and DB insertions.
- If using Docker networking, ensure advertised listeners and DB host are reachable from the app.

Troubleshooting

- Kafka connection refused: verify broker address and advertised listeners.
- DB errors: verify DB URL, credentials, and that the DB is reachable.
- For schema changes, spring.jpa.hibernate.ddl-auto is set in application.properties (default in repo: update).

How it works

- Components
  - Kafka consumer (Spring Kafka) — subscribes to the configured topic(s) and receives change events.
  - Deserialization — values use JsonDeserializer (configured in application.properties) mapped to ApiResponse (or your DTO).
  - Listener — a @KafkaListener or @KafkaHandler receives the deserialized ApiResponse and forwards it to a service.
  - Service + Repository — a Spring service transforms the DTO into an entity and saves it via a JpaRepository to PostgreSQL.
  - DB (Postgres) — stores persisted change records; JPA/Hibernate handles schema generation/update per spring.jpa.hibernate.ddl-auto.

- Typical event flow
  1. Kafka broker publishes a change event to a topic (e.g., wikimedia.recentchange).
  2. Spring Kafka consumer receives the message and ErrorHandlingDeserializer handles basic deserialization errors.
  3. The message is deserialized into the configured default value type (ApiResponse).
  4. The listener passes the DTO to the persistence service which maps and saves it to the DB.
  5. Offsets are committed according to the container/ack mode (default container-managed behavior unless overridden).

- Key config notes
  - application.properties sets JsonDeserializer trusted packages and default type; ensure these match your DTO package.
  - If you change the DTO/entity shape, update the DB schema or let Hibernate update it (see spring.jpa.hibernate.ddl-auto).
  - For production, adjust consumer group, max poll, concurrency, error handlers, and commit/ack strategies.

- Troubleshooting tips
  - Deserialization errors: add the DTO package to spring.json.trusted.packages.
  - Missing fields in DB: verify entity mapping and JPA column names.
  - High throughput: increase listener concurrency and tune Kafka consumer properties.
