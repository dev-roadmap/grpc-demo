package br.com.dev.roadmap.grcp;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServiceA {
    private static final Logger logger = LoggerFactory.getLogger(GrpcServiceA.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
        Server server = ServerBuilder.forPort(port)
                                     .addService(new HelloServiceImpl()).build();
        logger.info("Starting server on {}", port);
        server.start();
        server.awaitTermination();
    }
}
