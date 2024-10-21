package br.com.fiap.chatai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public String sendChatMessage(String message){
        return chatClient
                .prompt()
                .user(message)
                .system("""
                        Você é um professor de ciências.
                        Responda com uma linguagem adequada para crianças de 8 anos.
                        Você só pode falar sobre ciências.
                        Se você não souber, pode falar que não sabe.
                        """)
                .call()
                .content();
    }

}
