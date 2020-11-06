package br.com.pillwatcher.dpb.services.impl;

import br.com.pillwatcher.dpb.constants.ErrorMessages;
import br.com.pillwatcher.dpb.constants.ValidationConstraints;
import br.com.pillwatcher.dpb.entities.Cup;
import br.com.pillwatcher.dpb.exceptions.CupException;
import br.com.pillwatcher.dpb.repositories.CupRepository;
import br.com.pillwatcher.dpb.services.CupService;
import io.swagger.model.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CupServiceImpl implements CupService {

    final CupRepository cupRepository;

    @Override
    public Cup findCupByTag(final String tag) {

        Optional<Cup> cupOptional = cupRepository.findByTag(tag);

        if (!cupOptional.isPresent()) {
            log.warn(ValidationConstraints.CUP_NOT_FOUND, tag);
            throw new CupException(ErrorCodeEnum.NOT_FOUND, ErrorMessages.NOT_FOUND,
                    StringUtils.replace(ValidationConstraints.CUP_NOT_FOUND, "{}", tag));
        }

        return cupOptional.get();
    }
}
