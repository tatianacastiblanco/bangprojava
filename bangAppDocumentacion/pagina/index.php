<html>
<style>
table, th, td {
  border: 1px solid black;
  text-align: center;
  word-wrap: break-word;
  max-width:300px;
}
</style>
<script>
function changeTable(colum){
	document.getElementById("colum").setAttribute('width', 20px);
}
</script>
<form action="/" method = "post">
  <input type="checkbox" name="Usuario" value="Bike"> Usuario<br>
  <input type="checkbox" name="Genero" value="Bike"> Genero<br>
  <input type="checkbox" name="Estudio" value="Car"> Estudio<br>
  <input type="checkbox" name="Ocupacion" value="Boat"> Ocupación<br>
  <input type="checkbox" name="Pregunta" value="Boat"> ¿Cómo nos conocio?<br>
  <input type="checkbox" name="Planeta" value="Car"> Planeta<br>
  <input type="checkbox" name="Categoria" value="Boat"> Categoria<br>
  <input type="checkbox" name="Puntaje" value="Boat"> Puntaje<br>
  <input type = "submit" name = "filtro" value = "Aplicar filtro">
</form>
<input type = "submit" name = "w" value = "Aplicar s" onclick="changeTable('genero')">
<Table>
<tr>
<th>Usuario</th>
<th>Nombre</th>
<th>Genero</th>
<th>Cumpleaños</th>
<th>Nivel de estudio</th>
<th>Ocupación</th>
<th>Mail</th>
<th>¿Cómo nos conocio?</th>
<th>Planeta</th>
<th>Necesidad</th>
<th>Card1</th>
<th>Card2</th>
<th>Card3</th>
<th>Idea</th>
<th>Image</th>
<th>Puntaje</th>
</tr>
<tr>
<?php
	$response = "Ocurrio un error, vuelve a intentarlo";
	$conn = mysqli_connect("localhost", "adminarcunoid", "#IUblFd745$%33jdH", "bang");
	if(!$conn){
		$response = "Ocurrio un error, vuelve a intentarlo";
	}else{
		if(isset($_POST['filtro'])){
			
		}
		$tarjetas = array(
			array("El cielo es un vecindario", "Japón", "Humanice cualquier cosa", "Empieza por un garaje", "¿Qué pasaría si…?", "Pon otros usos", "Dale un vuelco a tu rutina", "Lego"),
            array("Quita las partes importantes", "Piensa cosas imposibles de hacer", "Cambia de roles", "Great Place To Work?", "Haz trocitos y después júntalos", "¿Qué soñaste ayer?", "Cuantas manos y cabezas sean necesarias", "Modifica", "Duro como una roca, flexible como…"),
            array("Piensa como un lobo", "Combina lo incombinable", "Corta una conexión", "Elimina, Reduce, Incrementa y crea", "Investiga tres tendencias en el área humana", "Observa de cerca los detalles más vergonzosos y amplíalos", "Sé aburrido", "Adapta"),
            array("Haz una lista de diez cosas que podrías hacer y haz lo último en la lista", "Elimina", "¿Qué está haciendo el sector de alimentos?", "Una sencilla solución, dos difíciles soluciones", "Piensa como Walt Disney", "Cambia de roles", "Japón", "Toma un descanso", "Mira el lado oscuro de la historia"),
            array("Singapur", "Busca un libro y lee su primer párrafo", "Desordénalo todo", "Papel", "Piensa como Steve Jobs", "¿Qué haría tu mejor amigo?", "Juega el juego", "Haz cosas imperfectas", "Déjà vu"));
		$sql1 = "select user.user, name, mail, birthDate, genero, estudio, ocupacion, pregunta, score, idea, mazo, necesidad, card1, card2, card3 from user inner join ideas on user.user = ideas.user";
		$result = $conn->query($sql1);
		if($result->num_rows > 0){
			while($row = $result->fetch_assoc()){
				$sql2 = "select image from images inner join ideas on images.idea = ideas.id where ideas.idea = '".$row['idea']."'";
				$result2 = $conn->query($sql2);
				$planeta = "Error";
				$color = "#ffffff";
				if($row['mazo'] == "Crispi"){
					$planeta = "Vorann(Crispi)";
					$color = "#5386f4";
				}else if($row['mazo'] == "Cesia"){
					$planeta = "Dirvon(Cesia)";
					$color = "#d04c42";
				}else if($row['mazo'] == "Cori"){
					$planeta = "Norali(Cori)";
					$color = "#80b587";
				}else if($row['mazo'] == "Carmel"){
					$planeta = "Ventio(Carmel)";
					$color = "#ebc350";
				}else if($row['mazo'] == "Cristal"){
					$planeta = "Inaria(Cristal)";
					$color = "#363660";
				}
				$color2 = "#ffffff";
				for($i = 0; $i < 5; $i++){
					for($j = 0; $j < count($tarjetas[$i]); $j++){
						if($row['card2'] == $tarjetas[$i][$j]){
							if($i == 0){
								$color2 = "#5386f4";
							}else if($i == 1){
								$color2 = "#d04c42";
							}else if($i == 2){
								$color2 = "#80b587";
							}else if($i == 3){
								$color2 = "#ebc350";
							}else if($i == 4){
								$color2 = "#363660";
							}
							end;
						}
					}
				}
				echo "<td>".$row['user']."</td><td>".$row['name']."</td><td name='genero'>".$row['genero']."</td><td>".$row['birthDate']."</td><td>".$row['estudio']."</td><td>".$row['ocupacion']."</td><td>".$row['mail']."</td><td>".$row['pregunta']."</td><td bgcolor='".$color."'>".$planeta."</td><td bgcolor='".$color."'>".$row['necesidad']."</td><td bgcolor='".$color."'>".$row['card1']."</td><td bgcolor='".$color2."'>".$row['card2']."</td><td bgcolor='".$color2."'>".$row['card3']."</td><td bgcolor='".$color."'>".$row['idea'];
				if($result2->num_rows>0){
					$row2 = $result2->fetch_array();
					echo "</td><td><img src='".$row2['image']."' width='135' height='240'>";
				}else{
					echo "</td><td>-------";
				}
				echo "</td><td>".$row['score']."</td></tr>";
			}
			$response = "";
        }else{
            $response = "No Existe2";
        }
		echo "</Table>";
	}
	echo $response;
?>