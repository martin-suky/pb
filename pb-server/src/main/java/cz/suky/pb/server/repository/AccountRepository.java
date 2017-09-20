package cz.suky.pb.server.repository;

import cz.suky.pb.server.domain.Account;
import cz.suky.pb.server.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by none_ on 16-Jul-17.
 */
@Repository
public interface AccountRepository extends AbstractEntityRepository<Account> {
    List<Account> findAccountsByOwner(User owner);

    Optional<Account> findAccountByOwnerAndId(User user, Long id);
}
