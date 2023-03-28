JPA (Java Persistence API)
============================================

**_ORM은 객체와 RDB 두 기둥위에 있는 기술이다. - 김영한_

#### JPA란
* JPA는 자바 ORM 기술에 대한  API 표준
- ORM이란 Object Realational Mapping의 약자로 객체와 관계형 데이터베이스를 매핑해주는 것을 말함

*객체는 객체 지향적으로, 데이터베이스는 데이터베이스대로 설계를 하며, JPA는 중간에서 매핑하는 역할을 함. 이를 통해서 개발자는 소스를 더 객체지향적으로 설계하고 비즈니스 로직에 집중할 수 있음.

**_즉 Entity Mapping만 내가 잘해준다면 쿼리문을 직접 짜는 귀찮음을 없앨 수 있다~!_**

#### JPA 사용 시 장점
1. 특정 데이터 베이스에 종속되지 않음 - *Database Dialect(방언)* 사용으로 DBMS마다 다른 SQL문법을 자동으로 처리해줌
2. 객체지향적 프로그래밍 - 데이터베이스 설계 중심의 패러다임에서 객체지향적으로 설계가 가능함
3. 생산성 향상 - 예를들어 테이블에 새로운 컬럼이 추가 되었을경우 해당 테이블을 사용하는 DTO 클래스의 필드도 모두 변경해야하나, JPA에서는 테이블과 매핑된 클래스에 필드만 추가한다면 쉽게 관리가 가능함.

#### JPA 사용시 단점
1. 복잡한 쿼리 처리 - 통계와 같은 복잡한 쿼리를 사용할 경우에는 직접 SQL문을 사용하는게 나을 수 있음. JPA에서는 네이티브 SQL을 통해 기존의 SQL문을 사용할 수는 있지만 특정 데이터베이스에 종속된다는 단점이 생김. 이를 보완하기 위해 JPQL을 지원함.
2. 성능 저하의 위험 - 객체간 매핑 설계를 잘못했을 때, 성능저하가 발생할 수 있음.
3. 학습 시간 - 제대로 JPA를 사용하려면 배워야 할게 많아서 학습 시간이 적지 않음....

#### JPA의 성능 최적화 기능
1. 1차 캐시와 동일성(identity) 보장
	- 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상!
	- DB Isolation Level이 Read Commitdㅣ어도 어플리케이션에서 Repeatable Read를 보장함
2. 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
	1) INSERT
		- 트랜잭션을 커밋하기 전까지 INSERT SQL을 모아놓음
		- JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송 -> *커밋시 한번에 쿼리문 발사!
	2) UPDATE
		- UPDATE, DELETE로 인한 로우(ROW)락 시간 최소화!
		- 트랜잭션 커밋시 UPDATE, DELETE SQL 실행 후 바로 커밋해버림!
1. 지연 로딩(Lazy Loading)
	- 지연 로딩과 즉시 로딩
		1) 지연 로딩 : 객체가 실제 사용될 때 로딩함
		2) 즉시 로딩 : JOIN SQL로 한번에 연관된 객체까지 미리 조회



##### 생산성 - JPA CRUD
1. Create - persist(객체)
2. Read - find()
3. Update - 객체.set필드명(수정할내용)  ex) member.setName("heechan")
4. Delete - remove(객체)

### JPA에서 가장 중요한 2가지
1. 객체와 관계형 데이터베이스 매핑하기(Object Relational Mapping)
2. **영속성 컨텍스트

###### 영속성 컨텍스트
- JPA를 이해하는데 가장 중요한 용어
- "엔티티를 영구 저장하는 환경" 이라는 뜻
- **EntityManager.persist(entity);

##### EntityManager ? 영속성 컨텍스트 ?
- 영속성 컨텍스트는 논리적인 개념이다.
- 엔티티 매니저를 통해서 영속성 컨텍스트에 접근

##### 엔티티의 생명주기
- 비영속 (new/transient)
  영속성 컨텍스트와 전혀 관계가 *없는* 새로운 상태
- 영속 (managed)
  영속성 컨텍스트에 *관리*되는 상태
- 준영속 (detached)
  영속성 컨텍스트에 저장되었다가 *분리*된 상태
