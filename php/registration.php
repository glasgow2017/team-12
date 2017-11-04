<?php 
/*require_once('config.php');*/
$con = mysqli_connect('localhost','root','password', 'testdb');

if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con,'testdb')) {
 echo 'Database Not Selected';
}

session_start();
// get JSON user registration data from Android app
$user_registration_info = file_get_contents("php://input");

// Extract relevant info to store in DB
$username = 'x';
$phone_num = '78594650265';
$email = '';
$service_affiliation = '';
$experience = '';

// username, email, phone, password -JSON format



?>