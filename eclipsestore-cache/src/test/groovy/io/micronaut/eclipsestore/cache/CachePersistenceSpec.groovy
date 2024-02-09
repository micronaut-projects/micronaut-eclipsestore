package io.micronaut.eclipsestore.cache

import io.micronaut.cache.SyncCache
import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.inject.qualifiers.Qualifiers
import spock.lang.Specification

class CachePersistenceSpec extends Specification {

    def "cache remains over restarts"() {
        given:
        def properties = [
                "eclipsestore.storage.cachestore.storage-directory": "build/eclipsestore-${UUID.randomUUID().toString()}",
                "eclipsestore.cache.backed.key-type"               : "java.lang.Integer",
                "eclipsestore.cache.backed.value-type"             : "java.lang.String",
                "eclipsestore.cache.backed.statistics-enabled"     : "true",
                "eclipsestore.cache.backed.storage"                : "cachestore",
                "eclipsestore.cache.unbacked.statistics-enabled"   : "true",
        ]

        def applicationContext = ApplicationContext.run(properties)

        when: 'we cache some data in the backed cache'
        def backedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("backed"))
        backedCache.put(1, "This is some data to cache")

        and: 'we cache some data in the unbacked cache'
        def unbackedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("unbacked"))
        unbackedCache.put(2, "And here is some more data")

        then:
        backedCache.get(1, Argument.of(String)).get() == "This is some data to cache"
        unbackedCache.get(2, Argument.of(String)).get() == "And here is some more data"

        when: 'we restart the app with the same backing data store'
        applicationContext.stop()
        applicationContext = ApplicationContext.run(properties)
        backedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("backed"))
        unbackedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("unbacked"))

        then: 'the data is still there in the backed cache'
        backedCache.get(1, Argument.of(String)).get() == "This is some data to cache"

        and: 'it is not in the unbacked cache'
        !unbackedCache.get(2, Argument.of(String)).present

        cleanup:
        applicationContext.stop()
    }

    def "async cache remains over restarts"() {
        given:
        def properties = [
                "eclipsestore.storage.cachestore.storage-directory": "build/eclipsestore-${UUID.randomUUID().toString()}",
                "eclipsestore.cache.backed.key-type"               : "java.lang.Integer",
                "eclipsestore.cache.backed.value-type"             : "java.lang.String",
                "eclipsestore.cache.backed.statistics-enabled"     : "true",
                "eclipsestore.cache.backed.storage"                : "cachestore",
                "eclipsestore.cache.unbacked.statistics-enabled"   : "true",
        ]

        def applicationContext = ApplicationContext.run(properties)

        when: 'we cache some data in the backed cache'
        def backedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("backed")).async()
        backedCache.put(1, "This is some data to cache").get()

        and: 'we cache some data in the unbacked cache'
        def unbackedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("unbacked")).async()
        unbackedCache.put(2, "And here is some more data").get()

        then:
        backedCache.get(1, Argument.of(String)).get().get() == "This is some data to cache"
        unbackedCache.get(2, Argument.of(String)).get().get() == "And here is some more data"

        when: 'we restart the app with the same backing data store'
        applicationContext.stop()
        applicationContext = ApplicationContext.run(properties)
        backedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("backed")).async()
        unbackedCache = applicationContext.getBean(SyncCache.class, Qualifiers.byName("unbacked")).async()

        then: 'the data is still there in the backed cache'
        backedCache.get(1, Argument.of(String)).get().get() == "This is some data to cache"

        and: 'it is not in the unbacked cache'
        !unbackedCache.get(2, Argument.of(String)).get().present

        cleanup:
        applicationContext.stop()
    }
}
