# Java 8 Features — Complete Learning Guide

This guide explains **every major Java 8 feature** in simple words, with **no conceptual gaps**, practical examples, edge cases, and pointers to runnable code in this project.

---

## Table of Contents

1. [Why Java 8 Matters](#why-java-8-matters)
2. [Project Structure & How to Run](#project-structure--how-to-run)
3. [Lambda Expressions](#1-lambda-expressions)
4. [Functional Interfaces](#2-functional-interfaces)
5. [Method References](#3-method-references)
6. [Stream API](#4-stream-api)
7. [Parallel Streams](#5-parallel-streams)
8. [Optional](#6-optional)
9. [Default & Static Methods in Interfaces](#7-default--static-methods-in-interfaces)
10. [java.time API](#8-javatime-api)
11. [Collectors](#9-collectors)
12. [How Features Connect](#how-all-features-connect)
13. [Interview / Trap Checklist](#interview--trap-checklist)

---

## Why Java 8 Matters

Before Java 8, Java was mostly **object-oriented + imperative**:

- You passed behavior using **anonymous inner classes** (long, noisy).
- You looped collections manually (`for`, `Iterator`).
- Missing values were usually **`null`**.
- Date/time used **`Date`** and **`Calendar`** (confusing, mutable).

Java 8 introduced **functional-style programming** into the standard library:

| Feature | One-line meaning |
|--------|------------------|
| Lambda | Write behavior compactly |
| Functional interface | Contract for that behavior |
| Method reference | Shorthand for single-method call |
| Stream | Declarative data pipeline |
| Optional | Explicit “maybe no value” |
| Interface `default`/`static` | Evolve APIs safely |
| `java.time` | Modern date/time |
| Collectors | Turn streams into results |

---

## Project Structure & How to Run

```
src/java_8_features/
├── Java8FeaturesMasterDemo.java          # runs everything
├── lambda/
│   ├── LambdaExpressionsDemo.java
│   ├── LambdaCollectionsApiDemo.java
│   └── LambdaPitfallsEdgeCasesDemo.java
├── functional_interfaces/
│   ├── FunctionalInterfacesDemo.java
│   ├── PrimitiveFunctionalInterfacesDemo.java
│   └── BuiltInFunctionalInterfacesDemo.java
├── method_references/
│   ├── MethodReferencesDemo.java
│   ├── MethodReferenceInStreamsDemo.java
│   └── MethodReferencePitfallsDemo.java
├── streams/
│   ├── StreamApiDemo.java
│   ├── StreamShortCircuitDemo.java
│   ├── StreamLazyEvaluationDemo.java
│   ├── StreamEdgeCasesDemo.java
│   └── ParallelStreamsDemo.java
├── optional/
│   ├── OptionalDemo.java
│   ├── OptionalChainingEdgeCasesDemo.java
│   └── OptionalWithStreamsDemo.java
├── interface_enhancements/
│   ├── DefaultAndStaticMethodsDemo.java
│   ├── InterfaceConflictResolutionDemo.java
│   └── InterfaceInheritanceRulesDemo.java
├── date_time/
│   ├── JavaTimeApiDemo.java
│   ├── DateTimeParsingPitfallsDemo.java
│   └── DateTimeBusinessRulesDemo.java
└── collectors/
    ├── CollectorsDemo.java
    ├── CollectorsToMapEdgeCasesDemo.java
    └── CollectorsAdvancedScenariosDemo.java
```

**Run all demos:**

```bash
javac -d out -sourcepath src src/java_8_features/Java8FeaturesMasterDemo.java
java -cp out java_8_features.Java8FeaturesMasterDemo
```

In IntelliJ: open any class → run `main()`.

---

## 1. Lambda Expressions

### Simple definition

A **lambda** is a short block of code that implements a **functional interface** (one abstract method).

```java
// Before Java 8
Runnable r = new Runnable() {
    public void run() {
        System.out.println("Hi");
    }
};

// Java 8
Runnable r = () -> System.out.println("Hi");
```

### Syntax cheat sheet

| Form | Example |
|------|---------|
| No param | `() -> action()` |
| One param | `x -> x * 2` |
| Multiple params | `(a, b) -> a + b` |
| Block body | `(a, b) -> { int s = a + b; return s; }` |

### Rules (close all loopholes)

1. **Not a new type** — it is syntax for SAM interfaces.
2. **Effectively final** — outer local variables used inside lambda cannot be reassigned.
3. **`this`** inside lambda refers to the **enclosing class**, not the lambda.
4. **Checked exceptions** — must be handled inside lambda or wrapped.
5. **Not always faster** than loops; readability is often the win.

### Real use cases

- Sorting: `list.sort((a, b) -> Integer.compare(a.price, b.price))`
- Callbacks: `button.setOnAction(e -> save())`
- Collection ops: `list.forEach(item -> log(item))`
- Streams: `list.stream().filter(x -> x > 10)`

### Java 8 collection helpers (lambda-powered)

| Method | Purpose |
|--------|---------|
| `forEach` | act on each element |
| `removeIf` | remove matching elements safely |
| `replaceAll` | transform each element in-place |
| `computeIfAbsent` | cache / lazy create map value |

**Code:** `lambda/LambdaExpressionsDemo.java`, `LambdaCollectionsApiDemo.java`  
**Edge cases:** `lambda/LambdaPitfallsEdgeCasesDemo.java`

### Common traps

| Trap | What happens |
|------|----------------|
| Reassign variable used in lambda | Compile error |
| Same name for local and lambda parameter | Compile error (no shadowing allowed) |
| `map` returns `null` | Later `NullPointerException` |
| Use lambda where anonymous class needed (multiple methods) | Won’t compile |
| `forEach` when you need `break` | Can’t stop early — use `findFirst` or loop |

---

## 2. Functional Interfaces

### Simple definition

An interface with **exactly one abstract method** (SAM).  
`@FunctionalInterface` tells compiler to enforce that rule.

### Core built-ins (`java.util.function`)

| Interface | Signature | Use |
|-----------|-----------|-----|
| `Predicate<T>` | `T → boolean` | filter |
| `Function<T,R>` | `T → R` | map / transform |
| `Consumer<T>` | `T → void` | forEach / side effects |
| `Supplier<T>` | `() → T` | lazy creation |
| `BiFunction<T,U,R>` | `(T,U) → R` | combine two inputs |
| `UnaryOperator<T>` | `T → T` | replaceAll-style |
| `BinaryOperator<T>` | `(T,T) → T` | reduce |

### Primitive specialization (important loophole closed)

`Stream<Integer>` **boxes** ints. For heavy numeric work use:

- `IntStream`, `LongStream`, `DoubleStream`
- `IntPredicate`, `ToIntFunction`, etc.

**Code:** `functional_interfaces/FunctionalInterfacesDemo.java`, `PrimitiveFunctionalInterfacesDemo.java`, `BuiltInFunctionalInterfacesDemo.java`

### Predicate composition

```java
Predicate<User> adult = u -> u.age >= 18;
Predicate<User> active = u -> u.active;
Predicate<User> adultAndActive = adult.and(active);
```

### Comparator trap

If `compare(a,b) == 0` but `!a.equals(b)`, sorted sets/maps (`TreeSet`, `TreeMap`) may behave unexpectedly.

---

## 3. Method References

### Simple definition

Shorthand when lambda **only calls one existing method**.

| Type | Syntax | Lambda equivalent |
|------|--------|-------------------|
| Static | `Integer::parseInt` | `s -> Integer.parseInt(s)` |
| Bound instance | `logger::info` | `msg -> logger.info(msg)` |
| Unbound instance | `String::trim` | `s -> s.trim()` |
| Constructor | `ArrayList::new` | `() -> new ArrayList<>()` |

### When NOT to use

If you add business logic (`"ORDER-" + id`), keep the lambda — clearer.

### Pitfalls

- **Overload resolution** depends on target functional interface type.
- Constructor reference arity must match SAM (`Supplier` vs `BiFunction`).
- `distinct()` uses `equals/hashCode`, not “distinct by field” magic.

**Code:** `method_references/*`

---

## 4. Stream API

### Simple definition

A **Stream** is a pipeline of operations on a data source.  
It is **not** a collection — it does not store elements.

```
source → intermediate → intermediate → terminal
         (lazy)         (lazy)         (runs pipeline)
```

### Intermediate operations (lazy)

`filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`, `skip`, `peek`

### Terminal operations (trigger execution)

`collect`, `forEach`, `reduce`, `count`, `min`, `max`, `findFirst`, `anyMatch`

### `map` vs `flatMap`

- `map`: 1 input → 1 output  
- `flatMap`: 1 input → many outputs flattened one level  

Example: posts → all tags.

### `reduce`

Combines all elements into one result (sum, max, concatenation).

```java
int total = prices.stream().reduce(0, Integer::sum);
```

### Short-circuit operations

`anyMatch`, `allMatch`, `noneMatch`, `findFirst`, `limit` may stop early.

**Empty stream trap:**

| Operation | Empty stream result |
|-----------|---------------------|
| `anyMatch` | `false` |
| `allMatch` | `true` (vacuous truth) |
| `noneMatch` | `true` |

### Lazy evaluation

Without terminal operation, intermediates do nothing.  
`peek` is for debugging — don’t use it for real business mutations.

### Edge cases (must know)

1. **Stream consumed once** — second terminal → `IllegalStateException`
2. **Null elements** — many ops throw NPE unless filtered
3. **Modify source during stream** — risk `ConcurrentModificationException`
4. **`sorted()` with nulls** — NPE

**Code:** `streams/StreamApiDemo.java`, `StreamShortCircuitDemo.java`, `StreamLazyEvaluationDemo.java`, `StreamEdgeCasesDemo.java`

---

## 5. Parallel Streams

### Simple definition

`.parallel()` / `parallelStream()` splits work across threads (common `ForkJoinPool`).

### When it helps

- Large in-memory data
- CPU-heavy per-element work
- No shared mutable state

### When it hurts

- Small lists
- Blocking I/O (DB, HTTP, files)
- Order-sensitive logic done wrong

### Golden rule

❌ `list.parallelStream().forEach(sharedList::add)`  
✅ `list.parallelStream().map(...).collect(toList())`

**Code:** `streams/ParallelStreamsDemo.java`

---

## 6. Optional

### Simple definition

`Optional<T>` wraps a value that **may be absent** — alternative to returning `null`.

### Creation

```java
Optional.of(value);         // value MUST NOT be null
Optional.ofNullable(value); // null → empty Optional
Optional.empty();
```

### Safe access

```java
opt.orElse("default");
opt.orElseGet(() -> loadFromDb());
opt.orElseThrow(() -> new NotFoundException());
opt.ifPresent(v -> System.out.println(v));
```

### Chaining

```java
opt.filter(v -> v.length() > 0)
   .map(String::toUpperCase)
   .flatMap(this::parseDomain);
```

### Loopholes closed

| Myth | Truth |
|------|-------|
| Optional removes null from Java | No — still possible everywhere |
| Use Optional everywhere | No — mainly return types |
| `get()` is safe | Only after `isPresent()`; prefer `orElse` |
| `Optional.of(null)` | Throws NPE immediately |

### Optional + Stream (Java 8 pattern)

```java
list.stream()
    .map(this::findEmail)      // returns Optional<String>
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(toList());
```

**Code:** `optional/*`

---

## 7. Default & Static Methods in Interfaces

### Problem solved

Adding methods to interfaces used to **break** all implementers.

### `default` method

- Instance method with body in interface
- Inherited by implementer
- Can be overridden

### `static` method

- Called as `InterfaceName.method()`
- **Not inherited** by implementer

### Diamond conflict (two defaults)

If two interfaces define same `default` method, class **must override**:

```java
@Override
public void connect() {
    Wifi.super.connect();
    Bluetooth.super.connect();
}
```

**Code:** `interface_enhancements/*`

---

## 8. java.time API

### Why new API?

Old `Date`/`Calendar`: mutable, confusing months, weak timezone model.

### Main types

| Class | Holds |
|-------|-------|
| `LocalDate` | date only |
| `LocalTime` | time only |
| `LocalDateTime` | date + time (no zone) |
| `ZonedDateTime` | date + time + zone |
| `Instant` | UTC timeline point |
| `Period` | years/months/days |
| `Duration` | hours/minutes/seconds |

### Properties

- **Immutable** → thread-safe
- **Month 1–12** (not 0–11 like old Calendar)
- Clear format/parse with `DateTimeFormatter`

### Parsing traps

| Mistake | Effect |
|---------|--------|
| `mm` vs `MM` | minutes vs months |
| Lenient parse | may accept invalid dates (Feb 31) |
| Wrong `Locale` | month names fail |

Use `ResolverStyle.STRICT` when validating user input.

### Testability loophole

`LocalDate.now()` is hard to test.  
Inject `Clock`:

```java
LocalDate today = LocalDate.now(clock);
```

**Code:** `date_time/*`

---

## 9. Collectors

Used in `stream.collect(Collectors....)`.

### Common collectors

| Collector | Result |
|-----------|--------|
| `toList()` | List |
| `toSet()` | Set |
| `joining(delimiter)` | String |
| `groupingBy(classifier)` | `Map<K, List<T>>` |
| `partitioningBy(predicate)` | `Map<Boolean, List<T>>` |
| `counting()` | count per group |
| `summingInt(mapper)` | sum per group |
| `summarizingInt(mapper)` | stats |
| `toMap(key, value)` | Map (watch duplicates) |

### `toMap` duplicate key trap

Without merge function, duplicate keys → `IllegalStateException`.

```java
Collectors.toMap(
    Item::key,
    Item::value,
    Integer::max   // merge on collision
);
```

For **one-to-many**, use `groupingBy`, not `toMap`.

**Code:** `collectors/*`

---

## How All Features Connect

```text
Collection / array
        │
        ▼
    stream()
        │
        ├─► lambda / method reference ──► functional interface operation
        │
        ▼
   intermediate ops (filter, map, flatMap...)
        │
        ▼
   terminal op (collect, reduce, forEach)
        │
        ├─► Collectors → List / Map / stats
        └─► Optional → safe "not found" handling
```

**Mental model:** describe *what* you want, not step-by-step *how* to loop.

---

## Interview / Trap Checklist

### Lambda
- [ ] Effectively final rule
- [ ] Lambda vs anonymous class differences
- [ ] Checked exceptions inside lambda

### Functional interfaces
- [ ] SAM definition
- [ ] Predicate `and/or/negate`
- [ ] Primitive streams vs boxed streams

### Method references
- [ ] Four reference types
- [ ] Target typing / overload resolution

### Streams
- [ ] Lazy vs terminal
- [ ] `map` vs `flatMap`
- [ ] Short-circuit behavior
- [ ] Empty stream `allMatch`/`noneMatch`
- [ ] Cannot reuse stream
- [ ] Null elements

### Parallel
- [ ] No shared mutable state
- [ ] collect vs forEach

### Optional
- [ ] `of` vs `ofNullable`
- [ ] `map` vs `flatMap`
- [ ] Don’t use as field/parameter everywhere

### Interfaces
- [ ] default vs static
- [ ] Diamond conflict resolution
- [ ] `Interface.super.method()`

### java.time
- [ ] Immutable types
- [ ] Period vs Duration
- [ ] Strict parsing
- [ ] Clock injection

### Collectors
- [ ] `toMap` duplicate keys
- [ ] `groupingBy` for one-to-many
- [ ] `Comparator` consistency with `equals`

---

## Suggested Learning Path (7 days)

| Day | Focus | Classes |
|-----|-------|---------|
| 1 | Lambdas + pitfalls | `lambda/*` |
| 2 | Functional interfaces | `functional_interfaces/*` |
| 3 | Method references | `method_references/*` |
| 4 | Streams + edge cases | `streams/*` (skip parallel first) |
| 5 | Parallel + Optional | `ParallelStreamsDemo`, `optional/*` |
| 6 | Interfaces + java.time | `interface_enhancements/*`, `date_time/*` |
| 7 | Collectors + revision | `collectors/*`, re-run master demo |

---

## Practice Exercises (do yourself)

1. Filter employees with salary > 50,000 and return sorted names (Stream).
2. Build `Map<department, totalSalary>` using `groupingBy` + `summingInt`.
3. Parse user birth date `"dd-MM-yyyy"` safely with strict validation.
4. Resolve conflicting `default` methods in two interfaces with explicit `super` calls.
5. Convert `List<Optional<String>>` to `List<String>` (present only) using Java 8 style.

---

## Quick Reference Card

```java
// Lambda
list.forEach(x -> System.out.println(x));

// Method reference
list.forEach(System.out::println);

// Stream pipeline
List<String> result = list.stream()
    .filter(Objects::nonNull)
    .map(String::trim)
    .sorted()
    .collect(Collectors.toList());

// Optional return
public Optional<User> findUser(String id) {
    return Optional.ofNullable(db.get(id));
}

// java.time
LocalDate due = LocalDate.parse("31-05-2026", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
```

---

*Happy learning — run the code, change the data, break things on purpose, then fix them. That is how Java 8 becomes intuitive.*
