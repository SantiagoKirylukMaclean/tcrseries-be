package com.puetsnao.tcrseries

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class TcrseriesApplicationTests {

	companion object {
		@Container
		private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:15-alpine").apply {
			withDatabaseName("tcrseries")
			withUsername("postgres")
			withPassword("postgres")
		}

		@JvmStatic
		@DynamicPropertySource
		fun properties(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
			registry.add("spring.datasource.username", postgresContainer::getUsername)
			registry.add("spring.datasource.password", postgresContainer::getPassword)
		}
	}

	@Test
	fun contextLoads() {
	}

}
