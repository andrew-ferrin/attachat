package dev.andrew.attachat.domain;

import java.util.List;

import io.github.sashirestela.openai.common.function.FunctionCall;

public record Response(
        String content,
        List<FunctionCall> functionCalls) {
}
