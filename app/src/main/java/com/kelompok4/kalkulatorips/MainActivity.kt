package com.kelompok4.kalkulatorips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok4.kalkulatorips.calculator.hitungBatasSksBerikutnya
import com.kelompok4.kalkulatorips.calculator.hitungIPSFormatted
import com.kelompok4.kalkulatorips.model.MataKuliah
import com.kelompok4.kalkulatorips.ui.theme.KalkulatorIPSDanSKSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorIPSDanSKSTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    KalkulatorIpsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun KalkulatorIpsScreen(modifier: Modifier = Modifier) {
    val contohDaftarMatkul = listOf(
        MataKuliah(kode = "IF101", nama = "Pemrograman Android", sks = 3, nilaiAngka = 88.0),
        MataKuliah(kode = "IF102", nama = "Basis Data lanjut", sks = 4, nilaiAngka = 78.0),
        MataKuliah(kode = "IF103", nama = "Algoritma & Struktur Data", sks = 3, nilaiAngka = 82.0),
        MataKuliah(kode = "IF104", nama = "Jaringan Komputer", sks = 3, nilaiAngka = 68.0),
        MataKuliah(kode = "IF105", nama = "Bahasa Inggris Teknik", sks = 2, nilaiAngka = 92.0)
    )

    val ips = hitungIPSFormatted(contohDaftarMatkul)
    val totalSks = contohDaftarMatkul.sumOf { it.sks }
    val batasSks = hitungBatasSksBerikutnya(ips)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Kalkulator IPS & SKS",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Indeks Prestasi Semester (IPS)",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = ips.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total SKS Ditempuh: $totalSks")
                    Text(
                        text = "Batas SKS Smt Depan: $batasSks SKS",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Daftar Mata Kuliah",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contohDaftarMatkul) { matkul ->
                MataKuliahItem(matkul = matkul)
            }
        }
    }
}

@Composable
fun MataKuliahItem(matkul: MataKuliah) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "${matkul.kode} - ${matkul.nama}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${matkul.sks} SKS | Nilai Angka: ${matkul.nilaiAngka ?: "-"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Huruf: ${matkul.huruf}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Bobot: ${matkul.bobot}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun KalkulatorIpsPreview() {
    KalkulatorIPSDanSKSTheme {
        KalkulatorIpsScreen()
    }
}
