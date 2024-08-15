package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.File;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.repository.FileRepository;
import kg.attractor.xfood.service.impl.FileServiceImpl;
import kg.attractor.xfood.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private AppealRepository appealRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileUtil fileUtil;

    @Mock
    private Appeal appeal;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFiles_InvalidFileSize() {
        Long appealId = 1L;
        when(appealRepository.findById(appealId)).thenReturn(Optional.of(appeal));
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(4 * 1024 * 1024L);

        assertThrows(IllegalArgumentException.class, () -> fileService.saveFiles(Collections.singletonList(file), appealId));
    }

    @Test
    void testSaveFiles_TooManyPhotos() {
        Long appealId = 1L;
        when(appealRepository.findById(appealId)).thenReturn(Optional.of(appeal));
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(2 * 1024 * 1024L); // 2 MB

        List<MultipartFile> files = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            files.add(file);
        }

        assertThrows(IllegalArgumentException.class, () -> fileService.saveFiles(files, appealId));
    }

    @Test
    void testGetPathsForAppealFiles() {
        Long appealId = 1L;
        String path1 = "path/to/file1";
        String path2 = "path/to/file2";
        File file1 = new File();
        file1.setPath(path1);
        File file2 = new File();
        file2.setPath(path2);

        when(fileRepository.findByAppealId(appealId)).thenReturn(List.of(file1, file2));

        List<String> paths = fileService.getPathsForAppealFiles(appealId);

        assertNotNull(paths);
        assertEquals(2, paths.size());
        assertTrue(paths.contains(path1));
        assertTrue(paths.contains(path2));

        verify(fileRepository, times(1)).findByAppealId(appealId);
    }

    @Test
    void testGetFile() {
        String path = "path/to/file";
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(new byte[0]));

        when(fileUtil.getFIle(path)).thenReturn(ResponseEntity.ok(resource));

        ResponseEntity<InputStreamResource> response = fileService.getFile(path);

        assertNotNull(response);
        assertEquals(resource, response.getBody());

        verify(fileUtil, times(1)).getFIle(path);
    }

    @Test
    void testValidateAppealFiles_ValidFiles() {
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(2 * 1024 * 1024L); // 2 MB

        List<MultipartFile> validFiles = fileService.validateAppealFiles(Collections.singletonList(file));

        assertNotNull(validFiles);
        assertEquals(1, validFiles.size());
    }

    @Test
    void testValidateAppealFiles_InvalidFileType() {
        when(file.getContentType()).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> fileService.validateAppealFiles(Collections.singletonList(file)));
    }

    @Test
    void testValidateAppealFiles_TooLargeFile() {
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(4 * 1024 * 1024L);

        assertThrows(IllegalArgumentException.class, () -> fileService.validateAppealFiles(Collections.singletonList(file)));
    }

    @Test
    void testValidateAppealFiles_TooManyFiles() {
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getSize()).thenReturn(2 * 1024 * 1024L);

        List<MultipartFile> files = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            files.add(file);
        }

        assertThrows(IllegalArgumentException.class, () -> fileService.validateAppealFiles(files));
    }

}