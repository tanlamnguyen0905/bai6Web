package hcmute.edu.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {
	private String location;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public StorageProperties(String location) {
		super();
		this.location = location;
	}

	public StorageProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}