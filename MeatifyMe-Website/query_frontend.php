<?php
mysql_connect("mysql.2freehosting.com","u860682274_admin","fcouceiro94") or die ("Cant connect");
mysql_select_db("u860682274_db") or die("Cant select db");
 
if($q=mysql_query($_POST['query'])){
	while($e=mysql_fetch_assoc($q))
        $output[]=$e;

	print(json_encode($output));
}
else die(mysql_error());

 

 
mysql_close();
?>