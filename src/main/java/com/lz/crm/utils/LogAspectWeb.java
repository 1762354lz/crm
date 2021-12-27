package com.lz.crm.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//使用aop的实例：
@Aspect
@Component
@Slf4j
public class LogAspectWeb {
    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义一个切面，拦截com.xxx.controller包下的所有方法
     */
    //以execution(* com.itcodai.course09.controller…*.*(…))) 表达式为例，
    //语法如下：
    //execution() 为表达式主体
    //第一个*号的位置：表示返回值类型，*表示所有类型
    //包名：表示需要拦截的包名，后面的两个句点表示当前包和当
    //前包的所有子包，com.xxx.controller 包、子包下所有类的方法
    //第二个*号的位置：表示类名，*表示所有类
    //*(…) ：这个星号表示方法名，*表示所有的方法，后面括弧
    //里面表示方法的参数，两个句点表示任何参数
    @Pointcut("execution(* com.lz.crm.settings..*.*(..))&&!execution(* com.lz.crm.settings.domain.*.*(..)) || execution(* com.lz.crm.workbench..*.*(..))&&!execution(* com.lz.crm.workbench.domain.*.*(..))")
    //&&!execution(* com.lz.crm.settings.domain.*.*(..))&&!execution(* com.lz.crm.workbench.domain.*.*(..))
    //&&execution(* com.lz.crm.workbench..*.*(..))&&!execution(* com.lz.crm.settings.domain.*.*(..))&&!execution(* com.lz.crm.workbench.domain.*.*(..))
    public void pointCut() {
    }

    /**
     * 在上面定义的切面方法之前执行该方法
     *
     * @param joinPoint jointPoint
     */
    @Around("pointCut()")
    //Proceedingjoinpoint 继承了 JoinPoint
    public Object around(ProceedingJoinPoint joinPoint) {

        // 获取签名
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
        //logger.info("即将执行方法为: {}，属于{}包", funcName, declaringTypeName);

        // 也可以用来记录一些信息，比如获取请求的url和ip
       // ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       // HttpServletRequest request = attributes.getRequest();
        // 获取请求url
        //String url = request.getRequestURL().toString();
        // 获取请求ip
       // String ip = request.getRemoteAddr();
        //System.out.println("---111---"+url+"请求//"+funcName+"usercontroller");

        log.error("~~~bbb进入~~~" + "//类->" + declaringTypeName + "//方法->" + funcName);
        Object result = null;
        // 表示开始调用设置切点的方法,result为目标方法返回值，可影响改变目标方法返回值
            try {
                result = joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("???ccc异常??? //类->" + declaringTypeName + "//方法->" + funcName);
                throwable.printStackTrace();
            } finally {
            log.error("！！！aaa结果！！！result->" + result + "//类->" + declaringTypeName + "//方法->" + funcName);
        }
        /*long startTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        log.info("调用方法之前答应()进入该方法的时间信息：{}", format);

        long time = System.currentTimeMillis() - startTime;
        log.info("方法执行的时间： {}", time);
        try {
            //记录日记信息
            saveLog (joinPoint, time);
        }catch (Exception e) {
            log.error("出现异常： {}", e.toString());
            e.printStackTrace();
        }*/
        return result;
    }
}
    /*@Around({"pointCutSService()","pointCutWService()"})
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        // 获取切入的包名
        String declaringTypeName = signature.getDeclaringTypeName();
        // 获取即将执行的方法名
        String funcName = signature.getName();
        Object result=null;
        try {

            // 表示开始调用设置切点的方法,result为目标方法返回值，可影响改变目标方法返回值
             result = joinPoint.proceed();
        }
        catch (Exception e){
            logger.error("???222???"+declaringTypeName+".funcName");
            e.printStackTrace();
        }
        finally {
            logger.error("result="+result+"！！！aaa！！！//"+declaringTypeName+".funcName");
        }
        /*long startTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        log.info("调用方法之前答应()进入该方法的时间信息：{}", format);

        long time = System.currentTimeMillis() - startTime;
        log.info("方法执行的时间： {}", time);
        try {
            //记录日记信息
            saveLog (joinPoint, time);
        }catch (Exception e) {
            log.error("出现异常： {}", e.toString());
            e.printStackTrace();
        }*/
        //return result;*/
    //}


    /**
     * 在上面定义的切面方法之后执行该方法
     * @param joinPoint jointPoint
     */
   /* @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {

        logger.info("====doAfter方法进入了====");
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        logger.info("方法{}已经执行完", method);
    }

    /**
     * 在上面定义的切面方法返回后执行该方法，可以捕获返回对象或者对返回对象进行增强
     * @param joinPoint joinPoint
     * @param result result
     */
   /* @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {

        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getName();
        logger.info("方法{}执行完毕，返回参数为：{}", classMethod, result);
        // 实际项目中可以根据业务做具体的返回值增强
        logger.info("对返回参数进行业务上的增强：{}", result + "增强版");
    }

    /**
     * 在上面定义的切面方法执行抛异常时，执行该方法
     * @param joinPoint jointPoint
     * @param ex ex
     */
    /*@AfterThrowing(pointcut = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getName();
        // 处理异常的逻辑
        logger.info("执行方法{}出错，异常为：{}", method, ex.getMessage());
    }
*/

