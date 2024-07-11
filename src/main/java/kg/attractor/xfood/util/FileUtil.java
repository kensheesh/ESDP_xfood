package kg.attractor.xfood.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Component
@Slf4j
public class FileUtil {
	
	private static final String UPLOAD_DIR = "data/";
	
	@SneakyThrows
	public String saveUploadedFile(MultipartFile file, String subDir, String fileName) {
		Path pathDir = Paths.get(UPLOAD_DIR, subDir);
		Files.createDirectories(pathDir);
		
		Path filePath = pathDir.resolve(fileName);
		if (! Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		try (OutputStream os = Files.newOutputStream(filePath)) {
			os.write(file.getBytes());
		} catch (IOException e) {
			log.error("Error writing file: {}", e.getMessage());
			throw new RuntimeException("Failed to save file", e);
		}
		log.info("File saved at: {}", filePath);
		return filePath.toString();
	}
	
	@SneakyThrows
	public ResponseEntity<InputStreamResource> downloadFile(String path) {
		try {
			Path filePath = Paths.get(path);
			if (! Files.exists(filePath)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(null);
			}
			
			InputStreamResource resource = new InputStreamResource(Files.newInputStream(filePath));
			MediaType mediaType = MediaType.parseMediaType(Files.probeContentType(filePath));
			return ResponseEntity.ok()
					.contentType(mediaType)
					.body(new InputStreamResource(resource.getInputStream()));
		} catch (IOException e) {
			log.error("No file found:", e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteOldAppealFiles() {
		try (Stream<Path> files = Files.walk(Paths.get(UPLOAD_DIR + "appealFiles/"))) {
			files.filter(Files :: isRegularFile)
					.forEach(file -> {
						try {
							BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
							Instant creationTime = attrs.creationTime().toInstant();
							if (creationTime.isBefore(Instant.now().minus(90, ChronoUnit.DAYS))) {
								Files.delete(file);
								log.info("Deleted old file: {}", file);
							}
						} catch (IOException e) {
							log.error("Error deleting file: {}", file, e);
						}
					});
		} catch (IOException e) {
			log.error("Error walking file tree: " + UPLOAD_DIR, e);
		}
	}
}