- 삭제 (remove)
  *삭제*된 상태

##### 영속성 컨텍스트의 이점
1. 1차 캐시
2. 동일성(identity) 보장
3. 트랜잭션을 지원하는 쓰기 지연
4. 변경 감지(Dirty Checking)
5. 지연 로딩(Lazy Loading)

##### 플러시
> 영속성 컨텍스트의 변경 내용을 데이터베이스에 반영

- 변경 감지
- 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)

###### 영속성 컨텍스트를 플러시하는 방법
1. entityManager.flush() - 직접 호출
2. 트랜잭션 커밋 - 플러시 자동 호출
3. JPQL 쿼리 실행 - 플러시 자동 호출

###### 플러시 모드 옵션
1. entityManager.setFlushMode(FlushModeType.AUTO)
   커밋이나 쿼리를 실행할 때 플러시 (기본값)
2. entityManager.setFlushMode(FlushModeType.COMMIT)
   커밋할 때만 플러시

###### 플러시는!!!
- 영속성 컨텍스트를 비우지 않는다.
- 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화함.
- *트랜잭션*이라는 작업 단위가 중요하다 -> 커밋 직전에만 동기화 하면 된다!!


##### 준영속 상태
- 영속 - >  준영속이 된 상태
- 영속 상태의 Entity가 영속성 컨텍스트에서 분리됨(detached)
- 영속성 컨텍스트가 제공하는 기능을 사용 못함

###### 준영속 상태로 만드는 방법
1. em.detach(entity) - 특정 엔티티만 준영속 상태로.
2. em.clear() - 영속성 컨텍스트를 완전히 초기화
3. em.close() - 영속성 컨텍스트를 종료함.


##### Entity 매핑
	객체와 테이블 매핑 : @Entity, @Table
	필드와 컬럼 매핑 : @Column
	기본키 매핑 : @Id
	*연관관계 매핑 : @ManyToOne, @JoinColumn

###### 객체와 테이블 매핑
1. @Entity
	- @Entity가 붙은 클래스는 JPA가 관리함.
	- JPA를 사용해서 테이블과 매핑할 클래스는 *@Entity* 필수!
	- 주의할 점
		- *기본 생성자 필수*(파라미터가 없는 public 또는 protected 생성자)
		- final클래스, enum, interface, inner 클래스 사용 *X*
		- 저장할 필드에 final 사용 *X*
	- 속성 정리
		- @Entity(name="")
			- JPA에서 사용할 엔티티 이름을 저장함
			- 기본값 : 클래스 이름을 그대로 사용(예 : Member)
			- 같은 클래스 이름이 없으면 가급적 기본값을 사용한다.

2. @Table
	- @Table은 엔티티와 매핑할 테이블 지정
	- 속성 정리
		- @Table(name="") - 매핑할 테이블 이름(기본값 : 엔티티 이름 사용)
		- @Table(catalog="") - 데이터베이스 catalog 매핑
		- @Table(schema="") - 데이터베이스 schema 매핑
		- @Table(uniqueConstraints) - DDL 생성 시 유니크 제약 조건 생성

> 데이터 베이스 스키마 자동 생성
- DDL을 어플리케이션 실행 시점에 자동 생성
- 테이블 중심 -> 객체 중심으로
- 데이터베이스 방언(Dialect)을 활용하여 데이터베이스에 맞는 적절한 DDL 생성함
- 이렇게 생성된 DDL은 *개발장비*에서만 사용하도록 한다!! -> *운영* 절대금지

> 데이터베이스 스키마 자동 생성 - 속성
hibernate.hbm2ddl.auto  (persistence.xml에 적용)
- create --> 기존 테이블 삭제 후 다시 생성함(DROP후 CREATE)
- create-drop --> create와 같으나 종료시점에 테이블을 삭제(DROP > CREATE > DROP)
- update --> 변경부분만 반영함(운영 DB에는 사용하지마세요!)
- validate --> 엔티티와 테이블이 정상 매핑 되었는지 확인함
- none --> 사용하지 않음(그냥 아예 쓰지 않는거랑 같음)

