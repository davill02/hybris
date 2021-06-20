package concerttours.services;

import concerttours.daos.TokenDao;
import concerttours.model.TokenModel;
import concerttours.service.TokenService;
import concerttours.service.impl.DefaultTokenService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
public class DefaultTokenServiceUnitTest {
    @Mock
    private ModelService modelService;
    @Mock
    private TokenDao tokenDao;
    @InjectMocks
    private TokenService tokenService = new DefaultTokenService();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldChangeValueAndSave() {
        List<TokenModel> tokens = new ArrayList<>();
        tokens.add(new TokenModel());
        when(tokenDao.getAllTokens()).thenReturn(tokens);

        tokenService.generateNewTokenValue();

        verify(modelService).save(any());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowException() {
        when(tokenDao.getAllTokens()).thenReturn(new ArrayList<>());

        tokenService.generateNewTokenValue();
    }
}
