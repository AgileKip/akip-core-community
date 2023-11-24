package org.akip.llm;

import org.akip.llm.chatgpt.ChatGPTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/llm")
public class LlmController {

    private final Logger log = LoggerFactory.getLogger(LlmController.class);

    private final ChatGPTService chatService;

    public LlmController(ChatGPTService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("/complete")
    public ResponseEntity<LlmResponseDTO> complete() throws URISyntaxException {
        LlmRequestDTO chatRequest = new LlmRequestDTO();
        chatRequest.setPromptConfigurationName("hello-world");

        Map<String, Object> context = new HashMap<>();
        context.put("purchaseOrder", "12345");
        chatRequest.setContext(context);

        Map<String, Object> params = new HashMap<>();
        params.put("language", "Portuguese");
        chatRequest.setParams(params);

        LlmResponseDTO chatResponse = chatService.complete(chatRequest);
        return ResponseEntity
                .ok(chatResponse);
    }

    @PostMapping("/complete")
    public ResponseEntity<LlmResponseDTO> complete(@Valid @RequestBody LlmRequestDTO chatRequest){
        LlmResponseDTO chatResponse = chatService.complete(chatRequest);
        return ResponseEntity
                .ok(chatResponse);
    }

}
