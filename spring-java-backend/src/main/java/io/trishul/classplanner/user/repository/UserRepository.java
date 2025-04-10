package io.trishul.classplanner.user.repository;

import org.springframework.stereotype.Repository;
import io.trishul.classplanner.common.repository.BaseRepository;
import io.trishul.classplanner.user.model.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
}
