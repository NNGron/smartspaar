# SmartSpaar

Microservices backend for real‑time grocery price comparison in the Netherlands.  
Java 21 • Spring Boot 3.3 • Spring Cloud • Redis • Feign • Maven

## Overview

- Compare product prices across stores (Jumbo, AH, Lidl, …).
- Fast response using Redis cache (TTL + ready for SWR).
- Microservices: aggregator + store services (one per store).
- Easy to add/remove stores via new service + simple config.

## Tech Stack

- Java 21 (LTS), Maven
- Spring Boot 3.3.x, Spring Cloud 2023.x (OpenFeign, Gateway later)
- Redis (cache), Docker (dev)
- Jsoup/JSON (scraping/API), Playwright/Selenium (fallback, later)

## Monorepo Layout  
smartspaar/
pom.xml # parent (not a Spring app)
common-model/ # shared DTOs (no Spring)
aggregator-service/ # REST+Feign+Redis (compare results)
store-jumbo-service/ # Jumbo store service (REST, demo now)
store-ah-service/ # (to add)
store-lidl-service/ # (to add)
store-plus-service/ # (to add)
store-dirk-service/ # (to add)
store-vomar-service/ # (to add)
store-spar-service/ # (to add)
store-aldi-service/ # (to add)  



## Prerequisites

- JDK 21:
  - `java -version` → 21.x
- Maven 3.9+:
  - `mvn -v`
- Docker (for Redis):
  - `docker run -d --name redis -p 6379:6379 redis:7`

## Build

From repo root:
```bash
mvn -q -DskipTests clean install
```


# Run (Dev, local)  
1. Start Redis (once):
```bash
docker run -d --name redis -p 6379:6379 redis:7
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
4. Test (MVP)
   
```Bash
# Compare by query using aggregator (will call Jumbo demo)
curl "http://localhost:8081/v1/compare?query=eieren"
```

# Configuration
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
  ah:
    url: http://localhost:8092  # add when service is ready
  lidl:
    url: http://localhost:8093  # add when service is ready
```
store-jumbo-service/src/main/resources/application.yml

```YAML
server:
  port: 8091
```

# API  
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
5. Call it in CompareController.

## Caching (now) and SWR (next)  
- Cache key: cmp:{query} (aggregator-service)
- TTL: 2h (sample)
- Next: SWR (serve stale, refresh in background)  

## Roadmap  
- Add stores: AH, Lidl, PLUS, Dirk, Vomar, SPAR, Aldi
- API Gateway (Spring Cloud Gateway)
- Jsoup/JSON real fetch (Jumbo/Lidl/PLUS/…)
- AH JSON or Headless worker (Playwright), not on request path
- Redis TTL per data type, SWR
- OCR receipts service (Vision/Tesseract)
- Smart refill (ML) + Routing service (OSRM/Maps)
- Notifications (FCM), Reports (weekly)

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

## Contributing
- Java 21, clean code, small PRs.
- Commit style: feat: ..., fix: ..., docs: ...
- Branches: feature/*, bugfix/*  

## License
MIT (to add)











