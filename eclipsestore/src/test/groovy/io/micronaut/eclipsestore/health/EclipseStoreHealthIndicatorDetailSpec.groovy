package io.micronaut.eclipsestore.health

import io.micronaut.context.ApplicationContext
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir

class EclipseStoreHealthIndicatorDetailSpec extends Specification {

    @TempDir
    @Shared
    File tempDir

    void "details are shown for the health endpoint if requested"() {
        given:
        EmbeddedServer server = ApplicationContext.run(EmbeddedServer, [
                "eclipsestore.storage.blue.root-class": BlueFlowers.class.name,
                "eclipsestore.storage.blue.storage-directory": tempDir.absolutePath,
                "endpoints.health.details-visible": "ANONYMOUS",
        ])
        BlockingHttpClient client = server.applicationContext.createBean(HttpClient, server.URL).toBlocking()

        when:
        Map<String, Object> result = client.retrieve("/health", Map)

        then:
        with(result.details.'eclipsestore.blue') {
            status == "UP"
            details == [startingUp:false, running:true, active:true, acceptingTasks:true, shuttingDown:false, shutdown:false]
        }

        cleanup:
        client?.close()
        server?.close()
    }

    @Introspected
    static class BlueFlowers {
        List<String> flowers = []
    }
}
