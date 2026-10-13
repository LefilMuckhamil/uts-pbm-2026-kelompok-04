package com.kelompok4.kalkulatorips.model

import com.kelompok4.kalkulatorips.calculator.konversiNilaiKeHuruf
import java.util.UUID

/**
 * Data class yang merepresentasikan sebuah Mata Kuliah.
 *
 * @property id Identitas unik mata kuliah (default: UUID)
 * @property kode Kode mata kuliah (contoh: "IF1234")
 * @property nama Nama mata kuliah (contoh: "Pemrograman Android")
 * @property sks Jumlah Bobot SKS (contoh: 3)
 * @property nilaiAngka Nilai dalam bentuk angka (0.0 - 100.0), opsional jika diisi langsung
 * @property nilaiHurufCustom Nilai huruf jika diinput manual (contoh: "A"), opsional
 * @property bobotCustom Bobot nilai jika diinput manual (contoh: 4.0), opsional
 */
data class MataKuliah(
    val id: String = UUID.randomUUID().toString(),
    val kode: String = "",
    val nama: String,
    val sks: Int,
    val nilaiAngka: Double? = null,
    val nilaiHurufCustom: String? = null,
    val bobotCustom: Double? = null
) {
    /**
     * Mendapatkan objek [NilaiHuruf] (huruf & bobot).
     * Memprioritaskan data custom jika diisi, jika tidak menggunakan konversi dari [nilaiAngka].
     */
    val grade: NilaiHuruf
        get() {
            if (bobotCustom != null && nilaiHurufCustom != null) {
                return NilaiHuruf(nilaiHurufCustom, bobotCustom)
            }
            if (nilaiAngka != null) {
                return konversiNilaiKeHuruf(nilaiAngka)
            }
            return NilaiHuruf(nilaiHurufCustom ?: "E", bobotCustom ?: 0.0)
        }

    /**
     * Bobot nilai (0.0 - 4.0).
     */
    val bobot: Double
        get() = grade.bobot

    /**
     * Huruf nilai ("A", "B", "C", "D", "E").
     */
    val huruf: String
        get() = grade.huruf

    /**
     * Total poin mutu mata kuliah ini (SKS * Bobot).
     */
    val totalPoin: Double
        get() = sks * bobot
}
