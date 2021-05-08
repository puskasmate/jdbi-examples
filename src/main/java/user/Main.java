package user;

import ex10.LegoSet;
import ex10.LegoSetDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:testuser");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.setSqlLogger(new Slf4JSqlLogger());
        List<User> users = jdbi.withExtension(UserDao.class, dao -> {
            dao.createTable();
            for (int i = 0; i < 10; i++) {
                FakeUser fakeUser;
                if (i % 2 == 0) {
                    fakeUser = new FakeUser(new Locale("hu"));
                }
                else {
                    fakeUser = new FakeUser(new Locale("en"));
                }
                dao.insertUser(fakeUser.getRandomUser());

            }
            System.out.println("---------------------------HERE--------------------------------");
            dao.listUsers().stream().forEach(System.out::println);
            System.out.println("---------------------------HERE--------------------------------");
            dao.deleteUser(dao.listUsers().get(1));
            System.out.println("---------------------------HERE--------------------------------");
            dao.listUsers().stream().forEach(System.out::println);
            System.out.println("---------------------------HERE--------------------------------");
            dao.getUser(Long.valueOf(3)).stream().forEach(System.out::println);
            System.out.println("---------------------------HERE--------------------------------");
            dao.getUserByUsername(dao.listUsers().get(3).getUsername()).stream().forEach(System.out::println);
            return dao.listUsers();
        });
        users.forEach(System.out::println);
    }
}
