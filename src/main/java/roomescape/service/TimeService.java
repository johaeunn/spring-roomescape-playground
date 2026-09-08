package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.exception.DuplicateTimeException;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

    public Time create(LocalTime requestTime) {
        Time time = new Time(requestTime);

        try {
            return timeRepository.save(time);
        } catch (DuplicateKeyException e) {
            throw new DuplicateTimeException("이미 존재하는 시간입니다.");
        }
    }
}
