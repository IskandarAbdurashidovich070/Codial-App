package com.example.codialapp.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.codialapp.Models.Courses
import com.example.codialapp.Models.Groups
import com.example.codialapp.Models.Mentors
import com.example.codialapp.Models.Students
import java.security.acl.Group

class MyDbHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbInterface {

    companion object {
        var DB_NAME = "codial_app"
        var DB_VERSION = 1

        var COURSES_TABLE = "courses"
        var COURSES_ID = "id"
        var COURSES_NAME = "name"
        var COURSES_ABOUT = "about"

        var GROUPS_TABLE = "groups"
        var GROUPS_ID = "id"
        var GROUPS_NAME = "name"
        var GROUPS_MENTORS_ID = "mentors"
        var GROUPS_TIME = "time"
        var GROUPS_DAY = "day"
        var GROUPS_GROUPS = "groups_groups"
        var GROUP_SELECTION = "selection"
        var GROUP_SELECTION2 = "selection2"
        var GROUP_SELECTION3 = "selection3"
        var START_GROUP = "start"

        var MENTORS_TABLE = "mentors"
        var MENTORS_ID = "id"
        var MENTORS_NAME = "name"
        var MENTORS_SURNAME = "surname"
        var MENTORS_NUMBER = "number"
        var MENTORS_GROUP = "mygroup"

        var STUDENTS_TABLE = "students"
        var STUDENTS_ID = "id"
        var STUDENTS_NAME = "name"
        var STUDENTS_SURNAME = "surname"
        var STUDENTS_NUMBER = "number"
        var STUDENTS_GROUPS = "groups_student"


    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val queryCourses =
            "create table $COURSES_TABLE ($COURSES_ID integer not null primary key autoincrement unique, $COURSES_NAME text not null, $COURSES_ABOUT text not null)"
        val queryMentors =
            "create table $MENTORS_TABLE ($MENTORS_ID integer not null primary key autoincrement unique, $MENTORS_NAME text not null, $MENTORS_SURNAME text not null, $MENTORS_NUMBER text not null, $MENTORS_GROUP integer not null)"
        val queryGroups = """
create table $GROUPS_TABLE
($GROUPS_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
$GROUPS_NAME TEXT NOT NULL,
$GROUPS_TIME TEXT NOT NULL,
$GROUPS_DAY TEXT NOT NULL, 
$GROUPS_MENTORS_ID INTEGER NOT NULL,
$GROUPS_GROUPS INTEGER NOT NULL,
$GROUP_SELECTION integer not null,
$GROUP_SELECTION2 integer not null,
$GROUP_SELECTION3 integer not null,
$START_GROUP integer not null,
FOREIGN KEY ($GROUPS_MENTORS_ID) REFERENCES $MENTORS_TABLE ($MENTORS_ID) )
            """.trimIndent()
        val queryStudents =
            "CREATE TABLE $STUDENTS_TABLE ($STUDENTS_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $STUDENTS_NAME TEXT NOT NULL, $STUDENTS_SURNAME TEXT NOT NULL, $STUDENTS_NUMBER text not null, $STUDENTS_GROUPS integer not null )"

        p0?.execSQL(queryStudents)
        p0?.execSQL(queryGroups)
        p0?.execSQL(queryMentors)
        p0?.execSQL(queryCourses)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    override fun addCourses(courses: Courses) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COURSES_NAME, courses.name)
        contentValues.put(COURSES_ABOUT, courses.about)
        database.insert(COURSES_TABLE, null, contentValues)
        database.close()
    }

    override fun addGroups(groups: Groups) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUPS_NAME, groups.name)
        contentValues.put(GROUPS_MENTORS_ID, groups.mentors?.id)
        contentValues.put(GROUPS_TIME, groups.time)
        contentValues.put(GROUPS_DAY, groups.days)
        contentValues.put(GROUPS_GROUPS, groups.group)
        contentValues.put(GROUP_SELECTION, groups.selection)
        contentValues.put(GROUP_SELECTION2, groups.selection2)
        contentValues.put(GROUP_SELECTION3, groups.selection3)
        contentValues.put(START_GROUP, groups.start_group)
        database.insert(GROUPS_TABLE, null, contentValues)
        database.close()
    }

    override fun addMentors(mentors: Mentors) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTORS_NAME, mentors.name)
        contentValues.put(MENTORS_SURNAME, mentors.surname)
        contentValues.put(MENTORS_NUMBER, mentors.number)
        contentValues.put(MENTORS_GROUP, mentors.groups)
        database.insert(MENTORS_TABLE, null, contentValues)
        database.close()
    }

    override fun addStudents(students: Students) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENTS_NAME, students.name)
        contentValues.put(STUDENTS_SURNAME, students.surname)
        contentValues.put(STUDENTS_NUMBER, students.number)
        contentValues.put(STUDENTS_GROUPS, students.groups)
        database.insert(STUDENTS_TABLE, null, contentValues)
        database.close()
    }

    override fun getCourses(): List<Courses> {
        val list = ArrayList<Courses>()
        val query = "select * from $COURSES_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Courses(
                        cursor.getInt(0), cursor.getString(1), cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getGroups(): List<Groups> {
        val list = ArrayList<Groups>()
        val query = "select * from $GROUPS_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                   Groups(
                       cursor.getInt(0),
                       cursor.getString(1),
                       cursor.getString(2),
                       cursor.getString(3),
                       getMentorsById(cursor.getInt(4)),
                       cursor.getInt(5),
                       cursor.getInt(6),
                       cursor.getInt(7),
                       cursor.getInt(8),
                       cursor.getInt(9)
                   )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getMentors(): List<Mentors> {
        val list = ArrayList<Mentors>()
        val query = "select * from $MENTORS_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Mentors(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                    )
                )
            } while (cursor.moveToNext())
        }

        return list
    }

    override fun getStudents(): List<Students> {
        val list = ArrayList<Students>()
        val query = "select * from $STUDENTS_TABLE"
        val database = readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Students(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getMentorsById(id: Int): Mentors {
        val database = readableDatabase
        val getMentorQuery = "SELECT * FROM $MENTORS_TABLE WHERE $MENTORS_ID = $id"
//        val cursor = database.query(
//            MENTORS_TABLE, arrayOf(
//                MENTORS_ID, MENTORS_NAME, MENTORS_SURNAME, MENTORS_NUMBER
//            ), "$MENTORS_ID = ?", arrayOf(id.toString()), null, null, null
//        )
        val cursor = database.rawQuery(getMentorQuery, null)
        cursor.moveToFirst()
        val mentors = Mentors(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getInt(4)
        )
        return mentors
    }

    override fun deleteCourses(courses: Courses) {
        val database = writableDatabase
        database.delete(COURSES_TABLE, "$COURSES_ID = ?", arrayOf(courses.id.toString()))
        database.close()
    }

    override fun deleteMentors(mentors: Mentors) {
        val database = writableDatabase
        database.delete(MENTORS_TABLE, "$MENTORS_ID = ?", arrayOf(mentors.id.toString()))
        database.close()
    }

    override fun editMentors(mentors: Mentors) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MENTORS_ID, mentors.id)
        contentValues.put(MENTORS_NAME, mentors.name)
        contentValues.put(MENTORS_SURNAME, mentors.surname)
        contentValues.put(MENTORS_NUMBER, mentors.number)
        contentValues.put(MENTORS_GROUP, mentors.groups)
        database.update(
            MENTORS_TABLE, contentValues, "$MENTORS_ID = ?", arrayOf(mentors.id.toString())
        )
        database.close()
    }

    override fun deleteStudents(students: Students) {
        val database = writableDatabase
        database.delete(STUDENTS_TABLE, "$STUDENTS_ID = ?", arrayOf(students.id.toString()))
        database.close()    }

    override fun editStudents(students: Students) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(STUDENTS_ID, students.id)
        contentValues.put(STUDENTS_NAME, students.name)
        contentValues.put(STUDENTS_SURNAME, students.surname)
        contentValues.put(STUDENTS_NUMBER, students.number)
        contentValues.put(STUDENTS_GROUPS, students.groups)
        database.update(
            STUDENTS_TABLE, contentValues, "$STUDENTS_ID = ?", arrayOf(students.id.toString())
        )
        database.close()    }

    override fun deleteGroups(groups: Groups) {
        val database = writableDatabase
        database.delete(GROUPS_TABLE, "$GROUPS_ID = ?", arrayOf(groups.id.toString()))
        database.close()
    }

    override fun editGroups(groups: Groups) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(GROUPS_ID, groups.id)
        contentValues.put(GROUPS_NAME, groups.name)
        contentValues.put(GROUPS_TIME, groups.time)
        contentValues.put(GROUPS_DAY, groups.days)
        contentValues.put(GROUPS_GROUPS, groups.group)
        contentValues.put(GROUP_SELECTION, groups.selection)
        contentValues.put(GROUP_SELECTION2, groups.selection2)
        contentValues.put(GROUP_SELECTION3, groups.selection3)
        contentValues.put(START_GROUP, groups.start_group)
        database.update(
            GROUPS_TABLE, contentValues, "$GROUPS_ID = ?", arrayOf(groups.id.toString())
        )
        database.close()
    }


}