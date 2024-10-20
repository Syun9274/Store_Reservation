package com.example.store_reservation.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ReviewRequest {

    private String contents;
}
