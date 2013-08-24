<!DOCTYPE html>
<html>

<head>
<title>MeatifyMe :: Level :: drop-off</title>
	 <link href="bootstrap/css/bootstrap.css" rel="stylesheet">  
	

	 <style>
	 body {
        padding-top: 40px; 
        background-color: #000;
      }
	 </style>
</head>
<body>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
	 <script src="bootstrap/js/bootstrap.js"></script>
	 <script src="bootstrap/js/bootstrap-collapse.js"></script>
	 <script src="bootstrap/js/bootstrap-transition.js"></script>

<div class="navbar navbar-inverse">
              <div class="navbar-inner">
                <div class="container">
                  <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                  </a>
                  <a class="brand" href="index.php">MeatifyMe </a>
                  <div class="nav-collapse collapse navbar-responsive-collapse">
                    <ul class="nav">
                      <li class="active"><a href="#"><i class="icon-home icon-white"></i> Home</a></li>
                    </ul>
                    <form class="navbar-search pull-left" action="#">
                      <input name="search_q" id="search_q" type="text" class="search-query span2" placeholder="Search">
                    </form>
                    <ul class="nav pull-right">
                      <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">More <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                          <li><a href="admin.php"><i class="icon-wrench"></i> Admin</a></li>
                        </ul>
                      </li>
                    </ul>
                  </div><!-- /.nav-collapse -->
                </div>
              </div><!-- /navbar-inner -->
            </div>
            
	<div class="container well">
    <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Name</th>
                  <th>Creator</th>
                </tr>
              </thead>
              <tbody>
                <?php
              mysql_connect("mysql.2freehosting.com","u860682274_admin","fcouceiro94") or die ("Cant connect");
              mysql_select_db("u860682274_db") or die("Cant select db");

              if($q=mysql_query("SELECT * FROM users;")){

                while($e=mysql_fetch_array($q)){
                  
                  
                  
                  $directory = $e['username'];

                  $it = new RecursiveIteratorIterator(new RecursiveDirectoryIterator($directory));

                  $i = 0;
                  while($it->valid()) {
                    $i++;
                    if (!$it->isDot()) {
                      print "<tr><td>";
                      print $i;
                      print "</td><td><a href='http://meatifyme-dropoff.ciki.me/download.php?name="; 
                      $nome = str_replace(".xml", "", $it->getSubPathName());
                      print $nome.".xml";
                      print "&author=".$directory."'>";
                      print $nome;
                      print "</a>";
                      print "</td><td>";
                      print $directory;
                      print "</td></tr>";
                    }

                    $it->next();
                  }
                                  
                }



              }
              else die(mysql_error());




              mysql_close();
              ?>
              </tbody>
            </table>
            <div style="text-align:center">
          </div>
	</div>

    

	 <br>

   <div class="container" style="text-align:center">
    <div class="label" style="color:red">
      <a style="color: white;" href="http://pt.linkedin.com/pub/francisco-couceiro/58/555/a02">&copy Ruthless Games, Co. </a>
    </div>
  </div>
</body>
</html>