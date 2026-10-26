UTS Pemrograman Berbasis Mobile 2026
Kelompok 04 – Kasus 4 Kalkulator IPS dan Batas SKS
Program Studi Teknologi Informasi Fakultas Teknik Universitas Teuku Umar
Dosen Pengampu: Rivansyah Suhendra, S.Kom., M.T.

Ringkasan Aplikasi
Aplikasi Kalkulator IPS dan SKS ini dibuat menggunakan Kotlin dan Jetpack Compose dalam bentuk aplikasi satu layar. Aplikasi ini bertujuan membantu mahasiswa menghitung Indeks Prestasi Semester (IPS) secara langsung, mengetahui total SKS yang diambil, melihat sebaran nilai mutu, serta menentukan batas maksimal SKS yang bisa diambil pada semester berikutnya sesuai aturan kampus.

Anggota Kelompok dan Pembagian Peran

1. 
Mengelola state utama di MainScreen.kt, penyetelan MainActivity.kt, integrasi antarmuka, dan pengujian skenario unit test.

2. Lefil Muckhamil (2205903040076) - Lefil Muckhamil
Merancang model data MataKuliah.kt, fungsi kalkulasi logika Kotlin di Logika.kt, serta aturan bisnis perhitungan IPS dan SKS.

3. Mutia Wati
Membuat formulir input mata kuliah FormTambahMataKuliahCard, penanganan validasi input, serta pesan eror form.

4. Nazla Chairunnisa
Mengembangkan komponen kartu mata kuliah ItemMataKuliahCard, fitur hapus item, serta tampilan saat data kosong.

5. Natijah 
Menyusun tata letak halaman HeaderSection dan SummaryStatCard, penyesuaian warna tema aplikasi, serta kartu ringkasan nilai.

Tautan Penting
Video Presentasi YouTube
Repositori GitHub: https://github.com/LefilMuckhamil/uts-pbm-2026-kelompok-04

Tangkapan Layar Aplikasi

Langkah Menjalankan Proyek di Android Studio

1. Spesifikasi Perangkat Lunak
Android Studio Ladybug atau versi terbaru.
JDK 11 atau JDK 17.
Minimum SDK: API Level 30 (Android 11.0).
Target SDK: API Level 35.

2. Kloning Repositori
Buka Terminal atau Git Bash, lalu jalankan perintah berikut:
git clone https://github.com/LefilMuckhamil/uts-pbm-2026-kelompok-04.git

3. Membuka dan Menjalankan Proyek
Buka Android Studio, pilih menu Open, lalu arahkan ke folder hasil clone.
Tunggu proses Gradle Sync sampai selesai.
Jalankan aplikasi menggunakan Emulator Android atau langsung dari HP.
