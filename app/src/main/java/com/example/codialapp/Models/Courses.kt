package com.example.codialapp.Models

class Courses {

    var id: Int = 0
    var name:String = ""
    var about:String = ""


    constructor(id: Int, name: String, about: String) {
        this.id = id
        this.name = name
        this.about = about
    }

    constructor(name: String, about: String) {
        this.name = name
        this.about = about
    }

    constructor()

}