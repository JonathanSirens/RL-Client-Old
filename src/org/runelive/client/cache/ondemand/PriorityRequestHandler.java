package org.runelive.client.cache.ondemand;

import org.runelive.task.Task;
import org.runelive.task.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class PriorityRequestHandler {

    private List<Integer>[] priorityFiles;
    private final CacheFileRequester requester;
    private boolean running;
    private boolean checkTaskRunning;

    public boolean isRunning() {
        return running;
    }

    public PriorityRequestHandler(CacheFileRequester requester) {
        this.priorityFiles = new ArrayList[6];
        for (int index = 0; index < this.priorityFiles.length; index++) {
            this.priorityFiles[index] = new ArrayList<>();
        }
        this.requester = requester;
    }

    public void addFile(int fileIndex, int fileId) {
        if (this.priorityFiles[fileIndex].contains(fileId)) {
            return;
        }
        this.priorityFiles[fileIndex].add(fileId);
    }

    public void addModel(int modelFileId) {
        if (modelFileId >= 65535) {
            modelFileId -= 65535;
            this.addFile(CacheFileRequest.MODEL_EXT, modelFileId);
            return;
        }
        this.addFile(CacheFileRequest.MODEL, modelFileId);
    }

    public void addAnim(int animFileId) {
        this.addFile(CacheFileRequest.ANIM, animFileId);
    }

    public void addMap(int mapFileId) {
        this.addFile(CacheFileRequest.MAP, mapFileId);
    }

    public void requestFiles() {
        for (int index = 0; index < this.priorityFiles.length; index++) {
            if (this.priorityFiles[index].isEmpty()) {
                continue;
            }
            for (int fileId : this.priorityFiles[index]) {
                this.requester.pushRequest(index, fileId);
            }
        }
        this.running = true;
    }

    private boolean checkRequests() {
        for (int index = 0; index < this.priorityFiles.length; index++) {
            if (this.priorityFiles[index].isEmpty()) {
                continue;
            }
            for (int fileId : this.priorityFiles[index]) {
                if (this.requester.remainingContains(index, fileId)) {
                    return false;
                }
            }
        }
        if (this.requester.getRemaining() == 0) {
            System.out.println("All required files downloaded.");
            this.running = false;
            for (int list = 0; list < this.priorityFiles.length; list++) {
                this.priorityFiles[list].clear();
            }
            this.priorityFiles = null;
            System.gc();
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

}