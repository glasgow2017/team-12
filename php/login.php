<?php 
$con = mysqli_connect('localhost','user','password', 'user_profiles');

if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con,'user_profiles')) {
 echo 'Database Not Selected';
}

session_start();
// get JSON user login data from Android app
$user_registration_info = file_get_contents("php://input");

// Extract relevant info to check in DB
$email = $user_registration_info["email"];
$password = $user_registration_info["password"];

// Check user profile DB-if unique user profile present then retrieve associated data
$sql_usercheck = mysqli_prepare($con, "SELECT userID FROM user_profile WHERE email='?' AND password='?");
mysqli_stmt_bind_param($sql_usercheck,"ss", $email, $password);

// Return unique user ID if user login info is correct
if ($result = mysqli_query($link, $query)) {
    while ($row = mysqli_fetch_row($result)) {
        // Set userID as session variable
        $_SESSION['userid'] = $row[0];
    }
    mysqli_free_result($result);
}
mysqli_stmt_close($sql_usercheck);
?>