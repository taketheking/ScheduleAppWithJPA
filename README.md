# JPA로 일정 관리 앱 서버 구축하기

## 프로젝트 소개
사용자가 입력한 일정을 날짜 별로 저장하고 조회할 수 있는 기능을 하는 앱의 서버를 JPA로 구축하는 프로젝트이다.

해당 프로젝트는 사용자 입력을 받는 앱이 있다는 가정하에 일정 생성, 일정 전체 조회, 일정 단건 조회, 일정 수정, 일정 삭제 및
유저 생성, 유저 조회, 유저 삭제, 로그인, 로그아웃 등의 기능을 하는 서버이다.


## 개발 환경

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

IDE : Intellij IDEA 2024.2.3

언어 : Java

프레임 워크 : 스프링 부트 3.3.5

JDK : corretto-17.0.13

RDBMS : MYSQL_Server 9.1.0 

버전관리도구 : GitHub


## 목표
jPA을 스프링부트 프로젝트에 활용해보자!


## 1. 요구사항 분석
### Lv 1. 일정 CRUD  `필수`

- [ ]  일정을 저장, 조회, 수정, 삭제할 수 있습니다.
- [ ]  일정은 아래 필드를 가집니다.
    - [ ]  `작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용하여 적용합니다.

### Lv 2. 유저 CRUD  `필수`

- [ ]  유저를 저장, 조회, 삭제할 수 있습니다.
- [ ]  유저는 아래와 같은 필드를 가집니다.
    - [ ]  `유저명`, `이메일`, `작성일`, `수정일` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용하여 적용합니다.
- [ ]  연관관계 구현
    - [ ]  일정은 이제 `작성 유저명` 필드 대신 `유저 고유 식별자` 필드를 가집니다.

### Lv 3. 회원가입  `필수`

- [ ]  유저에 `비밀번호` 필드를 추가합니다.
    - 비밀번호 암호화는 도전 기능에서 수행합니다.

### Lv 4. 로그인(인증)  `필수`

- 키워드
    
    **인터페이스**
    
    - HttpServletRequest / HttpServletResponse : 각 HTTP 요청에서 주고받는 값들을 담고 있습니다.
- [ ]  **설명**
    - [ ]  **Cookie/Session**을 활용해 로그인 기능을 구현합니다. → `2주차 Servlet Filter`
    - [ ]  필터를 활용해 인증 처리를 할 수 있습니다.
    - [ ]  `@Configuration` 을 활용해 필터를 등록할 수 있습니다.
- [ ]  **조건**
    - [ ]  `이메일`과 `비밀번호`를 활용해 로그인 기능을 구현합니다.
    - [ ]  회원가입, 로그인 요청은 인증 처리에서 제외합니다.
- [ ]  **예외처리**
    - [ ]  로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
     

### Lv 5. 다양한 예외처리 적용하기  `도전`

- [ ]  Validation을 활용해 다양한 예외처리를 적용해 봅니다. → `1주차 Bean Validation`
- [ ]  정해진 예외처리 항목이 있는것이 아닌 프로젝트를 분석하고 예외사항을 지정해 봅니다.
    - [ ]  Ex) 할일 제목은 10글자 이내, 유저명은 4글자 이내
    - [ ]  `@Pattern`을 사용해서 회원 가입 Email 데이터 검증 등
        - [ ]  정규표현식을 적용하되, 정규표현식을 어떻게 쓰는지 몰두하지 말 것!
        - [ ]  검색해서 나오는 것을 적용하는 것으로 충분!

### Lv 6. 비밀번호 암호화  `도전`

- [ ]  Lv.3에서 추가한 `비밀번호` 필드에 들어가는 비밀번호를 암호화합니다.
    - [ ]  암호화를 위한 `PasswordEncoder`를 직접 만들어 사용합니다.
        
### Lv 7. 댓글 CRUD  `도전`

- [ ]  생성한 일정에 댓글을 남길 수 있습니다.
    - [ ]  댓글과 일정은 연관관계를 가집니다.
- [ ]  댓글을 저장, 조회, 수정, 삭제할 수 있습니다.
- [ ]  댓글은 아래와 같은 필드를 가집니다.
    - [ ]  `댓글 내용`, `작성일`, `수정일`, `유저 고유 식별자`, `일정 고유 식별자` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용하여 적용합니다.

### Lv 8. 영속성 전이를 활용한 삭제   `도전`

- [ ]  유저를 삭제할 때 해당 유저가 생성한 일정과 댓글도 삭제
    - [ ]  이 때, JPA의 영속성 전이 기능을 활용

### Lv 9. 일정 페이징 조회  `도전`

- 키워드
    
    **데이터베이스**
    
    - offset / limit : SELECT 쿼리에 적용해서 데이터를 제한 범위에 맞게 조회할 수 있습니다.
    
    **페이징**
    
    - Pageable : Spring Data JPA에서 제공되는 페이징 관련 인터페이스 입니다.
    - PageRequest : Spring Data JPA에서 제공되는 페이지 요청 관련 클래스입니다.
- [ ]  일정을 Spring Data JPA의 `Pageable`과 `Page` 인터페이스를 활용하여 페이지네이션을 구현
    - [ ]  `페이지 번호`와 `페이지 크기`를 쿼리 파라미터로 전달하여 요청하는 항목을 나타냅니다.
    - [ ]  `할일 제목`, `할일 내용`, `댓글 개수`, `일정 작성일`, `일정 수정일`, `일정 작성 유저명` 필드를 조회합니다.
    - [ ]  디폴트 `페이지 크기`는 10으로 적용합니다.
- [ ]  일정의 `수정일`을 기준으로 내림차순 정렬합니다.



## 2. API 명세서

### 1) 일정 테이블 (Schedule Table)

<table>
    <tr>
      <th scope="col">기능</td>
      <th scope="col">Method</td>
      <th scope="col">URL</th>
      <th scope="col">Request</td>
      <th scope="col">Response</td>
      <th scope="col">상태코드</td>
    </tr>
    <tr>
      <td>일정 생성</td>
      <td>POST</td>
      <td>/api/schdules</td>
      <td>요청 body</td>
      <td>등록된 데이터 정보</td>
      <td>200: 정상, 400: 잘못된 값 입력</td>
    </tr>
    <tr>
      <td>전체 일정 조회</td>
      <td>GET</td>
      <td>/api/schdules</td>
      <td>요청 params</td>
      <td>조건에 맞는 데이터 정보들</td>
      <td>200: 정상, 400: 잘못된 값 입력</td>
    </tr>
    <tr>
      <td>단건 일정 조회</td>
      <td>GET</td>
      <td>/api/schdules/{id}</td>
      <td>요청 param</td>
      <td>요청한 데이터 정보</td>
      <td>200: 정상, 404: 해당 데이터 존재하지 않음</td>
    </tr>
    <tr>
      <td>단건 일정 수정</td>
      <td>PATCH</td>
      <td>/api/schdules/{id}</td>
      <td>요청 param, 요청 body</td>
      <td>수정된 데이터 정보</td>
      <td>200: 정상, 400: 잘못된 값 입력, 404: 해당 데이터 존재하지 않음</td>
    </tr>
    <tr>
      <td>단건 일정 삭제</td>
      <td>Delete</td>
      <td>/api/schdules/{id}</td>
      <td>요청 param, 요청 body</td>
      <td>없음</td>
      <td>200: 정상, 400: 잘못된 값 입력, 404: 해당 데이터 존재하지 않음</td>
    </tr>
  </table>

### 2) 유저 테이블 (User Table)

<table>
    <tr>
      <th scope="col">기능</td>
      <th scope="col">Method</td>
      <th scope="col">URL</th>
      <th scope="col">Request</td>
      <th scope="col">Response</td>
      <th scope="col">상태코드</td>
    </tr>
    <tr>
      <td>유저 생성</td>
      <td>POST</td>
      <td>/api/users/signup</td>
      <td>요청 body</td>
      <td>등록된 데이터 정보</td>
      <td>200: 정상, 400: 잘못된 값 입력</td>
    </tr>
    <tr>
      <td>유저 조회</td>
      <td>GET</td>
      <td>/api/users/{id}</td>
      <td>요청 params</td>
      <td>조건에 맞는 데이터 정보들</td>
      <td>200: 정상, 400: 잘못된 값 입력,  401: 로그인 인증 안됨</td>
    </tr>
    <tr>
      <td>유저 삭제 </td>
      <td>Delete</td>
      <td>/api/users/{id}</td>
      <td>요청 param</td>
      <td>요청한 데이터 정보</td>
      <td>200: 정상, 401: 로그인 인증 안됨, 404: 해당 데이터 존재하지 않음</td>
    </tr>
    <tr>
      <td>로그인</td>
      <td>POST</td>
      <td>/api/users/login</td>
      <td>요청 body</td>
      <td>수정된 데이터 정보</td>
      <td>200: 정상, 400: 잘못된 값 입력, 401: 로그인 정보 불일치, 404: 해당 데이터 존재하지 않음</td>
    </tr>
    <tr>
      <td>로그아웃</td>
      <td>POST</td>
      <td>/api/users/logout</td>
      <td>없음</td>
      <td>없음</td>
      <td>200: 정상, 400: 잘못된 값 입력, 401: 로그인 인증 안됨, 404: 해당 데이터 존재하지 않음</td>
    </tr>
  </table>

<details>
<summary>일정 생성</summary>
  
 - 설명 : 일정을 하나 생성합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/schdules</td>
    </tr>
  </table>

 - 예제
   - **요청** : JSON
    ```
    {
      "title" : "과제하기",
      "contents" : "레벨 6까지!",
      "email" : "sparta@teamsparta.com"
    }
    ```
   - **응답**
     
   HTTP/1.1 201 Created
   ```
   {
     "id": 3,
     "title": "과제하기",
     "contents": "레벨 6까지!",
     "username": "김스파르타"
   }
   ```

 - 본문
   - **요청**
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>title</td>
      <td>String</td>
      <td>O</td>
      <td>일정 제목</td>
    </tr>
     <tr>
      <td>contents</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
     <tr>
      <td>email</td>
      <td>String</td>
      <td>O</td>
      <td>작성자 email</td>
    </tr>
    
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>title</td>
      <td>String</td>
      <td>O</td>
      <td>일정 제목</td>
    </tr>
     <tr>
      <td>contents</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
     <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
    </tr>
  </table>
</details>

<details>
<summary>전체 일정 조회</summary>
  
 - 설명 : 조건에 맞는 일정을 모두 조회합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>localhost:8080/schdules</td>
    </tr>
  </table>

 - 예제
   - **요청** : 없음
    ```
  
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
   [
    {
        "id": 2,
        "title": "과제하기",
        "contents": "레벨 6까지!",
        "username": "김스파르타"
    },
    {
        "id": 3,
        "title": "잠자기",
        "contents": "잠이 보약이다.",
        "username": "김스파르타"
    }
]
    ```

 - 본문
   - **요청** : 없음
 
   - **응답** : List
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>title</td>
      <td>String</td>
      <td>O</td>
      <td>일정 제목</td>
    </tr>
     <tr>
      <td>contents</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
     <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
    </tr>
  </table>
</details>

<details>
<summary>선택 일정 조회</summary>
   
 - 설명 : 일정을 하나 조회합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/schdules/{id}</td>
    </tr>
  </table>

 - 예제
   - **요청** : PathVariable
    ```
      {
        2
      }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
    {
    "id": 2,
    "title": "과제하기",
    "contents": "레벨 6까지!",
    "username": "김스파르타"
    }
    ```

 - 본문
   - **요청** : PathVariable
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 고유 식별자 ID</td>
    </tr>
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
      <th scope="col">전달 방법</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
      <td>PathVariable</td>
    </tr>
    <tr>
      <td>title</td>
      <td>String</td>
      <td>O</td>
      <td>일정 제목</td>
      <td>JSON</td>
    </tr>
     <tr>
      <td>contents</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
       <td>JSON</td>
    </tr>
     <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
       <td>JSON</td>
    </tr>
  </table>
</details>

<details>
<summary>선택 일정 수정</summary>

 - 설명 : 일정을 하나 수정합니다.

 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>PATCH</td>
      <td>localhost:8080/schdules/{id}</td>
    </tr>
  </table>

 - 예제
   - **요청** : PathVariable + JSON
    ```
    // PathVariable
    {
      3
    }

    // requestBody 내의 JSON
    {
    "title" : "잠자기",
    "contents" : "잠이 보약이다."
    }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
   {
    "id": 3,
    "title": "잠자기",
    "contents": "잠이 보약이다.",
    "username": "김스파르타"
   }
    ```

 - 본문
   - **요청** : PathVariable + JSON
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
      <th scope="col">전달 방법</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 고유 식별자 ID</td>
      <td>PathVariable</td>
    </tr>
    <tr>
      <td>title</td>
      <td>String</td>
      <td>O</td>
      <td>일정 제목</td>
      <td>JSON</td>
    </tr>
     <tr>
      <td>contents</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
       <td>JSON</td>
    </tr>
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 테이블의 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>title</td>
      <td>String</td>
      <td>O</td>
      <td>일정 제목</td>
    </tr>
     <tr>
      <td>contents</td>
      <td>String</td>
      <td>O</td>
      <td>일정 내용</td>
    </tr>
     <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
    </tr>
  </table>
</details>

<details>
<summary>선택 일정 삭제</summary>

 - 설명 : 일정을 하나 삭제합니다.

 - 기본 정보
   
  |메소드|URL|
  |:---|:---:|
  |DELETE|localhost:8080/schdules/{id}|

 - 예제
   - **요청** : PathVariable
    ```
    // PathVariable
    {
      3
    }

    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
     {}
   ```
- 본문
   - **요청** : PathVariable
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>일정 고유 식별자 ID</td>
    </tr>
  </table>
  
   - **응답** :
     
    없음
</details>


---

<details>
<summary>유저 생성</summary>
  
 - 설명 : 유저를 생성합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/users/signup</td>
    </tr>
  </table>

 - 예제
   - **요청** : JSON
    ```
    {
    "username" : "김스파르타",
    "password" : "1234",
    "email" : "sparta@teamsparta.com"
    }
    ```
    
   - **응답** :
     
   HTTP/1.1 201 Created
   ```
   {
    "id": 1,
    "username": "김스파르타",
    "email": "sparta@teamsparta.com"
    }
    ```

 - 본문
   - **요청**
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
    </tr>
     <tr>
      <td>password</td>
      <td>String</td>
      <td>O</td>
      <td>비밀번호</td>
    </tr>
     <tr>
      <td>email</td>
      <td>String</td>
      <td>O</td>
      <td>유저 email</td>
    </tr>
    
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>유저 고유 식별자 아이디</td>
    </tr>
    <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
    </tr>
     <tr>
      <td>email</td>
      <td>String</td>
      <td>O</td>
      <td>유저 email</td>
    </tr>
  </table>
</details>

<details>
<summary>유저 조회</summary>
   
 - 설명 : 유저를 조회합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>localhost:8080/users/{id}</td>
    </tr>
  </table>

 - 예제
   - **요청** : PathVariable
    ```
      {
        1
      }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
   {
    "username": "김스파르타",
    "email": "sparta@teamsparta.com"
    }
    ```

 - 본문
   - **요청** : PathVariable
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>유저 고유 식별자 ID</td>
    </tr>
  </table>
  
   - **응답**
  <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
  </tr>
    <tr>
      <td>username</td>
      <td>String</td>
      <td>O</td>
      <td>유저명</td>
    </tr>
     <tr>
      <td>email</td>
      <td>String</td>
      <td>O</td>
      <td>유저 email</td>
    </tr>
  
  </table>
</details>

<details>
<summary>유저 삭제</summary>

 - 설명 : 유저를 삭제합니다.

 - 기본 정보
   
  |메소드|URL|
  |:---|:---:|
  |DELETE|localhost:8080/users/{id}|

 - 예제
   - **요청** : PathVariable
    ```
    // PathVariable
    {
      3
    }
    ```
   - **응답**
     
   HTTP/1.1 200 OK
   ```
     {}
   ```
- 본문
   - **요청** : PathVariable
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
    <tr>
      <td>id</td>
      <td>Long</td>
      <td>O</td>
      <td>유저 고유 식별자 ID</td>
    </tr>
  </table>
  
   - **응답** :
 
      없음
</details>

<details>
<summary>로그인</summary>
   
 - 설명 : 로그인합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/users/login</td>
    </tr>
  </table>

 - 예제
   - **요청** : JSON
    ```
    {
    "email" : "sparta@teamsparta.com",
    "password" : "1234"
    }
    ```
    
   - **응답** :
     
   HTTP/1.1 200 OK

   없음


 - 본문
   - **요청** : JSON
   <table>
    <tr>
      <th scope="col">이름(컬럼)</td>
      <th scope="col">타입</td>
      <th scope="col">필수(Nullable)</td>
      <th scope="col">설명</td>
    </tr>
     <tr>
      <td>email</td>
      <td>String</td>
      <td>O</td>
      <td>유저 email</td>
    </tr>
    <tr>
      <td>password</td>
      <td>String</td>
      <td>O</td>
      <td>비밀번호</td>
    </tr>
  </table>
  
   - **응답** :

    없음
</details>


<details>
<summary>로그아웃</summary>
   
 - 설명 : 로그아웃합니다.
  
 - 기본 정보
 <table>
    <tr>
      <th scope="col">메소드</td>
      <th scope="col">URL</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>localhost:8080/users/logout</td>
    </tr>
  </table>

 - 예제
   - **요청** :

     없음
   
   - **응답** :
     
   HTTP/1.1 200 OK

   없음


 - 본문
   - **요청** :

     없음
   
   - **응답** :
    
     없음
     
</details>


## 3. ERD

![ERD2](https://github.com/user-attachments/assets/6356c68b-4d00-4c9f-b9f5-540e2a53223a)


## 4. SQL

### 1) 일정 및 작성자 테이블 생성

```sql
CREATE TABLE user (
	id	bigint	NOT NULL	COMMENT 'Auto Increament',
	username	varchar(40)	NOT NULL	COMMENT '일정을 작성한 유저의 이름',
	email	varchar(40)	NOT NULL	COMMENT '일정을 작성한 유저의 E-mail',
	enroll_date	datetime(6)	NOT NULL	COMMENT '최초 작성자 정보 등록 날짜 - 자동',
  modify_date	datetime(6)	NOT NULL	COMMENT '가장 최근작성자 정보 수정 날짜 - 자동'
);

