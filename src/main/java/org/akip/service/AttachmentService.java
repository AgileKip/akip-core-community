package org.akip.service;

import org.akip.domain.Attachment;
import org.akip.domain.AttachmentEntity;
import org.akip.exception.BadRequestErrorException;
import org.akip.minio.IDocumentStorageService;
import org.akip.publisher.ProcessInstanceEventPublisher;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.repository.AttachmentRepository;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.mapper.AttachmentMapper;
import org.akip.validator.IAttachmentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link org.akip.domain.Attachment}.
 */
@Service
@Transactional
public class AttachmentService {

    private static final String MINIO_ENTITY_NAME = "Attachment";

    private final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentEntityRepository attachmentEntityRepository;

    private final AttachmentMapper attachmentMapper;

    private final IDocumentStorageService documentStorageService;

    private List<IAttachmentValidator> attachmentValidators = new ArrayList<>();

    private final ProcessInstanceEventPublisher processInstanceEventPublisher;

    public AttachmentService(
            ApplicationContext applicationContext,
            AttachmentRepository attachmentRepository,
            AttachmentEntityRepository attachmentEntityRepository,
            AttachmentMapper attachmentMapper, ProcessInstanceEventPublisher processInstanceEventPublisher
    ) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentEntityRepository = attachmentEntityRepository;
        this.attachmentMapper = attachmentMapper;
        this.processInstanceEventPublisher = processInstanceEventPublisher;

//        List<IDocumentStorageService> documentStorageServices = applicationContext.getBeanNamesForType(IDocumentStorageService.class);

        Map<String, IDocumentStorageService> beans = applicationContext.getBeansOfType(IDocumentStorageService.class);
        List<IDocumentStorageService> documentStorageServices = new ArrayList<>(beans.values());
        if (documentStorageServices.isEmpty()) {
            throw new RuntimeException("No IDocumentStorageService found. You need to configure a IDocumentStorageService (Minio, Azure, Amazon S3)");
        }

        List<IDocumentStorageService> activeDocumentStorageServices = documentStorageServices
                .stream()
                .filter(iDocumentStorageService -> iDocumentStorageService.isActive())
                .collect(Collectors.toList());

        if (activeDocumentStorageServices.isEmpty()) {
            throw new RuntimeException("No Active IDocumentStorageService found. You need to configure a IDocumentStorageService (Minio, Azure, Amazon S3)");
        }

        if (activeDocumentStorageServices.size() > 1) {
            throw new RuntimeException("More than one IDocumentStorageService found. You need to configure only one IDocumentStorageService (Minio, Azure, Amazon S3)");
        }

