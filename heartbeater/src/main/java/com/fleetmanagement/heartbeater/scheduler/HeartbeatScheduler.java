package com.fleetmanagement.heartbeater.scheduler;

import com.fleetmanagement.heartbeater.service.HeartbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatScheduler {

    @Autowired
    private HeartbeatService heartbeatService;

    // Schedule heartbeats every 5 seconds (TODO - make interval time a CONST)
    @Scheduled(fixedRate = 5000)
    public void scheduleHeartbeat() {
        heartbeatService.sendHeartbeat();
    }
}

