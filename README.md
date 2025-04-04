# 일정 관리 앱 Develop

Spring, Spring Boot, MySQL, JAVA, JPA, Bcrypt

---

## 구조

```text
/todo
    ├── common
    │       └── Const
    ├── config
    │       ├── PasswordEncoder
    │       └── WebConfig
    ├── controller
    │       ├── CommentController
    │       ├── MemberController
    │       └── ScheduleController
    ├── dto
    │     ├── CommentRequestDto
    │     ├── CommentResponseDto
    │     ├── DeleteMemberRequestDto
    │     ├── LoginRequestDto
    │     ├── LoginResponseDto
    │     ├── MemberResponseDto
    │     ├── ScheduleRequestDto
    │     ├── ScheduleResponseDto
    │     ├── SignUpRequestDto
    │     ├── SignUpResponseDto
    │     ├── UpdateCommentRequestDto
    │     ├── UpdatePasswordRequestDto
    │     ├── UpdateScheduleRequestDto
    │     └── UpdateUserNameRequestDto
    ├── entity
    │       ├── BaseEntity
    │       ├── Comment
    │       ├── Member
    │       └── Schedule
    ├── filter
    │       ├── CustomFilter
    │       └── LoginFilter
    ├── repository
    │           ├── CommentRepository
    │           ├── MemberRepository
    │           └── ScheduleRepository
    └── service
            ├── CommentService
            ├── MemberService
            └── ScheduleService
```

## 기능

### Lv 0. API 명세서 및 ERD 작성

1. **API 명세서**

- 회원가입
  - POST
  - /members/signup
> request
> ```json
> {
>     "username": "test",
>     "password": "qwer1234",
>     "email": "test@test.com"
> }
> ```
> response
> ```json
> {
>     "id": 1,
>     "username": "test",
>     "email": "test@test.com"
> }
> ```

- 로그인
  - POST
  - /members/login
> request
> ```json
> {
>    "email": "test@test.com",
>    "password": "qwer1234"
> }
> ```
> response
> ```json
> {
>     "id": 1,
>     "username": "test",
>     "email": "test@test.com"
> }
> ```

- 로그아웃
  - POST
  - /members/logout

- 멤버 조회
  - GET
  - /members/{id}
> response
> ```json
> {
>     "username": "test",
>     "email": "test@test.com"
> }
> ```

- 비밀번호 변경
  - PATCH
  - /members/{id}
> request
> ```json
> {
>     "oldPassword": "qwer1234",
>     "newPassword": "qwer3333"
> }
> ```

- 이름 변경
  - PATCH
  - /members/{id}/name
> request
> ```json
> {
>     "newName": "choi",
>     "password": "qwer3333"
> }
> ```

- 유저 삭제
  - DELETE
  - /members/{id}
> request
> ```json
> {
>     "password": "qwer3333"
> }
> ```

---

- 할일 생성
  - POST
  - /schedules
> request
> ```json
> {
>     "title": "test",
>     "contents": "content"
> }
> ```
> response
> ```json
> {
>     "id": 1,
>     "title": "test",
>     "contents": "content",
>     "username": "test"
> }
> ```

- 할일 전체 조회
  - GET
  - /schedules
> response
> ```json
> {
>     "content": [
>         {
>             "id": 2,
>             "title": "test",
>             "contents": "content",
>             "username": "test"
>         },
>         {
>             "id": 1,
>             "title": "test",
>             "contents": "content",
>             "username": "test"
>         }
>     ],
>     "pageable": {
>         "pageNumber": 0,
>         "pageSize": 10,
>         "sort": {
>             "empty": false,
>             "sorted": true,
>             "unsorted": false
>         },
>         "offset": 0,
>         "paged": true,
>         "unpaged": false
>    },
>     "last": true,
>     "totalPages": 1,
>     "totalElements": 2,
>     "size": 10,
>     "number": 0,
>     "sort": {
>         "empty": false,
>         "sorted": true,
>         "unsorted": false
>     },
>     "first": true,
>     "numberOfElements": 2,
>     "empty": false
> }
> ```

- 본인 할일 전체 조회
  - GET
  - /schedules/{userId}
> response
> ```json
> {
>     "content": [
>         {
>             "id": 2,
>             "title": "test",
>             "contents": "content",
>             "username": "test"
>         },
>         {
>             "id": 1,
>             "title": "test",
>             "contents": "content",
>             "username": "test"
>         }
>     ],
>     "pageable": {
>         "pageNumber": 0,
>         "pageSize": 10,
>         "sort": {
>             "empty": false,
>             "sorted": true,
>             "unsorted": false
>         },
>         "offset": 0,
>         "paged": true,
>         "unpaged": false
>    },
>     "last": true,
>     "totalPages": 1,
>     "totalElements": 2,
>     "size": 10,
>     "number": 0,
>     "sort": {
>         "empty": false,
>         "sorted": true,
>         "unsorted": false
>     },
>     "first": true,
>     "numberOfElements": 2,
>     "empty": false
> }
> ```

