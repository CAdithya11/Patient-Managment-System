package com.pm.patientService.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring service (a singleton bean)
public class BillingServiceGrpcClient {

    // This is the blocking stub that will be used to call remote gRPC methods.
    // A "blocking stub" waits for a response before continuing.
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // Create a logger instance for this class to output logs in the console.
    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    public BillingServiceGrpcClient(
        @Value("${billing.service.address.localhost}") String serverAddress, // e.g., "localhost" or "127.0.0.1"
        @Value("${billing.service.grpc.port}") int serverPort                 // e.g., 6565
    ) {
        // Log to the console to show that we’re trying to connect to the gRPC server.
        log.info("Connecting to the GRPC service at {}:{}", serverAddress, serverPort);

        // Build a channel to the remote gRPC server.
        // `usePlaintext()` means no encryption (for local development/testing).
        // This channel is like opening a connection to another computer.
        ManagedChannel channel = ManagedChannelBuilder
                                    .forAddress(serverAddress, serverPort) // host and port
                                    .usePlaintext()                         // no TLS/SSL encryption
                                    .build();                               // build the connection

        // Create a stub (client) using the channel. This will let us call remote methods.
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId,String name, String email){
        BillingRequest billingRequest = BillingRequest.newBuilder()
        .setPatientId(patientId)
        .setName(name)
        .setEmail(email)
        .build();
        
        BillingResponse billingResponse = blockingStub.createBillingAccount(billingRequest);
        log.info("Billing response recieved from grpc : {}", billingResponse);
        return billingResponse;
    }

    
}
