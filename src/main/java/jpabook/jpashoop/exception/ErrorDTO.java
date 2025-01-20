package jpabook.jpashoop.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorDTO {
    private String title;         // 제목
    private String message;       // 메시지
    private String errorCode;     // 특정 error 코드 (예: "USER_NOT_FOUND")
    private String errorLine;     // 특정 error 코드 (예: "USER_NOT_FOUND")
    private int statusCode;     // 특정 error 코드 (예: "USER_NOT_FOUND")
    private HttpStatus httpStatus; // HTTP 상태 코드 (200, 400, 500 등)

    // 생성자
    public ErrorDTO(String title, String message, String errorCode, HttpStatus httpStatus, String errorLine) {
        this.title = title;
        this.message = message;
        this.errorCode = errorCode;
        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.errorLine = errorLine;
    }

    // getter, setter 생략
}
