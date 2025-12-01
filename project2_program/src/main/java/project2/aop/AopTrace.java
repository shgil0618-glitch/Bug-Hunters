package project2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopTrace {
	@Around("execution(public  *  project2..*(..))")
	public Object trace( ProceedingJoinPoint  joinPoint)  throws Throwable{
		// 타겟메서드의 정보
		String signature = joinPoint.getSignature().toShortString();
		System.out.println(">>>> " + signature + " START! ");
		// 타겟메서드 호출시간확인
		long start = System.currentTimeMillis();
		// 타겟메서드 호출
		Object  result = joinPoint.proceed();
		long end  = System.currentTimeMillis();
		System.out.println("..... 실행시간 : " + (end - start)  + "ms");
		System.out.println(">>>> " + signature + " END! ");
		return result;
	}
}