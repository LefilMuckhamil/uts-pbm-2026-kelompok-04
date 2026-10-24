
package com.kelompok4.kalkulatorips

import java.math.BigDecimal
import java.math.RoundingMode

// Pusat logika kalkulasi IPS dan pemetaan nilai kampus
object Logika {

    // Daftar nilai huruf beserta bobot angkanya
    val daftarPilihanNilai = listOf(
        "A" to 4.00,
        "B+" to 3.50,
        "B" to 3.00,
        "C+" to 2.50,
        "C" to 2.00,
        "D" to 1.00,
        "E" to 0.00
    )

    // Konversi nilai angka menjadi nilai huruf
    fun konversiAngkaKeHuruf(nilaiAngka: Double): String {
        return when {
            nilaiAngka in 85.0..100.0 -> "A"
            nilaiAngka >= 75.0 && nilaiAngka < 85.0 -> "B+"
            nilaiAngka >= 65.0 && nilaiAngka < 75.0 -> "B"
            nilaiAngka >= 55.0 && nilaiAngka < 65.0 -> "C+"
            nilaiAngka >= 45.0 && nilaiAngka < 55.0 -> "C"
            nilaiAngka >= 35.0 && nilaiAngka < 45.0 -> "D"
            nilaiAngka >= 0.0 && nilaiAngka < 35.0 -> "E"
            else -> "E"
        }
    }

    // Mendapatkan bobot angka berdasarkan nilai huruf
    fun dapatkanBobot(nilaiHuruf: String): Double {
        return daftarPilihanNilai.find {
            it.first.equals(nilaiHuruf.trim(), ignoreCase = true)
        }?.second ?: 0.0
    }

    // Fungsi alternatif untuk mendapatkan bobot nilai
    fun getBobotFromHuruf(nilaiHuruf: String): Double =
        dapatkanBobot(nilaiHuruf)

    // Menghitung IPS berdasarkan bobot nilai dan jumlah SKS
    fun hitungIPS(daftarMK: List<MataKuliah>): Double {
        val totalSks = daftarMK.sumOf { it.sks }

        if (totalSks == 0) return 0.0

        val totalKredit = daftarMK.sumOf {
            it.sks * it.bobot
        }

        return totalKredit / totalSks
    }

    // Membulatkan hasil IPS sesuai jumlah angka desimal
    fun hitungIPSFormatted(
        daftarMK: List<MataKuliah>,
        presisi: Int = 2
    ): Double {
        val ips = hitungIPS(daftarMK)

        if (ips.isNaN() || ips.isInfinite()) return 0.0

        return BigDecimal(ips)
            .setScale(presisi, RoundingMode.HALF_UP)
            .toDouble()
    }

    // Menghitung total SKS seluruh mata kuliah
    fun hitungTotalSKS(daftarMK: List<MataKuliah>): Int {
        return daftarMK.sumOf { it.sks }
    }

    // Menentukan batas SKS untuk semester berikutnya
    fun tentukanBatasSKS(ips: Double): Int {
        return when {
            ips >= 3.00 -> 24
            ips >= 2.50 -> 21
            ips >= 2.00 -> 18
            ips >= 1.50 -> 15
            else -> 12
        }
    }

    // Fungsi alternatif untuk menentukan batas SKS
    fun hitungBatasSKS(ips: Double): Int =
        tentukanBatasSKS(ips)

    // Mendapatkan mata kuliah yang perlu diulang
    // Mata kuliah dengan nilai D atau E
    fun dapatkanMKPerluDiulang(
        daftarMK: List<MataKuliah>
    ): List<MataKuliah> {
        return daftarMK.filter {
            it.nilaiHuruf.equals("D", ignoreCase = true) ||
                    it.nilaiHuruf.equals("E", ignoreCase = true)
        }
    }

    // Menghitung jumlah mata kuliah berdasarkan nilai huruf
    fun hitungJumlahNilai(
        daftarMK: List<MataKuliah>,
        huruf: String
    ): Int {
        return daftarMK.count {
            it.nilaiHuruf.equals(huruf, ignoreCase = true)
        }
    }
}
