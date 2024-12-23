package com.fleetmanagement.penaltycalculator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleetmanagement.penaltycalculator.entity.Penalty;
import com.fleetmanagement.penaltycalculator.repository.PenaltyRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PenaltyService {

    @Autowired
    private PenaltyRepository penaltyRepository;

    private final PenaltyCalculator penaltyCalculator = new PenaltyCalculator();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "heartbeat-topic", groupId = "penalty-service-group")
    public void consumeHeartbeat(ConsumerRecord<String, String> record) {
        try {
            JsonNode heartbeatNode = objectMapper.readTree(record.value());
            String driverId = heartbeatNode.get("driverId").asText();
            double speed = heartbeatNode.get("speed").asDouble();

            int penaltyPoints = penaltyCalculator.calculatePenalty(speed);
            if (penaltyPoints > 0) {
                penaltyCalculator.applyPenalty(driverId, penaltyPoints);

                Penalty penalty = penaltyRepository.findByDriverId(driverId);
                if (penalty == null) {
                    penalty = new Penalty(driverId, penaltyPoints);
                } else {
                    penalty.addPoints(penaltyPoints);
                }
                penaltyRepository.save(penalty);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

