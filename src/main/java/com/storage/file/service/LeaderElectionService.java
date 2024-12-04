package com.storage.file.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

import static java.lang.System.*;

@Service
public class LeaderElectionService {

    @Value("${spring.application.name}")
    private String appName;

    private final File leaderFile = new File("/home/mina/files/leader.txt");

    public void startLeaderElection() {
        int retryCount = 0;
        int maxRetries = 5;
        long backoffTime = 2000L;

        while (retryCount < maxRetries) {
            if (leaderFile.exists()) {
                out.println(appName + " is a Follower.");
                simulateFollowerTasks();
                return;
            } else {
                try (FileOutputStream fos = new FileOutputStream(leaderFile)) {

                    try (FileLock lock = fos.getChannel().tryLock()) {
                        if (lock != null) {
                            fos.write("leader".getBytes());
                            out.println(appName + " is the Leader.");
                            simulateLeaderTasks();
                            lock.release();
                            return;
                        } else {
                            retryCount++;
                            out.println(appName + " could not create leader file. Retrying in " + backoffTime + "ms...");
                            Thread.sleep(backoffTime);
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        out.println(appName + " failed to become leader after " + maxRetries + " retries.");
    }

    private void simulateLeaderTasks() {
        out.println(appName + " is performing leader tasks...");
    }

    private void simulateFollowerTasks() {
        out.println(appName + " is performing follower tasks...");
    }

    public void cleanUp() {
        if (leaderFile.exists()) {
            if (leaderFile.delete()) {
                out.println(appName + " cleaned up and released the leadership.");
            } else {
                out.println(appName + " failed to release the leadership.");
            }
        }
    }
}
