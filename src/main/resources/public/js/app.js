function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $('#login_modal').modal(connected ? 'hide' : 'show');
    $("#disconnect").prop("disabled", !connected);

    if (connected) {
        $('#login_error').hide();
        $('#error_message').html("");
        $('#main_content').show();
        $('#disconnect').html('Disconnect');
    } else {
        $('#main_content').hide();
        $("#online_players").html("");
        $("#chat_output").html("");
        stopGame();
        $("#game-area").html("");
    }
}

function playOffline() {
    $("#connect").prop("disabled", true);
    $('#login_modal').modal('hide');
    $("#disconnect").prop("disabled", false);

    $('#main_content').show();
    $('#conversation').hide();
    $('#conversation').hide();
    $('#disconnect').html('Close');
    startGame();
}

function setLoggingIn(loggingIn) {
    $("#connect").prop("disabled", loggingIn);
}

function sendChat() {
    sendChatPacket($("textarea#chat_input").val());
}

function recievedStartGame(packet) {
    setPlayerId(packet.playerId);
    setPlayerSpawn(packet.x, packet.y);
    setGameName(packet.gameName);
    startGame();
}

function recievedChatMessage(message) {
    if (message.player)
        $("#chat_output").prepend("<tr><td>[" + message.player + "]> " + message.message + "</td></tr>");
    else {
        if (message.message)
            $("#chat_output").prepend("<tr><td>" + message.message + "</td></tr>");
        else
            $("#chat_output").prepend("<tr><td>" + message + "</td></tr>");
    }
}

function recievedAddEntity(packet) {
    //add on map
    addEntity(packet.entityId, packet.entityName, packet.x, packet.y, packet.health, packet.maxHealth);
    //add on sidebar
    if (packet.isPlayer) {
        addPlayer(packet);
    }
}

function recievedRemoveEntity(packet) {
    //if entity is stored, remove
    removeEntity(packet.entityId);
    //remove from sidebar
    removePlayer(packet);
}

function recievedMoveEntity(packet) {
    moveEntity(packet.entityId, packet.x, packet.y);
}

function addPlayer(player) {
    $("#online_players").append("<tr id='online_player_" + player.entityId + "'><td>" + player.entityName + "</td></tr>");
}

function removePlayer(player) {
    $("#online_player_" + player.entityId).remove();
}

function recievedPlayAttack(packet) {
    playAttack(packet.attackerId, packet.attack, packet.x, packet.y, packet.velocityX, packet.velocityY);
}

function recievedUpdateEntity(packet) {
    updateEntity(packet.entityId, packet.health, packet.maxHealth);
}

function recievedUpdateScore(packet) {
    setPlayerKills(packet.kills);
    setPlayerDeath(packet.death);
}

$(function () {
    setConnected(false);
    $('#login_error').hide();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect($('#playername').val());
    });
    $("#play_offline").click(function () {
        playOffline();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send_chat").click(function () {
        sendChat();
    });
});