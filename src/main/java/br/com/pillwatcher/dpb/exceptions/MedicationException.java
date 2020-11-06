package br.com.pillwatcher.dpb.exceptions;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import io.swagger.model.ErrorCodeEnum;

public class MedicationException extends BaseException {

    public MedicationException(
            final ErrorCodeEnum errorCodeEnum,
            final ErrorMessages errorMessages,
            final String message) {
        super(errorCodeEnum, errorMessages, message);
    }

}
