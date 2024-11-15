package org.akip.llm.chatgpt;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.akip.llm.LlmRequestDTO;
import org.akip.llm.LlmResponseDTO;
import org.akip.service.AkipPromptConfigurationService;
import org.akip.service.dto.AkipPromptConfigurationDTO;
import org.akip.service.dto.AkipPromptConfigurationMessageDTO;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ChatGPTService {

    private final Environment env;

    private final AkipPromptConfigurationService akipPromptConfigurationService;

    private final ChatGPTBindingBuilder chatGPTBindingBuilder;

    private String chatGptAuthorizationBearerToken;

    private String chatGptApiUrl = "https://api.openai.com/v1/chat/completions";

    public ChatGPTService(Environment env, AkipPromptConfigurationService akipPromptConfigurationService, ChatGPTBindingBuilder chatGPTBindingBuilder) {
        this.env = env;
        this.akipPromptConfigurationService = akipPromptConfigurationService;
        this.chatGPTBindingBuilder = chatGPTBindingBuilder;
        this.chatGptAuthorizationBearerToken = env.getProperty("akip.llm.chatgpt.authorizationBearerToken");
        if (env.getProperty("akip.llm.chatgpt.apiUrl") != null) {
            this.chatGptApiUrl = env.getProperty("akip.llm.chatgpt.apiUrl");
        }
    }

    public LlmResponseDTO complete(LlmRequestDTO llmRequest) {
        RestTemplate restTemplate = new RestTemplate();
        ChatGPTRequestDTO chatGPTRequest = toChatGTPRequest(llmRequest);
        HttpEntity<ChatGPTRequestDTO> request = new HttpEntity<>(chatGPTRequest, buildHttpHeaders());
        ChatGPTResponseDTO gptResponse = restTemplate.postForObject(chatGptApiUrl, request, ChatGPTResponseDTO.class);
        return new LlmResponseDTO().content(gptResponse.getChoices().get(0).getMessage().getContent());
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + this.chatGptAuthorizationBearerToken);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private ChatGPTRequestDTO toChatGTPRequest(LlmRequestDTO chatRequest) {
        AkipPromptConfigurationDTO akipPromptConfiguration = akipPromptConfigurationService.findByName(chatRequest.getPromptConfigurationName());

        ChatGPTRequestDTO chatGPTRequest = new ChatGPTRequestDTO()
                .model(akipPromptConfiguration.getModel())
                .temperature(akipPromptConfiguration.getTemperature());

        for (int i = 0; i < akipPromptConfiguration.getMessages().size(); i++) {
            AkipPromptConfigurationMessageDTO message = akipPromptConfiguration.getMessages().get(i);

            chatGPTRequest.addMessage(new ChatGPTMessageDTO()
                    .role(message.getRole())
                    .content(executeContentExpression(message.getContentExpression(), chatRequest))
            );

        }


        return chatGPTRequest;
    }

    private String executeContentExpression(String contentExpression, LlmRequestDTO chatRequest) {
        Binding binding = chatGPTBindingBuilder.buildBindingFromChatRequest(chatRequest);
        GroovyShell shell = new GroovyShell(binding);
        String expression = contentExpression;
        if (!expression.contains("\"\"\"")) {
            expression = "\"\"\"" + expression + "\"\"\"";
        }
        return shell.evaluate(expression).toString();
    }

    /**
     * If the any required property is not defined, the Spring component is not active.
     * @return
     */
    public boolean isActive() {
        if (env.getProperty("akip.chatgpt.inactive") != null && "true" == env.getProperty("akip.chatgpt.inactive")) {
            return false;
        }

        if (chatGptAuthorizationBearerToken == null) {
            return false;
        }

        return true;
    }


}
