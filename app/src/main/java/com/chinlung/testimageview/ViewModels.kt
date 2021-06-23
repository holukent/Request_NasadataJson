package com.chinlung.testimageview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.chinlung.testimageview.model.NasaDataItem
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class ViewModels(private val savedStateHandle: SavedStateHandle) : ViewModel() {


    private var _list: MutableLiveData<MutableList<NasaDataItem>> = MutableLiveData()
    val list: LiveData<MutableList<NasaDataItem>> get() = _list

    private var _sublist: MutableLiveData<List<NasaDataItem>> = MutableLiveData()
    val sublist: LiveData<List<NasaDataItem>> get() = _sublist


    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private var _filelist: MutableLiveData<List<String>> = MutableLiveData()
    val filelist: LiveData<List<String>> get() = _filelist



    init {
        _loading.value = false
        _sublist.value = listOf()
    }

    fun getRequest(context: Context, str: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mutableListOf<NasaDataItem>()
            val queue = Volley.newRequestQueue(context)
            val stringResquest = StringRequest(
                Request.Method.GET,
                str,
                { response ->
                    val array = JSONArray(response)
                    for (i in 0 until array.length()) {
                        result.add(
                            NasaDataItem(
                                array.getJSONObject(i).getString("date"),
                                array.getJSONObject(i).getString("title"),
                                array.getJSONObject(i).getString("url"),
                                array.getJSONObject(i).getString("description"),
                            )
                        )
                    }
//                    val result = Gson().fromJson(response, NasaData::class.java)
                    _list.postValue(result)
                },
                {}
            )
            queue.add(stringResquest)
        }
    }

    fun setFilesList(context: Context) {
        _filelist.value = context.fileList().toList()
    }

    fun updateList(context: Context, range: Int = 32)
    {
        viewModelScope.launch(Dispatchers.Main) {
            if (loading.value == false) {
                _loading.value = true
                (sublist.value!!.size..sublist.value!!.size + range).forEach {

                    if (!filelist.value!!.contains("${list.value!![it].date}.jpg")) {

                        downloadimg(context, list.value!![it]).join()
                    }
                    _sublist.value = list.value!!.subList(0, it)
                }
                _loading.value = false

            }
        }
    }


    fun downloadimg(context: Context, nasaDataItem: NasaDataItem?,quality:Int = 10) =
        viewModelScope.launch(Dispatchers.IO) {
            if (filterImage()) return@launch
            URL(nasaDataItem?.url).openStream()
                .use { inputStream ->
                    FileOutputStream(File(context.filesDir, "${nasaDataItem?.date}($quality).jpg"))
                        .use { fileOutputStream ->
                            BitmapFactory.decodeStream(inputStream).compress(
                                Bitmap.CompressFormat.JPEG, quality, fileOutputStream
                            )
                        }
                }
        }

    fun filterImage():Boolean {
        return  true
    }


    fun saveRecyclerView(str: String, onSaveInstanceState: Parcelable?) {
        savedStateHandle[str] = onSaveInstanceState
    }

    fun getRecyclerViewState(str: String): Parcelable? {
        return savedStateHandle[str]
    }
}


