# Python Docs Disabled Test Inventory

This file tracks Python docs examples of Micronaut EclipseStore that are present but disabled, or that deviate from the
Java example because the direct port currently fails compilation or at runtime (Python compiler gaps). Use it as the
bug-fixing task list for the final migration wave.

## Reconciliation

- Last generated active `@Disabled` count: 1.
- Last generated command: `rg -n "@Disabled\(" test-suite-python/src/test/python`.
- Last full-suite command: `./gradlew :test-suite-python:test -Ppython-ci`.
- Last full-suite result: build successful, 3 tests executed (3 test classes), 1 skipped (core 5.2.3 / micronaut-build 8.1.2).

## Migration Rules

- Methods that implement a Java interface keep the Java (camelCase) name; other methods and constructor parameters are
  snake_case (`find_by_id`, `root_provider`). Model attributes whose names are JSON keys keep the Java name (`firstName`).
- Models are `@dataclass` classes (`Customer`, `CustomerSave`, `Data`), the EclipseStore root instance uses
  `field(default_factory=dict)` for its map like the Kotlin data class.
- The Java `TaskExecutors.BLOCKING` / `HttpStatus.NO_CONTENT` constants are used as annotation members the same way.
- `XThreads.executeSynchronized(...)` receives a nested Python function; `Optional<Customer>` is `Customer | None`.
- Imported classes are used as runtime type arguments (`ApplicationContext.run(EmbeddedServer, ...)`, `createBean(HttpClient, url)`,
  `getBean(CounterService)`, `client.exchange(request, Customer)`); `getBean` of a Python class returns the Python object.
- The root instance is `@Introspected` so that the generated Java class is persistable (only its property fields are
  persistent, the GraalPy object is transient).
- `CacheTest` is a `@MicronautTest(environments=["cache"])` with the storage directory supplied through `@Property`
  instead of a manual `ApplicationContext.run(...)`; `PersistentCacheTest` has to restart the application and keeps the
  nested `ApplicationContext.run(EmbeddedServer, config, "cachepersist")` calls of the Java test (they work here).

## Active `@Disabled` Tests

| Test | Reason |
| --- | --- |
| `CustomerControllerTest.test_crud` | EclipseStore instantiates the root class reflectively (`InstantiationUtils.instantiate(rootClass)`), which calls the generated no-arg constructor. For an all-default `@dataclass` that constructor creates the object *in Python* (so that the dataclass defaults apply) and copies the fields to Java, so the root is Python-owned: Python code that receives it as a Java object (`StorageManager.root()`, `RootProvider.root()`) gets a fresh converted `HashMap` copy of `customers` on every access (`type(root().customers)` is `polyglot.Java_java.util.HashMap_generated`, and it is `{}` again right after `customers[id] = customer`). The entries added from Python are therefore neither stored nor found: the first `GET /customer/{id}` after the `POST` fails with `HttpClientResponseException: Not Found` for both repository implementations. Objects created through the field-assigning constructors of the generated class are Java-owned and their collections are the live state, as documented for core 5.2.3; the no-arg constructor should produce a Java-owned object too (assign the constant / `default_factory` defaults to the Java fields). |

## Commented Unsupported Snippet Ports

None.

## Intentionally Unsupported Snippet Targets

None.
