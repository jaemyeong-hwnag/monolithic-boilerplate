# monolithic-boilerplate

Spring Boot 기반의 **Modular Monolith 아키텍처** 프로젝트입니다.  
도메인 주도 설계(DDD) 원칙을 따르며, 각 계층은 단방향 의존성과 명확한 책임 분리를 기반으로 구성되어 있습니다.

## 계층 구조 및 의존 흐름

```
api → facade → service → reader/store → repository
```

---

## 계층 설명

### 1. `api` (입출력 담당)

- 역할: REST 또는 GraphQL 요청을 수신하고 DTO 매핑 처리
- 다루는 데이터:
    - 입력: Request DTO → Command 또는 VO
    - 출력: VO → Response DTO
- 비즈니스 로직 없음. facade만 호출

```java
orderController.createOrder(OrderRequest request) → OrderResponse
```

---

### 2. `facade` (유스케이스 흐름 조율)

- 역할: 트랜잭션 경계를 설정하고, 유스케이스 흐름을 조율
- 다루는 데이터: VO
- 내부에서 service, reader, store 조합 호출

```java
orderFacade.placeOrder(PlaceOrderCommand command) → OrderResult
```

---

### 3. `service` (도메인 로직 처리)

- 역할: 복잡한 도메인 규칙(할인, 상태 변경 등) 처리
- 다루는 데이터: VO
- 도메인 객체를 중심으로 계산 또는 검증 로직 수행

```java
discountService.calculateDiscount(Order order) → Money
```

---

### 4. `reader` / `store` (조회 / 저장 책임)

- `reader`: 조회 전용 (주로 BlazeJPAQuery, QueryDSL 사용)
- `store`: 저장 또는 변경 전용
- 내부는 Entity를 다루며, 외부에는 VO를 반환

```java
productReader.findById(Long productId) → Product
orderStore.save(Order order) → OrderId
```

---

### 5. `repository` (JPA Repository)

- 역할: JPA를 통해 DB와 직접 통신
- 다루는 데이터: Entity
- QueryDSL 또는 BlazeJPAQuery 기반의 JPQL 사용 권장 (native query는 최소화)

```java
productRepository.findById(Long id) → Optional<ProductEntity>
```

---

## 데이터 흐름 요약

```plaintext
[요청 JSON]
   ↓ (Request DTO)
api
   ↓ (Command/VO)
facade
   ↓ (VO)
service
   ↓ (VO)
reader/store
   ↓ (Entity)
repository
```

```plaintext
repository
   ↑ (Entity)
reader/store
   ↑ (Entity → VO)
service
   ↑ (VO)
facade
   ↑ (VO)
api
   ↑ (VO → Response DTO)
[응답 JSON]
```

---

## 설계 철학

- Entity는 repository/infra 하위에서만 존재
- 도메인 계층 내부에서는 VO 기반으로 로직 처리
- 외부 계층(API 등)에는 DTO만 노출
- 단방향 의존만 허용하여 구조 안정성 확보
- BlazeJPAQuery 및 QueryDSL 기반 조회 전략 사용 (native query 최소화)

---

## 예시 디렉토리 구조

```
domain/
├── domain/
│   ├── model/         # Entity, VO
│   └── exception/
├── service/
│   └── DiscountService.java
├── app/
│   └── facade/
│       └── OrderFacade.java
├── infrastructure/
│   ├── repository/
│   ├── reader/
│   └── store/
```

---

## Git 컨벤션

### 커밋 메시지

* `[TICKET-ID] prefix: 메시지` 형식으로 작성하지만, 현재는 사이드 프로젝트이므로 따로 티켓 없이 prefix 위주로 작성합니다. 추후 GitHub issue ID로 티켓 관리할 예정입니다.
* 사용 중인 prefix:

    * `feat:` 기능 추가
    * `fix:` 버그 수정
    * `style:` 코드 포맷팅, 세미콜론 누`r`락 등 기능 변경 없는 스타일 수정
    * `docs:` 문서 작성 또는 수정
    * `build:` 빌드 시스템 관련 변경 (Gradle 등)
    * `ci:` CI 설정 변경 (GitHub Actions, Jenkins 등)

### 브랜치 전략

* Git Flow 전략을 사용합니다.

### 커밋 트리 관리

* rebase 기반 워크플로우를 통해 커밋 트리를 선형(linear)으로 관리합니다.

---

## 기술 스택

- Java 21
- Spring Boot 3.2.x
- Spring Data JPA
- QueryDSL 5.x
- BlazeJPAQuery
- Gradle (KTS or Groovy)
- GraphQL / REST API

---

## 참고 자료

- [Domain-Driven Design - Eric Evans](https://www.domainlanguage.com/ddd/)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Shopify’s Modular Monolith](https://shopify.engineering/modular-monolith)
- [ai-coding-guidelines (GitHub)](https://github.com/jaemyeong-hwnag/ai-coding-guidelines)

---

## 작성자

- Jaemyeong Hang