package org.sopt.practice.exception;

import lombok.Getter;
import org.sopt.practice.common.dto.ErrorMessage;

@Getter //여기에 Getter 써야 하는지
public class BusinessException extends RuntimeException {

    private ErrorMessage errorMessage;
    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
