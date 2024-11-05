package org.akip.service;

import camundajar.impl.com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.domain.ProcessInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.EntityFieldDefinitionDTO;
import org.akip.service.dto.Filter;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MongoService {

    private final MongoTemplate mongoTemplate;

    private final ProcessInstanceRepository processInstanceRepository;

    private final EntityFieldDefinitionService entityFieldDefinitionService;

    private final ProcessInstanceMapper processInstanceMapper;

    public MongoService(MongoTemplate mongoTemplate, ProcessInstanceRepository processInstanceRepository, EntityFieldDefinitionService entityFieldDefinitionService, ProcessInstanceMapper processInstanceMapper) {
        this.mongoTemplate = mongoTemplate;
        this.processInstanceRepository = processInstanceRepository;
        this.entityFieldDefinitionService = entityFieldDefinitionService;
        this.processInstanceMapper = processInstanceMapper;
    }

    public void saveInMongo(ProcessInstance processInstance, ProcessInstanceDTO processInstanceSaved) {
        if(processInstanceRepository.findById(processInstance.getId()).isPresent()){
            Gson gson = new Gson();
            String json = gson.toJson(processInstanceSaved.getData());
            System.out.println("\n\n\n\n"+json+"\n\n\n\n");
            Document document = Document.parse(json);
            document.append("processInstanceId", processInstanceSaved.getId());
            document.append("domainEntityName", processInstanceSaved.getProps().get("domainEntityName"));
            mongoTemplate.save(document, "DomainEntities");
        }
    }

    public void saveInMongo(ProcessInstanceDTO processInstanceDTO) throws JsonProcessingException {

        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceDTO.getId()).orElseThrow();

        Query query = new Query();
        Gson gson = new Gson();

        query.addCriteria(Criteria.where("processInstanceId").is(processInstance.getId()));
        Document data = mongoTemplate.findOne(query, Document.class,"DomainEntities");

        Document newData = Document.parse(gson.toJson(processInstanceDTO.getData()));

        newData.append("_id", data.get("_id"));
        newData.append("processInstanceId", processInstance.getId());
        newData.append("domainEntityName", processInstanceMapper.stringToMap(processInstance.getProps()).get("domainEntityName"));

        mongoTemplate.save(newData, "DomainEntities");
    }

    public List<Object> findAll(String entityName) {
        return mongoTemplate.findAll(Object.class, "DomainEntities");
    }

    public List<Object> findByEntityName(String entityName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("domainEntityName").is(entityName));
        return mongoTemplate.find(query, Object.class, "DomainEntities");
    }

    public List<Object> findEntitiesFiltered(List<Filter> filters, String entityName, String collectionName) {
        return mongoTemplate.find(mountQueryWithFilters(filters, entityName), Object.class, collectionName);
    }

    public Query mountQueryWithFilters(List<Filter> filters, String entityName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("domainEntityName").is(entityName));
        filters.forEach(
                filter -> {
                    if (filter.getValue() != null) if (filter.getType().equals("String") || filter.getType().equals("TextArea")) {
                        switch (filter.getOperator()) {
                            case "contains":
                                query.addCriteria(Criteria.where(filter.getId()).regex(filter.getValue()));
                                break;
                            case "startWith":
                                query.addCriteria(Criteria.where(filter.getId()).regex("^" + filter.getValue()));
                                break;
                            case "exactly":
                                query.addCriteria(Criteria.where(filter.getId()).is(filter.getValue()));
                                break;
                        }
                    } else if (filter.getType().equals("Long")) {
                        switch (filter.getOperator()) {
                            case "exactly":
                                query.addCriteria(Criteria.where(filter.getId()).is(Float.valueOf(filter.getValue())));
                                break;
                            case "lessThan":
                                query.addCriteria(Criteria.where(filter.getId()).lt(Float.valueOf(filter.getValue())));
                                break;
                            case "greaterThan":
                                query.addCriteria(Criteria.where(filter.getId()).gt(Float.valueOf(filter.getValue())));
                                break;
                        }
                    } else if (filter.getType().equals("Boolean")) {
                        query.addCriteria(Criteria.where(filter.getId()).is(Boolean.valueOf(filter.getValue())));
                    }
                }
        );
        return query;
    }

    public Object findById(String id, String entityName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Object.class, entityName);
    }

    public void checkValidations(Map<String, String> data, String domainEntityName){

        List<EntityFieldDefinitionDTO> entityFieldDefinitions =
                entityFieldDefinitionService
                .findByDomainEntityName(domainEntityName)
                        .stream()
                        .filter(entityFieldDefinitionDTO -> !entityFieldDefinitionDTO.getValidations().isEmpty())
                        .collect(Collectors.toList());

        entityFieldDefinitions.forEach(entityFieldDefinitionDTO -> {
            entityFieldDefinitionDTO.getValidations().forEach(validationDTO -> {
                if ("max-length".equals(validationDTO.getKey())){
                    if("String".equals(entityFieldDefinitionDTO.getType()) || "TextArea".equals(entityFieldDefinitionDTO.getType())){
                        if (data.get(entityFieldDefinitionDTO.getName()) != null && data.get(entityFieldDefinitionDTO.getName()).length() > Integer.parseInt(validationDTO.getValue())){
                            throw new BadRequestErrorException("Max Size Field Validation", "maxSizeFieldValidation","max size violation");
                        }
                    } else if("Long".equals(entityFieldDefinitionDTO.getType())){
                        if (data.get(entityFieldDefinitionDTO.getName()) != null && Long.parseLong(data.get(entityFieldDefinitionDTO.getName())) > Integer.parseInt(validationDTO.getValue())){
                            throw new BadRequestErrorException("Max Size Field Validation", "maxSizeFieldValidation","max size violation");
                        }
                    }
                }
                if ("min-length".equals(validationDTO.getKey())){
                    if("String".equals(entityFieldDefinitionDTO.getType()) || "TextArea".equals(entityFieldDefinitionDTO.getType())){
                        if (data.get(entityFieldDefinitionDTO.getName()) != null && data.get(entityFieldDefinitionDTO.getName()).length() < Integer.parseInt(validationDTO.getValue())){
                            throw new BadRequestErrorException("Min Size Field Validation", "minSizeFieldValidation","min size violation");
                        }
                    } else if("Long".equals(entityFieldDefinitionDTO.getType())){
                        if (data.get(entityFieldDefinitionDTO.getName()) != null && Long.parseLong(data.get(entityFieldDefinitionDTO.getName())) < Integer.parseInt(validationDTO.getValue())){
                            throw new BadRequestErrorException("Min Size Field Validation", "minSizeFieldValidation","min size violation");
                        }
                    }
                }
                if ("special-characters".equals(validationDTO.getKey())){
                    if("String".equals(entityFieldDefinitionDTO.getType()) || "TextArea".equals(entityFieldDefinitionDTO.getType())){
                        if (data.get(entityFieldDefinitionDTO.getName()) != null &&
                            "false".equals(validationDTO.getValue()) &&
                            data.get(entityFieldDefinitionDTO.getName()).matches(".*[^a-zA-Z0-9].*")
                        ){
                            throw new BadRequestErrorException("Special Characters Validation", "specialCharactersValidation","special characters validation");
                        }
                    }
                }
            });
        });
    }

}
