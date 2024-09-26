package com.mglabs.twopagetodo.data.repository.room

import com.mglabs.twopagetodo.data.room.dao.ProjectDao
import com.mglabs.twopagetodo.data.room.entities.toDomainEntity
import com.mglabs.twopagetodo.domain.model.Project
import com.mglabs.twopagetodo.domain.model.toRoomEntity
import com.mglabs.twopagetodo.domain.repository.ProjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepositoryImpl @Inject constructor(private val projectDao: ProjectDao) :
    ProjectRepository {
    override fun findAll(): Flow<List<Project>> {
        return flow {
            projectDao.getProjects().collect{ list -> emit(list.map { it.toDomainEntity()  })}
        }
    }

    override suspend fun findById(projectId: Int): Project {
        return projectDao.findById(projectId).toDomainEntity()
    }

    override suspend fun create(project: Project): Project {
        projectDao.insert(project.toRoomEntity())
        return project
    }
}