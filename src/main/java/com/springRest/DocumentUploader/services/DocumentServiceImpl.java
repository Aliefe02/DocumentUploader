package com.springRest.DocumentUploader.services;

import com.springRest.DocumentUploader.entity.Document;
import com.springRest.DocumentUploader.entity.User;
import com.springRest.DocumentUploader.mappers.DocumentMapper;
import com.springRest.DocumentUploader.models.DocumentDTO;
import com.springRest.DocumentUploader.models.UserDTO;
import com.springRest.DocumentUploader.repositories.DocumentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private NotificationSchedulerService notificationSchedulerService;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;
    
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public DocumentDTO uploadDocument(DocumentDTO document, User user) {
        Document newDocument = documentMapper.documentDtoToDocument(document);
        newDocument.setUser(user);
        newDocument = documentRepository.save(newDocument);

        notificationSchedulerService.scheduleNotification(
                newDocument.getId(),
                user.getId(),
                newDocument.getDescription(),
                newDocument.getNotifyAt()
        );

        return documentMapper.documentToDocumentDto(newDocument);
    }

    @Override
    public Optional<DocumentDTO> getDocumentByUserAndId(User user, UUID id) {
        return Optional.ofNullable(documentMapper.documentToDocumentDto(documentRepository.findByIdAndUser(id, user).orElse(null)));
    }

    @Override
    public Optional<DocumentDTO> updateDocumentbyId(User user, UUID id, DocumentDTO documentDto) {
        Document document = documentRepository.findByIdAndUser(id, user).orElse(null);
        if (document != null){
            if (documentDto.getDescription() != null) {
                document.setDescription(documentDto.getDescription());
            }
            if (documentDto.getNotifyAt() != null){
                document.setNotifyAt(documentDto.getNotifyAt());
            }
            document = documentRepository.save(document);
            notificationSchedulerService.deleteNotificationJob(document.getId());
            notificationSchedulerService.scheduleNotification(
                    document.getId(),
                    user.getId(),
                    document.getDescription(),
                    document.getNotifyAt());
        }

        return Optional.of(documentMapper.documentToDocumentDto(document));
    }

    @Override
    public Page<DocumentDTO> listDocuments(User user, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        return documentRepository.findAllByUser(user, pageRequest).map(documentMapper::documentToDocumentDto);
    }

    public static PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
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
    public String deleteById(UUID documentId, User user) {
        Document document = documentRepository.findByIdAndUser(documentId, user).orElse(null);

        if (document != null){
            String fileName = document.getFileName();
            documentRepository.delete(document);
            notificationSchedulerService.deleteNotificationJob(documentId);
            return fileName;
        }

        return null;
    }

    @Override
    public Optional<Document> getDocumentEntityById(UUID id) {
        return documentRepository.findById(id);
    }
}
