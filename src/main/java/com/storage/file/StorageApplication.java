package com.storage.file;

import com.storage.file.service.LeaderElectionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StorageApplication implements CommandLineRunner {
	private final LeaderElectionService leaderElectionService;

    public StorageApplication(LeaderElectionService leaderElectionService) {
        this.leaderElectionService = leaderElectionService;
    }

    public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		leaderElectionService.startLeaderElection();
	}


	@PostConstruct
	public void init() {
		leaderElectionService.startLeaderElection();
	}

}
