package nhatro.auth.repo;

import nhatro.core.domain.entity.TokenUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenUserRepo extends CrudRepository<TokenUserEntity, Integer> {
    TokenUserEntity findTopByToken(String token);
}
