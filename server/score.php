<?php
/**
 * Created by PhpStorm.
 * User: УровеньGD
 * Date: 20.12.2015
 */
function generateKey($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

header('Content-Type: text/plain');

if(isset($_GET["id"]) && isset($_GET["name"]) && isset($_GET["score"])){

    $base = json_decode(file_get_contents("scores.json"),true);

    if($_GET["id"] == "none" || !array_key_exists($_GET["id"],$base)){

        $newid = generateKey(8);

        $player = new stdClass();
        $player->name = $_GET["name"];
        $player->score = 0;

        $base[$newid] = $player;
        file_put_contents("scores.json",json_encode($base,JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE),LOCK_EX);

        echo $newid;
    }else{

        $base[$_GET["id"]]["score"] = intval($_GET["score"]);

        uasort($base,function($a, $b){
            return $b["score"] - $a["score"];
        });
        
        file_put_contents("scores.json",json_encode($base,JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE),LOCK_EX);

        echo "BEST SCORES:\n";

        $i = 10;
        foreach($base as $key=>$value){

            $who = ($key == $_GET["id"]) ? "YOU" : $value["name"];

            echo (10 - ($i - 1)).". ".$who." [".$value["score"]."]\n";
            $i--;
            if($i == 0) break;
        }

        $yourposition = array_search($_GET["id"], array_keys($base));

        if($yourposition > 10){
            echo "...\n";
            echo $yourposition.". YOU"." [".$base[$_GET["id"]]["score"]."]\n";
        }

        echo "total players: ".count($base);
    }
}else{
    echo "server error";
}