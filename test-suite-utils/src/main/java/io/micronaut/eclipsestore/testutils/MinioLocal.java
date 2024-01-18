package io.micronaut.eclipsestore.testutils;

import org.testcontainers.containers.MinIOContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class MinioLocal extends MinIOContainer {

    private static final String ACCESS = "minio";
    private static final String SECRET = "miniotestkey";

    public MinioLocal() {
        super("minio/minio:latest");
        this.withUserName(ACCESS);
        this.withPassword(SECRET);
    }

    public Map<String, Object> getProperties() {
        if (!isRunning()) {
            start();
        }
        return Map.of(
            "aws.access-key-id", ACCESS,
            "aws.secret-key", SECRET,
            "aws.region", "us-east-1",
            "aws.services.s3.endpoint-override", getS3URL() + "/"
        );
    }

    @SuppressWarnings("java:S6437")
    public static S3Client s3Client(S3ConfigurationProperties s3Config) throws URISyntaxException {
        S3Client client = S3Client.builder()
            .endpointOverride(new URI(s3Config.getS3Configuration().getEndpointOverride()))
            .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS, SECRET)))
            .region(Region.of(s3Config.getRegion()))
            .serviceConfiguration(b -> b  // Required for minio
                .checksumValidationEnabled(false)
                .pathStyleAccessEnabled(true)
            )
            .build();
        if (client.listBuckets().buckets().stream().map(Bucket::name).noneMatch(s3Config.getBucketName()::equals)) {
            client.createBucket(b -> b.bucket(s3Config.getBucketName()));
        }
        return client;
    }
}
