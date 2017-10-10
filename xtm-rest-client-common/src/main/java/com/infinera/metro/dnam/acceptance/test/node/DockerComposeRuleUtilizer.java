//package com.infinera.metro.dnam.acceptance.test.node;
//
//import com.palantir.docker.compose.configuration.ShutdownStrategy;
//import com.palantir.docker.compose.connection.DockerMachine;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public abstract class DockerComposeRuleUtilizer {
//    protected static ShutdownStrategy shutdownStrategy;
//    protected static DockerMachine dockerMachine;
//    protected static String[] dockerComposeFilesArray;
//
//    protected static void setDockerComposeRuleSettings() {
//        setShutDownStrategy();
//        setDockerMachine();
//
//        logCommandLineParameters();
//    }
//
//    private static void setDockerMachine() {
//        if(System.getProperty("dockerMachineHost") == null || System.getProperty("dockerMachineSshDirectory") == null) {
//            dockerMachine = DockerMachine.localMachine().build();
//        } else {
//            dockerMachine = DockerMachine.remoteMachine()
//                .host(System.getProperty("dockerMachineHost"))
//                .withTLS(System.getProperty("dockerMachineSshDirectory"))
//                .build();
//        }
//    }
//
//    private static void setShutDownStrategy() {
//        final String shutdownStrategyFromCommandLine = (System.getProperty("shutdownStrategy") != null) ? System.getProperty("shutdownStrategy") : "";
//        switch (shutdownStrategyFromCommandLine) {
//            case "SKIP":
//                shutdownStrategy = ShutdownStrategy.SKIP;
//                break;
//            case "GRACEFUL":
//                shutdownStrategy = ShutdownStrategy.GRACEFUL;
//                break;
//            case "KILL_DOWN":
//                shutdownStrategy = ShutdownStrategy.GRACEFUL;
//                break;
//            default:
//                shutdownStrategy = ShutdownStrategy.GRACEFUL;
//        }
//    }
//
//    private static void logCommandLineParameters() {
//        log.info("System.getProperty(\"shutdownStrategy\"): {}", System.getProperty("shutdownStrategy"));
//        log.info("System.getProperty(\"dockerMachineHost\"): {}", System.getProperty("dockerMachineHost"));
//        log.info("System.getProperty(\"dockerMachineSshDirectory\"): {}", System.getProperty("dockerMachineSshDirectory"));
//    }
//}