CREATE TABLE `schedule` (
	`id`	bigint	NOT NULL	COMMENT 'Auto Increament',
	`title`	varchar(10)	NOT NULL	COMMENT '일정 제목',
	`contents`	varchar(200)	NOT NULL	COMMENT '일정 내용',
	`enroll_date`	datetime(6)	NOT NULL	COMMENT '최초 일정 작성 날짜 - 자동',
	`modify_date`	datetime(6)	NOT NULL	COMMENT '가장 최근 일정 내용 수정 날짜 - 자동',
	`user_id`	bigint	NOT NULL	COMMENT 'user 외래키',
  FOREIGN KEY (user_id) REFERENCES user(id)
);
```

### 2) 작성자 생성 및 일정 생성
```sql
-- Insert user
INSERT INTO user (id, username, email, enroll_date, modify_date)
VALUES (1, 'kim', 'sparta@teamsparta.co.kr', '2024-10-29 20:11:02', '2024-10-30 15:11:02');

-- Insert schedule
INSERT INTO schedule (id, title, contents, enroll_date, modify_date, user_id)
VALUES (1,'과제하기','로그인 기능 구현하기', '2024-10-29 09:12:40', '2024-10-30 19:20:24', 1);
```

### 3) 전체 일정 조회 
```sql
-- Select schedule
SELECT * FROM schedule;
)
```

### 4) 선택 일정 조회
```sql
-- Select schedule with id 1
SELECT * FROM schedule WHERE id = 1;
)
```

### 5) 선택 일정 수정
```sql
-- Update schedule
UPDATE schedule SET title = '상담하기', contents = " 오후 2시에 직업훈련 상담하기",  WHERE id = 1;
```

### 6) 선택 일정 삭제
```sql
-- Delete schedule with id 1
DELETE FROM schedule WHERE id = 1;
```

### 7) 유저 조회
```sql
-- Select user with id 1
SELECT * FROM user WHERE id = 1;
)
```

### 8) 유저 삭제
```sql
-- Delete user with id 1
DELETE FROM user WHERE id = 1;
```


## 5. 예시 API 결과 이미지 


## 6. 코드 설명 및 트러블 슈팅

[take_the_king 블로그](https://velog.io/@take_the_king/JPA-%EC%9D%BC%EC%A0%95-%EA%B4%80%EB%A6%AC-%EC%95%B1-%EC%84%9C%EB%B2%84-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EA%B3%BC%EC%A0%9C-%EC%84%A4%EB%AA%85-%EB%B0%8F-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)
