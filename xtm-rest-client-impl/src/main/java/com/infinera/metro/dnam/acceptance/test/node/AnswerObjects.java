package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infinera.metro.dnam.acceptance.test.node.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.Operation;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerObjects {
    private final List<AnswerObject> answerObjects;

    public AnswerObjects(List<AnswerObject> answerObjects) {
        this.answerObjects = answerObjects;
    }

    /**
     * TODO: Make sure we find error when setting expected frequency on line port!
     *
     * Check if AnswerObjects contains a success response (of type AnswerObject to a specific operation
     * on a specific mibEntry. If no AnswerObject is found a RuntimeException is thrown.
     *
     * If an error response (AnswerObject) corresponding to the specific operation and mibEntry is found
     * the error message of that response is included in the RuntimeException.
     *
     * @param operation             The operation performed
     * @param mibEntry              The mibEntry upon which the operation was performed
     * @throws RuntimeException     Thrown if no AnswerObject fulfills conditions for a successful message
     *                              for this specific operation
     */
    public void checkResponse(Operation operation, MibEntry mibEntry) throws RuntimeException {

        Optional<AnswerObject> answerObjectOptional = findSuccessAnswerObject(operation, mibEntry);
        if (!answerObjectOptional.isPresent()) {
            throw new RuntimeException(getErrorMessage(operation, mibEntry));
        }
    }

    /**
     * Checks if an success AnswerObject for a specific operation on a specific mibEntry exists in this AnswerObjects
     * instance.
     *
     * @param operation             The operation performed
     * @param mibEntry              The mibEntry upon with the operation was performed
     * @return                      Optional AnswerObject
     */
    private Optional<AnswerObject> findSuccessAnswerObject(Operation operation, MibEntry mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.isSuccessAnswerObject(operation, mibEntry))
                .findFirst();
    }

    private String getErrorMessage(Operation operation, MibEntry mibEntry) {
        String errorMessage;
        Optional<AnswerObject> answerObjectOptional = findErrorAnswerObject(operation, mibEntry);
        if (!answerObjectOptional.isPresent()) {
            errorMessage = String.format("Failed to get error message. No AnswerObject found corresponding to operation" +
                    " 'error' and mibEntry %s. Assure responses are returned synchronously", mibEntry.getMibEntryString());
        } else {
            errorMessage = answerObjectOptional.map(answerObject -> String.format("Error: %s", answerObject.getError())).orElse("Error message was null");
        }
        return errorMessage;
    }

    /**
     * Checks if an error AnswerObject for a specific operation on a specific mibEntry exists in this AnswerObjects
     * instance.
     *
     * @param operation             The operation performed, that did not succeed
     * @param mibEntry              The mibEntry upon with the operation was performed
     * @return                      Optional AnswerObject
     */
    private Optional<AnswerObject> findErrorAnswerObject(Operation operation, MibEntry mibEntry) {
        return answerObjects.stream()
                .filter(answerObject -> answerObject.isErrorAnswerObject(operation, mibEntry))
                .findFirst();
    }
}
