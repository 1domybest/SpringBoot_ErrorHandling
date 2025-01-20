package jpabook.jpashoop.controller;

import jpabook.jpashoop.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static jpabook.jpashoop.exception.ErrorCode.INVALID_EMAIL;


@RestController
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @PostMapping("/exception")
    public void exceptionTest() throws CustomException{

        Object[] titleArgs = {"123", "user@example.com"};
        Object[] messageArgs = {"123", "user@example.com", "John Doe"};

        ErrorDTO dto = Exceptions.getErrorDTO(INVALID_EMAIL, titleArgs, messageArgs);
        throw new CustomException(dto);
    }

}
