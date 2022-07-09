package com.booking.encryption;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BcryptPasswordEncoderTests {

    @Test
    public void emptyRawPasswordShouldNotMatchWithEncryptedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("password");
        assertThat(encoder.matches("", result)).isFalse();
    }

    @Test
    public void wrongPasswordShouldNotMatchWithEncryptedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("password");
        assertThat(encoder.matches("bogus", result)).isFalse();
    }

    @Test
    public void correctPasswordShouldMatchWithEncryptedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("password");
        assertThat(encoder.matches("password", result)).isTrue();
    }

    @Test
    public void badLowCustomStrength() {
        assertThatIllegalArgumentException().isThrownBy(() -> new BCryptPasswordEncoder(3));
    }

    @Test
    public void badHighCustomStrength() {
        assertThatIllegalArgumentException().isThrownBy(() -> new BCryptPasswordEncoder(32));
    }

    @Test
    public void doesNotMatchNullEncodedValue() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches("password", null)).isFalse();
    }

}
