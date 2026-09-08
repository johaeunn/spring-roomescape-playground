package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import roomescape.exception.DuplicateTimeException;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TimeServiceTest {

    private static final Long NON_EXISTENT_ID = 999L;

    private TimeRepository timeRepository;
    private TimeService timeService;

    @BeforeEach
    void setUp() {
        timeRepository = mock(TimeRepository.class);
        timeService = new TimeService(timeRepository);
    }

    @Test
    void 이미_존재하는_시간을_생성하면_예외가_발생한다() {
        when(timeRepository.save(any()))
                .thenThrow(new DuplicateKeyException("duplicate"));

        assertThrows(
                DuplicateTimeException.class,
                () -> timeService.create(LocalTime.of(10, 0))
        );
    }

    @Test
    void 존재하는_시간을_삭제할_수_있다() {
        Long id = 1L;

        when(timeRepository.deleteById(id))
                .thenReturn(true);

        timeService.delete(id);

        verify(timeRepository).deleteById(id);
    }

    @Test
    void 존재하지_않는_시간을_삭제하면_예외가_발생한다() {
        when(timeRepository.deleteById(NON_EXISTENT_ID))
                .thenReturn(false);

        assertThrows(
                TimeNotFoundException.class,
                () -> timeService.delete(NON_EXISTENT_ID)
        );

        verify(timeRepository).deleteById(NON_EXISTENT_ID);
    }
}
