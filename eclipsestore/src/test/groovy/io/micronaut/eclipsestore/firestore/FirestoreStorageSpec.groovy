package io.micronaut.eclipsestore.firestore

import com.google.cloud.firestore.Firestore
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.*
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.util.StringUtils
import io.micronaut.eclipsestore.BaseStorageSpec
import io.micronaut.eclipsestore.testutils.FirestoreLocal
import io.micronaut.inject.qualifiers.Qualifiers
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation
import org.eclipse.store.storage.types.StorageManager
import org.testcontainers.DockerClientFactory
import spock.lang.AutoCleanup
import spock.lang.Shared

@spock.lang.Requires({ DockerClientFactory.instance().isDockerAvailable() })
@MicronautTest
@Property(name = "eclipsestore.firestore.storage.foo.logical-directory", value = FirestoreStorageSpec.LOGICAL_DIRECTORY)
@Property(name = "eclipsestore.firestore.storage.foo.root-class", value = 'io.micronaut.eclipsestore.BaseStorageSpec$Root')
@Property(name = "micronaut.metrics.enabled", value = StringUtils.FALSE)
@Property(name = "spec.type", value = "storage")
@Property(name = "spec.name", value = "FirestoreStorageSpec")
class FirestoreStorageSpec extends BaseStorageSpec implements TestPropertyProvider {

    static final String LOGICAL_DIRECTORY = "eclipsestorefoo"

    @Shared
    @AutoCleanup
    FirestoreLocal firestoreLocal = new FirestoreLocal()

    @Inject
    BeanContext beanContext

    @Inject
    @Shared
    Firestore client

    @Factory
    @Requires(property = "spec.name", value = "FirestoreStorageSpec")
    static class FireStoreClient {

        @Replaces(Firestore)
        @Singleton
        Firestore buildClient() {
            FirestoreLocal.firestoreClient()
        }
    }

    @Inject
    CustomerRepository customerRepository

    @NonNull
    @Override
    Map<String, String> getProperties() {
        return firestoreLocal.getProperties();
    }

    void "expected beans are created"() {
        expect:
        beanContext.containsBean(FirestoreStorageConfigurationProvider, Qualifiers.byName("foo"))
        !beanContext.containsBean(Firestore, Qualifiers.byName("foo"))
        beanContext.containsBean(Firestore)
        beanContext.containsBean(EmbeddedStorageFoundation, Qualifiers.byName("foo"))
        beanContext.getBeansOfType(StorageManager).size() == 1

        when:
        customerRepository.updateName("foo")

        then:
        customerRepository.name() == "foo"

        when:
        beanContext.getBean(CustomerRepository)

        then:
        noExceptionThrown()
    }
}
