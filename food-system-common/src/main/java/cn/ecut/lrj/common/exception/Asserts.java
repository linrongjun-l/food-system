package cn.ecut.lrj.common.exception;


import cn.ecut.lrj.common.api.IErrorCode;
import cn.ecut.lrj.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 断言处理类，用于抛出各种API异常
 * Created by macro on 2020/2/27.
 */
@Slf4j
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
    /**
     * 断言对象不为空
     * obj 为空则抛异常
     * @param obj
     * @param responseEnum
     */
    public static void notNull(Object obj, ResultCode responseEnum){
        if(obj == null){
            log.info("obj is null.....................");
            throw new ApiException(responseEnum);
        }
    }

    /**
     * 断言表达式为真
     * 如果不为真，则抛出异常
     *
     * @param expression 是否成功
     */
    public static void isTrue(boolean expression, ResultCode responseEnum) {
        if (!expression) {
            log.info("fail...............");
            throw new ApiException(responseEnum);
        }
    }

    /**
     * 断言两个对象不相等
     * 如果相等，则抛出异常
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void notEquals(Object m1, Object m2,  ResultCode responseEnum) {
        if (m1.equals(m2)) {
            log.info("equals...............");
            throw new ApiException(responseEnum);
        }
    }
    /**
     * 断言两个对象相等
     * 如果不相等，则抛出异常
     * @param m1
     * @param m2
     * @param responseEnum
     */
    public static void equals(Object m1, Object m2,  ResultCode responseEnum) {
        if (!m1.equals(m2)) {
            log.info("not equals...............");
            throw new ApiException(responseEnum);
        }
    }
    /**
     * 断言参数不为空
     * 如果为空，则抛出异常
     * @param s
     * @param responseEnum
     */
    public static void notEmpty(String s, ResultCode responseEnum) {
        if (StringUtils.isEmpty(s)) {
            log.info("is empty...............");
            throw new ApiException(responseEnum);
        }
    }
}
