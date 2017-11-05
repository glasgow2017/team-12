<?php 
$config = require_once('config.php');
$con = mysqli_connect($config['HOST'], $config['USERNAME'], $config['PASSWORD'], $config['DATABASE']);
if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con,$config['DATABASE'])) {
 echo 'Database Not Selected';
 }
$response = array();
$code_f = "login_failed";
$code_s = "login_success";

session_start();
// Extract relevant info from input to check in DB
$email = mysqli_escape_string($con, $_POST["email"]);
$password = mysqli_escape_string($con, $_POST["password"]);
echo $email . $password;

// Check user profile DB-if unique user profile present then retrieve associated data
$sql_usercheck = mysqli_prepare($con, "SELECT userID FROM user_profiles WHERE email='?' AND password='?");
mysqli_stmt_bind_param($sql_usercheck,"ss", $email, $password);
mysqli_stmt_execute($sql_usercheck) or die("Unable to execute query");
mysqli_stmt_bind_result($sql_usercheck, $userID);

// Successful login
if(mysqli_stmt_fetch($sql_usercheck)){
  $_SESSION['user'] = $userID; // Set userID as session variable
  array_push($response, array("code"=>$code_s));
  echo json_encode($response);
// Unsuccesful login
} else {
  array_push($response, array("code"=>$code_f));
  echo json_encode($response);
}
mysqli_stmt_close($sql_usercheck);
?>