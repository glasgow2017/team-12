<?php 
$con = mysqli_connect('localhost','user','password', 'user_profiles');

if(!$con){
 echo 'Not Connected To Server';
}
if (!mysqli_select_db ($con,'user_profiles')) {
 echo 'Database Not Selected';
}
$response = array();
$code_f = "login_failed";
$code_s = "login_success";

session_start();
// Extract relevant info from input to check in DB
$email = $_POST["email"];
$password = $_POST["password"];

// Check user profile DB-if unique user profile present then retrieve associated data
$sql_usercheck = mysqli_prepare($con, "SELECT userID FROM user_profile WHERE email='?' AND password='?");
mysqli_stmt_bind_param($sql_usercheck,"ss", $email, $password);

if ($result = mysqli_query($link, $query)) {
    // Successful login
    if (mysqli_num_rows($result) > 0){
        $_SESSION['userid'] = $row[0]; // Set userID as session variable
        array_push($response, array("code"=>$code_s));
        echo json_encode($response);
    // Unsuccessful login
    } else {        
        array_push($response, array("code"=>$code_f));
        echo json_encode($response);
    }
    mysqli_free_result($result);
}
mysqli_stmt_close($sql_usercheck);
?>