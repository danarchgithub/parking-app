package parking;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private static final String template = "The server is running.";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/status")
    public Status status() {
        return new Status(counter.incrementAndGet(),
                            String.format(template));
    }

}
