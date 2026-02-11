# 놀이시설물 관리 시스템 (Outdoor Shop)

## 📋 프로젝트 소개
놀이시설물(벤치, 파고라, 놀이기구 등) 주문 및 관리 시스템입니다.  
과거 Mybatis 프로젝트에서 번아웃으로 완성하지 못했던 **주문/결제 기능**을 JPA로 다시 구현하며 완성했습니다.

---

## 🔄 프로젝트 발전 과정

### v1.0 - Mybatis 프로젝트 (2022)
- 기본 CRUD 기능 구현
- ❌ 주문/결제 기능 미완성 (번아웃으로 중단)
- [기존 프로젝트 링크]

### v2.0 - JPA 전환 프로젝트 (2024) ⭐ 현재
- ✅ **주문/결제/취소 시스템 완성**
- ✅ **JPA 성능 최적화 적용**
- ✅ **REST API 구현**
- ✅ **DTO 변환 패턴 적용**

---

## 🛠 기술 스택

### Backend
- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database**
- **Gradle**

### Frontend
- **Thymeleaf**
- **HTML/CSS**
- **Bootstrap 5**

---

## ✨ 주요 기능

### 1. 회원 관리
- 회원 가입
- 회원 목록 조회

### 2. 상품 관리
- 상품 등록 / 수정 / 삭제
- 상품 목록 조회
- 재고 관리

### 3. 주문 시스템 (핵심 완성 기능) 🎯
- ✅ **주문 등록**
- ✅ **주문 목록 조회**
- ✅ **주문 취소**
- ✅ **주문 검색** (회원명, 주문상태)
- ✅ **배송 정보 관리**

### 4. REST API
- 회원 등록 API
- 회원 조회 API
- DTO를 통한 엔티티 보호

---

## 🚀 성능 최적화

### 1. Fetch Join으로 N+1 문제 해결

**문제 상황:**
```java
// 주문 목록 조회 시 N+1 문제 발생
List<Order> orders = orderRepository.findAll();
// 주문 1개 조회 + 회원 N개 조회 + 상품 N개 조회 = 1 + N + N 쿼리
```

**해결 방법:**
```java
// Fetch Join 적용 - 한 번의 쿼리로 모든 데이터 조회
@Repository
public class OrderRepository {
    public List<Order> findAllWithItems() {
        return em.createQuery(
            "select distinct o from Order o " +
            "join fetch o.member m " +        // 회원 정보
            "join fetch o.orderItems oi " +   // 주문 상품
            "join fetch oi.item i",           // 상품 정보
            Order.class)
        .getResultList();
    }
}
```

**결과:** 조회 쿼리 1번으로 성능 대폭 개선 ⚡

### 2. DTO 직접 조회

**엔티티 조회 후 DTO 변환 (기존):**
```java
// 1. 엔티티 조회
List<Order> orders = orderRepository.findAll();
// 2. DTO로 변환
List<OrderDto> result = orders.stream()
    .map(o -> new OrderDto(o))
    .collect(Collectors.toList());
```

**DTO 직접 조회 (최적화):**
```java
// 필요한 데이터만 SELECT
"select new com.example.dto.OrderQueryDto(" +
"  o.id, m.name, o.orderDate, o.status) " +
"from Order o " +
"join o.member m"
```

**장점:**
- 필요한 컬럼만 조회
- 네트워크 전송량 최소화
- 메모리 사용량 감소

### 3. 지연 로딩 최적화
```java
@Entity
public class Order {
    @ManyToOne(fetch = FetchType.LAZY)  // 지연 로딩
    @JoinColumn(name = "member_id")
    private Member member;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  // 영속성 전이
    private List<OrderItem> orderItems = new ArrayList<>();
}
```

**핵심 전략:**
- `FetchType.LAZY`: 필요한 시점에만 데이터 로드
- `CascadeType.ALL`: 부모-자식 엔티티 함께 관리
- `@JsonIgnore`: API 응답 시 순환 참조 방지

---

## 📂 프로젝트 구조
```
outdoorShop/
├── domain/
│   ├── member/           # 회원 엔티티
│   ├── items/            # 상품 엔티티 (Bench, Playground 등)
│   │   └── product/
│   ├── order/            # 주문 엔티티
│   └── delivery/         # 배송 엔티티
├── repository/           # JPA Repository
├── service/              # 비즈니스 로직
├── controller/           # 컨트롤러 (Web + API)
│   ├── HomeController
│   ├── MemberController
│   ├── ItemController
│   ├── OrderController
│   └── MemberApiController
└── api/                  # REST API 전용
```

---

## 💡 주요 학습 내용

### 1. JPA 연관관계 매핑
- `@ManyToOne` / `@OneToMany` 양방향 관계 설정
- **`@ManyToMany` 사용 금지** → 중간 엔티티로 풀어냄
- 연관관계 편의 메서드 작성

### 2. 엔티티 설계 원칙
- **Setter 제거** → `save~()` 메서드로 대체
- 생성 메서드 패턴 (`createOrder()`)
- 변경 감지(Dirty Checking) 활용

### 3. DTO 패턴
- **폼 전용 DTO**: `@Setter` 사용 (Thymeleaf 바인딩)
- **API 전용 DTO**: 엔티티 노출 방지
- 엔티티 ↔ DTO 변환 분리

### 4. 성능 최적화
- Fetch Join으로 N+1 해결
- DTO 직접 조회로 불필요한 데이터 제거
- 지연 로딩 전략 적용

---

## 🎯 과거 프로젝트와의 차이점

| 구분 | v1.0 (Mybatis) | v2.0 (JPA) |
|------|----------------|------------|
| 주문 시스템 | ❌ 미완성 | ✅ 완성 |
| 결제 로직 | ❌ 없음 | ✅ 구현 |
| 취소 기능 | ❌ 없음 | ✅ 구현 |
| N+1 문제 | 고려 안 함 | ✅ Fetch Join 해결 |
| DTO 활용 | 기본 변환 | ✅ 직접 조회 최적화 |
| API | 없음 | ✅ REST API 구현 |

---

## 🚀 실행 방법

### 1. 프로젝트 
```bash
https://github.com/RedSunSmile/outdoorShop-01.git
```
과거 프로젝트
https://github.com/RedSunSmile/website_board.git
### 2. 실행
```bash
./gradlew bootRun
```

### 3. 접속
```
http://localhost:8080
```

### 4. H2 콘솔 (선택)
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:outdoorShop
```

---

## 📝 API 명세

### 회원 API

**회원 등록**
```http
POST /api/v1/members
Content-Type: application/json

{
  "name": "홍길동",
  "address": {
    "city": "서울",
    "street": "강남대로",
    "zipcode": "12345"
  }
}
```

**회원 조회**
```http
GET /api/v1/members
```

---

## 🔥 개선 예정 사항

- [ ] 배포 (AWS / Railway)
- [ ] CI/CD 파이프라인 구축
- [ ] 컬렉션 조회 최적화 추가
- [ ] 페이징 처리
- [ ] 상품 이미지 업로드
- [ ] 결제 금액 계산 로직 고도화

---

## 📚 참고 자료

- [김영한의 실전! 스프링 부트와 JPA 활용1](https://www.inflearn.com/course/스프링부트-JPA-활용-1)
- [김영한의 실전! 스프링 부트와 JPA 활용2](https://www.inflearn.com/course/스프링부트-JPA-API개발-성능최적화)

---

## 👤 개발자

**이름:** [본인 이름]  
**GitHub:** [GitHub 링크]  
**Blog:** [블로그 링크]

---

## 📄 License

이 프로젝트는 학습 목적으로 제작되었습니다.