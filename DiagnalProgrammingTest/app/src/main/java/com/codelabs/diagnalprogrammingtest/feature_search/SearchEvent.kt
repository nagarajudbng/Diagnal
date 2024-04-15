package com.codelabs.diagnalprogrammingtest.feature_search

sealed class SearchEvent(){
    data class OnSearchQuery(val query:String):SearchEvent()
    data class OnFocusChange(val focus:Boolean):SearchEvent()

    data object OnClearPressed : SearchEvent()
}
