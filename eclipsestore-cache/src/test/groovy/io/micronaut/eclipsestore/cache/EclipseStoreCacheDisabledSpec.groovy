package io.micronaut.eclipsestore.cache

import io.micronaut.context.ApplicationContext
import io.micronaut.core.util.StringUtils
import spock.lang.Specification

class EclipseStoreCacheDisabledSpec extends Specification {

    void "you can disable cache by setting eclipsestore.cache.enabled to false"() {
        given:
        ApplicationContext context = ApplicationContext.run(
                ['eclipsestore.cache.enabled': StringUtils.FALSE])

        expect:
        !context.containsBean(CacheConfiguration.class)

        cleanup:
        context.close()
    }
}
