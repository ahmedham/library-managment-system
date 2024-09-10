package maids.springboot.library.aspect;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@Log4j2
public class LibraryLoggingAspect {

    @Pointcut("execution(* maids.springboot.library.service.BookService.insert(..))")
    public void bookAddition() {}

    @Pointcut("execution(* maids.springboot.library.service.BookService.update(..))")
    public void bookUpdate() {}

    @Pointcut("execution(* maids.springboot.library.service.PatronService.insert(..))")
    public void patronAddition() {}

    @Pointcut("execution(* maids.springboot.library.service.PatronService.update(..))")
    public void patronUpdate() {}

    @Pointcut("execution(* maids.springboot.library.service.PatronService.deleteById(..))")
    public void patronDelete() {}


    @Around("bookAddition() || bookUpdate() || patronAddition() || patronUpdate() || patronDelete()")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        long startTime = System.currentTimeMillis();

        log.info("Starting method {} in {}", methodName, className);
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in method {}: {}", methodName, throwable.getMessage());
            throw throwable;
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("Method {} in {} executed in {} ms", methodName, className, elapsedTime);

        return result;
    }

    @AfterThrowing(pointcut = "bookAddition() || bookUpdate() || patronAddition() || patronUpdate() || patronDelete()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.error("Exception occurred in method {} at {}: {}", methodName, formattedTime, exception.getMessage());
    }



}
