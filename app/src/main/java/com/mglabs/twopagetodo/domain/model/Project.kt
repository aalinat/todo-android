package com.mglabs.twopagetodo.domain.model

import com.mglabs.twopagetodo.data.local.entities.Project as LocalProject
import com.mglabs.twopagetodo.data.room.entities.Project as RoomProject

data class Project(var id: Int,
                   var name: String,
                   var isDeleted: Boolean = false)


fun Project.toLocalEntity(): LocalProject {
    return LocalProject(id, name, isDeleted)
}

fun Project.toRoomEntity(): RoomProject {
    return RoomProject(id, name, isDeleted)
}