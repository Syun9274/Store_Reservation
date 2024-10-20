package com.example.store_reservation.exception.custom;

import com.example.store_reservation.exception.BasicException;
import org.springframework.http.HttpStatus;

public class UserException {

    public static final class NotExistUserException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String errorMessage() {
            return "존재하지 않는 사용자 입니다.";
        }
    }

    public static final class AlreadyExistUserException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String errorMessage() {
            return "이미 존재하는 사용자 입니다. 로그인을 시도하세요.";
        }
    }
}
