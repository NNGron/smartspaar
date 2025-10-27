# SmartSpaar 🛒  
> A hands-on monorepo for learning microservices, scraping, caching, and observability with Spring Boot.
**Educational Microservice Project with Monitoring and Jumbo Price Fetching**

SmartSpaar is an educational project built with a modular architecture (monorepo) to demonstrate key concepts in microservices, external data fetching, and professional monitoring systems.
 
---
## Project Goals

1. **Fetch product prices from the Jumbo online store**  
   Using Spring Boot and scraping/API techniques, the project retrieves product pricing data from Jumbo.

2. **Implement a complete monitoring system**  
   With Prometheus, Grafana, and exporters, the system monitors services, databases, and infrastructure in real time.

---
## Tech Stack
- Java 21 (LTS), Maven
- Spring Boot 3.3.x, Spring Cloud (Gateway, OpenFeign)
- Redis (as cache)
- Docker & Docker Compose
- Prometheus, Grafana
- PostgreSQL, Redis, Kafka
- H2 (for dev testing)
- Spring Cloud Gateway 2023.x (OpenFeign, Gateway)
- OpenAPI / Swagger
- Jsoup/JSON (scraping/API)

## Project Structure
| Module | Responsibility |
|--------|----------------|
| `smartspaar` | parent (not a Spring app). |
| `store-jumbo-service` | Fetches product data from Jumbo via scraping or API (demo implementation) |
| `aggregator-service` | Collects and compares prices from stores (currently Jumbo). Includes clients for store integration. |
| `price-catalog-service` | Stores and caches fetched prices. Supports H2 for fast testing and PostgreSQL for production. |
| `list-service` | Manages user shopping lists. Allows adding, removing, and viewing list items. |
| `api-gateway` | Central entry point for routing requests to internal services. Built with Spring Cloud Gateway. |
| `common-model` | Shared module containing data models like `ProductDto`, `SearchResponse`, and `PriceBulkRequest`. Used across services. |

---  
## How to Run the Project
### Local Development
You can run all services via Docker Compose, or start them individually for debugging or development.

- Make sure Docker and Docker Compose are installed. Then run:
 ```bash
docker-compose up --build
```
- JDK 21:
  - `java -version` → 21.x
- Maven 3.9+:
  - `mvn -v`

## Build

From repo root:
```bash
mvn -q -DskipTests clean install
```

2. Start store-jumbo-service: <br> . IntelliJ: Run StoreJumboApplication
   <br>. or:
   
```bash
cd store-jumbo-service
mvn -q -DskipTests spring-boot:run
# runs on port 8091
```
3.Start aggregator-service:
- IntelliJ: Run AggregatorServiceApplication
- or:  
```Bash
cd aggregator-service
mvn -q -DskipTests spring-boot:run
# runs on port 8081
```

## Configuration
aggregator-service/src/main/resources/application.yml
```YAML
spring:
  application:
    name: aggregator-service
  data:
    redis:
      host: localhost
      port: 6379

server:
  port: 8081

stores:
  jumbo:
    url: http://localhost:8091
```
store-jumbo-service/src/main/resources/application.yml

```YAML
server:
  port: 8091
```

## API  
- Aggregator  
  .. GET /v1/compare?query={q}  
  .. Response:  
  
```JSON
{
  "items": [
    {"name":"Eieren 12 stuks","brand":"Jumbo","quantity":"12 stuks","packaging":"pack","regularPrice":4.79,"discountPrice":null,"store":"JUMBO"}
  ],
  "cached": false,
  "lastUpdated": 1710000000000
}
```
- Store (per store)  
  - GET /v1/search?query={q}  
  - Returns SearchResponse (list of ProductDto)

## Add a New Store (fast)
1. Create a new Spring Boot module (e.g., store-lidl-service).
2. Add REST controller /v1/search?query=... returning SearchResponse.
3. Add route in aggregator application.yml:
```YAML
stores:
  lidl:
    url: http://localhost:8093
```
4. Add a new Feign client in aggregator:
```Java
@FeignClient(name = "storeLidl", url = "${stores.lidl.url}")
interface StoreLidlClient { @GetMapping("/v1/search") SearchResponse search(@RequestParam String query); }
```

---
## All services will start automatically. You can access them via:
| Service	 | URL |
|--------|----------------|
| `Prometheus` | 	[http://localhost:9090](http://localhost:9090) |
| `Grafana` | [http://localhost:3000](http://localhost:3000) (user: admin, pass: admin123) |
| `Swagger UI (e.g. aggregator)` | [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) |
| `H2 Console (for price-catalog-service)` | [http://localhost:8082/h2-console](http://localhost:8082/h2-console) |

---

## Monitoring System
> The monitoring stack is fully auto-provisioned via Docker Compose and ready to use out of the box.
Prometheus automatically scrapes metrics from exporters defined in `prometheus.yml`. No manual setup required.


## Purpose  
- To monitor the health, performance, and metrics of services, databases, and infrastructure in real time.

## Components
| Tool	 | Role |
|--------|----------------|
| `Prometheus` | 	Collects metrics from services and exporters |
| `Grafana` | 	Visualizes metrics with customizable dashboards |
| `Exporters` | 	Provide readable metrics for Prometheus from various sources |

## Exporters Used
- redis-exporter: Monitors Redis memory and performance
- postgres-exporter: Tracks PostgreSQL connections, queries, and resource usage
- kafka-exporter: Monitors Kafka topics, offsets, and lag

## Key Files
- prometheus.yml: Defines scrape jobs and targets for Prometheus
- grafana-datasource.yml: Automatically connects Grafana to Prometheus
- grafana-dashboard.yml: Auto-imports dashboards into Grafana
- grafana-dashboard.json: Prebuilt dashboard with charts for Redis, PostgreSQL, Kafka, and Prometheus

## Dashboards  
Grafana includes a dashboard named SmartSpaar Monitoring with:
- Redis memory usage
- Active PostgreSQL connections
- Kafka message rates and lag
- Prometheus scrape durations

---

## Caching (now) and SWR (next)  
- Cache key: cmp:{query} (aggregator-service)
- TTL: 2h (sample)
- Next: SWR (serve stale, refresh in background)  

---

## Dev Tips

- Each service has its own application.yml.
- Parent POM is not a Spring app (packaging = pom).
- Feign scan:
```Java
@EnableFeignClients(basePackages = "...agg.client")
```

- Redis prop (Boot 3.3): spring.data.redis.host/port.

## Scripts (handy)  
Build all:
```Bash
mvn -q -DskipTests clean install
```
Run aggregator:
```Bash
cd aggregator-service && mvn -q -DskipTests spring-boot:run
```
Run Jumbo:
```Bash
cd store-jumbo-service && mvn -q -DskipTests spring-boot:run
```

## Testing & Exploration
- price-catalog-service uses H2 for fast local testing
- Swagger UI is enabled for all services to test endpoints
- Metrics are visible in Prometheus and visualized in Grafana


## Contributing
- Java 21, clean code, small PRs.
- Commit style: feat: ..., fix: ..., docs: ...
- Branches: feature/*, bugfix/*  
- This project is published as an educational monorepo to demonstrate microservice architecture and monitoring. It does not include full business logic or production-grade testing

## License
- This project is released under the MIT License.
