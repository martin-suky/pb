package cz.suky.pb.server.repository;

import cz.suky.pb.server.domain.User;

import java.util.Optional;

/**
 * Created by none_ on 03/13/16.
 */
public interface UserRepository extends AbstractEntityRepository<User> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
