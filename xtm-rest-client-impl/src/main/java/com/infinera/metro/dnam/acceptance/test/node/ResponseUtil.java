package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.MibEntry;
import com.infinera.metro.dnam.acceptance.test.mib.Operation;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObject;
import com.infinera.metro.dnam.acceptance.test.node.dto.AnswerObjects;

import java.util.Optional;

//TODO: Move this functionality to AnswerObjects class?
public class ResponseUtil {

    /** set, get responses **/

    public static void checkSetResponse(AnswerObjects answerObjects, MibEntry mibEntry) throws RuntimeException {
        checkResponse(answerObjects, Operation.CREATE.SET, mibEntry);
    }

    public static void checkGetResponse(AnswerObjects answerObjects, MibEntry mibEntry) {
        checkResponse(answerObjects, Operation.GET, mibEntry);
    }

    private static void checkResponse(AnswerObjects answerObjects, Operation operation, MibEntry mibEntry) throws RuntimeException {
        Optional<AnswerObject> answerObjectOptional = answerObjects.getAnswerObject(operation, mibEntry);
        if (answerObjectOptional.isPresent() == false || answerObjectOptional.get().isSuccess() == false) {
            throw new RuntimeException(getErrorMessage(answerObjects, mibEntry));
        }
    }

    private static String getErrorMessage(AnswerObjects answerObjects, MibEntry mibEntry) {
        String errorMessage = "";
        Optional<AnswerObject> answerObjectOptional = answerObjects.getErrorAnswerObject(mibEntry);
        if (answerObjectOptional.isPresent() == false) {
            errorMessage = String.format("Failed to get error message. No AnswerObject found corresponding to operation 'error' and mibEntry %s. Assure responses are returned synchronously", mibEntry.getMibEntryString());
        } else {
            errorMessage = String.format("Error: %s", answerObjectOptional.get().getError());
        }
        return errorMessage;
    }

    /** create response **/

    public static void checkCreateResponse(AnswerObjects answerObjects, MibEntry mibEntry) throws RuntimeException {
        Optional<AnswerObject> answerObjectOptional = answerObjects.getAnswerObject(Operation.CREATE); //Create response has no entry set in R-attributes
        if(answerObjectOptional.isPresent() == false || answerObjectOptional.get().isSuccess() == false ||
                answerObjectOptional.get().getAttributeObject(mibEntry.getMibEntryString()).isPresent() == false) {
            throw new RuntimeException(getCreateErrorMessage(answerObjects, mibEntry));
        }
    }

    private static String getCreateErrorMessage(AnswerObjects answerObjects, MibEntry mibEntry) {
        String errorMessage = "";
        Optional<AnswerObject> answerObjectOptional = answerObjects.getCreateErrorAnswerObject(mibEntry);
        if (answerObjectOptional.isPresent() == false) {
            errorMessage = String.format("Failed to get error message. No AnswerObject found corresponding to operation 'error' with AttributeObject.name=%s. Assure responses are returned synchronously", mibEntry.getMibEntryString());
        } else {
            errorMessage = String.format("Error: %s", answerObjectOptional.get().getError());
        }
        return errorMessage;
    }

}
