package br.com.dev.roadmap.grcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dev.roadmap.grcp.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void hello(
            HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        logger.info("Request received: {}", request);

        HelloResponse response = HelloResponse.newBuilder()
                                              .setGreeting(String.format("Hello, %s %s!", request.getFirstName(), request.getLastName()))
                                              .build();
        logger.info("Sending response: {}", response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
