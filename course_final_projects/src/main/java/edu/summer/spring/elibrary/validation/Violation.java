package edu.summer.spring.elibrary.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Violation {
    private final String    fieldName;
    private final String    message;
}
