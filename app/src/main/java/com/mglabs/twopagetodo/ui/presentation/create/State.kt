package com.mglabs.twopagetodo.ui.presentation.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateFormState(var title: String, var content: String, var dueDate: String)
