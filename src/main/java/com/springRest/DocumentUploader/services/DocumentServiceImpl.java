package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.mappers.DocumentMapper;
import com.springRest.DocumentUploader.models.DocumentDTO;
import com.springRest.DocumentUploader.models.UserDTO;
import com.springRest.DocumentUploader.repositories.DocumentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;
    
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public DocumentDTO uploadDocument(DocumentDTO document, User user) {
        Document newDocument = documentMapper.documentDtoToDocument(document);
        newDocument.setUser(user);
        return documentMapper.documentToDocumentDto(documentRepository.save(newDocument));
    }

    @Override
    public Optional<DocumentDTO> getDocumentByUserAndId(UUID documentId, User user) {

        return Optional.ofNullable(documentMapper.documentToDocumentDto(documentRepository.findByUserAndId(user, documentId).orElse(null)));
    }

    @Override
    public Page<Document> listDocuments(User user, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        return documentRepository.findAllByUser(user, pageRequest);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;
        if (pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        } else{
            queryPageNumber = DEFAULT_PAGE;
        }
        
        if (pageSize == null){
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else if (pageSize > 1000) {
            queryPageSize = 1000;
        } else {
            queryPageSize = pageSize;
        }

        Sort sort = Sort.by(Sort.Order.asc("createdAt"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    @Override
    public boolean deleteById(UUID documentId) {
        if (documentRepository.existsById(documentId)){
            documentRepository.deleteById(documentId);
            return true;
        }
        return false;
    }
}
