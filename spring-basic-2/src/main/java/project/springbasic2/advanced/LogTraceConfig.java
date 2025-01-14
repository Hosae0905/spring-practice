package project.springbasic2.advanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.springbasic2.advanced.trace.logtrace.LogTrace;
import project.springbasic2.advanced.trace.logtrace.ThreadLocalLogTrace;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
