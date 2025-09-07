package com.example.demo.server;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookingLoggingAspect {

  private static final Logger log = LoggerFactory.getLogger(BookingLoggingAspect.class);

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)"
      + " || within(@org.springframework.stereotype.Repository *)")
  public void springBeanPointcut() {}

  
  @Pointcut("within(com.example.demo.controller..*) || within(com.example.demo.repository..*)")
  public void applicationPackagePointcut() {}

  
  @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    log.error("Exception in {}.{}() cause={}",
        joinPoint.getSignature().getDeclaringTypeName(),
        joinPoint.getSignature().getName(),
        (e.getCause() != null ? e.getCause() : "NULL"), e);
  }

  
  @Around("applicationPackagePointcut() && springBeanPointcut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isDebugEnabled()) {
      log.debug("Enter: {}.{}() args={}",
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName(),
          Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (log.isDebugEnabled()) {
        log.debug("Exit:  {}.{}() result={}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), summarize(result));
      }
      return result;
    } catch (IllegalArgumentException ex) {
      log.error("Illegal argument: {} in {}.{}()",
          Arrays.toString(joinPoint.getArgs()),
          joinPoint.getSignature().getDeclaringTypeName(),
          joinPoint.getSignature().getName());
      throw ex;
    }
  }

  private String summarize(Object result) {
    if (result == null) return "null";
    String s = result.toString();
    return s.length() > 500 ? s.substring(0, 500) + "...(truncated)" : s;
  }
}
