package com.example.codialapp.DB

import com.example.codialapp.Models.Courses
import com.example.codialapp.Models.Groups
import com.example.codialapp.Models.Mentors
import com.example.codialapp.Models.Students

interface MyDbInterface {

    fun addCourses(courses: Courses)
    fun addGroups(groups: Groups)
    fun addMentors(mentors: Mentors)
    fun addStudents(students: Students)

    fun getCourses():List<Courses>
    fun getGroups():List<Groups>
    fun getMentors():List<Mentors>
    fun getStudents():List<Students>

    fun getMentorsById(id:Int):Mentors

    fun deleteCourses(courses: Courses)

    fun deleteMentors(mentors: Mentors)
    fun editMentors(mentors: Mentors)

    fun deleteStudents(students: Students)
    fun editStudents(students: Students)

    fun deleteGroups(groups: Groups)
    fun editGroups(groups: Groups)

}