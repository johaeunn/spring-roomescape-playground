package roomescape.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.dto.TimeResponse;
import roomescape.service.TimeService;

import java.util.List;

@RestController
public class TimeController {

    private final TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/times")
    public List<TimeResponse> times() {
        List<Time> times = timeService.findAll();

        return times.stream()
                .map(TimeResponse::from)
                .toList();
    }
}
