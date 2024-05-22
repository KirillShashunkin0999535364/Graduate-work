package com.yashmerino.online.shop.repositories;


import com.yashmerino.online.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Users' repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * @param username is the username to be used for search.
     * @return an <code>Optional</code> object.
     */
    Optional<User> findByUsername(final String username);

    /**
     * Checks if a user exists by username.
     *
     * @param username is the username to be used for search.
     * @return <code>true</code> if exists and <code>false</code> otherwise.
     */
    Boolean existsByUsername(final String username);
}
