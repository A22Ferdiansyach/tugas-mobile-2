<?php
$include = 'koneksi.php';
$pathname = 'qrcode/';
$path = $pathname . $_GET['kode'] . '.png';
$conn = "UPDATE data_barang SET image = '$path' WHERE kode = '" . $_GET['kode'] . "'";
