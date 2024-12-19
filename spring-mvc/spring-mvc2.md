## 검증2 - Bean Validation

### Bean Validation
- 검증 기능을 매번 코드로 작성하는 것은 상당히 번거롭다.
- 특히 특정 필드에 대한 검증 로직은 대부분 빈 값인지 아닌지, 특정 크기를 넘는지 아닌지를 검증하는게 대부분이다.
- 검증 로직을 모든 프로젝트에 적용할 수 있게 공통화하고 표준화한 것이 Bean Validation이다.
- Bean Validation이란 특정한 구현체가 아니라 기술 표준이다.
- 검증 애노테이션과 여러 인터페이스의 모임이다.
- Bean Validation을 구현한 구현체 중에서 주로 사용하는 것은 Hibernate Validator이다. (ORM과 상관 없음)

### Bean Validation 사용하기
- spring-boot-starter-validation을 다운받아야 hibernate 구현체가 있다.
- 실무에서는 대부분 hibernate validation을 사용한다.
- Bean Validation을 직접 사용하면 validator를 생성해서 메시지를 출력해볼 수 있다.

### Bean Validation 스프링 적용
- 스프링 MVC는 어떻게 Bean Validation을 사용할까?
- 스프링 부트가 validation 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합한다.
- 스프링 부트는 자동으로 글로벌 Validator로 등록한다.
- LocalValidatorFactoryBean을 글로벌 Validator로 등록한다.
- 해당 Validator가 @NotNull 같은 애노테이션을 보고 검증을 수행한다.
- 이렇게 글로벌 Validator가 적용되면 @Valid, @Validated에 적용된다.
- 검증 오류가 발생하면 FieldError, ObjectError를 생성해서 BindingResult에 담아준다.
- 직접 글로벌 Validator를 등록하면 스프링 부트는 Bean Validator를 글로벌 Validator로 등록하지 않는다.
- 항상 수동이 우선인 것을 잊지말자.
- @Valid는 자바 표준 기술, @Validated는 스프링 전용 검증 애노테이션
- @Validated는 내부에 groups라는 기능을 포함하고 있다. @Valid는 없음
- 검증 순서
  - @ModelAttribute 각각의 필드에 타입 변환 시도
    - 성공하면 다음으로
    - 실패하면 typeMismatch로 FieldError 추가
  - Validator 적용
- 바인딩에 성공한 필드만 Bean Validation 적용
- Bean Validator는 바인딩에 실패한 필드는 Bean Validation을 적용하지 않는다.
- 생각해보면 타입 변환에 성공해서 바인딩에 성공한 필드여야 Bean Validation 적용이 의미가 있다.
- 값이 정상적으로 들어와야 의미가 있다는 뜻
- 
