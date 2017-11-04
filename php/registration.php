<?php 
/*require_once('config.php');*/
$con = mysqli_connect('localhost','user','password', 'user_profiles');

if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con,'user_profiles')) {
 echo 'Database Not Selected';
}

session_start();
// get JSON user registration data from Android app
$user_registration_info = file_get_contents("php://input");

// Extract relevant info to store in DB
$username = $user_registration_info["username"];
$phone_num = $user_registration_info["phone"];
$email = $user_registration_info["email"];
$password = $user_registration_info["password"];
$who_i_am = $user_registration_info['whoIAm'];
$here_for = $user_registration_info['imHereFor'];

// Check for duplicate usernames and emails
$sql_usercheck = mysqli_prepare($con, "SELECT userID FROM user_profile WHERE email='?' OR username='?");
mysqli_stmt_bind_param($sql_usercheck,"ss", $email, $username);
$count = mysqli_num_rows($sql_usercheck);
if ($count > 0){
    echo 'Error: user already exists!';
}
else {
    // Update user profile DB
    $sql_userprofile = mysqli_prepare($con, "INSERT INTO user_profile (username, phone_number, service_affiliation, experience, email, password) VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($sql_userprofile,"sssss", $username, $phone_num, $pickupcity, $who_i_am, $here_for, $email, $password);
    $successful_update = mysqli_stmt_execute($sql_userprofile);
    if (!$successful_update) {
        echo 'User registration not successful';
    }
}
mysqli_stmt_close($sql_usercheck);
mysqli_stmt_close($sql_userprofile);
?>