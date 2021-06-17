package concerttours.daos;

import concerttours.model.TokenModel;

import java.util.List;

public interface TokenDao {
    List<TokenModel> getAllTokens();

    void save(TokenModel tokenModel);
}
