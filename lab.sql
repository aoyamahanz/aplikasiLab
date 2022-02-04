-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 04, 2022 at 04:37 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lab`
--

-- --------------------------------------------------------

--
-- Table structure for table `data_barang`
--

CREATE TABLE `data_barang` (
  `kd_barang` int(11) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `deskripsi` varchar(100) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_barang`
--

INSERT INTO `data_barang` (`kd_barang`, `nama_barang`, `deskripsi`, `stok`) VALUES
(6, 'crimping tools', 'Alat untuk memotong cable dan mengunci rj45', 8),
(7, 'lan tester', 'Alat untuk mengecek apakah kabel lan sudah terkonfigutasi dengan baik', 9),
(10, 'qwert', 'qwert', 10);

-- --------------------------------------------------------

--
-- Table structure for table `data_peminjam`
--

CREATE TABLE `data_peminjam` (
  `id` varchar(11) NOT NULL,
  `nis_nik` varchar(50) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `no_tlp` varchar(15) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_peminjam`
--

INSERT INTO `data_peminjam` (`id`, `nis_nik`, `nama`, `jenis_kelamin`, `no_tlp`, `role`) VALUES
('1212201', '12160500', 'rifqi', 'laki-laki', '082123456789', 'Guru'),
('1217673', '12160600', 'rusandika', 'laki-laki', '082134567890', 'Guru'),
('1219566', '12160400', 'herman', 'laki-laki', '082299398409', 'Murid');

-- --------------------------------------------------------

--
-- Table structure for table `data_peminjaman`
--

CREATE TABLE `data_peminjaman` (
  `kd_peminjaman` int(11) NOT NULL,
  `tgl_peminjaman` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_peminjaman`
--

INSERT INTO `data_peminjaman` (`kd_peminjaman`, `tgl_peminjaman`) VALUES
(146, '2021-08-31 12:42:47'),
(147, '2021-08-31 12:45:28'),
(148, '2022-02-04 13:56:43'),
(149, '2022-02-04 14:18:49'),
(150, '2022-02-04 14:23:01'),
(151, '2022-02-04 15:28:56');

-- --------------------------------------------------------

--
-- Table structure for table `data_pengembalian`
--

CREATE TABLE `data_pengembalian` (
  `kd_pengembalian` int(11) NOT NULL,
  `kd_peminjaman` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `data_pengembalian`
--

INSERT INTO `data_pengembalian` (`kd_pengembalian`, `kd_peminjaman`) VALUES
(43, 146),
(44, 146),
(45, 150),
(46, 146),
(47, 151),
(48, 150);

-- --------------------------------------------------------

--
-- Table structure for table `detail_peminjaman`
--

CREATE TABLE `detail_peminjaman` (
  `kd_peminjaman` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `stok` int(11) NOT NULL,
  `deskripsi_peminjaman` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_peminjaman`
--

INSERT INTO `detail_peminjaman` (`kd_peminjaman`, `id`, `nama_barang`, `stok`, `deskripsi_peminjaman`, `status`) VALUES
(146, 1212201, 'lan tester', 2, 'test', 'belum kembali'),
(146, 1212201, 'crimping tools', 1, 'test', 'sudah kembali'),
(147, 1212201, 'crimping tools', 1, 'test', 'sudah kembali'),
(148, 1212201, 'crimping tools', 1, 'test', 'sudah kembali'),
(149, 1212201, 'crimping tools', 1, 'test', 'belum kembali'),
(150, 1212201, 'crimping tools', 1, 'test', 'sudah kembali'),
(150, 1212201, 'lan tester', 1, 'test', 'sudah kembali'),
(151, 1212201, 'crimping tools', 1, 'test', 'sudah kembali'),
(151, 1212201, 'lan tester', 1, 'test', 'belum kembali');

-- --------------------------------------------------------

--
-- Table structure for table `detail_pengembalian`
--

CREATE TABLE `detail_pengembalian` (
  `kd_pengembalian` int(11) NOT NULL,
  `tgl_peminjaman` timestamp NULL DEFAULT NULL,
  `tgl_pengembalian` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id` int(11) NOT NULL,
  `nama_barang` varchar(30) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_pengembalian`
--

INSERT INTO `detail_pengembalian` (`kd_pengembalian`, `tgl_peminjaman`, `tgl_pengembalian`, `id`, `nama_barang`, `stok`) VALUES
(43, '2021-08-31 12:42:47', '2021-08-31 12:43:04', 1212201, 'crimping tools', 1),
(44, '2021-08-31 12:42:47', '2021-08-31 12:43:20', 1212201, 'lan tester', 2),
(45, '2022-02-04 14:23:01', '2022-02-04 14:24:43', 1212201, 'crimping tools', 1),
(46, '2021-08-31 12:42:47', '2022-02-04 15:26:22', 1212201, 'crimping tools', 1),
(47, '2022-02-04 15:28:56', '2022-02-04 15:29:18', 1212201, 'crimping tools', 1),
(48, '2022-02-04 14:23:01', '2022-02-04 15:36:25', 1212201, 'lan tester', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `nama`) VALUES
(1, 'admin', 'admin', 'administrator');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `data_barang`
--
ALTER TABLE `data_barang`
  ADD PRIMARY KEY (`kd_barang`);

--
-- Indexes for table `data_peminjam`
--
ALTER TABLE `data_peminjam`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `data_peminjaman`
--
ALTER TABLE `data_peminjaman`
  ADD PRIMARY KEY (`kd_peminjaman`);

--
-- Indexes for table `data_pengembalian`
--
ALTER TABLE `data_pengembalian`
  ADD PRIMARY KEY (`kd_pengembalian`);

--
-- Indexes for table `detail_peminjaman`
--
ALTER TABLE `detail_peminjaman`
  ADD KEY `detail_peminjaman_ibfk_1` (`kd_peminjaman`);

--
-- Indexes for table `detail_pengembalian`
--
ALTER TABLE `detail_pengembalian`
  ADD KEY `kd_pengembalian` (`kd_pengembalian`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `data_barang`
--
ALTER TABLE `data_barang`
  MODIFY `kd_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `data_peminjaman`
--
ALTER TABLE `data_peminjaman`
  MODIFY `kd_peminjaman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=152;

--
-- AUTO_INCREMENT for table `data_pengembalian`
--
ALTER TABLE `data_pengembalian`
  MODIFY `kd_pengembalian` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_peminjaman`
--
ALTER TABLE `detail_peminjaman`
  ADD CONSTRAINT `detail_peminjaman_ibfk_1` FOREIGN KEY (`kd_peminjaman`) REFERENCES `data_peminjaman` (`kd_peminjaman`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `detail_pengembalian`
--
ALTER TABLE `detail_pengembalian`
  ADD CONSTRAINT `detail_pengembalian_ibfk_1` FOREIGN KEY (`kd_pengembalian`) REFERENCES `data_pengembalian` (`kd_pengembalian`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
