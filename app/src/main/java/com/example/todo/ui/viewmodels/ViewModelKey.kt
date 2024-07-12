package com.example.todo.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

// Честно говоря уже сложно сказать что происходит.
// Но это для инжекта зависимостей с помощью Dagger 2
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)