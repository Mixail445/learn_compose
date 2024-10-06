package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoAboutPeople

interface AboutPeopleView {
    data class Model(
        val name:String ="",
        val number:String=""
    )
    sealed class Event{
        data object OnClickBack:Event()
        data object OnClickEdit:Event()
        data object OnClickLogOut:Event()
    }
    sealed class UiLabel{
        data object ShowBackScreen:UiLabel()
        data object ActionLogOut:UiLabel()
    }
}