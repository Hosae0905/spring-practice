## 스프링 컨테이너와 스프링 빈

### 스프링 컨테이너 생성
- ApplicationContext를 스프링 컨테이너라 부른다.
- ApplicationContext는 인터페이스로 다형성이 적용된 상태이다.
- 스프링 컨테이너는 XML 기반으로 만들 수 있고, 애노테이션 기반의 자바 설정 클래스로 만들 수 있다.
- AppConfig를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만드는 방식이다.
  - 참고로 더 장확히는 스프링 컨테이너를 부를 때 BeanFactory, ApplicationContext로 구분해서 이야기 한다.
  - BeanFactory를 직접 사용하는 경우는 거의 없으므로 일반적으로 ApplicationContext를 스프링 컨테이너라 한다.
- 빈을 등록할 때 빈의 이름은 항상 다른 이름을 부여해야 한다. 왜냐하면 같은 이름을 부여하면 다른 빈이 무시되거나 기존 빈을 덮어버리거나 설정에 따라 오류가 발생한다.
- 스프링 빈을 생성한 뒤 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)한다.
- 스프링은 빈을 생성하고 의존관계를 주입하는 단계가 나눠져 있다.

### 컨테이너에 등록된 모든 빈 조회


### 동일한 타입이 둘 이상일 경우
- 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류가 발생한다. 이때는 빈 이름을 지정하면 된다.


### 상속관계에서 빈 조회
- 부모 타입으로 조회하면 자식 타입도 함께 조회한다.
- 모든 자바 객체의 최고 부모인 Object 타입으로 조회하면 모든 스프링 빈을 조회한다.
- 빈을 조회하는 기능을 알아야 하는 이유는 거의 쓸일은 없지만 순수 스프링을 사용할 때 빈 조회가 필요할 수 있기 때문

### BeanFactory와 ApplicationContext
- BeanFactory
  - 스프링 컨테이너의 최상위 인터페이스
  - 스프링 빈을 관리하고 조회하는 역할을 담당
  - getBean()과 같은 기능은 BeanFactory가 제공하는 기능이다.
- ApplicationContext
  - BeanFactory 기능을 모두 상속받아서 제공한다.
  - 애플리케이션을 개발할 때는 빈은 관리하고 조회하는 기능은 물론이고 수 많은 부가 기능이 필요하다.
  - 이것이 BeanFactory와 ApplicationContext의 차이
  - EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver 기능을 가지고 있다.
  - MessageSource(메시지소스를 활용한 국제화 기능) --> 한국에서 들어오면 한국어, 영어권에서 들어오면 영어로 출력
  - EnvironmentCapable(환경 변수) --> 로컬, 개발, 운영등을 구분해서 처리
  - ApplicationEventPublisher(애플리케이션 이벤트) --> 이벤트를 발생하고 구독하는 모델을 편리하게 지원
  - ResourcePatternResolver(편리한 리소스 조회) --> 파일, 클래스 패스, 외부 등에서 리소스를 편리하게 조회
  - ApplicationContext는 빈 관리기능 + 편리한 부가 기능을 제공한다.

### 다양한 설정 형식 지원
- 스프링 컨테이너는 다양한 형식의 설정 정보를 받아드릴 수 있게 유연하게 설계되어 있다.
- 레거시 프로젝트에서는 설정 정보를 XML로 사용할 수 있기 때문에 한 번쯤 배워두는 것도 좋다.
- XML을 사용하면 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점도 있다.

### 스프링 빈 설정 메타 정보 - BeanDefinition
- BeanDefinition이라는 추상화를 통해서 스프링은 다양한 설정 형식을 지원한다.
- 역할과 구현을 개념적으로 나눈 것으로 스프링 컨테이너는 오직 BeanDefinition만 알고 있지 그것이 자바 코드나 XML로 되어 있는지는 모른다.
- 이러한 BeanDefinition을 빈 설정 메타정보라고 한다.
- @Bean, <bean> 을 사용하면 각각 하나씩 메타 정보가 생성되는데 스프링 컨테이너는 해당 정보를 기반으로 스프링 빈을 생성한다.
- AnnotationConfigApplicationContext는 AnnotatedBeanDefinitionReader를 통해서 자바 코드로 이루어진 설정 정보를 읽고 BeanDefinition을 생성한다.
- XML은 XmlBeanDefinitionReader를 통해서 설정 정보를 읽고 BeanDefinition을 생성한다.
- 스프링이 다양한 형태의 설정 정보를 BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하면 된다.
- 가끔 스프링 코드나 관련 오픈 소스의 코드를 볼 때 BeanDefinition 이라는 것이 보일 때 이러한 메커니즘을 떠올리면 된다.
- 평소에 사용되는 애너테이션 빈 등록 방식은 팩토리 메서드 방식이다.
