package com.kelompok4.kalkulatorips

import java.util.UUID

/**
 * Model data class MataKuliah
 */
data class MataKuliah(
    val id: String = UUID.randomUUID().toString(),
    val nama: String,
    val sks: Int,
    val nilaiHuruf: String,
    val bobot: Double,
)
