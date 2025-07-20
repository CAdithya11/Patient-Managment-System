package com.pm.billing_service.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BillingGRPCService extends BillingServiceImplBase {
    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
            io.grpc.stub.StreamObserver<BillingResponse> responseObserver) {

        System.out.println(">>> patientId: " + billingRequest.getPatientId());
        System.out.println(">>> name     : " + billingRequest.getName());
        System.out.println(">>> email    : " + billingRequest.getEmail());

        BillingResponse respone = BillingResponse.newBuilder()
                .setStatus("ACTIVE")
                .setAccountId("123")
                .build();
        responseObserver.onNext(respone); // Send a response from the Grpc Server back to the patient
        // If many response, send before complete with the help ON NEXT
        responseObserver.onCompleted(); // Send is complete and we are ending the cycle

    }
}
