package joon.springcontroller.log;

import joon.springcontroller.log.entity.Logs;
import joon.springcontroller.log.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogsRepository logsRepository;

    public void add(String logMessage) {
        Logs logs = Logs.builder().text(logMessage).build();
        logsRepository.save(logs);
    }
}
