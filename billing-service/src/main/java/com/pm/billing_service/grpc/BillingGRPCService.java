package com.pm.billing_service.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService    
public class BillingGRPCService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(
            BillingGRPCService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
            StreamObserver<BillingResponse> responseObserver) {
        log.info("CreateBillingAccount request received: {}", billingRequest.toString());
        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("1123")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted(); // Can send many responses as want until oncomplete is called
        log.info("CreateBillingAccount response sent: {}", response.toString());
    }
}
