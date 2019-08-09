<?php
if(isset($_POST['key'])){
	$user = $_POST['user'];
	$response = "Ocurrio un error, vuelve a intentarlo.";
	$conn = mysqli_connect("localhost", "adminarcunoid", "#IUblFd745$%33jdH", "bang");
	if(!$conn){
		$response = "Ocurrio un error, vuelve a intentarlo";
	}else{
		//$sql1 = "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA  = 'bang'";
		//$sql1 = "Describe ideas";
        $sql1 = "select user from user where user = '$user'";
		//$sql1 = "create table images(id int(45) primary key, image varchar(145), idea int(45), FOREIGN KEY(idea) REFERENCES ideas(id));";
		//$sql1 = "select * from cards_usages";
		$result = $conn->query($sql1);
        if($result->num_rows > 0){
			/*while($row = $result->fetch_assoc()){
                //echo "id: ".$row['Field']." Idea: ".$row['Type']."<br>";
				//echo "id: ".$row['id']." Idea: ".$row['idea']." Mazo: ".$row['mazo']." Necesidad: ".$row['necesidad']."user: ".$row['user']."<br>";
				//echo "id: ".$row['id']." Idea: ".$row['ideasId']." Mazo: ".$row['cardsId']."<br>";
            }*/
            $response = "Existe";
        }else{
            $response = "No existe";
        }
		/*if($result===true){
            $response = "Registrado";
        }else{
            $response = "Error".$conn->error;
        }*/
	}
	echo $response;
}else{
    $response = "No entro";
    echo $response;
}
?>