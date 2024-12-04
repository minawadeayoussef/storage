package com.storage.file.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.*;

@Service
public class LeaderElectionService {

    private final String appName;
    private final AtomicBoolean isLeader = new AtomicBoolean(false);

    public LeaderElectionService(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    public void startLeaderElection() {
        if (isLeader.compareAndSet(false, true)) {
            out.println(appName + " is the leader now!");
            simulateLeaderTasks();
        } else {
            out.println(appName + " is a follower now.");
            simulateFollowerTasks();
        }
    }

    private void simulateLeaderTasks() {
        out.println(appName + " is performing leader tasks...");
    }

    private void simulateFollowerTasks() {
        out.println(appName + " is performing follower tasks...");
    }
}
