package br.com.pillwatcher.dpb.exceptions;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import io.swagger.model.ErrorCodeEnum;

public class PrescriptionException extends BaseException {

    public PrescriptionException(
            final ErrorCodeEnum errorCodeEnum,
            final ErrorMessages errorMessages,
            final String message) {
        super(errorCodeEnum, errorMessages, message);
    }
}
