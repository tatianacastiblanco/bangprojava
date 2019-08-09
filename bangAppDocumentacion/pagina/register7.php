<?php
    $key = $_POST['key'];
    $user = $_POST['user'];
    $name = $_POST['name'];
	$idea = $_POST['idea'];
	$b64 = $_POST['b64'];
	$response = "Ocurrio un error, vuelve a intentarlo.";
	$conn = mysqli_connect("localhost", "adminarcunoid", "#IUblFd745$%33jdH", "bang");
	if(!$conn){
		$response = "Ocurrio un error, vuelve a intentarlo";
	}else{
			$sqlId = "select id from ideas where idea = '".$idea."' and user = '".$user."'";
			$resultId = $conn->query($sqlId);
			if($resultId->num_rows>0){
				$idIdea = $resultId->fetch_array();
				$idIdea = $idIdea['id'];
				$sqlImg = "select max(id) as idImg from images";
				$resultIdImg = $conn->query($sqlImg);
				$idImg = $resultIdImg->fetch_array();
				$idImg = $idImg['idImg']+1;
				$sql2 = "insert into images(id, image, idea) values($idImg, 'bangs/".$user."/".$name."', ".$idIdea.")";
				$result = $conn->query($sql2);
				if($result===true){
					$response = "Registrado";
				}else{
					$response = "Error".$conn->error;
				}
				$response .= "Entre2";
				$binary = base64_decode($b64);
				if(!file_exists('bangs/'.$user.'/')){
					mkdir('bangs/'.$user.'/', 0777, true);
				}
				$file = fopen('bangs/'.$user.'/'.$name, 'wb');
				print_r(error_get_last());
				fwrite($file, $binary);
				print_r(error_get_last());
				fclose($file);
				print_r(error_get_last());
			}else{
				$response = "sin datos";
			}
	}
	echo $response;
?>