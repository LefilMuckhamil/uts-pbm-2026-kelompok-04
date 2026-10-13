package com.kelompok4.kalkulatorips.calculator

import com.kelompok4.kalkulatorips.model.MataKuliah
import com.kelompok4.kalkulatorips.model.NilaiHuruf
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Konversi nilai angka skala 0 - 100 ke huruf nilai beserta bobotnya.
 *
 * Standar Skala A-E (Default):
 * - 80.0 - 100.0 -> A (4.0)
 * - 70.0 - 79.99  -> B (3.0)
 * - 60.0 - 69.99  -> C (2.0)
 * - 50.0 - 59.99  -> D (1.0)
 * -  0.0 - 49.99  -> E (0.0)
 *
 * Pilihan Skala Plus/Minus (jika [gunakanSkalaPlusMinus] = true):
 * - 85 - 100 -> A (4.0), 80 - 84.99 -> A- (3.7)
 * - 75 - 79.99 -> B+ (3.3), 70 - 74.99 -> B (3.0), 65 - 69.99 -> B- (2.7)
 * - 60 - 64.99 -> C+ (2.3), 55 - 59.99 -> C (2.0)
 * - 40 - 54.99 -> D (1.0), < 40 -> E (0.0)
 *
 * @param nilaiAngka Nilai dalam rentang 0 hingga 100
 * @param gunakanSkalaPlusMinus Jika true, menggunakan variasi nilai A-, B+, B-, C+
 * @return [NilaiHuruf] berisi huruf dan bobot
 */
fun konversiNilaiKeHuruf(
    nilaiAngka: Double,
    gunakanSkalaPlusMinus: Boolean = false
): NilaiHuruf {
    val nilaiValid = nilaiAngka.coerceIn(0.0, 100.0)

    return if (gunakanSkalaPlusMinus) {
        when {
            nilaiValid >= 85.0 -> NilaiHuruf("A", 4.0)
            nilaiValid >= 80.0 -> NilaiHuruf("A-", 3.7)
            nilaiValid >= 75.0 -> NilaiHuruf("B+", 3.3)
            nilaiValid >= 70.0 -> NilaiHuruf("B", 3.0)
            nilaiValid >= 65.0 -> NilaiHuruf("B-", 2.7)
            nilaiValid >= 60.0 -> NilaiHuruf("C+", 2.3)
            nilaiValid >= 55.0 -> NilaiHuruf("C", 2.0)
            nilaiValid >= 40.0 -> NilaiHuruf("D", 1.0)
            else -> NilaiHuruf("E", 0.0)
        }
    } else {
        when {
            nilaiValid >= 80.0 -> NilaiHuruf("A", 4.0)
            nilaiValid >= 70.0 -> NilaiHuruf("B", 3.0)
            nilaiValid >= 60.0 -> NilaiHuruf("C", 2.0)
            nilaiValid >= 50.0 -> NilaiHuruf("D", 1.0)
            else -> NilaiHuruf("E", 0.0)
        }
    }
}

/**
 * Menghitung Indeks Prestasi Semester (IPS).
 *
 * Rumus:
 * IPS = Total (SKS * Bobot) / Total SKS
 *
 * @param daftarMataKuliah Daftar mata kuliah yang diambil pada semester ini
 * @return Nilai IPS dalam tipe Double
 */
fun hitungIPS(daftarMataKuliah: List<MataKuliah>): Double {
    if (daftarMataKuliah.isEmpty()) return 0.0

    val totalSks = daftarMataKuliah.sumOf { it.sks }
    if (totalSks <= 0) return 0.0

    val totalPoin = daftarMataKuliah.sumOf { it.sks * it.bobot }
    return totalPoin / totalSks
}

/**
 * Menghitung IPS dan membulatkannya ke sejumlah desimal tertentu (default: 2 desimal).
 *
 * @param daftarMataKuliah Daftar mata kuliah
 * @param presisi Jumlah angka di belakang koma (default: 2)
 * @return IPS yang sudah dibulatkan
 */
fun hitungIPSFormatted(daftarMataKuliah: List<MataKuliah>, presisi: Int = 2): Double {
    val ipsRaw = hitungIPS(daftarMataKuliah)
    return BigDecimal(ipsRaw)
        .setScale(presisi, RoundingMode.HALF_UP)
        .toDouble()
}

/**
 * Menentukan batas maksimum SKS yang dapat diambil pada semester berikutnya
 * berdasarkan IPS semester ini sesuai standar aturan perguruan tinggi (Permendikbud/SN-Dikti).
 *
 * Aturan Umum:
 * - IPS >= 3.00       : Maksimal 24 SKS
 * - 2.50 <= IPS < 3.00: Maksimal 21 SKS
 * - 2.00 <= IPS < 2.50: Maksimal 18 SKS
 * - 1.50 <= IPS < 2.00: Maksimal 15 SKS
 * - IPS < 1.50        : Maksimal 12 SKS
 *
 * @param ips Nilai Indeks Prestasi Semester
 * @return Batas SKS maksimal untuk semester berikutnya (12, 15, 18, 21, atau 24)
 */
fun hitungBatasSksBerikutnya(ips: Double): Int {
    return when {
        ips >= 3.00 -> 24
        ips >= 2.50 -> 21
        ips >= 2.00 -> 18
        ips >= 1.50 -> 15
        else -> 12
    }
}
