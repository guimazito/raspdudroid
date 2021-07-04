<html>
<head>
	<link rel="shortcut icon" href="images/home.ico" type="image/x-icon" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />	
	<meta name="description" content="Casa Inteligente" />	
	<link rel="stylesheet" href="css/estilo.css">
	<title>Raspdudroid Web</title>
</head>
<body>
	<center><h1>PAINEL DE CONTROLE</h1></center>
	<center>

<?php
	//AÇÃO CORRESPONDENTE AO BOTÃO	
	if(isset($_POST['bits'])) {
		$msg = $_POST['bits'];
		if(isset($_POST['Quarto'])){ if($msg[0]=='0') { $msg[0]='1'; serialArduino('1'); }else{ $msg[0]='0'; serialArduino('2'); }}
		if(isset($_POST['Garagem'])){ if($msg[1]=='0') { $msg[1]='1'; serialArduino('4'); }else{ $msg[1]='0'; serialArduino('5'); }}
		if(isset($_POST['ClimaQuartoL'])){ if($msg[4]=='0') { $msg[4]='1'; serialArduino('6'); }else{ $msg[4]='0'; serialArduino('6'); }}
		if(isset($_POST['ClimaQuartoD'])){ if($msg[5]=='0') { $msg[5]='1'; serialArduino('7'); }else{ $msg[5]='0'; serialArduino('7'); }}
		if(isset($_POST['ClimaDiminuir'])){ if($msg[6]=='0') { $msg[6]='1'; serialArduino('a'); }else{ $msg[6]='0'; serialArduino('a'); }}
		if(isset($_POST['ClimaAumentar'])){ if($msg[7]=='0') { $msg[7]='1'; serialArduino('b'); }else{ $msg[7]='0'; serialArduino('b'); }}
		if(isset($_POST['Jardim'])){ if($msg[8]=='0') { $msg[8]='1'; serialArduino('8'); }else{ $msg[8]='0'; serialArduino('8'); }}
	}

	//ÚLTIMO STATUS
	$port = fopen('/dev/ttyACM0', 'w+');
	sleep(2);
	fwrite($port, 's');
	sleep(1);
	$status = fgets($port);
	fclose($port);

	//ALTERANDO STATUS DOS BOTÕES
	if ($status[0]=='0') $cor1 = 'lightcoral';
  	  else $cor1 = 'lightgreen';
  	if ($status[1]=='0') $cor2 = 'lightcoral';
  	  else $cor2 = 'lightgreen';
	if ($status[4]=='0') $cor3 = 'lightgreen';
  	  else $cor3 = 'lightgreen';
	if ($status[5]=='0') $cor4 = 'lightcoral';
  	  else $cor4 = 'lightcoral';
	if ($status[6]=='0') $cor5 = 'orange';
  	  else $cor5 = 'orange';
	if ($status[7]=='0') $cor6 = 'orange';
  	  else $cor6 = 'orange';
	if ($status[8]=='0') $cor7 = 'skyblue';
  	  else $cor7 = 'skyblue';
	
	//TEMPERATURA
	$temp = $status[2];
	$temp .= $status[3];

	//MOSTRANDO NA TELA
	echo "<form method =\"post\" action=\"index.php\">";
	echo "<input type=\"hidden\" name=\"bits\" value=\"$status\">";
	echo "<label style=\"width:400; height:30;font: bold 14px Arial\" Name = \"Iluminacao\">ILUMINAÇÃO</label></br>";
	echo "<button style=\"width:600; height:30; background-color: $cor1 ;font: bold 14px Arial\" type = \"Submit\" Name = \"Quarto\">Quarto</button>";
	echo "<button style=\"width:600; height:30; background-color: $cor2 ;font: bold 14px Arial\" type = \"Submit\" Name = \"Garagem\">Garagem</button></br>";
	echo "<label style=\"width:400; height:30;font: bold 14px Arial\" Name = \"Irrigacao\">IRRIGAÇÃO</label></br>";
	echo "<button style=\"width:600; height:30; background-color: $cor7 ;font: bold 14px Arial\" type = \"Submit\" Name = \"Jardim\">Jardim</button></br>";
	echo "<label style=\"width:400; height:30;font: bold 14px Arial\" Name = \"Climatizacao\">CLIMATIZAÇÃO</label></br>";

	echo "<form method =\"post\" action=\"index.php\">";
	echo "<button style=\"width:300; height:30; background-color: $cor3 ;font: bold 14px Arial\" type = \"Submit\" Name = \"ClimaQuartoL\">Ligar</button>";
	echo "<button style=\"width:300; height:30; background-color: $cor4 ;font: bold 14px Arial\" type = \"Submit\" Name = \"ClimaQuartoD\">Desligar</button></br>";
	echo "<button style=\"width:300; height:30; background-color: $cor5 ;font: bold 14px Arial\" type = \"Submit\" Name = \"ClimaDiminuir\">Diminuir</button>";
	echo "<button style=\"width:300; height:30; background-color: $cor6 ;font: bold 14px Arial\" type = \"Submit\" Name = \"ClimaAumentar\">Aumentar</button></br>";
	echo "</form>";

	echo "<label style=\"width:400; height:40;font: bold 14px Arial\" Name = \"Temp1\">TEMPERATURA</label></br>";
	echo "<label style=\"width:400; height:40;font: bold 14px Arial\" Name = \"Temp2\">$temp C</label></br>";
  	echo "</form>";
?>

	<center><p id="rodape">Desenvolvido por Claudio Albano Guimaraes</i></a></p></center>
</body>
</html>

<?php
function serialArduino($acao) {
?>
<?php
$port = fopen('/dev/ttyACM0', 'w+');
sleep(2);
fwrite($port, $acao);
sleep(1);
fclose($port);
?>
<?php   
}
?>
