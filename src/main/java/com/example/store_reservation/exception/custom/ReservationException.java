package com.example.store_reservation.exception.custom;

import com.example.store_reservation.exception.BasicException;
import org.springframework.http.HttpStatus;

public class ReservationException {

    public static final class NotFoundReservationException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.NOT_FOUND.value();
        }

        @Override
        public String errorMessage() {
            return "예약 정보를 찾을 수 없습니다.";
        }
    }

    public static final class AlreadyReservationException extends BasicException {

        @Override
        public int statusCode() {
            return HttpStatus.BAD_REQUEST.value();
        }

        @Override
        public String errorMessage() {
            return "이미 예약이 완료된 매장입니다.";
        }
    }
}
