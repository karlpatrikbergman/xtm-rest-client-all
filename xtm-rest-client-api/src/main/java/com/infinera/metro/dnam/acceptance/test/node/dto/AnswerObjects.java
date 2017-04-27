package com.infinera.metro.dnam.acceptance.test.node.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Operation;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerObjects {
    private final List<AnswerObject> answerObjects;

    public List<AnswerObject> getAnswerObjects() {
        return answerObjects;
    }

    public AnswerObjects(List<AnswerObject> answerObjects) {
        this.answerObjects = answerObjects;
    }

    /**
     * Doesn't work with "get", "set", "error" (and more?) since they have both operation and mibentry in
     * response.
     * Does now work with "create" since it only has operation and not mibentry in response. For create the
     * mibentry is instead in the attributes array.
     */
    public Optional<AnswerObject> getAnswerObject(Operation operation, MibEntry mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.getOperation().equals(operation.getName()) && answerObject.getEntry().equals(mibEntry.getMibEntryString()))
                .findFirst();
    }

    public Optional<AnswerObject> getAnswerObject(MibEntry mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.getEntry().equals(mibEntry.getMibEntryString()))
                .findFirst();
    }

    public Optional<AnswerObject> getAnswerObject(Operation operation) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.getOperation().equals(operation.getName()))
                .findFirst();
    }

    public Optional<AnswerObject> getErrorAnswerObject(MibEntry mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.isSuccess() == false &&
                        answerObject.getOperation().equals(Operation.ERROR.getName()) &&
                        answerObject.getEntry().equals(mibEntry.getMibEntryString()))
                .findFirst();
    }

    public Optional<AnswerObject> getCreateErrorAnswerObject(MibEntry mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.isSuccess() == false &&
                                        answerObject.getOperation().equals(Operation.ERROR.getName()) &&
                                        answerObject.getAttributeObjectList().stream()
                                                .filter(attributeObject -> attributeObject.getName().equals(mibEntry.getMibEntryString()))
                                                .findAny().isPresent())
                .findFirst();
    }
//
//    public void checkSetResponse(MibEntry mibEntry) throws RuntimeException{
//        checkResponse(Operation.CREATE.SET, mibEntry);
//    }
//
//    public void checkGetResonse(MibEntry mibEntry) {
//        checkResponse(Operation.CREATE.GET, mibEntry);
//    }
//
//    private void checkResponse(Operation operation, MibEntry mibEntry) throws RuntimeException {
//        Optional<AnswerObject> answerObjectOptional = getAnswerObject(operation, mibEntry);
//        if (answerObjectOptional.isPresent() == false || answerObjectOptional.get().isSuccess() == false) {
//            throw new RuntimeException(getErrorMessage(mibEntry));
//        }
//    }
//
//    private String getErrorMessage(MibEntry mibEntry) {
//        String errorMessage;
//        Optional<AnswerObject> answerObjectOptional = getErrorAnswerObject(mibEntry);
//        if(answerObjectOptional.isPresent() == false) {
//            errorMessage = String.format("Failed to get error message. No AnswerObject found corresponding to operation 'error' and mibEntry %s.", mibEntry.getMibEntryString());
//        } else {
//            errorMessage = String.format("Error: %s", answerObjectOptional.get().getError());
//        }
//        return errorMessage;
//    }
}
