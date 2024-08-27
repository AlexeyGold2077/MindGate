package com.alexeygold2077.MindGate;

import com.alexeygold2077.MindGate.dto.SendMessageDTO;
import com.alexeygold2077.MindGate.dto.proxyapi.ChatCompletionRequest;
import com.alexeygold2077.MindGate.dto.proxyapi.ChatCompletionResponse;
import com.alexeygold2077.MindGate.dto.proxyapi.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class Proxyapi {

    private final String PROXY_API_KEY;

    private final Users users;

    private final String OPENAI_URL = "https://api.proxyapi.ru/openai/v1/chat/completions";

    private final String GPT4 = "gpt-4";
    private final String GPT4O = "gpt-4o";
    private final String GPT4TURBO = "gpt-4-turbo";
    private final String GPT4OMINI = "gpt-4o-mini";

    private final String DEFAULT_MODEL = GPT4OMINI;

    Proxyapi(String PROXY_API_KEY) {
        this.PROXY_API_KEY = PROXY_API_KEY;
        this.users = new Users(DEFAULT_MODEL);
    }

    public SendMessageDTO sendMessage(String id, String message, String role) throws IOException {

        users.addMessage(id, message, role);

        ChatCompletionRequest request = new ChatCompletionRequest(users.getModel(id), users.getMessages(id));

        ChatCompletionResponse response = getChatCompletion(request);

        users.addMessage(id, response.choices().get(0).message().content(), "assistant");

        return new SendMessageDTO(
                response.choices().get(0).message().content(),
                response.usage().prompt_tokens(),
                response.usage().completion_tokens(),
                response.usage().total_tokens()
        );
    }

    public void clearDialogue(String id) {

        users.clearDialogue(id);
    }

    public LinkedList<Message> getMessages(String id) {

        return users.getMessages(id);
    }

    public String getModel(String id) {

        return users.getModel(id);
    }

    public void setModel(String id, String model) {

        users.setModel(id, model);
    }

    private ChatCompletionResponse getChatCompletion(ChatCompletionRequest request) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + PROXY_API_KEY);

        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(request), headers);

        String response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, httpEntity, String.class).getBody();

        return objectMapper.readValue(response, ChatCompletionResponse.class);
    }
}