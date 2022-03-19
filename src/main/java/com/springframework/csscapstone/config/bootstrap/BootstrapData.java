package com.springframework.csscapstone.config.bootstrap;

import com.springframework.csscapstone.controller.domain.Campaign;
import com.springframework.csscapstone.controller.domain.Role;
import com.springframework.csscapstone.data.repositories.CategoryRepository;
import com.springframework.csscapstone.data.status.CampaignStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapData implements ApplicationRunner {

    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository repository;

    private final List<Role> roles = Arrays.asList(
            new Role("Admin"),
            new Role("Enterprise"),
            new Role("Collaborator"));

    private final List<Campaign> campaigns = Arrays.asList(
            new Campaign("The Spring Campaign", LocalDateTime.of(2002, Month.JANUARY, 29, 19, 30, 40), LocalDateTime.now(),
                    "Spring Awesome", "This is description", 2022L, CampaignStatus.FINISHED),
            new Campaign("The Summer Campaign", LocalDateTime.of(2002, Month.JANUARY, 29, 19, 30, 40), LocalDateTime.now(),
                    "Spring Awesome", "This is description", 2022L, CampaignStatus.FINISHED),
            new Campaign("The Fall Campaign", LocalDateTime.of(2002, Month.JANUARY, 29, 19, 30, 40), LocalDateTime.now(),
                    "Spring Awesome", "This is description", 2022L, CampaignStatus.FINISHED),
            new Campaign("The Winter Campaign", LocalDateTime.of(2002, Month.JANUARY, 29, 19, 30, 40), LocalDateTime.now(),
                    "Spring Awesome", "This is description", 2022L, CampaignStatus.FINISHED)
    );

    @Override
    public void run(ApplicationArguments args) {
    }
}
