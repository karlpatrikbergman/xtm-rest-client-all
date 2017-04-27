package com.infinera.metro.dnam.acceptance.test.node;

import com.infinera.metro.dnam.acceptance.test.mib.*;
import com.infinera.metro.dnam.acceptance.test.xtmrest.XtmRestMibUtil;
import org.springframework.http.ResponseEntity;

class NodeRestClient {

    private final NodeConnection nodeConnection;
    private final XtmRestMibUtil xtmRestMibUtil;

    NodeRestClient(NodeConnection nodeConnection) {
        this.nodeConnection = nodeConnection;
        this.xtmRestMibUtil = XtmRestMibUtil.INSTANCE;
    }

    ResponseEntity<String> createBoard(BoardEntry boardEntry) {
        return performEquipmentBoardRequest(boardEntry, Command.CREATE_JSON);
    }

    ResponseEntity<String> getBoard(BoardEntry boardEntry) {
        return performEquipmentBoardRequest(boardEntry, Command.GET_JSON);
    }

    ResponseEntity<String> deleteBoard(BoardEntry boardEntry) {
        return performEquipmentBoardRequest(boardEntry, Command.DELETE_JSON);
    }

    ResponseEntity<String> listBoards() {
        return performEquipmentBoardRequest(Command.LIST_JSON);
    }

    private ResponseEntity<String> performEquipmentBoardRequest(MibEntry mibEntry, Command command) {
        final String mibPathAndOperation = xtmRestMibUtil.mibRestUrl(mibEntry, command);
        return nodeConnection.performRestAction(mibPathAndOperation + "?_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE");
    }

    //TODO: Make more generic
//    private ResponseEntity<String> performEquipmentBoardRequest(BoardEntry boardEntry, Command command) {
//        final String mibPath = xtmRestMibUtil.mibRestUrl(Module.EQUIPMENT, GroupOrTable.BOARD, boardEntry, command);
//        return nodeConnection.performRestAction(mibPath + "?_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE");
//    }

    private ResponseEntity<String> performEquipmentBoardRequest(Command command) {
        final String mibPath = xtmRestMibUtil.mibRestUrl(Module.EQUIPMENT, GroupOrTable.BOARD, command);
        return nodeConnection.performRestAction(mibPath + "?_RFLAGS_=RAISEMGNOQPCYVULTBJK&_AFLAGS_=AVNDHPUIMJOSE");
    }
}
