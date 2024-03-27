package org.user_service.services.util;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class IntegrationTestsUtils {
    private static final PostgreSQLContainer<?> SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:9.6.12"));

    @BeforeAll
    static void starUp(){
        SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", SQL_CONTAINER::getJdbcUrl);
    }
}
