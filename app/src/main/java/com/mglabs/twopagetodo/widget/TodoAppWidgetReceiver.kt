package com.mglabs.twopagetodo.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class TodoAppWidgetReceiver  : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TodoAppWidget()

}