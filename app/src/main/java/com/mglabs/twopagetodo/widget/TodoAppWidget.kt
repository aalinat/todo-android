package com.mglabs.twopagetodo.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle

class TodoAppWidget: GlanceAppWidget()  {
    override val sizeMode: SizeMode = SizeMode.Exact

//    override val stateDefinition = ImageStateDefinition


    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }
    @Composable
    fun Content() {
        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .appWidgetBackground()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp)
            ) {
                Text(
                    text = "Notes App Widget",
                    style = TextStyle(
                        color = GlanceTheme.colors.onSurface,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.End,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(GlanceTheme.colors.surface)
                )
            }
        }
    }
}