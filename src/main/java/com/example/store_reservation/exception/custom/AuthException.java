package com.example.store_reservation.exception.custom;

import com.example.store_reservation.exception.BasicException;
import org.springframework.http.HttpStatus;

public class AuthException {

    public static final class WrongAuthInfoException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.UNAUTHORIZED.value();
        }

        @Override
        public String errorMessage() {
            return "사용자명 또는 비밀번호가 틀렸습니다. 다시 시도하세요.";
        }
    }

    public static final class NotMatchUserInfoException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String errorMessage() {
            return "사용자 정보가 일치하지 않습니다.";
        }
    }

    public static final class NotMatchStoreInfoException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String errorMessage() {
            return "매장 정보가 일치하지 않습니다.";
        }
    }


}
