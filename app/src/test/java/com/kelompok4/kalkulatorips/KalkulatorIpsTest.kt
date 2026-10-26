package com.kelompok4.kalkulatorips

import org.junit.Assert.assertEquals
import org.junit.Test

class KalkulatorIpsTest {

    @Test
    fun testHitungIPS() {
        val listMatkul = listOf(
            MataKuliah(nama = "Pemrograman Android", sks = 3, nilaiHuruf = "A", bobot = 4.0),
            MataKuliah(nama = "Basis Data", sks = 4, nilaiHuruf = "B", bobot = 3.0),
            MataKuliah(nama = "Matematika Diskrit 2", sks = 3, nilaiHuruf = "C", bobot = 2.0)
        )

        val ips = Logika.hitungIPS(listMatkul)
        assertEquals(3.00, ips, 0.001)
        assertEquals(3.00, Logika.hitungIPSFormatted(listMatkul), 0.001)
    }

    @Test
    fun testHitungIPS_kosong() {
        assertEquals(0.0, Logika.hitungIPS(emptyList()), 0.001)
    }

    @Test
    fun testHitungBatasSKS() {
        assertEquals(24, Logika.hitungBatasSKS(3.50))
        assertEquals(24, Logika.hitungBatasSKS(3.00))

        assertEquals(21, Logika.hitungBatasSKS(2.85))
        assertEquals(21, Logika.hitungBatasSKS(2.50))

        assertEquals(18, Logika.hitungBatasSKS(2.20))
        assertEquals(18, Logika.hitungBatasSKS(2.00))

        assertEquals(15, Logika.hitungBatasSKS(1.75))
        assertEquals(15, Logika.hitungBatasSKS(1.50))

        assertEquals(12, Logika.hitungBatasSKS(1.20))
        assertEquals(12, Logika.hitungBatasSKS(0.00))
    }

    @Test
    fun testGetBobotFromHuruf() {
        assertEquals(4.00, Logika.getBobotFromHuruf("A"), 0.001)
        assertEquals(3.75, Logika.getBobotFromHuruf("A-"), 0.001)
        assertEquals(3.50, Logika.getBobotFromHuruf("B+"), 0.001)
        assertEquals(3.00, Logika.getBobotFromHuruf("B"), 0.001)
        assertEquals(2.00, Logika.getBobotFromHuruf("C"), 0.001)
        assertEquals(0.00, Logika.getBobotFromHuruf("E"), 0.001)
    }
}
