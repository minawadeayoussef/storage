package com.storage.file.service;

import org.springframework.stereotype.Service;

@Service
public class LeaderElectionService
{
    private boolean isLeader = false;

    public void setLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public String performTask() {
        if (isLeader) {
            return "Task performed by the leader";
        } else {
            return "Not the leader, task skipped";
        }
    }

    // Return the leadership status
    public boolean isLeader() {
        return isLeader;
    }
}
