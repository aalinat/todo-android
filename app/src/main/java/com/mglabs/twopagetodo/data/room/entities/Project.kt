package com.mglabs.twopagetodo.data.room.entities

import com.mglabs.twopagetodo.domain.model.Project as DomainProject
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(@PrimaryKey(autoGenerate = true) var id: Int,
                   var name: String,
                   var isDeleted: Boolean = false)


fun Project.toDomainEntity(): DomainProject {
    return DomainProject(id, name, isDeleted)
}