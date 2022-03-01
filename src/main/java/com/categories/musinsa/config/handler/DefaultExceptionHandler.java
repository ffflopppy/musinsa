package com.categories.musinsa.config.handler;

import com.categories.musinsa.config.MusinErrorResponse;
import com.categories.musinsa.config.exception.MusinCodeEnum;
import com.categories.musinsa.config.exception.MusinException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*.*.* Exception *.*.*/

@Slf4j
@ControllerAdvice("com.categories.musinsa")
public class DefaultExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    MusinErrorResponse defaultErrorHandler (HttpServletRequest httpServletRequest , Exception e) throws Exception {

        String errorCode = null;
        String errorMessage = null;

        boolean isMusinException = false;
        if ( e instanceof MusinException) {
            isMusinException = true;

            MusinException musinException = (MusinException) e;
            MusinCodeEnum musinCodeEnum = musinException.getCode();

            if ( musinCodeEnum == null) {
                errorMessage = e.getMessage();
            } else {
                errorCode = musinCodeEnum.getName();
                errorMessage = musinCodeEnum.getMessage();

                if (musinException.getArgs() != null && !musinException.getArgs().isEmpty() ) {
                    Map<String,String> argsMap = musinException.getArgs();
                    Iterator<Map.Entry<String,String>> iterator =  argsMap.entrySet().iterator();
                    while (iterator.hasNext() ) {
                        Map.Entry<String,String> entry = iterator.next();
                        String argsKey = "{" + entry.getKey() + "}";
                        errorMessage = errorMessage.replace(argsKey , entry.getValue());
                    }
                }
            }
        } else if(e instanceof MethodArgumentNotValidException){
            Map<String,String> errors = new HashMap<>();
            errorCode = "VALIDATION_ERROR";
             ((MethodArgumentNotValidException) e).getBindingResult()
                    .getAllErrors()
                    .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
            errorMessage = errors.toString();
        }

        if ( errorMessage == null) {
            errorMessage = "system error";
        }

        log.error("## Musin exception response ##");
        log.error("{}" , e);

        if (isMusinException) {
            return MusinErrorResponse.error( errorCode , errorMessage);
        } else {
            return MusinErrorResponse.systemError( errorCode , errorMessage);
        }
    }
}
