package joon.springcontroller.common.aop;

import joon.springcontroller.log.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class BoardLogger {
    private final LogService logService;
    @Around("execution(* joon.springcontroller..*.*Controller.detail(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(" 컨트롤러detail 호출 전 !!!!!!!!!!!!!");

        Object result =joinPoint.proceed();

        log.info(" 컨트롤러detail 호출 후 !!!!!!!!!!!!!");

        return result;
    }
}
