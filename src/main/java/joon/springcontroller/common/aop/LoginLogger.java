package joon.springcontroller.common.aop;

import joon.springcontroller.log.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LoginLogger {
    private final LogService logService;

    @Around("execution(* joon.springcontroller..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("서비스 호출 전");
        Object result=joinPoint.proceed();
        if("login".equals(joinPoint.getSignature().getName())) {
            StringBuilder builder=new StringBuilder();
            log.info("서비스 호출 후");
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("함수명:" + joinPoint.getSignature().getDeclaringType() +", "+ joinPoint.getSignature().getName());
            logService.add(sb.toString());
            log.info(sb.toString());
        }
        log.info(" 서비스호출 후 !!!!!!!!!!!!!");

        return result;
    }
}