> 데이터베이스 스키마 자동 생성 - 주의
- *운영 장비에는 절대 create, create-drop, update를 사용하지 말라!*
- 개발 초기 단계에서는 create 또는 update
- 테스트 서버에서는 update 또는 validate
- 스테이징과 운영 서버는 validate 또는 none

> DDL 생성 기능
- 제약조건 추가
  ex) 회원 이름은 필수, 10자 초과X
  @Column(nullable = false, length = 10)
- 유니크 제약조건 추가
  @Table(uniqueConstraints = {@UniqueConstraint( name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"} )})
- *DDL 생성 기능은 DDL을 자동 생성할 때에만 사용되고, JPA 실행 로직에는 영향을 주지 않는다.*

###### 필드와 컬럼 매핑
1. @Column : 컬럼 매핑
	- 속성 정리
		- name - 필드와 매핑할 테이블의 컬럼 이름( 기본값 : 객체의 필드 이름)
		- insertable, updatable - 등록, 변경 가능 여부( 기본값 : true)
		- nullable(DDL) - null 가능여부 설정 -- false일때 *not null* 붙음
		- unique(DDL) - 유니크 제약조건 걸때 사용
		- columnDefinition(DDL) - 데이터베이스 컬럼 정보를 직접 줄 수 있음 ex) varchar(100) default 'EMPTY'
		- length(DDL) - 문자 길이 제약 조건 -- String 타입에*만* 사용한다.
		- precision, scale(DDL) - BigDecimal 타입에서 사용함(BigInteger도 가능) / precision은 소수점을 포함한 전체 자릿수를, scale은 소수의 자릿수
1. @Temporal : 날짜 타입 매핑
	- 날짜 타입(Date, Calendar)을 매핑할 때 사용
	- 참고 - LocalDate, LocalDateTime은 생략 가능(최신 Hibernate에서 지원함!)
	- 속성 정리
		- value
			- TemporalType.DATE : 날짜, 데이터베이스 date 타입과 매핑 ex) 2013-10-11
			- TemporalType.TIME : 시간, 데이터베이스 time 타입과 매핑 ex) 11:11:11
			- TemporalType.TIMESTAMP : 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 ex) 2013-1011 11:11:11
1. @Enumerated : enum 타입 매핑
	- 자바 enum 타입을 매핑할 때 사용
	- *주의! ORIDINAL 사용 금지 ---- enum 순서를 저장하는데 enum에 추가가 된다면 순서가 달라질 수 있기 때문이다.*
	- 속성 정리
		- value
			- EnumType.ORIDINAL : enum 순서를 데이터베이스에 저장함 *기본값*
			- EnumType.String : enum 이름을 데이터베이스에 저장함 *이걸 사용하도록*
1. @Lob : BLOB, CLOB 매핑
	- 데이터베이스 BLOB, CLOB 타입과 매핑
	- @LOB에는 지정할 수 있는 속성이 *없음*
	- 매핑하는 필드 타입이 *문자*면 CLOB 매핑, *나머지*는 BLOB 매핑
1. @Transient : 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
	- 필드 매핑 X
	- 데이터베이스에 저장X, 조회X
	- 주로 메모리상에만 임시로 어떤 값을 보관하고 싶을때 사용함.


###### 기본키 매핑
1. @Id
2. @GeneratedValue

