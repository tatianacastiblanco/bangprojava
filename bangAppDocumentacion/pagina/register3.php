<?php
    if(isset($_POST['key'])){
        $user = $_POST['user'];
        $ideas = $_POST['ideas'];
$response = "Ocurrio un error, vuelve a intentarlo.";
	$conn = mysqli_connect("localhost", "adminarcunoid", "#IUblFd745$%33jdH", "bang");
	if(!$conn){
		$response = "Ocurrio un error, vuelve a intentarlo";
	}else{
		$someArray = json_decode($ideas, true);
		$sqlId = "select MAX(ideas.id) as maxId from ideas";
		$resultId = $conn->query($sqlId);
		$idIdea = $resultId->fetch_array();
		$idIdea = $idIdea['maxId']+1;
		$sql = "insert into ideas(id, idea, mazo, necesidad, card1, card2, card3, user) values";
		for($i = 0; $i < count($someArray['ideas']); $i++){
			$sql.= "(".$idIdea.", ";
			for($j = 0; $j < count($someArray['ideas'][$i]); $j++){
				$sql.="'".$someArray['ideas'][$i][$j]."', ";
			}
			$sql = substr($sql, 0, strlen($sql)-2);
			$sql.=", '".$user."'), ";
			$idIdea++;
		}
		$sql = substr($sql, 0, strlen($sql)-2);
		$result = $conn->query($sql);
		if($result===true){
            $response = "Registrado";
        }else{
            $response = "Error".$conn->error;
        }
	}
	echo $response;
}else{
    $response = "No entro";
    echo $response;
}
?>