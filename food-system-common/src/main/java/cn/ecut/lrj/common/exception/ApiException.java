package cn.ecut.lrj.common.exception;


import cn.ecut.lrj.common.api.IErrorCode;
import cn.ecut.lrj.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义API异常
 * Created by macro on 2020/2/27.
 */
@Slf4j
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(ResultCode resultCode, Throwable cause) {
        super(cause);
        log.error("{code:"+resultCode.getCode()+" ,message:"+resultCode.getMessage()+"}");
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
