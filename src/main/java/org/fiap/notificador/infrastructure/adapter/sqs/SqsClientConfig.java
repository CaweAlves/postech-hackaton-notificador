package org.fiap.notificador.infrastructure.adapter.sqs;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.fiap.notificador.infrastructure.adapter.sqs.consumer.SqsConsumerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class SqsClientConfig {

    private final SqsConsumerProperties props;

    @Bean
    public SqsClient sqsClient() {
        SqsClientBuilder builder = SqsClient.builder()
                .region(Region.of(props.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(props.getAccessKey(), props.getSecretKey())));

        if (props.getEndpoint() != null && !props.getEndpoint().isBlank()) {
            builder = builder.endpointOverride(URI.create(props.getEndpoint()));
        }

        return builder.build();
    }
}
