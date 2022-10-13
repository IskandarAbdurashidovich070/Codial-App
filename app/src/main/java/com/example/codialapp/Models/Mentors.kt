package com.example.codialapp.Models

class Mentors {

    var id:Int = 1
    var name:String = ""
    var surname:String = ""
    var number:String = ""
    var groups:Int? = null

    constructor(id: Int, name: String, surname: String, number: String, groups: Int) {
        this.id = id
        this.name = name
        this.surname = surname
        this.number = number
        this.groups = groups
    }

    constructor(name: String, surname: String, number: String, groups: Int) {
        this.name = name
        this.surname = surname
        this.number = number
        this.groups = groups

    }

    constructor(name: String, surname: String, number: String) {
        this.name = name
        this.surname = surname
        this.number = number
    }

}