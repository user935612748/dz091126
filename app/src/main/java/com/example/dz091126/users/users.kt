package com.example.dz091126.users

class Reader(
    val name: String,
    private val borrower: BookBorrower
) : BookBorrower by borrower

class Administrator(
    val name: String,
    private val adder: BookAdder
) : BookAdder by adder
