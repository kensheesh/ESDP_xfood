package kg.attractor.xfood.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
	void saveFiles(List<MultipartFile> files, Long appealId);
}