- 직접 할당시 : @Id만 사용
- 자동 생성시 : @GeneratedValue 사용
	- IDENTITY : 데이터베이스에 위임 ex) @GeneratedValue(strategy = GenerationType.IDENTITY)
		- 기본 키 생성을 데이터베이스에 *위임*함
		- 주로 MySQL, POstgreSQL, SQL Server, DB2에서 사용 -- ex) MySQL : AUTO_INCREMENT
		- JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
		- IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL을 실행하고, DB에서 식별자를 조회함
	- SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용 ex) @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
		- 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트
		- Oracle, PostgreSQL, DB2, H2 에서 사용 -- ex) ORACLE SEQUENCE
		- @SequenceGenerator 속성정리
			- name : 식별자 생성기 이름 -- 기본값 : 필수
			- sequenceName : 데이터베이스에 등록된 시퀀스 이름 -- 기본값 : hibernate_sequence
			- initialValue : DDL 생성시에만 사용됨, 시퀀스 DDL을 생성할때 처음 시작하는 수를 지정함 -- 기본값 : 1
			- allocationSize : 시퀀스 한번 호출당 증가하는 수(*데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있다면 이 값을 반드시 1로 설정해야함*
			- catalog, schema : 데이터베이스 catalog, schema 이름
	- TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용됨 ex) @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
		- 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
		- 장점 : 모든 데이터베이스에 적용 가능
		- 단점 : 성능이슈
		- @TableGenerator 속성정리
			- name : 식별자 생성기 이름
			- table : 키생성 테이블명
			- pkColumnName : 시퀀스 컬럼 명
			- valueColumnNa : 시퀀스 값 컬럼 명 -- 기본값 : next_val
			- pkColumnValue : 키로 사용할 값 이름
			- initialValue : 초기 값, 마지막으로 생성된 값이 기준이다.
			- allocationSize : 시퀀스 한번 호출에 증가하는 수 -- 기본값 : *50*
			- catalog, schema : 데이터베이스 catalog, schema 명
			- uniqueConstraints(DDL) : 유니크 제약조건을 지정 가능
	- AUTO : 방언에 따라 자동 지정됨 *기본값*

> 권장하는 식별자 전략
- *기본 키 제약 조건* : not null, unique, *변하면 안된다.*
- 미래까지 위의 조건을 만족하는 자연키는 찾기 어려움.. > 대리키(대체키)를 사용하자.
- 권장 : Long 타입 + 대체키 + 키 생성전략 사용
-  ---- > 즉, 기본키는 auto increment나 sequence를 사용!


## 실전 예제 - 1. 요구 사항 분석과 기본 매핑

> 요구사항 분석
- 회원은 상품을 주문할 수 있다.
- 주문 시 여러 종류의 상품을 선택할 수 있다.

> 도메인 모델 분석
- 회원과 주문의 관계 : 회원은 여러번 주문 할 수 있다. (일대 다)
- 주문과 상품의 관계 : 주문할 때 여러 상품 선택 가능 - 반대로 한 상품도 여러번 주문 가능 -> 주문 상품 이라는 모델을 만들어 다대다 관계를 일대다, 다대일 관계로 풀어냄

> 테이블 설계

| MEMBER | ORDERS | ORDER_ITEM | ITEM |
| :---: |  :---: |  :---: |  :---: | 
|MEMBER_ID | ORDER_ID | ORDER_ITEM_ID | ITEM_ID
|NAME| MEMBER_ID(FK) | ORDER_ID(FK) | NAME
|CITY | ORDERDATE | ITEM_ID (FK) | PRICE
|STREET | STATUS | ORDERPRICE | STOCKQUANTITY
|ZIPCODE | | |COUNT | |


> 데이터 중심 설계의 문제점!!!
- 위의 방식은 객체 설계를 테이블 설계에 맞춘 방식이야.
- 테이블의 외래키를 객체에 그대로 가져옴
- 객체 그래프 탐색이 *불가능*
- 참조가 없으므로 UML도 잘못됨.

### 연관관계 매핑 기초
> 목표
- 객체와 테이블 연관관계의 차이를 이해하자.
- 객체의 참조와 테이블의 외래 키 매핑
- 용어 이해
	- 방향(Direction) : 단방향, 양방향
	- 다중성(Multiplicity): 다대일 (N:1), 일대다 (1:N), 일대일 (1:1), 다대다 (N:N) 이해
	- 연관관계의 주인(Owner) : 객체 양방향 연관관계는 관리 주인이 필요함.

> 연관관계가 필요한 이유

객체를 테이블에 맞춰 데이터 중심으로 모델링 하면, 협력 관계를 만들 수 없다.
- 테이블은 외래키로 조인을 사용해서 연관된 테이블을 찾는다.
- 객체는 참조를 사용해서 연관된 객체를 찾는다.
- 테이블과 객체 사이에는 이런 큰 간격이 있다.


##### 양방향 연관관계와 연관관계의 주인(중요!)

> 객체와 테이블이 관계를 맺는 차이
- 객체 연관 관계 = 2개
	-  회원 -> 팀 연관관계 1개(단방향)
	- 팀 -> 회원 연관관계 1개(단방향)
