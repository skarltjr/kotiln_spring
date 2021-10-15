# BE-recruit

## 1주차 
### 1. 과제 설명

요구사항 항목을 참고하여 `spring-web-mvc`, `spring-data-jpa`, `kotlin` **메모장** API를 개발한다.


### 2. 요구사항

- API 응답 포맷을 아래와 같이 정의한다.
- API 실행결과에 알맞는 HttpStatus 를 정의해야한다.
- JSON 포멧이여야 한다. (View를 보지않습니다.)
- **단위 테스트 및 통합테스트 작성**
- Swagger 사용
- 각각의 브랜치에서 작업, 완료 후에는 1주차-[이름]으로 PR올리기

구현해야 하는 API 목록은 아래와 같다.

### 2.1 메모 저장 API

메모를 저장하고 저장된 결과를 리턴한다.

- URL: POST /memos
- Response: 정상적으로 저장된 메모 정보
    - createdAt & updatedAt (포멧: yyyy-MM-dd HH:mm:ss)

```json
{
  "memo": {
    "id": 1,
    "title": "This is Test Memo !",
    "text": "Lorem Ipsum is simply dummy text of the printing and typesetting industy.",
    "createdAt": "2021-09-01 15:30:30",
    "updatedAt": "2021-09-01 15:30:30"
  }
}
```

### 2.2 메모 수정 API

기존에 있던 메모를 수정하고 결과를 리턴한다

- URL: PUT /memos/{memoId}
    - {memoId}: memo PK
- Response: 정상적으로 수정된 메모 정보
    - createdAt & updatedAt (포멧: yyyy-MM-dd HH:mm:ss)

```json
{
  "memo":{
      "id": 1,
      "title": "This is Updated Memo !",
      "createdAt": "2021-09-01 15:30:30",
      "updatedAt": "2021-09-01 16:40:00"
    } 
}
```

### 2.3 메모 삭제 API

기존에 있던 메모를 삭제한다.

- URL: DELETE /memos/{memoId}
    - {memoId}: memo PK

### 2.4 날짜별 간단 메모정보 검색 API

기존에 있던 메모들을 날짜로 검색하고 생성시간 기준 최신순으로 간단한 결과를 

한 페이지당 5개의 리스트로 리턴한다. 

- URL: GET /memos?date={date} &page={page}
    - {date}: 생성날짜 조회 파라미터 (포멧: yyyy-MM-dd)
    - {page}: 검색을 원하는 페이지 (포멧: integer)
- Response: 날짜에 따른 간단한 메모검색 정보
    - createdAt & updatedAt (포멧: yyyy-MM-dd HH:mm:ss)

```json
{
  "memos": 
  [
    {
      "id": 3,
      "title": "This is Updated Memo !",
      "createdAt": "2021-09-01 21:30:30",
      "updatedAt": "2021-09-01 21:30:30"

    } ,
    {
      "id": 1,
      "title:": "This is Updated Memo !",
      "createdAt": "2021-09-01 12:30:20",
      "updatedAt": "2021-09-01 22:40:12"
    }
  ]
}
```

### 2.5 메모 상제정보 검색 API

기존에 있던 메모를 PK로 검색하고 상세 결과를 리턴한다. 

- URL: GET /memos/{memoId}
    - {memoId}: memoPK
- Response: 메모에 대한 상세정보
    - createdAt & updatedAt (포멧: yyyy-MM-dd HH:mm:ss)

```json
{
  "memo": {
    "id": 3,
    "title": "This is  Memo !",
    "text": "Lorem Ipsum is simply dummy text of the printing and typesetting industy.",
    "createdAt": "2021-09-01 15:30:30",
    "updatedAt": "2021-09-01 16:40:00"
  }
}
```
***

