package com.example.moltitube.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import coil3.compose.AsyncImage
import com.example.moltitube.Data.MockData
import com.example.moltitube.Model.ActiveVideoUiState
import com.example.moltitube.Model.Id
import com.example.moltitube.Model.Item
import com.example.moltitube.Model.YotubeSearchResponse
import com.example.moltitube.Network.YoutubeClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response


private const val TAG = "DisplayScreenViewModel"

class DisplayScreenViewModel : ViewModel() {
    val playingVideo: MutableState<Item> = mutableStateOf(MockData().item)
    val showSearchList = mutableStateOf(true)

fun cancelPlayingVideo(){
    showPlayingVideo.value = false
    showSearchList.value = true
}

    var showPlayingVideo = mutableStateOf(false)

fun imageClicked(item: Item){

    playingVideo.value = item
    showPlayingVideo.value = true
    showSearchList.value = false


}
    private val searchQuery = mutableStateOf("")
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    val searchResults = MutableStateFlow(emptyList<Item>())


    fun searchYoutube(searchQuery: String) {

        viewModelScope.launch {


            try {
                Log.i(TAG, "youtube call started")
                val response =
                    YoutubeClient().youtubeService.searchYoutube(searchQuery = searchQuery)
                if (response.isSuccessful) {
                    Log.i(TAG, "youtube call was succesful: ${response.body()?.items}")
                    Log.i(TAG, "youtube raw response: ${response.raw()}")
                    if (!response.body()?.items.isNullOrEmpty()) {
                        searchResults.value = response.body()!!.items
                    }


                } else {
                    Log.i(TAG, "youtube call was not succesful: ${response.body()}")
                    Log.i(
                        TAG,
                        "youtube call was not succesful,error body: ${response.errorBody()}"
                    )
                    Log.i(TAG, "youtube call was not succesful, message: ${response.message()}")
                    Log.i(TAG, "youtube call was not succesful, response code: ${response.code()}")
                    Log.i(TAG, "youtube call was not succesful: raw resonse: ${response.raw()}")
                }


            } catch (e: HttpException) {

                Log.e(TAG, "http error occurred. Error message: ${e.message}")
                emptyList<Item>()
            } catch (e: Exception) {
                Log.e(TAG, "unknown error occurred. Error message: ${e.message}")

            }
        }

    }
}
class DisplayScreenViewModelFactory:ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        Log.i(TAG, "new viewmodel created")
        return DisplayScreenViewModel() as T
    }

}