- 할일 단일 조회
  - GET
  - /schedules/{userId}/{scheduleId}
> response
> ```json
> {
>     "id": 1,
>     "title": "test",
>     "contents": "content",
>     "username": "test"
> }
> ```

- 할일 수정
  - PATCH
  - /schedules/{userId}/{scheduleId}
> request
> ```json
> {
>     "newTitle": "newTitle",
>     "newContents": "newSchedule"
> }
> ```

- 할일 단일 삭제
  - DELETE
  - /schedules/{userId}/{scheduleId}

---

- 댓글 생성
  - POST
  - /{scheduleId}/comments
> request
> ```json
> {
>     "comment": "testComment"
> }
> ```
> response
> ```json
> {
>     "id": 1,
>     "comments": "testComment",
>     "username": "test"
> }
> ```

- 댓글 전체 조회
  - GET
  - /{scheduleId}/comments
> response
> ```json
> {
>     "content": [
>         {
>             "id": 2,
>             "comments": "testComment",
>             "username": "test"
>         },
>         {
>             "id": 1,
>             "comments": "testComment",
>             "username": "test"
>         }
>     ],
>     "pageable": {
>         "pageNumber": 0,
>         "pageSize": 10,
>         "sort": {
>             "empty": false,
>             "sorted": true,
>             "unsorted": false
>         },
>         "offset": 0,
>         "paged": true,
>         "unpaged": false
>     },
>     "last": true,
>     "totalPages": 1,
>     "totalElements": 2,
>     "first": true,
>     "numberOfElements": 2,
>     "size": 10,
>     "number": 0,
>     "sort": {
>         "empty": false,
>         "sorted": true,
>         "unsorted": false
>     },
>     "empty": false
> }
> ```

- 댓글 수정
  - PATCH
  - /{scheduleId}/comments/{commentId}
> request
> ```json
> {
>     "newComment": "newComment"
> }
> ```

- 댓글 삭제
  - DELETE
  - /{scheduleId}/comments/{commentId}

---

2. **ERD**

![erd.png](erd.png)

---

### Lv 1. 일정 CRUD

- 일정을 생성, 조회, 수정, 삭제
- 일정은 아래 필드를 가짐
  - 작성 유저명, 할일 제목, 할일 내용, 작성일, 수정일 필드
  - 작성일, 수정일 필드는 `JPA Auditing`을 활용

---

### Lv 2. 유저 CRUD

- 유저를 생성, 조회, 수정, 삭제
- 유저는 아래와 같은 필드를 가짐
  - 유저명, 이메일, 작성일, 수정일 필드
  - 작성일, 수정일 필드는 `JPA Auditing`을 활용
- 연관관계 구현
  - 일정은 이제 작성 유저명 필드 대신 유저 고유 식별자 필드를 가짐

---

### Lv 3. 회원가입

1. 유저에 비밀번호 필드를 추가

---

### Lv 4. 로그인(인증)

- Cookie / Session을 활용해 로그인 기능을 구현
- 필터를 활용해 인증 처리
- @Configuration 을 활용해 필터를 등록
- 조건
  - 이메일과 비밀번호를 활용해 로그인 기능을 구현
  - 회원가입, 로그인 요청은 인증 처리에서 제외

---

### Lv 5. 다양한 예외처리 적용하기

- Validation을 활용해 다양한 예외처리를 적용
- 정해진 예외처리 항목이 있는것이 아닌 프로젝트를 분석하고 예외사항을 지정

---

### Lv 6. 비밀번호 암호화

- Lv.3에서 추가한 비밀번호 필드에 들어가는 비밀번호를 암호화
  - 암호화를 위한 `PasswordEncoder`를 직접 만들어 사용

---

### Lv 7. 댓글 CRUD

- 생성한 일정에 댓글을 남길 수 있음
  - 댓글과 일정은 연관관계를 가짐
- 댓글을 저장, 조회, 수정, 삭제
- 댓글은 아래와 같은 필드
  - 댓글 내용, 작성일, 수정일, 유저 고유 식별자, 일정 고유 식별자 필드
  - 작성일, 수정일 필드는 `JPA Auditing`을 활용하여 적용

---

### Lv 8. 일정 페이징 조회

- 일정을 Spring Data JPA의 Pageable과 Page 인터페이스를 활용하여 페이지네이션을 구현
  - 페이지 번호와 페이지 크기를 쿼리 파라미터로 전달하여 요청하는 항목을 나타냄
  - 할일 제목, 할일 내용, 댓글 개수, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드를 조회
  - 디폴트 페이지 크기는 10으로 적용
- 일정의 수정일을 기준으로 내림차순 정렬

---