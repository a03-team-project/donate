package com.sparta.donate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DonateApplication

fun main(args: Array<String>) {
    runApplication<DonateApplication>(*args)
}
