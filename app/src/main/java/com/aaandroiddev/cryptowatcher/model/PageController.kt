package com.aaandroiddev.cryptowatcher.model

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class PageController {

    private val subject = PublishSubject.create<Int>()

    fun pageSelected(position: Int) = subject.onNext(position)

    fun getPageObservable(): Observable<Int> = subject
}