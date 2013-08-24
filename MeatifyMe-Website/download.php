<?php
$level_name = $_GET['name'];
$author = $_GET['author'];
$file = $author . "/" . $level_name;

header('Content-disposition: attachment; filename='.$level_name);
header('Content-type: application/xml');
readfile($file);
?>