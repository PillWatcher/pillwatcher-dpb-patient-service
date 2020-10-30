package br.com.pillwatcher.dpb.exceptions;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import io.swagger.model.ErrorCodeEnum;

public class PatientException extends BaseException {

    public PatientException(
            final ErrorCodeEnum errorCodeEnum,
            final ErrorMessages errorMessages,
            final String message) {
        super(errorCodeEnum, errorMessages, message);
    }
}
