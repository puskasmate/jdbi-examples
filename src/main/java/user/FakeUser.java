package user;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;

public class FakeUser {

    private Faker faker;

    public FakeUser(Locale locale) {
        this.faker = new Faker(locale);
    }

    public User getRandomUser() {
        return User.builder()
                .username(faker.name().username())
                .password(DigestUtils.md5Hex(faker.internet().password()))
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(User.Gender.MALE, User.Gender.FEMALE))
                .birthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .enabled(faker.bool().bool())
                .build();
    }

}