- 테이블 연관관계 = 1개
	-  회원 <-> 팀 연관관계 1개(양방향)

> 테이블의 양방향 연관관계
- 테이블은 외래 키(FK) 하나로 두 테이블의 연관관계를 관리한다.
- MEMBER.TEAM_ID 외래키 하나로 양방향 연관관계를 가짐 (양쪽으로 쪼인 가능!)

> **연관관계의 주인(Owner)**
양방향 매핑 규칙
- 객체의 두 관계중 하나를 연관관계의 주인으로 지정
- **연관관계의 주인만이 외래 키를 관리(등록, 수정)
- **주인이 아닌쪽은 읽기만 가능함
- 주인은 mappedBy 속성 사용X (그럼 주인은 joinColumn을 사용하겠지 ?)
- 주인이 아니면 mappedBy 속성으로 주인 지정함

> **누구를 주인으로 할까 ???
- **외래키**가 있는곳을 주인으로 정하셈.

*객체 관계일때*
| MEMBER |     | TEAM |  
|:---:|-----|:---:|  
| id |     | id |  
| Team team |     | name |  
| username |     | List members|

*테이블 관계일때*
| MEMBER |     | TEAM |  
|:---:|-----|:---:|  
| MEMBER_ID(PK) |     | TEAM_ID(PK) |  
| TEAM_ID(FK) |     | NAME |  
| USERNAME |     | |
- 여기서는 MEMBER 테이블에 외래키(FK)가 있으니까 Member.team이 연관관계의 주인이라.
  그럼 Member의 team에는 @JoinColumn , Team의 members는 mappedBy 사용하겠지 ??
  1 : N 일경우 N쪽이 주인이 되어야해. 명심해.

##### 양방향 매핑시 가장 많이하는 실수
(연관관계의 주인에 값을 입력하지 않는 실수..)
```
Team team = new Team();
team.setName("TeamA");
em.persist(team);

Member member = new Member();
member.setName("member1");

// 역방향(주인이 아닌 방향)만 연관관계 설정
team.getMembers().add(member);

*** member.setTeam(team) ***

em.persist(member);
```
Member의 team이 주인일때,  member.team(주인)에 값을 주입 해줘야만 데이터 등록이 가능함.


> 양방향 연관관계 주의
- *순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자*  >>> 즉 주인만 해주지말고 둘다 값 넣어주자구~!
- 연관관계 편의 메소드를 작성하다
- 양방향 매핑시 무한루프를 *조심*하자 ex) toString(), lombok, JSON 생성 라이브러리

#### 양방향 매핑 정리
- 단방향 매핑만으로도 이미 연관관계 매핑은 완료.
- 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많음
- 단방향 매핑을 잘 하고 양방향은 필요할때 추가해도 됨(테이블에 영향을 주지 않음)

연관관계의 주인을 정하는 기준은 >
*연관관계의 주인은 외래키의 위치를 기준으로 정하라.*



### 연관관계 매핑 시작해보기
> practiceExample2 패키지
1. 테이블 구조일때
   <img width="433" alt="스크린샷 2023-03-24 오후 4 07 51" src="https://user-images.githubusercontent.com/88572442/227451824-2b813c93-798e-41b4-883d-d80788833e8a.png">

2. 객체구조일때(참조를 사용하도록 변경)
   <img width="419" alt="스크린샷 2023-03-24 오후 4 07 45" src="https://user-images.githubusercontent.com/88572442/227451849-6254bac9-94d0-4ec9-a9f0-e364569096e2.png">


### 다양한 연관관계 매핑
#### 연관관계 매핑시 고려사항 3가지
1. 다중성
	1) 다대일 : @ManyToOne <!-- 비중 최고 -->
	2) 일대다 : @OneToMany
	3) 일대일 : @OneToOne
	4) 다대다 : @ManyToMany <!-- 사실 실무에서 쓰면 안되.. -->
2. 단방향, 양방향
	1) 테이블
		- 외래 키 하나로 양쪽 조인 가능
		- 사실 방향이라는 개념이 없음
	2) 객체
		- *참조용 필드*가 있는 쪽으로만 참조 가능
		- 한쪽만 참조하면 단방향
		- 양쪽이 참조하면 양방향~! <!-- 사실 양방향이지만 단방향이 서로를 바라보는 느낌이라 생각해 -->
