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

### Bean Validation - 에러 코드
- Bean Validation이 기본으로 제공하는 오류 메시지를 좀 더 자세히 변경하고 싶으면 어떻게 하면 될까?
- bindingResult에 등록된 검증 오류 코드를 보면 애노테이션 이름으로 등록되는 것을 알 수 있다.
- 애노테이션 이름의 오류 코드를 기반으로 MessageCodesResolver를 통해 다양한 메시지 코드가 순서대로 생성된다.
- Bean Validation 메시지 찾는 순서
  - 생성된 메시지 코드 순서대로 messageSource에서 메시지 찾기
  - 애노테이션의 message 속성 사용
  - 라이브러리가 제공하는 기본 값 사용

### Bean Validation - 오브젝트 오류
- 필드에 들어가는 FieldError가 아닌 ObjectError는 어떻게 처리할 수 있을까?
- @ScriptAssert()를 사용하면 된다.
- 실제 사용하기에는 제약이 많고 복잡하다.
- 권장하지 않는 방법이고 그냥 자바 코드로 하자.

### Bean Validation - 한계
- 여러 기능에서 Bean Validation을 사용할 경우, 예를 들어 등록과 수정에서 검증을 사용할 경우 검증 조건의 충돌이 발생할 수 있다.
- 여러 기능에서 같은 Bean Validation을 적용할 수 없다.
- 어떻게 해결할 수 있을까?

### Bean Validation - groups
- 동일한 모델 객체를 각각 다르게 검증하는 방법 2가지
  - Bean Validation의 groups 기능을 사용한다.
  - 같은 폼 전송을 위한 별도의 모델 객체를 만들어서 사용한다.
- @Valid에는 groups 기능이 없다.
- groups 기능을 사용해보니 복잡도가 올라간다.
- 실무에서는 주로 등록용 폼 객체와 수정용 폼 객체를 분리해서 사용한다.

### Form 전송 객체 분리
- 실무에서는 groups를 잘 사용하지 않는데 그 이유는 폼에서 전달하는 데이터가 도메인 객체와 딱 맞지 않기 때문이다.
- 보통 도메인 객체를 직접 전달받지 않고 복잡한 폼의 데이터를 컨트롤러까지 전달할 별도의 객체를 만들어서 전달한다.
- DTO를 생각해보자.
- 폼 데이터 전달에 도메인 객체를 사용하는 것은 간단한 경우에만 적용할 수 있고, 수정시 검증이 중복될 수 있기 때문에 groups를 사용해야 한다.
- 폼 데이터 전달을 위한 별도의 객체를 사용하면 폼 데이터를 기반으로 컨트롤러에서 객체를 생성하는 변환 과정이 추가된다.
- 하지만 전송하는 폼 데이터가 복잡해도 거기에 맞춘 별도의 폼 객체를 사용해서 데이터를 전달 받을 수 있다.
- 도메인 객체를 폼 전달 데이터로 사용하고 그대로 쭉 넘기면 편리하겠지만 실무에서는 도메인 객체만 넘어오는 것이 아니라 무수한 추가 데이터가 넘어온다.
- 더 나아가서 도메인 객체를 생성하는데 필요한 추가 데이터를 DB나 다른 곳에서 찾아와야 할 수도 있다.

### Form 전송 객체 분리 개발

### Bean Validation - HTTP 메시지 컨버터
- @Valid, @Validation은 HttpMessageConverter에도 적용할 수 있다. (@RequestBody)
- 오류가 발생하면 컨트롤러 호출이 안 된다. 왜냐하면 JSON이라는 덩어리를 객체로 바꾸지 못했기 때문에
- API의 경우 3가지 경우를 나누어 생각해야 한다.
  - 성공 요청: 성공
  - 실패 요청: JSON을 객체로 생성하는 것 자체가 실패함
  - 검증 오류 요청: JSON을 객체로 생성하는 것은 성공했고, 검증에서 실패함
- 실제 개발할 때는 객체들을 그대로 사용하지 않고 필요한 데이터만 뽑아서 별도의 API 스펙을 정의하고 그에 맞는 객체를 만들어서 반환해야 한다.
- @ModelAttribute vs @RequestBody
- HTTP 요청 파라미터를 처리하는 @ModelAttribute는 각각의 필드 단위로 세밀하게 적용된다. 따라서 특정 필드에 타입이 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있다.
- HttpMessageConverter는 각각의 필드 단위로 적용되는 것이 아니라 전체 객체 단위로 적용된다. 따라서 메시지 컨버터의 작동이 성공해서 객체를 만들어야 @Valid, @Validated가 적용된다.
- HttpMessageConverter 단계에서 실패하면 예외가 발생하는데 원하는 모양으로 예외를 처리할 수 있다.
