package org.runelive.client.cache.ondemand;

import org.runelive.task.Task;
import org.runelive.task.TaskManager;

import java.util.ArrayList;
import java.util.List;

public final class PriorityRequestHandler {

    private final List<Integer> priorityMapFiles;
    private final CacheFileRequester requester;
    private boolean running;
    private boolean checkTaskRunning;

    public boolean isRunning() {
        return running;
    }

    public PriorityRequestHandler(CacheFileRequester requester) {
        this.priorityMapFiles = new ArrayList<>();
        this.requester = requester;
    }

    public void addMap(int mapFileId) {
        if (this.priorityMapFiles.contains(mapFileId)) {
            return;
        }
        this.priorityMapFiles.add(mapFileId);
    }

    public boolean containsMap(int mapFileId) {
        return this.priorityMapFiles.contains(mapFileId);
    }

    public void requestFiles() {
        for (int mapFileId : this.priorityMapFiles) {
            //System.out.println("Pushing priority request for map file: " + mapFileId);
            this.requester.pushRequest(CacheFileRequest.MAP, mapFileId);
        }
        this.running = true;
    }

    private void startCheckTask() {
        TaskManager.submit(new Task() {
            @Override
            public void execute() {
                while (isRunning()) {
                    checkRequests();
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private boolean checkRequests() {
        for (int mapFileId : this.priorityMapFiles) {
            if (this.requester.remainingContains(CacheFileRequest.MAP, mapFileId)) {
                return false;
            }
        }
        if (this.requester.getRemaining() == 0) {
            System.out.println("All required files downloaded.");
            this.running = false;
            return true;
        }
        return true;
    }

    public boolean process() {
        if (!this.checkTaskRunning) {
            this.checkTaskRunning = true;
            startCheckTask();
            return true;
        }
        return true;
    }

}