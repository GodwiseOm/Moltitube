package com.example.moltitube.Screens

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose.MoltiTubeTheme
import com.example.moltitube.Data.MockData
import com.example.moltitube.Model.Item


private const val TAG = "youtube"


@Composable
fun YoutubeScreen(video: Item, modifier: Modifier= Modifier) {

// gets the javascript data for youtube video
    fun getHtml(videoId: String): String {
        return """
        <html>
        
            <body style="margin:40px;padding:40px;">
               <div id="player"></div>
                <script>
                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '450',
                            width: '650',
                            videoId: '$videoId',
                            playerVars: {
                                'autoplay': 1,
                                'controls': 1,
                               
                                'rel': 0,
                                'modestbranding': 1,
                                'playsinline': 1
                            },
                            events: {
                                'onReady': onPlayerReady
                            }
                        });
                    }
                    function onPlayerReady(event) {
                     player.playVideo();
                        // Player is ready
                    }
                    function seekTo(time) {
                        player.seekTo(time, true);
                    }
                      function playVideo() {
                        player.playVideo();
                    }
                    function pauseVideo() {
                        player.pauseVideo();
                    }
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """.trimIndent()


    }

    val webView = WebView(LocalContext.current).apply {
        settings.useWideViewPort = true
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        webViewClient = WebViewClient()
    }
    val javaScriptData = getHtml(video.id.videoId)

    Column(modifier = modifier) {

        AndroidView(
            modifier = Modifier

               ,
            factory = { webView }) { view ->

            view.loadDataWithBaseURL(
                "https://www.youtube.com",
                javaScriptData,
                "text/html",
                "utf-8",
                null
            )
            Log.i(TAG, "YoutubeScreen: ")
        }

        Text(
            text = video.snippet.title,
            style = MaterialTheme.typography.titleSmall ,
            modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally)
        )

        //Button(onClick = {webView.loadUrl("javascript:pauseVideo();")}) { Text("Pause Video")}
        // Button(onClick = {webView.loadUrl("javascript:seekTo(60);")}) { Text("Seek Video")}
       // Button(onClick = { webView.loadUrl("javascript:playVideo();") }) { Text("Play Video") }
    }
}

@Preview
@Composable
fun YoutubeFramePreview(){
    MoltiTubeTheme { YoutubeScreen(video = MockData().item) }
}