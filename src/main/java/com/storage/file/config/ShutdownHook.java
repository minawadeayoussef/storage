package com.storage.file.config;

import com.storage.file.service.LeaderElectionService;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ShutdownHook {

    private final LeaderElectionService leaderElectionService;

    public ShutdownHook(LeaderElectionService leaderElectionService) {
        this.leaderElectionService = leaderElectionService;
    }

    @PreDestroy
    public void cleanUp() {
        leaderElectionService.cleanUp();
    }
}
