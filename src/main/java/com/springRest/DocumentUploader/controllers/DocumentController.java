package com.springRest.DocumentUploader.controllers;

import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.DocumentDTO;
import com.springRest.DocumentUploader.services.DocumentService;
import com.springRest.DocumentUploader.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.springRest.DocumentUploader.controllers.UserController.getUserFromToken;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/document/")
public class DocumentController {
    private final DocumentService documentService;
    private final UserService userService;

    private final String storagePath = System.getProperty("user.dir") + "/uploads/";

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDTO> uploadDocument(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("notifyAt") LocalDateTime notifyAt) {
        try {
            File directory = new File(storagePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = storagePath + file.getOriginalFilename();
            File newFile = new File(filePath);

            file.transferTo(newFile);

            if (!newFile.exists()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }



            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setFileName(file.getOriginalFilename());
            documentDTO.setContentType(file.getContentType());
            documentDTO.setDescription(description);
            documentDTO.setNotifyAt(notifyAt);

            User user = getUserFromToken(userService);

            DocumentDTO savedDocument = documentService.uploadDocument(documentDTO, user);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/document/" + savedDocument.getFileName());

            return new ResponseEntity<>(savedDocument, headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("media/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable("id") UUID id) throws IOException{
        User user = getUserFromToken(userService);

        DocumentDTO documentDTO = documentService.getDocumentByUserAndId(user, id).orElseThrow(NotFoundException::new);
        String fileName = documentDTO.getFileName();

        Path filePath = Paths.get(storagePath).resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()){
            return ResponseEntity.notFound().build();       }

        String contentType = Files.probeContentType(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PatchMapping("{id}")
    public DocumentDTO updateDocumentDescription(@PathVariable("id") UUID id, @RequestBody() DocumentDTO documentDto){
        User user = getUserFromToken(userService);
        return documentService.updateDocumentbyId(user, id, documentDto).orElseThrow(NotFoundException::new);
    }

    @GetMapping("{id}")
    public DocumentDTO getDocumentById(@PathVariable("id") UUID id){
        User user = getUserFromToken(userService);
        return documentService.getDocumentByUserAndId(user, id).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DocumentDTO> deleteById(@PathVariable("id") UUID id){
        User user = getUserFromToken(userService);
        String fileName = documentService.deleteById(id, user);
        if (fileName == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Path filePath = Paths.get("uploads", fileName);
        try{
            Files.delete(filePath);
        } catch (IOException e){
            throw new RuntimeException("Failed to delete file: " + filePath, e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("all")
    public Page<DocumentDTO> listDocuments(@RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) Integer pageNumber){

        User user = getUserFromToken(userService);

        return documentService.listDocuments(user, pageNumber, pageSize);
    }

}
