package com.kelompok4.kalkulatorips

import com.kelompok4.kalkulatorips.calculator.hitungBatasSksBerikutnya
import com.kelompok4.kalkulatorips.calculator.hitungIPS
import com.kelompok4.kalkulatorips.calculator.hitungIPSFormatted
import com.kelompok4.kalkulatorips.calculator.konversiNilaiKeHuruf
import com.kelompok4.kalkulatorips.model.MataKuliah
import org.junit.Assert.assertEquals
import org.junit.Test

class KalkulatorIpsTest {

    @Test
    fun testKonversiNilaiKeHuruf_skalaStandar() {
        // Nilai A (80 - 100)
        assertEquals("A", konversiNilaiKeHuruf(85.0).huruf)
        assertEquals(4.0, konversiNilaiKeHuruf(85.0).bobot, 0.001)

        // Nilai B (70 - 79.99)
        assertEquals("B", konversiNilaiKeHuruf(75.0).huruf)
        assertEquals(3.0, konversiNilaiKeHuruf(75.0).bobot, 0.001)

        // Nilai C (60 - 69.99)
        assertEquals("C", konversiNilaiKeHuruf(62.0).huruf)
        assertEquals(2.0, konversiNilaiKeHuruf(62.0).bobot, 0.001)

        // Nilai D (50 - 59.99)
        assertEquals("D", konversiNilaiKeHuruf(55.0).huruf)
        assertEquals(1.0, konversiNilaiKeHuruf(55.0).bobot, 0.001)

        // Nilai E (< 50)
        assertEquals("E", konversiNilaiKeHuruf(45.0).huruf)
        assertEquals(0.0, konversiNilaiKeHuruf(45.0).bobot, 0.001)
    }

    @Test
    fun testKonversiNilaiKeHuruf_skalaPlusMinus() {
        assertEquals("A-", konversiNilaiKeHuruf(82.0, gunakanSkalaPlusMinus = true).huruf)
        assertEquals(3.7, konversiNilaiKeHuruf(82.0, gunakanSkalaPlusMinus = true).bobot, 0.001)

        assertEquals("B+", konversiNilaiKeHuruf(78.0, gunakanSkalaPlusMinus = true).huruf)
        assertEquals(3.3, konversiNilaiKeHuruf(78.0, gunakanSkalaPlusMinus = true).bobot, 0.001)
    }

    @Test
    fun testHitungIPS() {
        val listMatkul = listOf(
            MataKuliah(nama = "Pemrograman Android", sks = 3, nilaiAngka = 85.0), // A -> 4.0 * 3 = 12.0
            MataKuliah(nama = "Basis Data", sks = 4, nilaiAngka = 75.0),           // B -> 3.0 * 4 = 12.0
            MataKuliah(nama = "Matematika Diskrit", sks = 3, nilaiAngka = 65.0)    // C -> 2.0 * 3 = 6.0
        )
        // Total Poin = 12 + 12 + 6 = 30.0
        // Total SKS = 3 + 4 + 3 = 10
        // IPS = 30.0 / 10 = 3.00

        val ips = hitungIPS(listMatkul)
        assertEquals(3.00, ips, 0.001)
        assertEquals(3.00, hitungIPSFormatted(listMatkul), 0.001)
    }

    @Test
    fun testHitungIPS_kosong() {
        assertEquals(0.0, hitungIPS(emptyList()), 0.001)
    }

    @Test
    fun testHitungBatasSksBerikutnya() {
        assertEquals(24, hitungBatasSksBerikutnya(3.50))
        assertEquals(24, hitungBatasSksBerikutnya(3.00))

        assertEquals(21, hitungBatasSksBerikutnya(2.85))
        assertEquals(21, hitungBatasSksBerikutnya(2.50))

        assertEquals(18, hitungBatasSksBerikutnya(2.20))
        assertEquals(18, hitungBatasSksBerikutnya(2.00))

        assertEquals(15, hitungBatasSksBerikutnya(1.75))
        assertEquals(15, hitungBatasSksBerikutnya(1.50))

        assertEquals(12, hitungBatasSksBerikutnya(1.20))
        assertEquals(12, hitungBatasSksBerikutnya(0.00))
    }
}
