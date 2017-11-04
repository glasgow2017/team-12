<?php 
$con = mysqli_connect('localhost','user','password', 'user_profiles');

if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con,'testdb')) {
 echo 'Database Not Selected';
}

session_start();
// get JSON user login data from Android app
$user_registration_info = file_get_contents("php://input");

// Extract relevant info to check in DB
$email = $user_registration_info["email"];
$password = $user_registration_info["password"];

// Check user profile DB-if 
$sql_usercheck = mysqli_prepare($con, "SELECT * FROM user_profile WHERE email='?' AND password='?");
mysqli_stmt_bind_param($sql_usercheck,"ss", $email, $password);

$successful_update = mysqli_stmt_execute($sql_usercheck);
if (!$successful_update) {
    echo 'User registration not successful';
}
mysqli_stmt_close($sql_usercheck);
?>