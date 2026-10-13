package com.kelompok4.kalkulatorips.model

/**
 * Data class yang menyimpan hasil konversi nilai berupa huruf dan bobot angkanya.
 *
 * @property huruf Nilai dalam bentuk huruf ("A", "B", "C", "D", "E", dll)
 * @property bobot Bobot nilai (4.0, 3.0, 2.0, 1.0, 0.0)
 */
data class NilaiHuruf(
    val huruf: String,
    val bobot: Double
)
