package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file/")
@RequiredArgsConstructor
public class FileController {
	
	private final FileService fileService;
	
	@PostMapping("upload")
	public ResponseEntity<?> save(@RequestParam("files") List<MultipartFile> files, Long appealId) {
		fileService.saveFiles(files, appealId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("download")
	public ResponseEntity<?> show(MultipartFile file) {
		return null;
	}
}

