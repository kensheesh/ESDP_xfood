package kg.attractor.xfood.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileUtil {
	
	private static final String UPLOAD_DIR = "data/";
	
	@SneakyThrows
	public String saveUploadedFile(MultipartFile file, String subDir, String fileName) {
		Path pathDir = Paths.get(UPLOAD_DIR + subDir);
		Files.createDirectories(pathDir);
		
		Path filePath = Paths.get(pathDir + "/" + fileName);
		if (! Files.exists(filePath)) {
			Files.createFile(filePath);
		}
		try (OutputStream os = Files.newOutputStream(filePath)) {
			os.write(file.getBytes());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return filePath.toString();
	}
	
	public ResponseEntity<?> downloadFile(String filename, String subDir) throws IOException {
		Path filePath = Paths.get(UPLOAD_DIR + subDir + "/" + filename);
		byte[] file = Files.readAllBytes(filePath);
		Resource resource = new ByteArrayResource(file);
		
		String contentType = Files.probeContentType(filePath);
		if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.contentLength(resource.contentLength())
				.contentType(MediaType.parseMediaType(contentType))
				.body(resource);
		
	}
}
