package kg.attractor.xfood.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
	
	void saveFiles(List<MultipartFile> files, Long appealId);
	
	List<String> getPathsForAppealFiles(Long appealId);
	
	ResponseEntity<InputStreamResource> downloadFile(String path);
}
