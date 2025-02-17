package com.springRest.DocumentUploader.controllers;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.models.DocumentDTO;
import com.springRest.DocumentUploader.services.DocumentService;
import com.springRest.DocumentUploader.services.UserService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springRest.DocumentUploader.controllers.UserController.getUserFromToken;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DocumentController {
    private final DocumentService documentService;
    private final UserService userService;

    @PostMapping("/document/upload")
    public ResponseEntity<DocumentDTO> createNewDocument(@Validated @RequestBody DocumentDTO document){
        User user = getUserFromToken(userService);

        DocumentDTO savedDocument = documentService.uploadDocument(document, user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/document/" + savedDocument.getId());
        return new ResponseEntity<>(savedDocument, headers, HttpStatus.CREATED);
    }

    @GetMapping("/document/{id}")
    public DocumentDTO getDocumentById(@PathVariable("id") UUID id){
        User user = getUserFromToken(userService);
        return documentService.getDocumentByUserAndId(id, user).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/document/{id}")
    public ResponseEntity<DocumentDTO> deleteById(@PathVariable("id") UUID id){
        if (!documentService.deleteById(id)){
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/document")
    public Page<Document> listDocuments(@RequestParam(required = true) String username,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) Integer pageNumber){

        User user = userService.getUserEntityByUsername(username).orElseThrow(NotFoundException::new);
        return documentService.listDocuments(user, pageNumber, pageSize);
    }

}
