package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;


@RegisterBeanMapper(User.class)
public interface UserDao {
    @SqlUpdate("""
        CREATE TABLE user (
            id IDENTITY PRIMARY KEY,
            username VARCHAR NOT NULL,
            password VARCHAR NOT NULL,
            email VARCHAR NOT NULL,
            birthDate DATE NOT NULL,
            gender ENUM('MALE', 'FEMALE') NOT NULL,
            enabled BIT NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO user (username, password, email, birthDate, gender, enabled) VALUES (:username, :password, :email, :birthDate, :gender, :enabled)")
    @GetGeneratedKeys
    Long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user WHERE id = :id")
    Optional<User> getUser(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM user WHERE username = :username")
    Optional<User> getUserByUsername(@Bind("username") String username);

    @SqlUpdate("DELETE FROM user WHERE id = :id")
    void deleteUser(@BindBean User user);

    @SqlQuery("SELECT * FROM user")
    List<User> listUsers();
}
