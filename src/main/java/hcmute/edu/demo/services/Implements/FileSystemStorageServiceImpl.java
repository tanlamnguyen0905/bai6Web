package hcmute.edu.demo.services.Implements;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import hcmute.edu.demo.services.Interfaces.IStorageService;
import hcmute.edu.demo.config.*;
import hcmute.edu.demo.exception.StorageException;

public class FileSystemStorageServiceImpl implements IStorageService {
	private final Path rootLocation;

	public FileSystemStorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
		} catch (Exception e) {
			throw new StorageException("Could not read file: ", e);
		}

	}

	@Override
	public void delete(String storeFilename) throws Exception {
		// TODO Auto-generated method stub
		Path destinationFile = rootLocation.resolve(Paths.get(storeFilename)).normalize().toAbsolutePath();
		Files.delete(destinationFile);

	}

	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageException("Can not read file: " + filename);
		} catch (Exception e) {
			throw new StorageException("Could not read file: " + filename);
		}
	}

	@Override
	public void store(MultipartFile file, String storeFilename) {
		// TODO Auto-generated method stub
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file");
			}
			Path destinationFile = this.rootLocation.resolve(Paths.get(storeFilename)).normalize().toAbsolutePath(); // lấy
																														// đường
																														// dẫn
																														// tuyệtđối
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Cannot store file outsidecurent directory");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new StorageException("Failed to store file: ", e);
		}

	}

	@Override
	public String getSorageFilename(MultipartFile file, String id) {
		// TODO Auto-generated method stub
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext;
	}

}
