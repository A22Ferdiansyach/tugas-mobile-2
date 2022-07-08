<?php
include_once('connection2.php');

class emp
{
}

$image = $_POST['gambar'];
$kode = $_POST['kode'];

$nama_file = $kode . ".png";

$path = "image_product/" . $nama_file;

// sesuiakan ip address laptop/pc atau URL server
$actualpath = "http://192.168.11.19/android/upload_image/$path";

$query = mysqli_query($conn, "INSERT INTO barang (kode, gambar) VALUES ('$kode', '$nama_file')");

if ($query) {
	file_put_contents($path, base64_decode($image));

	$response = new emp();
	$response->success = 1;
	$response->message = "Nota sudah disimpan";
	die(json_encode($response));
} else {
	$response = new emp();
	$response->success = 0;
	$response->message = "Upload nota gagal";
	die(json_encode($response));
}
mysqli_close($con);
