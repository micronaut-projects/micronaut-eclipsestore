import uuid

from micronaut.context import ApplicationContext
from micronaut.runtime.server import EmbeddedServer
from micronaut.test.extensions.junit5.annotation import MicronautTest
from org.junit.jupiter.api import Test

from .CounterService import CounterService


@MicronautTest
class PersistentCacheTest:

    @Test
    def cache_persists_over_restarts(self) -> None:
        config = {"storageDirectory": "build/eclipsestore-cache-" + str(uuid.uuid4())}
        # When we create the app, and use a cached method
        server: EmbeddedServer = ApplicationContext.run(EmbeddedServer, config, "cachepersist")
        try:
            counter = server.getApplicationContext().getBean(CounterService)
            counter.set_count("Tim", 1337)
            count = counter.current_count("Tim")
            assert count == 1337
            counter.set_count("Tim", 666)
        finally:
            server.close()

        # Then restarting the app with the same storage location, the value is still cached
        server = ApplicationContext.run(EmbeddedServer, config, "cachepersist")
        try:
            counter = server.getApplicationContext().getBean(CounterService)
            count = counter.current_count("Tim")
            assert count == 666
        finally:
            server.close()
