package org.akip.llm.chatgpt;

import groovy.lang.Binding;
import org.akip.groovy.HqlApi;
import org.akip.groovy.JsonApi;
import org.akip.groovy.MessageApi;
import org.akip.llm.LlmRequestDTO;
import org.akip.resolver.UserResolver;
import org.akip.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class ChatGPTBindingBuilder {

    @Autowired
    private HqlApi hqlApi;

    private JsonApi jsonApi = new JsonApi();

    @Autowired
    private MessageApi messageApi;

    @Autowired
    private Environment env;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserResolver userResolver;

    public Binding buildBinding() {
        Binding binding = new Binding();
        binding.setVariable("env", env);
        binding.setVariable("hqlApi", hqlApi);
        binding.setVariable("jsonApi", jsonApi);
        binding.setVariable("messageApi", messageApi);
        binding.setVariable("entityManager", entityManager);
        return binding;
    }

    public Binding buildBindingFromChatRequest(LlmRequestDTO chatRequest) {
        Binding binding = buildBinding();
        binding.setVariable("source", chatRequest.getSource());
        binding.setVariable("params", chatRequest.getParams());
        binding.setVariable("context", chatRequest.getContext());

        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            binding.setVariable("currentUser", userResolver.getUser(SecurityUtils.getCurrentUserLogin().get()));
        }

        return binding;
    }

}
