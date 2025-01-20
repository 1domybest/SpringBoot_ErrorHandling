package jpabook.jpashoop.exception;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorDTO errorDTO;

    public CustomException(ErrorDTO errorDTO) {
        super(String.format(errorDTO.getMessage())); // 메시지 설정
        // 이부분은 클라에 보내지말고 서버로그에 따로 저장해야할듯
        StackTraceElement element = this.getStackTrace()[0];
        String fileNameAndLineNumber = element.getFileName() + ":" + element.getLineNumber();
        System.out.println("에러발생 에러코드:" + errorDTO.getErrorCode() + "\n발생위치: " + fileNameAndLineNumber);

        // 이부분은 PROD 서버가아닌 QA 서버일때만 내보내자
        errorDTO.setErrorLine(fileNameAndLineNumber);
        this.errorDTO = errorDTO; // ErrorDTO 객체 생성
    }
}