<?php
if(isset($_POST['register'])){
	$user = $_POST['user'];
	$name = $_POST['name'];
	$mail = $_POST['mail'];
	$date = $_POST['date'];
	$genero = $_POST['genero'];
    $educ = $_POST['educacion'];
    $ocup = $_POST['ocupacion'];
    $preg = $_POST['pregunta'];
	$type = $_POST['type'];
	$response = "Ocurrio un error, vuelve a intentarlo.";
	$conn = mysqli_connect("localhost", "adminarcunoid", "#IUblFd745$%33jdH", "bang");
	if(!$conn){
		$response = "Ocurrio un error, vuelve a intentarlo";
	}else{
		$sql = "insert into user(user, name, mail, birthDate, genero, estudio, ocupacion, pregunta) values('$user', '$name', '$mail', '$date', '$genero', '$educ', '$ocup', '$preg')";
		/*$sql2 = "select * from user";
		$sql3 = "Truncate table user";*/
		$result = $conn->query($sql);
		if($result===true){
			$response = "Registrado";
		}else{
			$response = "Error".$conn->error;
		}
		/*if($result->num_rows > 0){
			while($row = $result->fetch_assoc()){
				echo "user: ".$row['user']." name: ".$row['name']." mail: ".$row['mail']." date: ".$row['birthDate']." genero: ".$row['genero']." educacion: ".$row['estudio']." ocupacion: ".$row['ocupacion']." pregunta: ".$row['pregunta']."<br>";
			}
			$response = "1";
		}else{
			$response = "0";
		}*/
	}
	echo $response;
}else{
    $response = "No entro";
    echo $response;
}
?>