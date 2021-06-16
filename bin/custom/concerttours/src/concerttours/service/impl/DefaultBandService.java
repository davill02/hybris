package concerttours.service.impl;

import concerttours.daos.BandDao;
import concerttours.model.BandModel;
import concerttours.service.BandService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class DefaultBandService implements BandService {
    @Resource
    private BandDao bandDao;

    @Override
    public List<BandModel> getAllBands() {
        return bandDao.getAllBands();
    }

    @Override
    public Optional<BandModel> getBandModelByName(String name) {
        List<BandModel> bands = bandDao.findBandsByName(name);
        if (bands.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(bands.get(0));
    }

    @Override
    public Optional<BandModel> getBandModelByCode(String code) {
        final List<BandModel> result = bandDao.findBandsByCode(code);
        if (result.isEmpty()) {
            return Optional.empty();
        } else if (result.size() > 1) {
            throw new AmbiguousIdentifierException("Band code '" + code + "' is not unique, " + result.size() + " bands found!");
        }
        return Optional.of(result.get(0));
    }
}
