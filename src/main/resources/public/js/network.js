var socket = null;
var gameStarted = false;

function connect(playername) {
    socket = new WebSocket('ws://' + window.location.host + '/ws');

    // Add an event listener for when a connection is open
    socket.onopen = function () {
        console.log('WebSocket connection opened. Ready to send messages.');

        // Send a message to the server
        sendJoinPacket(playername);
        loggingIn = true;
        setLoggingIn(loggingIn);
    };

    // Add an event listener for when a message is received from the server
    socket.onmessage = function (message) {
        var packet = null;
        try {
            packet = JSON.parse(message.data);
        } catch (error) {
//            console.error(error);
        }
//        console.log('Message received from server: ' + JSON.stringify(packet));

        if (packet && packet.packetID) {
            switch (packet.packetID) {
                case 1:
                    handleJoinResponse(packet);
                    break;
                case 3:
                    recievedChatMessage(packet);
                    break;
                case 4:
                    recievedStartGame(packet);
                    break;
                case 5:
                    recievedAddEntity(packet);
                    break;
                case 6:
                    recievedRemoveEntity(packet);
                    break;
                case 8:
                    recievedMoveEntity(packet);
                    break;
                case 10:
                    recievedPlayAttack(packet);
                    break;
                case 12:
                    recievedUpdateEntity(packet);
                    break;
                case 14:
                    recievedUpdateScore(packet);
                    break;
            }
        }
    };

    // Add an event listener for when a connection is closed from server
    socket.onclose = function () {
        disconnect();
    };
}

function sendPacket(packet) {
    if (socket !== null) {
        socket.send(JSON.stringify(packet));
//        console.log('Message sent to server: ' + JSON.stringify(packet));
    } else
        console.error("WebSocket is closed, can't send packet !");
}

function disconnect() {
    if (socket !== null) {
        socket.close();
    }
    socket = null;
    setConnected(false);
    setLoggingIn(false);
    console.log("Disconnected");
}

function handleJoinResponse(packet) {
    if (packet.status === 0) {
        if (packet.message) {
            $('#login_error').show();
            $('#error_message').html(packet.message);
//            console.log("Join failed : " + packet.message);
        } else
//            console.log("Join failed !");
        disconnect();
    } else if (packet.status === 1) {
//        console.log("Join success !");
        setConnected(true);
    } else {
//        console.log("Join failed !");
        disconnect();
    }
}

// Packets
//
//player join
function sendJoinPacket(playername) {
    sendPacket({packetID: 0, playername: playername});
}
//player ready
function sendReadyPacket() {
    sendPacket({packetID: 13});
}
//player chat
function sendChatPacket(message) {
    sendPacket({packetID: 2, message: message});
}
//player chat
function sendMovePacket(x, y) {
    sendPacket({packetID: 7, x: x, y: y});
}
//player attack
function sendPlayerAttack(attack, x, y, velocityX, velocityY) {
    sendPacket({packetID: 9, attack: attack, x: x, y: y, velocityX: velocityX, velocityY: velocityY});
}
//player get damaged
function sendPlayerDamage(attack, from) {
    sendPacket({packetID: 11, attack: attack, from: from});
}