3. 연관관계의 주인
	1)  테이블은 *외래 키 하나*로 두 테이블이 연관관계를 맺음
	2) 객체 양방향 관계는 A -> B, B -> A 처럼 참조가 2군데
	3) 객체 양방향 관계는 참조가 2군데 있음. 둘중 테이블의 외래 키를 관리할 곳을 지정해야 함
	4) 연관관계의 주인 : 외래 키를 관리하는 참조 (주인은 @JoinColumn(name = "KEY")를 사용해 --> mappedBy 사용 XXX)
	5) 주인의 반대편 : 외래 키에 영향을 주지 않음, 단순 조회만 가능(mappedBy 사용 ex: @OneToMany(mappedBy = "order"))

#### 다대일 [N:1]
*가장 많이 사용 하는 연관 매핑~!*

> 다대일 단방향
<img width="380" alt="스크린샷 2023-03-24 오후 4 47 01" src="https://user-images.githubusercontent.com/88572442/228167465-c97ddb97-f8b8-47a6-ac6b-fec8dc9200f8.png">
1. Member의 Team team에 @ManyToOne 사용, @JoinColumn(name = "TEAM_ID")로 외래키 설정

> 다대일 양방향
<img width="295" alt="스크린샷 2023-03-24 오후 4 52 46" src="https://user-images.githubusercontent.com/88572442/228167489-0f7e1e0c-06aa-49e5-b406-3fdb1f0dce83.png">

1. Member의 Team team에 @ManyToOne 사용
2. @JoinColumn(name = "TEAM_ID")로 외래키 설정(주인)
3. Team의 List members에 @OneToMany(*mappedBy = "MEMBER_ID"*) 사용
###### 다대일 양방향 정리
- 외래 키가 있는 쪽이 연관관계의 주인
- 양쪽을 서로 참조하도록 개발

#### 일대다 [1:N]
> 일대다 단방향
<img width="276" alt="스크린샷 2023-03-28 오후 1 55 39" src="https://user-images.githubusercontent.com/88572442/228166286-22b9e602-4e4c-489a-8d9d-a9c62415b54e.png">
<!--Team을  중심으로 연관관계 성립 그러나 위의 그림은 추천하지 않음. 연관관계 주인을 다 쪽으로 가져가자-->
<!-- 일대다 단방향으로 연관관계 매핑을 하는것보단 다대일 양방향 매핑이 더욱 쓰임새가 좋다.-->
###### 일대다 단방향 정리
- 일대다 단방향은 *일(1)이 연관관계의 주인이다*
- 테이블 일대다 관계는 항상 *다(N) 쪽에 외래 키가 있음*
- 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 *특이한* 구조
- @JoinColumn을 *꼭* 사용해야한다. 그렇지 않으면 조인테이블 방식을 사용함(중간에 테이블을 하나 추가함)

- 일대다 단방향 매핑의 단점
	- 엔티티가 관리하는 외래 키가 다른 테이블에 있음
	- 연관관계 관리를 위해 추가로 UPDATE SQL 실행
- 일대다 단방향 매핑보다는 *다대일 양방향 매핑을 사용하자*

> 일대다 양방향
<img width="364" alt="스크린샷 2023-03-28 오후 4 11 42" src="https://user-images.githubusercontent.com/88572442/228166314-bce8ce15-f9d9-4e8d-9c8b-04c1b4495ce8.png">
<!-- 이런 매핑은 공식적으로 존재하지 않는다.-->
<!-- 허나 야매(?)로 가능은 하다
다(N)쪽의 컬럼에 @JoinColumn(name = "", insertable = false, updateable = false)를 넣어주는 방식으로...-->

- *읽기 전용 필드*를 사용해서 양방향 처럼 사용하는 방법
- *다대일 양방향을 사용하자!!!!!!!!!!!!!!!!!!!!!!!*

#### 일대일 [1:1]


#### 다대다 [N:M]

#### 실전 예제 - 3. 다양한 원관관계 매핑
