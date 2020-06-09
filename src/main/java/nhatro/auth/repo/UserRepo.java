package nhatro.auth.repo;

import nhatro.core.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Integer> {
    UserEntity findTopById(Integer id);

    UserEntity findTopByUsernameAndAndHashkey(String userName, String hashKey);
}
