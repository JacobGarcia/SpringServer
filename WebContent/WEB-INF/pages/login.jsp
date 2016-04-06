<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <!DOCTYPE html>
<html lang="en">
  <head>

    <!-- Basic Page Needs
    ================================================== -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <title>Web University</title>

    <!-- Mobile Specific Metas
    ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Favicon -->
    <link rel="shortcut icon" type="image/icon" href="img/favicon.png"/>

    <!-- CSS
    ================================================== -->       
    <!-- Bootstrap css file-->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Font awesome css file-->
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <!-- Superslide css file-->
    <link rel="stylesheet" href="css/superslides.css">
    <!-- Slick slider css file -->
    <link href="css/slick.css" rel="stylesheet"> 
    <!-- Circle counter cdn css file -->
    <link rel='stylesheet prefetch' href='https://cdn.rawgit.com/pguso/jquery-plugin-circliful/master/css/jquery.circliful.css'>  
    <!-- smooth animate css file -->
    <link rel="stylesheet" href="css/animate.css"> 
    <!-- gallery slider css -->
    <link type="text/css" media="all" rel="stylesheet" href="css/jquery.tosrus.all.css" />    
    <!-- Default Theme css file -->
    <link id="switcher" href="css/themes/default-theme.css" rel="stylesheet">
    <!-- Main structure css file -->
    <link href="css/style.css" rel="stylesheet">
   
    <!-- Google fonts -->
    <link href='http://fonts.googleapis.com/css?family=Merriweather' rel='stylesheet' type='text/css'>   
    <link href='http://fonts.googleapis.com/css?family=Varela' rel='stylesheet' type='text/css'>    
 
  </head>
  <body>    

    <!--=========== BEGIN HEADER SECTION ================-->
    <header id="header">
      <!-- BEGIN MENU -->
      <div class="menu_area">
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">  <div class="container">
            <div class="navbar-header">
              <!-- FOR MOBILE VIEW COLLAPSED BUTTON -->
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <!-- LOGO -->
              <!-- TEXT BASED LOGO -->
              <a class="navbar-brand" href="home">Web <span>University</span></a>              
            </div>

              <div id="navbar" class="navbar-collapse collapse">
              <ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
                <li class="active"><a href="#">Sign In</a></li>
              </ul>           
              <ul id="top-menu" class="nav navbar-nav navbar-right main-nav">
                <li class="active"><a href="#">Sign Up</a></li>
              </ul>   
            </div><!--/.nav-collapse -->
          </div>     
        </nav>  
      </div>
      <!-- END MENU -->    
    </header>
    <!--=========== END HEADER SECTION ================--> 

        <!--=========== BEGIN COURSE BANNER SECTION ================-->
    <section id="imgBanner">
      <h2>LOGIN SECTION</h2>
    </section>
    <!--=========== END COURSE BANNER SECTION ================-->

    <!--=========== BEGIN LOGIN SECTION ================-->
    <section id="contact">
      <div class="container">
       <div class="row">
         <div class="col-lg-8 col-md-8 col-sm-8">
           <div class="contact_form wow fadeInLeft">
              <form class="submitphoto_form" action="login/user" method="get">
                <input type="text" class="wp-form-control wpcf7-text" placeholder="Username" required name="username" value="eve">
                <input type="password" class="wp-form-control wpcf7-email" placeholder="Password" name="password"  value="eve" required> 
                <input type="submit" value="LOGIN"  name="bLogin" class="wpcf7-submit" formnovalidate>
              </form>
           </div>
         </div>
       </div>
       </div>
    </section>
    <!--=========== END LOGIN SECTION ================-->

  </body>
</html>