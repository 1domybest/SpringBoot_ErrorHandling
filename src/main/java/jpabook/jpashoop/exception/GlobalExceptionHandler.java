package jpabook.jpashoop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> handleCustomException(CustomException e) {
        // CustomException이 발생하면, 해당 ErrorDTO를 반환하고 HTTP 상태 코드를 설정
        return new ResponseEntity<>(e.getErrorDTO(), e.getErrorDTO().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        // 기타 예외 발생 시, 일반적인 응답을 반환
        Map<String, String> response = new HashMap<>();
        response.put("error", "Unexpected Error");
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
