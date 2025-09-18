package hcmute.edu.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import hcmute.edu.demo.Entity.Category;
import hcmute.edu.demo.config.StorageProperties;
import hcmute.edu.demo.services.Interfaces.IStorageService;

@SpringBootApplication(scanBasePackages = { "hcmute.edu.demo.Controller" })
@ComponentScan
@EnableConfigurationProperties(StorageProperties.class) // thêm cấu hình storage
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		Category cate = new Category();

	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;

	}

	// thêm cấu hình storage
	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}

}
