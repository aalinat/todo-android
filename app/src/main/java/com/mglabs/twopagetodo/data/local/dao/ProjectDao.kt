package com.mglabs.twopagetodo.data.local.dao

import com.mglabs.twopagetodo.data.local.entities.Project
import com.mglabs.twopagetodo.data.local.entities.toDomain
import javax.inject.Singleton


@Singleton
class ProjectDao {
    private val _projects: MutableList<Project> = mutableListOf()

    init {
        _projects.add(Project(1, "Sample Project"))
    }
    private fun filterBy(predicate: (todo: Project) -> Boolean): List<Project> {
        return _projects.filter { predicate(it) }
    }
    fun findAll(): List<Project> {
        return filterBy { !it.isDeleted }
    }
    fun findById(projectId: Int): Project? {
        return _projects.find { it.id == projectId }
    }
    fun insert(project: Project): Project {
        _projects.add(project)
        return project
    }
}