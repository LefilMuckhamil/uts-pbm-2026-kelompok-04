package com.kelompok4.kalkulatorips

import java.math.BigDecimal
import java.math.RoundingMode

// Pusat logika kalkulasi IPS dan pemetaan nilai kampus
object Logika {

    // Patokan acuan nilainya huruf ke bobot angkanya
    val daftarPilihanNilai = listOf(
        "A" to 4.00,
        "A-" to 3.75,
        "B+" to 3.50,
        "B" to 3.00,
        "B-" to 2.75,
        "C+" to 2.50,
        "C" to 2.00,
        "D" to 1.00,
        "E" to 0.00,
    )

    // Konversi cepat dari nilai angka (0-100) ke huruf mutu
    fun konversiAngkaKeHuruf(nilaiAngka: Double): String {
        return when {
            nilaiAngka in 85.0..100.0 -> "A"
            nilaiAngka in 80.0..<85.0 -> "A-"
            nilaiAngka in 75.0..<80.0 -> "B+"
            nilaiAngka in 70.0..<75.0 -> "B"
            nilaiAngka in 65.0..<70.0 -> "B-"
            nilaiAngka in 60.0..<65.0 -> "C+"
            nilaiAngka in 55.0..<60.0 -> "C"
            nilaiAngka in 40.0..<55.0 -> "D"
            else -> "E"
        }
    }

    // Cari bobot angka berdasarkan huruf nilainya
    fun dapatkanBobot(nilaiHuruf: String): Double {
        return daftarPilihanNilai.find { it.first.equals(nilaiHuruf, ignoreCase = true) }?.second
            ?: when (nilaiHuruf.uppercase()) {
                "A" -> 4.0
                "B+" -> 3.5
                "B" -> 3.0
                "C+" -> 2.5
                "C" -> 2.0
                "D" -> 1.0
                "E" -> 0.0
                else -> 0.0
            }
    }

    fun getBobotFromHuruf(nilaiHuruf: String): Double = dapatkanBobot(nilaiHuruf)

    // Rumus sakti hitung IPS: Total (SKS * Bobot) / Total SKS
    fun hitungIPS(daftarMK: List<MataKuliah>): Double {
        val totalSks = daftarMK.sumOf { it.sks }
        if (totalSks == 0) return 0.0
        val totalKredit = daftarMK.sumOf { it.sks * it.bobot }
        return totalKredit / totalSks
    }

    // Biar rapi, kita bulatkan IPS ke 2 angka di belakang koma
    fun hitungIPSFormatted(daftarMK: List<MataKuliah>, presisi: Int = 2): Double {
        val ips = hitungIPS(daftarMK)
        if (ips.isNaN() || ips.isInfinite()) return 0.0
        return BigDecimal(ips)
            .setScale(presisi, RoundingMode.HALF_UP)
            .toDouble()
    }

    // Totalin seluruh SKS yang lagi diambil
    fun hitungTotalSKS(daftarMK: List<MataKuliah>): Int {
        return daftarMK.sumOf { it.sks }
    }

    // Aturan standar kampus buat nentuin jatah SKS semester depan
    fun tentukanBatasSKS(ips: Double): Int {
        return when {
            ips >= 3.00 -> 24
            ips >= 2.50 -> 21
            ips >= 2.00 -> 18
            ips >= 1.50 -> 15
            else -> 12
        }
    }

    fun hitungBatasSKS(ips: Double): Int = tentukanBatasSKS(ips)

    // Cek matkul yang harus diulang (yang dapet D atau E)
    fun dapatkanMKPerluDiulang(daftarMK: List<MataKuliah>): List<MataKuliah> {
        return daftarMK.filter { it.nilaiHuruf == "D" || it.nilaiHuruf == "E" }
    }

    // Hitung berapa matkul yang dapet nilai huruf tertentu
    fun hitungJumlahNilai(daftarMK: List<MataKuliah>, huruf: String): Int {
        return daftarMK.count { it.nilaiHuruf.equals(huruf, ignoreCase = true) }
    }
}
