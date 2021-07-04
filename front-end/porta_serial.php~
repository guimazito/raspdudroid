<?php
// Exibir erros do PHP na tela
ini_set('display_erros', 1);

//Aqui é curinthian
require("includes/php_serial.class.php");


$serial = new phpSerial();

$serial->deviceSet("COM3"); 
/* Acima voce configura o seu dev de acordo com a porta COM emulada caso esteja
	usando uma maquina virtual com Linux, caso contrario é só setar a COM ou /dev/ttyS
	correspondente
*/ 

$serial->confBaudRate(9600);     //Taxa de transmissao
$serial->confParity("none");     //Paridade
$serial->confCharacterLength(8); //Comprimento

$serial->confStopBits(1);        //Bits de paragem
$serial->confFlowControl("none");//Controle

//$serial->deviceSet("/dev/ttyS2");
//Abrindo a conexao
//$serial->deviceOpen("r");
$serial->deviceOpen();


//Tempo para comunicar e o arduino estar pronto
sleep(2);

//Enviar 
$serial->sendMessage("t");

// Tempo de espera para recever dados da Serial
sleep(1);

//$serial->flush();

global $ler_serial;
$ler_serial = $serial->readPort();

//Fechando a conexao
$serial->deviceClose();

?>
