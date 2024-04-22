package com.ps.culinarycompanion.user;

import com.ps.culinarycompanion.dao.UserRepository;
import com.ps.culinarycompanion.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    public UserService(EntityManager entityManager, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    /**
     * Retrieves all the users from the user repository.
     *
     * @return a list of User objects representing all the users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID from the user repository.
     *
     * @param  id  the ID of the user to retrieve
     * @return     the User object representing the user with the given ID, or null if not found
     * @throws NotFoundException if the user with the given ID does not exist
     */
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User id:"+id);
        }

        return user.get();
    }

    /**
     * Retrieves a user by their email from the user repository.
     *
     * @param  email  the email of the user to retrieve
     * @return        the User object representing the user with the given email, or throws a NotFoundException if not found
     */
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException("User email:"+email);
        }

        return user.get();
    }

    /**
     * Creates a new user in the repository.
     *
     * @param  user  the User object to be created
     * @return       the created User object
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates the user information in the database with the provided user object and email.
     *
     * @param  user    the user object containing the updated information
     * @param  email   the email of the user to be updated
     */
    @Transactional
    public void updateUser(User user, String email) {
        try {
            User existingUser = entityManager.find(User.class, email);

            if (existingUser != null) {
                existingUser.setFullName(user.getFullName());
                existingUser.setEmail(user.getUsername());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a user from the repository based on the provided email.
     *
     * @param  email   the email of the user to be deleted
     */
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

}
