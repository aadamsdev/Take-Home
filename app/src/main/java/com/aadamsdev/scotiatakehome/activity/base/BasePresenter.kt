package com.aadamsdev.scotiatakehome.activity.base

abstract class BasePresenter(private var view: BaseViewInterface?){
    fun onViewAttached(view: BaseViewInterface) {
        this.view = view
    }

    fun onViewDetached() {
        view = null
    }
}