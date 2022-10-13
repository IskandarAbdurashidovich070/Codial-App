package com.example.codialapp.Models

import android.text.Selection

class Groups {

    var id:Int = 0
    var name:String= ""
    var time:String = ""
    var days:String = ""
    var mentors:Mentors? = null
    var group:Int? = null
    var selection:Int? = null
    var selection2 :Int? = null
    var selection3 : Int? = null
    var start_group : Int? = 0

    constructor(id: Int, name: String, time: String, days: String, mentors: Mentors?, group:Int, selection: Int, selection2: Int, selection3: Int, start_group: Int) {
        this.id = id
        this.name = name
        this.time = time
        this.days = days
        this.mentors = mentors
        this.group = group
        this.selection = selection
        this.selection2 = selection2
        this.selection3 = selection3
        this.start_group = start_group
    }

    constructor(name: String, time: String, days: String, mentors: Mentors?, group: Int, selection: Int, selection2: Int, selection3: Int,start_group: Int) {
        this.name = name
        this.time = time
        this.days = days
        this.mentors = mentors
        this.group = group
        this.selection = selection
        this.selection2 = selection2
        this.selection3 = selection3
        this.start_group = start_group
    }

    constructor()

}