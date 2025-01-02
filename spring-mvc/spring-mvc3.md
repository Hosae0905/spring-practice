## API 예외처리

### 시작하기
- API 예외는 생각할 내용이 더 많다.
- 각 오류 상황에 맞는 오류 응답 스펙을 정하고 JSON 데이터를 내려주어야 한다.
- API를 요청했는데 오류가 발생할 경우 잘못하면 오류 페이지가 반환될 수 있다.

### 스프링 부트 기본 오류 처리
- 스프링 부트 기본 설정은 오류 발생시 '/error' 를 오류 페이지로 요청한다.
- BasicErrorController는 해당 경로를 기본으로 받는다.
- 옵션을 설정하면 더 자세한 오류 정보를 추가할 수 있지만 막 추가하면 보안상 위험할 수 있다.
- 간결한 메시지만 외부로 노출하고 나머지는 로그를 통해서 확인하자.

### html 페이지 vs API 오류
- BasicErrorController를 확장하면 JSON 메시지도 변경할 수 있다. --> 이렇게 할 수 있다만 알고있자.
- 하지만 @ExceptionHandler가 제공하는 기능을 사용하는 것이 더 나은 방법이다.
- BasicErrorController는 HTML 페이지를 제공하는 경우 매우 편리하다. 하지만 API 방식에서는 다르다.
- 각각의 API 마다 혹은 각각의 컨트롤러나 예외마다 서로 다른 응답 결과를 출력해야 할 수도 있다.
- 따라서 HTML 화면을 처리할 때 BasicErrorController를 사용하고 API 오류 처리는 @ExceptionHandler를 사용하자.

### HandlerExceptionResolver
- 스프링 MVC는 컨트롤러(핸들러) 밖으로 예외가 던져진 경우 예외를 해결하고 동작을 새로 정의할 수 있는 방법을 제공한다.
- 이때 HandlerExceptionResolver를 사용하면 된다. 줄여서 ExceptionResolver라고 한다.
- 참고로 ExceptionHandler로 예외를 해결해도 인터셉터의 postHandle()은 호출되지 않는다.
- ExceptionResolver가 ModelAndView를 반환하는 이유는 마치 try, catch를 하듯이 Exception을 처리해서 정상 흐름처럼 변경하는 것이 목적이다.
- HandlerExceptionResolver의 반환 값에 따른 DispatcherServlet의 동작 방식
  - 빈 ModelAndView: 뷰를 렌더링하지 않고 정상 흐름으로 서블릿이 리턴된다.
  - ModelAndView 지정: ModelAndView에 View, Model 등의 정보를 지정해서 반환하면 뷰를 랜더링한다.
  - null: 다음 ExceptionHandler를 찾아서 실행한다. 만약 처리할 수 있는 ExceptionHandler가 없으면 예외 처리가 안되고 기존에 발생한 예외를 서블릿 밖으로 던진다.
- 참고로 configureHandlerExceptionResolvers를 사용하면 스프링이 기본으로 등록하는 ExceptionHandler가 제거되므로 주의해야 한다.

### HandlerExceptionResolver - 활용
- 예외가 발생하면 WAS까지 예외를 던지고 WAS에서 오류 페이지 정보를 찾아서 다시 '/error'를 호출하는 과정은 너무 복잡하다.
- ExceptionResolver를 활용하면 예외가 발생했을 때 이러한 복잡한 과정 없이 문제를 해결할 수 있다.
- ExceptionResolver를 사용하면 컨트롤러에서 예외가 발생해도 ExceptionResolver에서 예외를 처리해버린다.
- 따라서 예외가 발생해도 서블릿 컨테이너까지 예외가 전달되지 않고 스프링 MVC에서 예외 처리는 끝이 난다.
- 결과적으로 WAS 입장에서는 정상 처리가 된 것이다. 예외를 이곳에서 모두 처리할 수 있다는 것이 핵심

