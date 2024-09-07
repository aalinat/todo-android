package com.mglabs.twopagetodo.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mglabs.twopagetodo.ui.presentation.layouts.AppBarLayout
import com.mglabs.twopagetodo.ui.presentation.viewmodels.DetailsScreenViewModel


@Composable
fun DetailsScreen(onNavigateToHome: () -> Unit) {
    val viewModel: DetailsScreenViewModel = hiltViewModel<DetailsScreenViewModel>()
    val item = viewModel.getTodoTask()
    AppBarLayout(
            title = item.title,
            onPrev = onNavigateToHome,
            navActions = {
                FavoriteAction(item.isFavorite) {
                    when (item.isFavorite) {
                        true -> viewModel.unFavorite()
                        false -> viewModel.favorite()
                    }
                }
                UpdateAction { viewModel.updateItem(item) }
                DeleteAction {viewModel.deleteItem()}
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text(
                        text = item.createdAt,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(text = "By ${item.author}")
                    Text(text = "is favorite? ${item.isFavorite}")
                    Text(text = "is deleted? ${item.isDeleted}")

                    Spacer(modifier = Modifier.padding(10.dp))

                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = item.content, style = MaterialTheme.typography.bodyLarge)
                }
            })

}

@Composable
fun FavoriteAction(isFavorite: Boolean, onAction: () -> Unit) {
    IconButton(onClick = {
        onAction()
    }) {
        FavIcon(isFavorite)
    }
}

@Composable
fun FavIcon(isFavorite: Boolean){
    var favIcon = Icons.Default.FavoriteBorder
    var favIconDescription = "Mark as Favorite"
    if (isFavorite) {
        favIcon = Icons.Default.Favorite
        favIconDescription = "Unmark as Favorite"
    }
    Icon(imageVector = favIcon, contentDescription = favIconDescription)
}
@Composable
fun UpdateAction(onUpdate: () -> Unit) {
    IconButton(onClick = onUpdate) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = "Edit Item"
        )
    }
}

@Composable
fun DeleteAction(onDelete: () -> Unit) {
    IconButton(onClick = onDelete) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete Item"
        )
    }
}