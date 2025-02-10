package com.typeform.example.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecentHandler(
    private val context: Context
) {
    companion object {
        private const val FORM_IDS = "form.ids"
    }

    val recentFormIds: StateFlow<Set<String>>
        get() = mutableFormIds.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences("com.typeform.example.preferences", MODE_PRIVATE)
    private val mutableFormIds = MutableStateFlow<Set<String>>(emptySet())

    init {
        try {
            sharedPreferences.getStringSet(FORM_IDS, emptySet())?.let {
                mutableFormIds.value = it
            }
        } catch (_: Exception) {
        }
    }

    fun addFormId(formId: String) {
        val formIds = mutableFormIds.value
        if (formIds.contains(formId)) {
            return
        }

        val set = formIds.toMutableSet()
        set.add(formId)
        mutableFormIds.value = set
        sharedPreferences.edit().putStringSet(FORM_IDS, set).apply()
    }

    fun clear() {
        val set = emptySet<String>()
        mutableFormIds.value = set
        sharedPreferences.edit().putStringSet(FORM_IDS, set).apply()
    }
}
