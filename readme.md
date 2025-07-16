# monolithic-boilerplate

Spring Boot 기반의 **Modular Monolith 아키텍처** 프로젝트입니다.  
도메인 주도 설계(DDD) 원칙을 따르며, 각 계층은 단방향 의존성과 명확한 책임 분리를 기반으로 구성되어 있습니다.


AI 작업시 [ai-coding-guidelines (GitHub)](https://github.com/jaemyeong-hwnag/ai-coding-guidelines) 반드시 준수한다.

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
    - 출력: VO → Response DTO (자동으로 ApiResponseDto로 래핑)
- 비즈니스 로직 없음. facade만 호출
- **REST API 공통 설정**: 모든 응답이 자동으로 표준화된 형태로 변환

```java
orderController.createOrder(OrderRequest request) → OrderResponse
// 실제 응답: ApiResponseDto<OrderResponse>
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
- 공통 예외 처리를 위한 Common 모듈 활용
- Domain Exception은 BaseException 상속 및 CommonError 구현

---

## 예시 디렉토리 구조

```
src/main/java/com/hjm/monolithicboilerplate/
├── api/
│   ├── rest/
│   │   ├── controller/
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── common/
│   │   │   ├── ApiResponseDto.java
│   │   │   ├── ApiResponseAdvice.java
│   │   │   ├── ApiResponseAnnotation.java
│   │   │   └── ApiResponseCustomizer.java
│   │   └── config/
│   │       └── SwaggerConfig.java
│   └── graphql/
│       ├── resolver/
│       ├── type/
│       └── mutation/
├── app/
│   └── facade/
├── common/
│   ├── exception/
│   │   ├── BaseException.java
│   │   ├── BusinessException.java
│   │   ├── ValidationException.java
│   │   └── SystemException.java
│   ├── error/
│   │   └── CommonError.java
│   └── response/
│       ├── ApiResponse.java
│       └── ErrorResponse.java
├── domain/
│   ├── domain/
│   │   ├── entity/
│   │   ├── vo/
│   │   └── exception/
│   │       ├── UserNotFoundException.java
│   │       ├── UserAlreadyExistsException.java
│   │       ├── InvalidEmailException.java
│   │       └── UserError.java
│   └── service/
└── infrastructure/
    ├── repository/
    ├── reader/
    └── store/
```

---

## 예외 처리 구조

### CommonError 인터페이스
```java
public interface CommonError {
    @JsonValue
    String getCode();
    String getMessage();
    int getStatus();
}
```

### Domain Exception 패턴
- Domain Exception은 BaseException 상속
- Domain Error는 CommonError 구현
- 일관된 예외 처리 및 응답 형식

```java
// Domain Error (CommonError 구현)
public enum UserError implements CommonError {
    USER_NOT_FOUND("USER_001", "사용자를 찾을 수 없습니다.", 404),
    USER_ALREADY_EXISTS("USER_002", "이미 존재하는 사용자입니다.", 409);
}

// Domain Exception (BaseException 상속)
public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(UserError.USER_NOT_FOUND);
    }
}
```

---

## Git 컨벤션

### 커밋 메시지

* `[TICKET-ID] prefix: 메시지` 형식으로 작성하지만, 현재는 사이드 프로젝트이므로 따로 티켓 없이 prefix 위주로 작성합니다. 추후 GitHub issue ID로 티켓 관리할 예정입니다.
* 사용 중인 prefix:

    * `feat:` 기능 추가
    * `fix:` 버그 수정
    * `style:` 코드 포맷팅, 세미콜론 누락 등 기능 변경 없는 스타일 수정
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
- Virtual Threads
- MySQL
- SpringDoc OpenAPI (Swagger UI)
- Lombok

---

## REST API 공통 설정

### 개요
모든 REST API 응답을 표준화하고 자동화된 문서화를 제공하는 공통 인프라입니다.

### 핵심 컴포넌트

#### 1. ApiResponseDto - 표준 응답 DTO
모든 API 응답의 표준화된 형태를 제공합니다.

```java
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {
    private final boolean success;      // 성공 여부
    private final String code;          // 응답 코드
    private final String message;       // 응답 메시지
    private final T data;               // 실제 응답 데이터
    private final LocalDateTime timestamp; // 응답 시간
}
```

**표준 응답 형태:**
```json
{
  "success": true,
  "code": "SUCCESS",
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": { /* 실제 데이터 */ },
  "timestamp": "2025-07-16T12:34:56"
}
```

#### 2. ApiResponseAdvice - 자동 응답 래핑
모든 컨트롤러 응답을 자동으로 `ApiResponseDto`로 래핑합니다.

```java
@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    // byte[] 타입 제외하고 모든 응답을 자동 래핑
    // null 응답은 빈 성공 응답으로 변환
}
```

#### 3. ApiResponseAnnotation - 커스텀 어노테이션
Swagger 문서에서 API 응답 상태 코드별 설명을 커스터마이징합니다.

```java
@ApiResponseAnnotation(
    response200 = "성공적으로 조회됨",
    response201 = "성공적으로 생성됨",
    response404 = "요청한 리소스를 찾을 수 없음",
    response403 = "권한이 없음"
)
@GetMapping("/userEntities/{id}")
public UserResponse getUser(@PathVariable Long id) {
    // 컨트롤러 로직
}
```

#### 4. SwaggerConfig - API 문서화 설정
Swagger UI 설정 및 응답 스키마 자동 래핑을 제공합니다.

- **OpenAPI 정의**: 프로젝트 정보 설정
- **응답 스키마 래핑**: 모든 API 응답을 `ApiResponseDto` 형태로 자동 변환
- **예시 데이터**: 성공/실패 응답에 대한 예시 제공

### 사용법

#### 기본 컨트롤러 작성
```java
@RestController
@RequestMapping("/api/userEntities")
public class UserController {
    
    @ApiResponseAnnotation(
        response200 = "사용자 정보 조회 성공",
        response404 = "사용자를 찾을 수 없음"
    )
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        // 비즈니스 로직
        return userService.getUser(id);
    }
}
```

#### 자동 응답 변환
컨트롤러에서 직접 `ApiResponseDto`를 반환할 필요가 없습니다:

```java
// ❌ 불필요한 래핑
@GetMapping("/{id}")
public ApiResponseDto<UserResponse> getUser(@PathVariable Long id) {
    return ApiResponseDto.success(userService.getUser(id));
}

// ✅ 자동 래핑 (권장)
@GetMapping("/{id}")
public UserResponse getUser(@PathVariable Long id) {
    return userService.getUser(id);
}
```

### Swagger UI 접근
- **URL**: `http://localhost:8080/swagger-ui/index.html`
- **특징**: 
  - 모든 API 응답이 표준화된 형태로 표시
  - 상태 코드별 상세한 설명 제공
  - 실제 응답 예시 미리보기 가능

### 장점

1. **개발 생산성 향상**: 표준화된 응답 구조로 개발 시간 단축
2. **API 일관성**: 모든 엔드포인트가 동일한 응답 형태 사용
3. **자동화된 문서화**: Swagger UI를 통한 실시간 API 문서 생성
4. **유지보수성**: 중앙화된 응답 처리로 변경사항 적용 용이
5. **개발자 경험**: 직관적인 API 문서와 예시 제공

---

## 참고 자료

- [Domain-Driven Design - Eric Evans](https://www.domainlanguage.com/ddd/)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Shopify's Modular Monolith](https://shopify.engineering/modular-monolith)
- [ai-coding-guidelines (GitHub)](https://github.com/jaemyeong-hwnag/ai-coding-guidelines)

---

## 작성자

- Jaemyeong Hang