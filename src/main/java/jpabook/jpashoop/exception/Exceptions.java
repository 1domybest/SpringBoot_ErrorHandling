package jpabook.jpashoop.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;


public class Exceptions {

    // private 생성자: 인스턴스화 방지
    private Exceptions() {
    }

    // static 메서드: ErrorDTO 생성
    public static ErrorDTO getErrorDTO(ErrorCode errorCode, Object[] titleArgs, Object[] messageArgs) {
        return new ErrorDTO(
                MessageFormat.format(errorCode.getTitle(), titleArgs), // titleArgs로 포맷팅
                MessageFormat.format(errorCode.getMessage(), messageArgs), // messageArgs로 포맷팅
                errorCode.getErrorCode(), // ErrorCode의 이름 사용
                errorCode.getHttpStatus(), // HTTP 상태 코드
                ""
        );
    }
}