package jpabook.jpashoop.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("User Not Found", "User not found with ID: {0}", "PIP_01", HttpStatus.NOT_FOUND),
    INVALID_EMAIL("Invalid Email", "The email format is invalid: {0}", "PIP_02", HttpStatus.BAD_REQUEST);


    private final String title;         // 제목
    private final String message;       // 메시지
    private final String errorCode;     // 특정 error 코드 (예: "USER_NOT_FOUND")
    private final HttpStatus httpStatus; // HTTP 상태 코드 (200, 400, 500 등)

    ErrorCode(String title,
              String message,
              String errorCode,
              HttpStatus httpStatus) {
        this.title = title;
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

}
