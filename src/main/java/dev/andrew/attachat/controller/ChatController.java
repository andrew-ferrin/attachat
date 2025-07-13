package dev.andrew.attachat.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.andrew.attachat.config.properties.LlmProperties;
import dev.andrew.attachat.domain.Request;
import dev.andrew.attachat.domain.Response;
import dev.andrew.attachat.function.RetrieveAttachmentFunction;
import io.github.sashirestela.openai.SimpleOpenAI;
import io.github.sashirestela.openai.common.function.FunctionDef;
import io.github.sashirestela.openai.common.function.FunctionExecutor;
import io.github.sashirestela.openai.common.tool.ToolCall;
import io.github.sashirestela.openai.domain.chat.Chat;
import io.github.sashirestela.openai.domain.chat.ChatMessage.SystemMessage;
import io.github.sashirestela.openai.domain.chat.ChatMessage.UserMessage;
import io.github.sashirestela.openai.domain.chat.ChatRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final LlmProperties properties;
    private final SimpleOpenAI llm;

    private static final FunctionExecutor FX = new FunctionExecutor(
            List.of(
                    FunctionDef.builder()
                            .name("retrieve_attachment")
                            .description("Retrieve an attachment by its filename and sequence number")
                            .functionalClass(RetrieveAttachmentFunction.class)
                            .strict(Boolean.TRUE)
                            .build()));

    @PostMapping("/chat")
    public Response chat(@RequestBody Request request) throws Exception {
        ChatRequest req = createChatRequest(request);
        Chat chat = llm.chatCompletions().create(req).join();

        return new Response(
                chat.firstContent(),
                Optional.ofNullable(chat.firstMessage().getToolCalls())
                        .map(List::stream)
                        .orElse(Stream.empty())
                        .map(ToolCall::getFunction)
                        .toList());
    }

    private ChatRequest createChatRequest(Request request) {
        return ChatRequest.builder()
                .message(SystemMessage.of(request.systemPrompt()))
                .message(UserMessage.of(request.prompt()))
                .tools(FX.getToolFunctions())
                .model(properties.model())
                .temperature(0.1)
                .build();
    }

}
