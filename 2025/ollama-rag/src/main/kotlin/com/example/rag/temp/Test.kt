package com.example.rag.temp

fun main() {
//    println(fib(8))
    (0..3L).forEach { println(fib(it))}
}

fun fib(i: Long): Long {
    if(i==0L){
        return 0
    }
    if(i==1L){
        return 1
    }

    return fib(i-2)+fib(i-1)
}
