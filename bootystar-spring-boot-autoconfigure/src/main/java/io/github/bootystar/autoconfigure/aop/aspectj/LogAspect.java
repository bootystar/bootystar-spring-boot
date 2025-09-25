package io.github.bootystar.autoconfigure.aop.aspectj;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bootystar.autoconfigure.aop.annotation.MethodLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
@Order(1)
@Slf4j
public class LogAspect {

    /**
     * 切入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(io.github.bootystar.autoconfigure.aop.annotation.MethodLog)")
    public void webLog() {
    }

    /**
     * 前置通知
     * @param joinPoint 切点
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();


        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args   : {}", new ObjectMapper().writeValueAsString(getRequestArgs(joinPoint)));
    }


    /**
     * 环绕通知
     * @param proceedingJoinPoint 切点
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        log.info("Response Args  : {}", new ObjectMapper().writeValueAsString(result));
        // 执行耗时
        log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
        return result;
    }



    /**
     * 获取切面注解
     *
     * @param joinPoint 切点
     * @return
     * @throws Exception
     */
    public MethodLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(MethodLog.class);
        }
        return null;
    }

    /**
     * 获取请求参数
     * @param joinPoint
     * @return
     */
    private Map<String, Object> getRequestArgs(JoinPoint joinPoint) {
        Map<String, Object> requestArgs = new HashMap<>();
        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile || arg instanceof MultipartFile[]){
                continue;
            }
            requestArgs.put(argNames[i], arg);
        }
        return requestArgs;
    }


}