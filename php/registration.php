<?php 
require_once('config.php');
$con = mysqli_connect(HOST, USERNAME, PASSWORD, DATABASE);

if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con, DATABASE)) {
 echo 'Database Not Selected';
}
session_start();
// Extract relevant info from input to store in DB
$username = $_POST["username"];
$phone_num = $_POST["phone"];
$email = $_POST["email"];
$password = $_POST["password"];
$who_i_am = $_POST['whoIAm'];
$here_for = $_POST['imHereFor'];

// Check for duplicate usernames and emails
$sql_usercheck = mysqli_prepare($con, "SELECT userID FROM user_profiles WHERE email='?' OR username='?");
mysqli_stmt_bind_param($sql_usercheck,"ss", $email, $username);
$count = mysqli_num_rows($sql_usercheck);
if ($count > 0){
    echo 'Error: user already exists!';
}
else {
    $response = array();
    $code_f = "reg_failed";
    $code_s = "reg_success";
    // Update user profile DB
    $sql_userprofile = mysqli_prepare($con, "INSERT INTO user_profiles (username, phone_number, service_affiliation, experience, email, password) VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($sql_userprofile,"sssss", $username, $phone_num, $pickupcity, $who_i_am, $here_for, $email, $password);
    $successful_update = mysqli_stmt_execute($sql_userprofile);
    if (!$successful_update) {
        array_push($response, array("code"=>$code_f));
        echo json_encode($response);
    } else {
        array_push($response, array("code"=>$code_s));
        echo json_encode($response);
    }
}
mysqli_stmt_close($sql_usercheck);
mysqli_stmt_close($sql_userprofile);
?>