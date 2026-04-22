package com.aashu.trialvitamate.network

data class MedicineResponse(
    val results: List<Medicine>
)

data class Medicine(
    val purpose: List<String>?,
    val warnings: List<String>?,
    val adverse_reactions: List<String>?
)