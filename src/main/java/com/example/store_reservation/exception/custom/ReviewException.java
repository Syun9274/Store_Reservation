package com.example.store_reservation.exception.custom;

import com.example.store_reservation.exception.BasicException;
import org.springframework.http.HttpStatus;

public class ReviewException {

    public static final class AlreadyExistsReviewException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String errorMessage() {
            return "이미 리뷰를 작성하셨습니다.";
        }
    }

    public static final class NotFoundReviewException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.NOT_FOUND.value();
        }

        @Override
        public String errorMessage() {
            return "작성된 리뷰를 찾을 수 없습니다.";
        }
    }

}
