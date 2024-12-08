package com.example.moltitube.Data

import androidx.webkit.internal.ApiFeature.M
import com.example.moltitube.Model.Default
import com.example.moltitube.Model.High
import com.example.moltitube.Model.Id
import com.example.moltitube.Model.Item
import com.example.moltitube.Model.Medium
import com.example.moltitube.Model.Snippet
import com.example.moltitube.Model.Thumbnails

class MockData {
    val item = Item(
        etag = "etag",
        id = Id(kind = "kind", videoId = "videoId"),
        kind = "kind",
        snippet = Snippet(
            "channelId",
            channelTitle = "channelTitle",
            description = "description",
            liveBroadcastContent = "liveBroadcastContent",
            publishTime = "publishTime",
            publishedAt = "publishedAt",
            thumbnails = Thumbnails(
                default = Default(height = 0, url = "url", width = 0),
                high = High(height = 0, url = "url", width = 0),
                medium = Medium(height = 0, url = "url", width = 0),
            ), title = "title"

        )
    )
}