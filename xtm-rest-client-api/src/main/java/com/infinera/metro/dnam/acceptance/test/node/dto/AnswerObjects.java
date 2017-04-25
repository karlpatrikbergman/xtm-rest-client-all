package com.infinera.metro.dnam.acceptance.test.node.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerObjects {
    private final List<AnswerObject> answerObjects;

    public AnswerObjects(List<AnswerObject> answerObjects) {
        this.answerObjects = answerObjects;
    }

    public Optional<AnswerObject> getAnswerObject(String operationName, String mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.getOperation().equals(operationName) && answerObject.getEntry().equals(mibEntry))
                .findFirst();
    }

    public Optional<AnswerObject> getAnswerObject(String operationName) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.getOperation().equals(operationName))
                .findFirst();
    }
}
