package com.kelompok4.kalkulatorips

data class MataKuliah(
    val id: String = UUID.randomUUID().toString(),
    val nama: String,
    val sks: Int,
    val nilaiHuruf: String,
    val bobot: Double
)
