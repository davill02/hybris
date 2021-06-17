package concerttours.service.impl;

import concerttours.daos.TokenDao;
import concerttours.model.TokenModel;
import concerttours.service.TokenService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.jgroups.util.UUID;

import javax.annotation.Resource;
import java.util.List;
import java.util.NoSuchElementException;

public class DefaultTokenService implements TokenService {
    @Resource
    private TokenDao tokenDao;
    @Resource
    private ModelService modelService;

    @Override
    public TokenModel getToken() {
        List<TokenModel> tokens = tokenDao.getAllTokens();
        if (tokens.isEmpty()) {
            throw new NoSuchElementException("We don't have tokens");
        }
        return tokens.get(0);
    }

    @Override
    public void generateNewTokenValue() {
        TokenModel token = getToken();
        token.setToken(UUID.randomUUID().toString());
        modelService.save(token);
    }
}