        documentStorageService = activeDocumentStorageServices.get(0);

    }

    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        if (attachmentDTO.getId() == null) {
            return create(attachmentDTO);
        }

        return update(attachmentDTO);
    }

    public AttachmentDTO create(AttachmentDTO attachmentDTO) {
        log.debug("Request to create Attachment : {}", attachmentDTO);
        Attachment attachment = attachmentRepository.save(attachmentMapper.toEntity(attachmentDTO));
        documentStorageService.put(MINIO_ENTITY_NAME, attachment.getUploadedDate(), MINIO_ENTITY_NAME + attachment.getId(), attachmentDTO.getBytes());
        linkAttachmentToEntities(attachment, attachmentDTO);
        processInstanceEventPublisher.publishEventAddedAttachment(this, attachmentMapper.toDto(attachment));
        return attachmentMapper.toDto(attachment);
    }

    public AttachmentDTO update(AttachmentDTO attachmentDTO) {
        log.debug("Request to update Attachment : {}", attachmentDTO);
        Attachment attachment = attachmentRepository.save(attachmentMapper.toEntity(attachmentDTO));
        if (attachmentDTO.getBytes() != null) {
            // In the update, the bytes could be null.
            // This happens when the user is updating other attachment fields such as type or linking the attachment to other entities.
            documentStorageService.put(
                MINIO_ENTITY_NAME,
                attachment.getUploadedDate(),
                MINIO_ENTITY_NAME + attachment.getId(),
                attachmentDTO.getBytes()
            );
        }
        linkAttachmentToEntities(attachment, attachmentDTO);
        processInstanceEventPublisher.publishEventChangedAttachment(this, attachmentDTO);
        return attachmentMapper.toDto(attachment);
    }

    public List<AttachmentDTO> save(List<AttachmentDTO> attachmentsDTO) {
        return attachmentsDTO.stream().map(this::save).collect(Collectors.toList());
    }

    private void linkAttachmentToEntities(Attachment attachment, AttachmentDTO attachmentDTO) {
        linkAttachmentToEntity(attachment, attachmentDTO.getEntityName(), attachmentDTO.getEntityId());
        attachmentDTO
            .getOtherEntities()
            .forEach(
                attachmentEntityDTO -> {
                    linkAttachmentToEntity(attachment, attachmentEntityDTO.getEntityName(), attachmentEntityDTO.getEntityId());
                }
            );
    }

    private void linkAttachmentToEntity(Attachment attachment, String entityName, Long entityId) {
        validate(entityName, entityId);
        if (attachmentEntityRepository.findByEntityNameAndEntityIdAndAttachmentId(entityName, entityId, attachment.getId()).isPresent()) {
            //The attachment is already linked to the entity (name/id)
            return;
        }

        if (entityName == null || entityId == null) {
            return;
        }

        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setAttachment(attachment);
        attachmentEntity.setEntityName(entityName);
        attachmentEntity.setEntityId(entityId);
        attachmentEntityRepository.save(attachmentEntity);
    }

    public AttachmentDTO get(Long attachmentId) {
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachmentRepository.getOne(attachmentId));
        attachmentDTO.setBytes(
                documentStorageService.get(MINIO_ENTITY_NAME, attachmentDTO.getUploadedDate(), MINIO_ENTITY_NAME + attachmentDTO.getId())
        );
        return attachmentDTO;
    }

    public void delete(Long attachmentId) {
        log.debug("Request to delete Attachment : {}", attachmentId);
        AttachmentDTO attachmentDTO = attachmentMapper.toDto(attachmentRepository.getOne(attachmentId));
        attachmentEntityRepository.deleteByAttachmentId(attachmentId);
        attachmentRepository.deleteById(attachmentId);
        processInstanceEventPublisher.publishEventRemovedAttachment(this, attachmentDTO);
        documentStorageService.delete(MINIO_ENTITY_NAME, attachmentDTO.getUploadedDate(), MINIO_ENTITY_NAME + attachmentDTO.getId());
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> findByEntityNameAndEntityId(String entityName, Long entityId) {
        log.debug("Request to findByEntityNameAndEntitiesIds AttachmentEntity : entityName={}; entityId={}", entityName, entityId);
        return attachmentRepository
            .findByEntityNameAndEntityId(entityName, entityId)
            .stream()
            .map(attachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<AttachmentDTO> findByEntityNameAndEntityIdAndTypes(String entityName, Long entityId, List<String> types) {
        log.debug("Request to findByEntityNameAndEntityIdAndTypes: entityName={}; entityId={}; types{}", entityName, entityId, types);
        return attachmentRepository
            .findByEntityNameAndEntityIdAndTypeIn(entityName, entityId, types)
            .stream()
            .map(attachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public void registerValidator(IAttachmentValidator attachmentValidator) {
        this.attachmentValidators.add(attachmentValidator);
    }

    private void validate(String entityName, Long entityId) {
        this.attachmentValidators.stream()
            .filter(attachmentValidator -> attachmentValidator.getEntityName().equals(entityName))
            .forEach(attachmentValidator -> attachmentValidator.validate(entityId));
    }

    public void checkAtLeastOneAttachmentRequiredByTypes(String entityName, Long entityId, List<String> types) {
        isEmptyTypesToThrow(findByEntityNameAndEntityIdAndTypes(entityName, entityId, types), types);
    }

    private void isEmptyToThrow(List<AttachmentDTO> attachmentDTOS) {
        if (attachmentDTOS.isEmpty()) {
            throw new BadRequestErrorException("loginProcessosApp.attachment.errorRequired");
        }
    }

    private void isEmptyTypesToThrow(List<AttachmentDTO> attachmentDTOS, List<String> types) {
        if (attachmentDTOS.isEmpty()) {
            throw new BadRequestErrorException("loginProcessosApp.attachment.errorRequiredType", String.join(" ou ", types));
        }
    }

    public void checkAtLeastOneAttachmentRequired(String entityName, Long entityId) {
        isEmptyToThrow(findByEntityNameAndEntityId(entityName, entityId));
    }
}
