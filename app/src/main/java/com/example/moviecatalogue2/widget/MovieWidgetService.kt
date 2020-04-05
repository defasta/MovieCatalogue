package com.example.moviecatalogue2.widget

import android.content.Intent
import android.widget.RemoteViewsService

class MovieWidgetService : RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}