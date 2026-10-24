package com.kelompok4.kalkulatorips

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun MainScreen() {
    val context = LocalContext.current

    // Data awal sampel matkul biar pas pertama kali buka langsung keliatan cantik
    val daftarMataKuliah = remember {
        mutableStateListOf(
            MataKuliah(nama = "Algoritma dan Pemrograman", sks = 3, nilaiHuruf = "A", bobot = 4.00),
            MataKuliah(nama = "Matematika Diskrit", sks = 3, nilaiHuruf = "B+", bobot = 3.50),
            MataKuliah(nama = "Sistem Informasi", sks = 2, nilaiHuruf = "A-", bobot = 3.75),
            MataKuliah(nama = "Basis Data", sks = 3, nilaiHuruf = "B", bobot = 3.00),
            MataKuliah(nama = "Jaringan Komputer", sks = 3, nilaiHuruf = "A", bobot = 4.00),
            MataKuliah(nama = "Pemrograman Web", sks = 3, nilaiHuruf = "B+", bobot = 3.50),
        )
    }

    // State penampung inputan form
    var namaInput by remember { mutableStateOf("") }
    var sksSelected by remember { mutableStateOf<Int?>(null) }
    var nilaiSelected by remember { mutableStateOf<String?>(null) }

    // Hitung ulang statistik tiap kali data matkul berubah
    val ips = Logika.hitungIPSFormatted(daftarMataKuliah)
    val totalSks = Logika.hitungTotalSKS(daftarMataKuliah)
    val batasSks = Logika.hitungBatasSKS(ips)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        // Banner header atas
        item {
            HeaderSection()
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Baris kartu indikator (IPS, Total SKS, Batas SKS)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SummaryStatCard(
                        title = "IPS Semester",
                        value = String.format(Locale.US, "%.2f", ips),
                        icon = Icons.Default.Analytics,
                        iconBgColor = Color(0xFFEFF6FF),
                        iconTint = Color(0xFF2563EB),
                        modifier = Modifier.weight(1f)
                    )

                    SummaryStatCard(
                        title = "Total SKS",
                        value = "$totalSks",
                        subtext = "/ 144",
                        icon = Icons.Default.Book,
                        iconBgColor = Color(0xFFECFDF5),
                        iconTint = Color(0xFF10B981),
                        modifier = Modifier.weight(1f)
                    )

                    SummaryStatCard(
                        title = "Batas SKS",
                        value = "$batasSks",
                        subtext = "/ 144",
                        icon = Icons.Default.TrackChanges,
                        iconBgColor = Color(0xFFF3E8FF),
                        iconTint = Color(0xFF9333EA),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Form buat nambah matkul baru
                FormTambahMataKuliahCard(
                    namaMatkul = namaInput,
                    onNamaChange = { namaInput = it },
                    sksSelected = sksSelected,
                    onSksSelected = { sksSelected = it },
                    nilaiSelected = nilaiSelected,
                    onNilaiSelected = { nilaiSelected = it },
                    onTambahClicked = {
                        if (namaInput.isBlank()) {
                            Toast.makeText(context, "Isi dulu nama mata kuliahnya ya!", Toast.LENGTH_SHORT).show()
                            return@FormTambahMataKuliahCard
                        }
                        if (sksSelected == null) {
                            Toast.makeText(context, "Pilih jumlah SKS terlebih dahulu!", Toast.LENGTH_SHORT).show()
                            return@FormTambahMataKuliahCard
                        }
                        if (nilaiSelected == null) {
                            Toast.makeText(context, "Pilih nilai hurufnya dulu ya!", Toast.LENGTH_SHORT).show()
                            return@FormTambahMataKuliahCard
                        }

                        val bobot = Logika.dapatkanBobot(nilaiSelected!!)
                        daftarMataKuliah.add(
                            0, // Taruh paling atas
                            MataKuliah(
                                nama = namaInput.trim(),
                                sks = sksSelected!!,
                                nilaiHuruf = nilaiSelected!!,
                                bobot = bobot
                            )
                        )

                        // Riset inputan setelah berhasil
                        namaInput = ""
                        sksSelected = null
                        nilaiSelected = null
                        Toast.makeText(context, "Sip! Mata kuliah berhasil ditambah", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Judul daftar matkul
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatListBulleted,
                            contentDescription = "Daftar Matkul",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Daftar Mata Kuliah (${daftarMataKuliah.size})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
            }
        }

        // List matkul yang udah diinput
        items(
            items = daftarMataKuliah,
            key = { it.id }
        ) { matkul ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                ItemMataKuliahCard(
                    matkul = matkul,
                    onDeleteClicked = {
                        daftarMataKuliah.remove(matkul)
                        Toast.makeText(context, "Mata kuliah dihapus", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        // Section ringkasan nilai & sebaran
        item {
            Box(modifier = Modifier.padding(16.dp)) {
                RingkasanNilaiCard(
                    daftarMataKuliah = daftarMataKuliah,
                    ips = ips,
                    batasSks = batasSks,
                    totalSks = totalSks
                )
            }
        }
    }
}