### 스프링이 지원하는 ExceptionResolver 1
- 스프링 부트가 기본으로 제공하는 ExceptionResolver는 HandlerExceptionResolverComposite에 다음 순서로 등록된다.
  1. ExceptionHandlerExceptionResolver: @ExceptionHandler를 처리한다.
  2. ResponseStatusExceptionResolver: HTTP 상태 코드를 지정해준다.
     - 예외에 따라서 HTTP 상태 코드를 지정해주는 역할을 한다.
     - @ResponseStatus가 달려있는 예외
     - ResponseStatusException 예외
  3. DefaultHandlerExceptionResolver: 스프링 내부 기본 예외를 처리한다.
- @ResponseStatus는 개발자가 직접 변경할 수 없는 예외에는 적용할 수 없다.
- 추가로 애노테이션을 사용하기 때문에 조건에 따라 동적으로 변경하는 것도 어렵다.
- 이럴 경우 ResponseStatusException 예외를 사용하면 된다.

### 스프링이 지원하는 ExceptionResolver 2
- DefaultHandlerExceptionResolver는 스프링 내부에서 발생하는 스프링 예외를 해결한다.
- 대표적으로 파라미터 바인딩 시점에 타입이 맞지 않으면 내부에서 TypeMismatchException이 발생하는데 이 경우 예외가 발생했기 때문에 그냥 두면 서블릿 컨테이너까지 오류가 올라가고 결과적으로 500 오류가 발생한다.
- 하지만 파라미터 바인딩은 대부분 클라이언트 HTTP 요청 정보를 잘못 호출해서 발생하는 문제이다.
- DefaultHandlerExceptionResolver는 해당 오류를 500이 아니라 400오류로 변경해준다.
- HandlerExceptionResolver를 직접 사용하기는 복잡하다. API 오류 응답의 경우 response에 직접 데이터를 넣어야 해서 매우 불편하고 번거롭다.
- ModelAndView를 반환해야 하는 것도 API에 잘 맞지 않는다.

### @ExceptionHandler
- API는 단순히 HTML Form을 보여주는 것보다 훨씬 더 세밀한 작업이 필요하다.
- 특정 컨트롤러에서만 발생하는 예외를 별도로 처리하기 어렵다.
- 예를 들어 회원을 처리하는 컨트롤러에서 발생하는 RuntimeException과 상품을 관리하는 컨트롤러에서 발생하는 동일한 RuntimeException 예외를 서로 다른 방식으로 처리해야하는 상황이 생긴다.
- 각각 따로 처리를 하면 너무 지저분해진다.
- 이러한 문제를 해결하기 위해 나온 것이 @ExceptionHandler 애노테이션이다.
- 해당 애노테이션이 붙어있으면 ExceptionHandlerExceptionResolver로 동작하게 된다.
- 스프링에서는 ExceptionHandlerExceptionResolver를 기본으로 제공해주고 제일 우선순위가 높다.
- ExceptionHandlerExceptionResolver가 @ExceptionHandler 애노테이션이 붙은 곳을 호출해준다.
- @ExceptionHandler 예외 처리 방법
  - @ExceptionHandler 애노테이션을 선언한다.
  - 해당 컨트롤러에서 처리하고 싶은 예외를 지정해준다.
  - 해당 컨트롤러에서 예외가 발생하면 이 메서드가 호출된다.
  - 지정한 예외 또는 그 예외의 자식 클래스는 모두 잡을 수 있다.
- 스프링에서 예외를 처리할 경우 우선순위는 자식예외처리가 먼저 우선권을 가지게 된다.
- 자식 예외가 발생하면 부모예외처리(), 자식예외처리() 둘 다 호출 대상이 된다.
- 부모 예외가 발생하면 부모예외처리()만 호출 대상이 된다.

### API 예외처리 - @ControllerAdvice
- 여러 컨트롤러에서 발생하는 예외들을 한 곳에서 처리할 수 있게 해준다.
- @ControllerAdvice는 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBinder 기능을 부여해주는 역할을 한다.
- 대상을 따로 지정하지 않으면 모든 컨트롤러에 모두 적용된다.
- 패키지 경로를 지정해서 적용 대상을 직접 설정할 수 있다.
