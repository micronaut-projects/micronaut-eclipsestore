package io.micronaut.eclipsestore.dynamodb

import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Property
import io.micronaut.core.annotation.NonNull
import io.micronaut.eclipsestore.testutils.DynamoDbLocal
import io.micronaut.inject.qualifiers.Qualifiers
import io.micronaut.eclipsestore.BaseStorageSpec
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Property(name = "eclipsestore.dynamodb.storage.red.table-name", value = "redtable")
@Property(name = "eclipsestore.dynamodb.storage.red.root-class", value = 'io.micronaut.eclipsestore.BaseStorageSpec$Root')
@Property(name = "eclipsestore.dynamodb.storage.blue.table-name", value = "bluetable")
@Property(name = "eclipsestore.dynamodb.storage.blue.root-class", value = 'io.micronaut.eclipsestore.BaseStorageSpec$Root')
@Property(name = "aws.region", value = "us-east-1")
@MicronautTest(startApplication = false)
class DynamoDbStorageConfigurationProviderSpec extends Specification implements TestPropertyProvider {
    @Shared
    @AutoCleanup
    DynamoDbLocal dynamoDbLocal = new DynamoDbLocal()

    @NonNull
    @Override
    Map<String, String> getProperties() {
        dynamoDbLocal.getProperties() as Map<String, String>
    }

    @Inject
    BeanContext beanContext

    void "you can have multiple beans of type DynamoDbStorageConfigurationProvider"() {

        when:
        Collection<DynamoDbStorageConfigurationProvider> dynamoDbStorageConfigurationProviderCollection = beanContext.getBeansOfType(DynamoDbStorageConfigurationProvider)

        then:
        dynamoDbStorageConfigurationProviderCollection
        dynamoDbStorageConfigurationProviderCollection.size() == 2
        dynamoDbStorageConfigurationProviderCollection.any { it.tableName == 'redtable' && it.name == 'red' && it.rootClass == BaseStorageSpec.Root && it.dynamoDbClientName.isEmpty() }
        dynamoDbStorageConfigurationProviderCollection.any { it.tableName == 'bluetable' && it.name == 'blue' && it.rootClass == BaseStorageSpec.Root && it.dynamoDbClientName.isEmpty() }

        and:
        beanContext.getBeansOfType(EmbeddedStorageFoundation).size() == 2
        beanContext.containsBean(EmbeddedStorageFoundation, Qualifiers.byName("red"))
        beanContext.containsBean(EmbeddedStorageFoundation, Qualifiers.byName("blue"))
    }
}
