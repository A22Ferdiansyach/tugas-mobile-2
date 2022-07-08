<?php
$servername = "localhost";
$username = "root";
$dbname = "qrcode";
$password = "";



try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $kode = $_POST['kode'];
    $jumlah = $_POST['jumlah'];


    // Menghapus barang jika stok = 0

    if ($jumlah == 0 || $jumlah < 0) {
        $msql = "delete from data_barang where kode = ?";
        $stat = $conn->prepare($msql);
        $res = $stat->execute([$kode]);
        if ($res) {
            $data = ['kode' => $kode, 'jumlah' => $jumlah];
            echo json_encode($data);
        } else {
            echo json_encode(['error' => $stat->errorCode()]);
        }
    }

    // Mengurangi barang -1 jika telah dilakukan transaksi

    if ($jumlah != 0) {
        $msql = "update data_barang set jumlah = $jumlah   where kode = ?";
        $stat = $conn->prepare($msql);
        $res = $stat->execute([$kode]);
        if ($res) {
            $data = ['kode' => $kode, 'jumlah' => $jumlah];
            echo json_encode($data);
        } else {
            echo json_encode(['error' => $stat->errorCode()]);
        }
    }
}