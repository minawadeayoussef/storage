package com.storage.file;

import com.storage.file.service.LeaderElectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderElectionServiceTest
{
    private LeaderElectionService leaderElectionService;

    @BeforeEach
    void setUp() {
        leaderElectionService = new LeaderElectionService();
    }

    @Test
    void testPerformTaskAsLeader() {
        leaderElectionService.setLeader(true);

        String result = leaderElectionService.performTask();
        assertEquals("Task performed by the leader", result);
    }

    @Test
    void testPerformTaskAsNonLeader() {
        leaderElectionService.setLeader(false);

        String result = leaderElectionService.performTask();
        assertEquals("Not the leader, task skipped", result);
    }

    @Test
    void testIsLeaderStatus() {
        // Test if the leader status is set correctly
        leaderElectionService.setLeader(true);
        assertTrue(leaderElectionService.isLeader());

        leaderElectionService.setLeader(false);
        assertFalse(leaderElectionService.isLeader());
    }
}
