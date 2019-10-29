package my.reactive.kotlin.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Mono

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

fun <T, R> Mono<T>.enrich(process: (T) -> Mono<R>, combine: (R, T) -> T): Mono<T> {
    return flatMap { process(it).map { response -> combine(response, it) } }
}

fun <T, R> Mono<T>.notify(process: (T) -> Mono<R>): Mono<T> {
    return flatMap { process(it).thenReturn(it) }
}

fun <T> Mono<T>.choice(test: (T) -> Boolean, thenProcess: (T) -> Mono<T>, elseProcess: (T) -> Mono<T>): Mono<T> {
    return flatMap {
        if (test(it)) {
            thenProcess(it)
        } else {
            elseProcess(it)
        }
    }
}

fun <T> Mono<T>.choice(test: (T) -> Boolean, thenProcess: (T) -> Mono<T>): Mono<T> {
    return flatMap {
        if (test(it)) {
            thenProcess(it)
        } else {
            Mono.just(it)
        }
    }
}

fun <T> Mono<T>.multicast(vararg processes: (T) -> Mono<T>): Mono<T> {
    val process = processes.reduce({ p1, p2 -> compose(p1, p2) })
    return flatMap { process(it) }
}


private fun <T> compose(process1: (T) -> Mono<T>, process2: (T) -> Mono<T>): (T) -> Mono<T> {
    return { process1(it).then(process2(it)) }
}
