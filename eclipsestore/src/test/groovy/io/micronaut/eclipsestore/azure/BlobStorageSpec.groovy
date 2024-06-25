package io.micronaut.eclipsestore.azure

import com.azure.core.credential.AzureNamedKeyCredential
import com.azure.storage.blob.BlobServiceClient
import com.azure.storage.blob.BlobServiceClientBuilder
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.core.util.StringUtils
import io.micronaut.eclipsestore.BaseStorageSpec
import io.micronaut.eclipsestore.testutils.AzureBlobLocal
import io.micronaut.eclipsestore.testutils.MinioLocal
import io.micronaut.inject.qualifiers.Qualifiers
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation
import org.eclipse.store.storage.types.StorageManager
import org.testcontainers.DockerClientFactory
import software.amazon.awssdk.services.s3.S3Client
import spock.lang.AutoCleanup
import spock.lang.Requires
import spock.lang.Shared

@Requires({ DockerClientFactory.instance().isDockerAvailable() })
@MicronautTest
@Property(name = "eclipsestore.blob.storage.foo.container-name", value = BlobStorageSpec.CONTAINER_NAME)
@Property(name = "eclipsestore.blob.storage.foo.root-class", value = 'io.micronaut.eclipsestore.BaseStorageSpec$Root')
@Property(name = "micronaut.metrics.enabled", value = StringUtils.FALSE)
@Property(name = "spec.type", value = "storage")
@Property(name = "spec.name", value = "BlobStorageSpec")
class BlobStorageSpec extends BaseStorageSpec {

    static final String CONTAINER_NAME = "devstoreaccount1"

    @Shared
    @AutoCleanup
    AzureBlobLocal azureBlobLocal = new AzureBlobLocal()

    @Inject
    BeanContext beanContext

    @Shared
    @Inject
    BlobServiceClient blobServiceClient

    @Factory
    @io.micronaut.context.annotation.Requires(property = "spec.name", value = "BlobStorageSpec")
    static class BlobServiceClientMock {

        @Replaces(BlobServiceClient)
        @Singleton
        BlobServiceClient buildClient() {
            BlobServiceClient client = new BlobServiceClientBuilder()
                    .endpoint(AzureBlobLocal.properties.get("endpoint").toString())
                    .connectionString(AzureBlobLocal.properties.get("connection-string").toString())
                    .buildClient()
            client.createBlobContainer(AzureBlobLocal.properties.get("blob-container").toString())
            return client
        }
    }

    @Inject
    CustomerRepository customerRepository

    void "expected beans are created"() {
        blobServiceClient.getProperties()
        expect:
        beanContext.containsBean(BlobStorageConfigurationProvider, Qualifiers.byName("foo"))
        beanContext.containsBean(BlobServiceClient)
        beanContext.containsBean(EmbeddedStorageFoundation, Qualifiers.byName("foo"))
        beanContext.getBeansOfType(StorageManager).size() == 1

        //when:
        //customerRepository.updateName("foo")

        //then:
        //customerRepository.name() == "foo"

        //when:
        //beanContext.getBean(CustomerRepository)

        //then:
        //noExceptionThrown()
    }
}
