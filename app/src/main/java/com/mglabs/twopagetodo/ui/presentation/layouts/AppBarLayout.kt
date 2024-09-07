package com.mglabs.twopagetodo.ui.presentation.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarLayout(
    title: String = "Application Title",
    content: @Composable () -> Unit,
    navActions: @Composable (() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    onPrev: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            if (floatingButton != null) {
                floatingButton()
            }
        },
        topBar = {
            AppBar(title, scrollBehavior, { navActions?.let { it() } }, onPrev)
        }, content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                content()
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navActions: @Composable () -> Unit,
    onPrev: (() -> Unit)?
) {
    TopAppBar(title = {
        Text(text = title, color = Color.Red)
    },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            onPrev?.let {
                IconButton(onClick = { onPrev() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        },
        actions = {
            navActions()
        })
}