package concerttours.service;

import concerttours.model.TokenModel;

public interface TokenService {
    TokenModel getToken();
    void generateNewTokenValue();
}
