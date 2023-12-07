package org.akip.service;

import camundajar.impl.com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class MongoService {

    private final MongoTemplate mongoTemplate;

    private final ProcessInstanceRepository processInstanceRepository;

    private final ProcessInstanceMapper processInstanceMapper;

    public MongoService(MongoTemplate mongoTemplate, ProcessInstanceRepository processInstanceRepository, ProcessInstanceMapper processInstanceMapper) {
        this.mongoTemplate = mongoTemplate;
        this.processInstanceRepository = processInstanceRepository;
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

}
