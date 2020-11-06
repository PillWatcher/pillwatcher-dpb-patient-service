package br.com.pillwatcher.dpb.services;

import br.com.pillwatcher.dpb.entities.Cup;

public interface CupService {

    Cup findCupByTag(String tag);
}
