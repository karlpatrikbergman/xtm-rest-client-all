package com.infinera.metro.dnam.acceptance.test.node;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinera.metro.dnam.acceptance.test.node.mib.entry.MibEntry;
import com.infinera.metro.dnam.acceptance.test.node.mib.OperationType;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/*
Answer object / _RFLAGS
-----------------------
    Members:
    R                   rFlags              string
    A                   aFlags              string
    I                   queryID             number
    S                   success             bool
    E                   error               string
    M                   module              string
    G                   groupOrTable        string
    N                   entry               string
    O                   operation           string
    Q                   queryString         string
    T                   attribute [ ]       array
    P                   incPropMask         number
    C                   excPropMask         number
    Y                   incAttrPropMask     number
    V                   excAttrPropMask     number
    U                   subRack             number
    L                   slot                number
    K                   links [ ]           array
    F                   Only to be used for extremely large datasets, Fast mode gives reduced answer
    J                   For debugging purposes only, returns JSON with linebreaks and tabs
    X                   Include all of the above except F and J

Returned when checking every checkbox in JSONRequestMaker
OBS! Shows only first element in attribute array!
{
  "R": "RAISEMGNOQPCYVULTBJK",
  "A": "AVNDHPUIMJOSE",
  "I": 0,
  "S": true,
  "B": false,       //WHAT IS THIS? NOT DESCRIBED IN DOCUMENT??
  "M": "eq",
  "G": "board",
  "N": "tp10g:1:2",
  "O": "GET",
  "Q": "_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE",
  "T": [
    {
      "A": "index",
      "V": "10102",
      "N": "equipmentBoardIndex",
      "D": "Index",
      "H": "An arbitrary index assigned to each\nboard entry.\n\n",
      "P": 134217729,
      "I": 0,
      "R": 0,
      "M": 4294967295,
      "J": 3,
      "O": 0
    }
  ]
}

Skipped in response for the later case are
    E   error   Maybe because there was no error?
    P   incPropMask
    C   excAttrPropMask
    Y   incAttrPropMask     number
    V   excAttrPropMask     number
    U   subRack             number
    L   slot                number
    K   links [ ]           array

*/
@AllArgsConstructor(access = AccessLevel.PUBLIC) //Needed by Orika
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //Needed by Hibernate and Jackson
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value //Needs jackson > 2.8
@Builder
public class AnswerObject implements Serializable {
    private String rFlags;          //R
    private String aFlags;          //A
    private int queryID;            //I
    private boolean success;        //S
    private String error;           //E
    private String module;          //M
    private String groupOrTable;    //G
    private String entry;           //N
    private String operation;       //O
    private String queryString;     //Q
    private List<AttributeObject> attributeObjectList; //T

    public Optional<AttributeObject> getAttributeObject(String name) {
        assert attributeObjectList != null;
        return attributeObjectList.stream()
                .filter(attributeObject -> attributeObject.getName().equals(name))
                .findFirst();
    }

    public Optional<AttributeObject> getAttributeObjectByAlias(String alias) {
        assert attributeObjectList != null;
        return attributeObjectList.stream()
            .filter(attributeObject -> attributeObject.getAlias().equals(alias))
            .findFirst();
    }

    /**
     * Checks if this AnswerObject is the SUCCESS response to a performed operation
     * For get, set and configure operations the mibEntry is found in field AnswerObject.entry
     * For create and delete operations the mibEntry is found in field AttributeObject.name
     *
     * @param operationType     The performed operation, create, get, configure, delete
     * @param mibEntry      The mibEntry for the performed operation
     * @return
     */
    public boolean isSuccessAnswerObject(OperationType operationType, MibEntry mibEntry) {
        final boolean mibEntryFound = mibEntryEqualsAnswerObjectEntryFieldOrAttributeObjectNameField(operationType, mibEntry);
        return isSuccess() && getModule().equals(mibEntry.getModuleType().getValue()) &&
                getGroupOrTable().equals(mibEntry.getGroupOrTableType().getValue()) && mibEntryFound;
    }

    /**
     * Checks if this AnswerObject is the ERROR response to a performed operation.
     * For get, set and configure operations the mibEntry is found in field AnswerObject.entry
     * For create and delete operations the mibEntry is found in field AttributeObject.name
     *
     * IMPORTANT NOTE:
     * If the operation initially performed was get/set/delete/configure, and it failed, the attribute
     * AnswerObject.operation will be "error"and NOT "get/set/delete/configure".
     *
     * @param operationType     The performed operation, create, get, configure, delete
     * @param mibEntry      The mibEntry for the performed operation
     * @return
     */
    @SuppressWarnings("UnnecessaryLocalVariable") //Good to have when debugging
    public boolean isErrorAnswerObject(OperationType operationType, MibEntry mibEntry) {
        final boolean mibEntryFound = mibEntryEqualsAnswerObjectEntryFieldOrAttributeObjectNameField(operationType, mibEntry);
        final boolean result = !isSuccess() && getOperation().equals("error") && getModule().equals(mibEntry.getModuleType().getValue()) &&
                getGroupOrTable().equals(mibEntry.getGroupOrTableType().getValue()) && mibEntryFound;
        return result;
    }

    private boolean mibEntryEqualsAnswerObjectEntryFieldOrAttributeObjectNameField(OperationType operationType, MibEntry mibEntry) {
        boolean result;
        if(operationType.equals(OperationType.CREATE) || operationType.equals(OperationType.DELETE)) {
            result = containsAttributeObjectWithNameEqualToMibEntry(mibEntry);
        } else {
            result = getEntry().equals(mibEntry.getMibEntryString());
        }
        return result;
    }

    private boolean containsAttributeObjectWithNameEqualToMibEntry(MibEntry mibEntry) {
        return getAttributeObject(mibEntry.getMibEntryString()).isPresent();
    }
}
