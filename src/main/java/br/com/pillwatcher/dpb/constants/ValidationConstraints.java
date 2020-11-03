package br.com.pillwatcher.dpb.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationConstraints {

    public static final String INVALID_PARAMETER = "Parameter {} is invalid for this type of request";

    public static final String NURSE_NOT_FOUND = "Nurse with id {} not found";

    public static final String PATIENT_NOT_FOUND = "Patient with CPF {} not found";
    public static final String PATIENT_ALREADY_EXISTS = "Patient with CPF {} already exists";

    public static final String NAME_SIZE_MUST_BE_BETWEEN = "Name size must be between {min} and {max} characters long";

    public static final String MISSING_PARAMETERS = "Method {} is missing parameters";
    public static final String FORBIDDEN_NURSE = "Nurse {} can't delete this patient";

    public static final String PRESCRIPTION_NOT_FOUND = "Prescription with ID {} not found";

    public static final int NAME_MIN_SIZE = 3;
    public static final int NAME_MAX_SIZE = 120;


}
