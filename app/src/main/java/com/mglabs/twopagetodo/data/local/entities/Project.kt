package com.mglabs.twopagetodo.data.local.entities
import com.mglabs.twopagetodo.domain.model.Project as DomainProject

data class Project(
    var id: Int,
    var name: String,
    var isDeleted: Boolean = false
)

fun Project.toDomain(): DomainProject {
    return DomainProject(id, name)
}