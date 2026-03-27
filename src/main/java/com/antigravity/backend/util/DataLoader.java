package com.antigravity.backend.util;

import com.antigravity.backend.entity.ObjectEntity;
import com.antigravity.backend.entity.Simulation;
import com.antigravity.backend.entity.User;
import com.antigravity.backend.repository.SimulationRepository;
import com.antigravity.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SimulationRepository simulationRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Loading initial sample data...");

            User user1 = User.builder()
                    .name("Admin System")
                    .email("admin@antigravity.com")
                    .build();

            User user2 = User.builder()
                    .name("Test User")
                    .email("test@antigravity.com")
                    .build();

            userRepository.saveAll(List.of(user1, user2));

            Simulation sim1 = Simulation.builder()
                    .name("Free Fall Test")
                    .description("Testing gravity free fall")
                    .user(user1)
                    .build();

            ObjectEntity obj1 = ObjectEntity.builder()
                    .name("Apple")
                    .mass(0.2)
                    .velocity(0.0)
                    .positionX(0.0)
                    .positionY(10.0)
                    .force(1.96)
                    .simulation(sim1)
                    .build();

            sim1.setObjects(List.of(obj1));
            simulationRepository.save(sim1);

            log.info("Sample data loaded successfully.");
        }
    }
}
