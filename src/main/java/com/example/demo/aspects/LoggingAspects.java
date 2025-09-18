package com.example.demo.aspects;
 

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Aspect
@Component
public class LoggingAspects {

	public static final Logger logger = LoggerFactory.getLogger(LoggingAspects.class);

	@Around("execution(* com.example.demo.Controller.BlogController.*(..)) || execution(* com.example.demo.Service.BlogService.*(..))")

	public Object logMethodExecution(ProceedingJoinPoint jointPoint) throws Throwable {
		String methodName = jointPoint.getSignature().toShortString();

		logger.error("BlogPorject -- Entering Method: {}", methodName);

		Long startTime = System.nanoTime();
		Object result = jointPoint.proceed();
		Long endTime = System.nanoTime();
		System.out.println(endTime - startTime);
		long duration = (endTime - startTime) / 1_000_000;

		logger.info("BlogProject -- Exiting method : {} . Execution Time : {} ms", methodName, duration);

		return result;

	}

}