package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.exception.DuplicateTimeException;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import({TimeRepository.class, TimeService.class})
public class TimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimeService timeService;

    @Test
    void 이미_존재하는_시간을_생성하면_예외가_발생한다() {
        jdbcTemplate.update(
                "INSERT INTO time (time) VALUES (?)",
                LocalTime.of(10, 0)
        );

        assertThrows(
                DuplicateTimeException.class,
                () -> timeService.create(LocalTime.of(10, 0))
        );
    }
}
