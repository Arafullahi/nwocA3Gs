package com.nwoc.a3gs.group.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.jfilter.EnableJsonFilter;
import com.nwoc.a3gs.group.app.dto.FileStorageProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
    FileStorageProperties.class
})
//@ComponentScan({"com.jfilter.components"})
@EnableJsonFilter
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
