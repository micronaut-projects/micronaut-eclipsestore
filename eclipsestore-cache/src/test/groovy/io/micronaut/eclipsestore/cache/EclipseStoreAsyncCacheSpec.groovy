package io.micronaut.eclipsestore.cache

import io.micronaut.cache.tck.AbstractAsyncCacheSpec
import io.micronaut.context.ApplicationContext
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.TempDir

class EclipseStoreAsyncCacheSpec extends AbstractAsyncCacheSpec {

    @TempDir
    @Shared
    File tempDir

    @AutoCleanup
    ApplicationContext applicationContext

    @Override
    ApplicationContext createApplicationContext() {
        this.applicationContext = ApplicationContext.run(
                'eclipsestore.cache.counter.statistics-enabled': "true",
                'eclipsestore.cache.counter2.statistics-enabled': "true",
                'eclipsestore.cache.test.statistics-enabled': "true",
        )
    }
}
