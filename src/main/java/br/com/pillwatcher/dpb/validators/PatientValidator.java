package br.com.pillwatcher.dpb.validators;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Nurse;
import br.com.pillwatcher.dpb.exceptions.PatientException;
import br.com.pillwatcher.dpb.repositories.NurseRepository;
import io.swagger.model.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PatientValidator {

    private final NurseRepository repository;

    /**
     * Validates if the param is a valid nurse id
     * @param joinPoint
     */
    @Before("execution(* br.com.pillwatcher.dpb.controllers.PatientController.getAllPatients(..))")
    public void validateNurseIdAsFirstArg(final JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        if (args == null) {
            throw new PatientException(ErrorCodeEnum.UNEXPECTED_ERROR,
                    ErrorMessages.BAD_REQUEST,
                    StringUtils.replace(ValidationConstraints.MISSING_PARAMETERS, "{}", "GetAllPatients"));
        }

        final String nurseId =  String.valueOf(args[0]);
        validateNurseId(nurseId);
    }

    /**
     * Validates if the param is a valid nurse id
     * @param joinPoint
     */
    @Before("execution(* br.com.pillwatcher.dpb.controllers.PatientController.getPatient(..)) ")
    public void validateNurseIdAsSecondArg(final JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        if (args == null) {
            throw new PatientException(ErrorCodeEnum.UNEXPECTED_ERROR,
                    ErrorMessages.BAD_REQUEST,
                    StringUtils.replace(ValidationConstraints.MISSING_PARAMETERS, "{}", "delete/getPatient"));
        }

        final String nurseId = String.valueOf(args[1]);
        validateNurseId(nurseId);
    }

    /**
     * Validates if the param is a valid nurse id
     * @param joinPoint
     */
    @Before("execution(* br.com.pillwatcher.dpb.controllers.PatientController.updatePatient(..)) ||"
            + "execution(* br.com.pillwatcher.dpb.controllers.PatientController.deletePatient(..))")
    public void validateNurseIdAsThirdArg(final JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        if (args == null) {
            throw new PatientException(ErrorCodeEnum.UNEXPECTED_ERROR,
                    ErrorMessages.BAD_REQUEST,
                    StringUtils.replace(ValidationConstraints.MISSING_PARAMETERS, "{}", "UpdatePatient"));
        }

        final String nurseId = String.valueOf(args[1]);
        validateNurseId(nurseId);
    }

    /**
     * Given a nurseId, it transforms to Long and verify if there is any on the database
     * @param nurseId
     */
    private void validateNurseId(final String nurseId) {

        if (isNull(nurseId)) {
            log.warn(ValidationConstraints.INVALID_PARAMETER, "null");
            throw new PatientException(ErrorCodeEnum.INVALID_PARAMETER,
                    ErrorMessages.BAD_REQUEST,
                    StringUtils.replace(ValidationConstraints.INVALID_PARAMETER, "{}", null));
        }

        Long nursePk = Long.valueOf(nurseId);

        Optional<Nurse> nurseFound = repository.findById(nursePk);

        if (!nurseFound.isPresent()) {
            log.warn(ValidationConstraints.NURSE_NOT_FOUND, nurseId);
            throw new PatientException(ErrorCodeEnum.INVALID_PARAMETER,
                    ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.NURSE_NOT_FOUND, "{}", nurseId));

        }
    }

}
