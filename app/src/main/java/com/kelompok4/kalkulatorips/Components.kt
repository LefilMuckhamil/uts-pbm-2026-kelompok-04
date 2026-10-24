package com.kelompok4.kalkulatorips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

// Palette warna khusus tema aplikasi
val HeaderNavy = Color(0xFF0F2B5B)
val LightBg = Color(0xFFF4F6FB)
val CardBorderColor = Color(0xFFE2E8F0)
val PrimaryBlue = Color(0xFF1D61E7)
val TextDark = Color(0xFF1E293B)
val TextGray = Color(0xFF64748B)
val TrashRed = Color(0xFFEF4444)
val GreenSuccess = Color(0xFF10B981)
val GreenSuccessBg = Color(0xFFECFDF5)

// Header paling atas, udah pake stauts biar gak ketutup
@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderNavy)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2563EB)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Logo Kampus",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Kalkulator IPS & SKS",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cek nilai, hitung IPS, dan pantau batas SKS dengan mudah.",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp
                )
            }
        }
    }
}

// Card kecil ringkasan angka di bagian atas (IPS, SKS, Batas SKS)
@Composable
fun SummaryStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    subtext: String? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                color = TextGray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                if (subtext != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = subtext,
                        fontSize = 12.sp,
                        color = TextGray,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
        }
    }
}

// Card form tempat user nginput nama matkul, SKS, dan nilainya
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTambahMataKuliahCard(
    namaMatkul: String,
    onNamaChange: (String) -> Unit,
    sksSelected: Int?,
    onSksSelected: (Int) -> Unit,
    nilaiSelected: String?,
    onNilaiSelected: (String) -> Unit,
    onTambahClicked: () -> Unit
) {
    var expandedSks by remember { mutableStateOf(false) }
    var expandedNilai by remember { mutableStateOf(false) }

    val daftarSks = listOf(1, 2, 3, 4, 5, 6)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Judul form
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Tambah Mata Kuliah",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Textfield nama matkul
            Text(
                text = "Nama Mata Kuliah",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = namaMatkul,
                onValueChange = onNamaChange,
                placeholder = {
                    Text(
                        text = "Contoh: Algoritma dan Pemrograman",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = CardBorderColor
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dropdown milih jumlah SKS
            Text(
                text = "SKS",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = expandedSks,
                onExpandedChange = { expandedSks = !expandedSks },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = if (sksSelected != null) "$sksSelected SKS" else "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "Pilih jumlah SKS",
                            color = Color(0xFF94A3B8),
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Ikon SKS",
                            tint = TextGray,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSks) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = CardBorderColor
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedSks,
                    onDismissRequest = { expandedSks = false }
                ) {
                    daftarSks.forEach { sks ->
                        DropdownMenuItem(
                            text = { Text("$sks SKS") },
                            onClick = {
                                onSksSelected(sks)
                                expandedSks = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dropdown milih nilai huruf
            Text(
                text = "Nilai",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = expandedNilai,
                onExpandedChange = { expandedNilai = !expandedNilai },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = if (nilaiSelected != null) {
                        val bobot = Logika.dapatkanBobot(nilaiSelected)
                        "$nilaiSelected (${String.format(Locale.US, "%.2f", bobot)})"
                    } else "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "Pilih nilai huruf",
                            color = Color(0xFF94A3B8),
                            fontSize = 13.sp
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedNilai) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = CardBorderColor
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedNilai,
                    onDismissRequest = { expandedNilai = false }
                ) {
                    Logika.daftarPilihanNilai.forEach { (huruf, bobot) ->
                        DropdownMenuItem(
                            text = { Text("$huruf (${String.format(Locale.US, "%.2f", bobot)})") },
                            onClick = {
                                onNilaiSelected(huruf)
                                expandedNilai = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Tombol buat nyimpen data matkul
            Button(
                onClick = onTambahClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Simpan",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Simpan Mata Kuliah",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Tampilan tiap item matkul di dalam list, lengkap sama tombol hapus
@Composable
fun ItemMataKuliahCard(
    matkul: MataKuliah,
    onDeleteClicked: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = matkul.nama,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "SKS: ${matkul.sks}   |   Nilai: ${matkul.nilaiHuruf} (${String.format(Locale.US, "%.2f", matkul.bobot)})",
                    fontSize = 12.sp,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = String.format(Locale.US, "%.2f", matkul.bobot),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }

                IconButton(
                    onClick = onDeleteClicked,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Mata Kuliah",
                        tint = TrashRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// Card bagian bawah buat ngeliat statistik sebaran nilai & info syarat SKS
@Composable
fun RingkasanNilaiCard(
    daftarMataKuliah: List<MataKuliah>,
    ips: Double,
    batasSks: Int,
    totalSks: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header ringkasan
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = "Grafik Bar",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Ringkasan Nilai",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Distribusi Nilai",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            val gradesToShow = listOf(
                Triple("A", 4.00, Color(0xFF2563EB)),
                Triple("A-", 3.75, Color(0xFF10B981)),
                Triple("B+", 3.50, Color(0xFFF59E0B)),
                Triple("B", 3.00, Color(0xFFF97316)),
                Triple("C+", 2.50, Color(0xFFCBD5E1)),
                Triple("C", 2.00, Color(0xFFCBD5E1))
            )

            // Barchart mini sebaran nilai
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                gradesToShow.forEach { (huruf, bobot, colorBar) ->
                    val count = Logika.hitungJumlahNilai(daftarMataKuliah, huruf)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = huruf, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text(text = "(${String.format(Locale.US, "%.2f", bobot)})", fontSize = 8.sp, color = TextGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "$count", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(if (count > 0) colorBar else Color(0xFFE2E8F0))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Banner status kelayakan SKS semester depan
            val isSyaratLulus = ips >= 3.00
            val sisaSks = (batasSks - totalSks).coerceAtLeast(0)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(GreenSuccessBg)
                    .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(GreenSuccess),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Selesai",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Batas semester berikutnya: $batasSks SKS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46)
                            )
                            Text(
                                text = if (sisaSks > 0) "Kamu masih bisa mengambil $sisaSks SKS lagi." else "Jumlah SKS telah mencapai batas.",
                                fontSize = 11.sp,
                                color = Color(0xFF047857)
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Syarat OK",
                                tint = GreenSuccess,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "IPS ≥ 3,00",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46)
                            )
                        }
                        Text(
                            text = if (isSyaratLulus) "Memenuhi syarat" else "Di bawah syarat",
                            fontSize = 10.sp,
                            color = Color(0xFF047857)
                        )
                    }
                }
            }
        }
    }
}
