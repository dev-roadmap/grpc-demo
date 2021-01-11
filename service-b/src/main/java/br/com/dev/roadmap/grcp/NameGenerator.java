package br.com.dev.roadmap.grcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameGenerator {
    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);

    public class FullName {
        private final String firstName;
        private final String lastName;

        private FullName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String firstName() {
            return firstName;
        }

        public String lastName() {
            return lastName;
        }

    }

    private List<FullName> names;
    private Random random;

    public NameGenerator() {
        names = new ArrayList<>();
        random = new Random();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(GrpcServiceB.class.getResourceAsStream("/names.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    String[] tokens = line.split("\\s");
                    if (tokens.length == 2) {
                        names.add(new FullName(tokens[0], tokens[1]));
                    } else {
                        logger.info("Invalid line: {}", line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FullName next() {
        return names.get(random.nextInt(names.size()));
    }
}
