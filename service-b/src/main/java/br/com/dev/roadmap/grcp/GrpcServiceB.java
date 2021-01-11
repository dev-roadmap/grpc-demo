package br.com.dev.roadmap.grcp;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dev.roadmap.grcp.NameGenerator.FullName;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcServiceB {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServiceB.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;

        AtomicBoolean running = new AtomicBoolean(true);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                running.set(false);
            }
        });

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
                                                      .usePlaintext()
                                                      .build();

        NameGenerator generator = new NameGenerator();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        while (running.get()) {
            FullName name = generator.next();
            logger.info("Sending message....");
            HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                                                                 .setFirstName(name.firstName())
                                                                 .setLastName(name.lastName())
                                                                 .build());
            logger.info("Response: {}", helloResponse);
        }
        channel.shutdown();
        logger.info("Shutdown....");
    }
}
