var game = null;

var entities;
var bullets;

var platforms;
var cursors;

var lifeText;
var killsText;
var deathText;

// player vars
var player;
var playerId;
var gameName;
var maxHealth;
var playerX;
var playerY;
var playerSpeed = 1;
var attackDelay = 250;//ms
var canAttack = true;

function startGame() {
    var config = {
        type: Phaser.CANVAS,
        parent: 'game-area',
        width: 800,
        height: 600,
        physics: {
            default: 'arcade'
        },
        scene: {
            preload: preload,
            create: create,
            update: update
        }
    };

    game = new Phaser.Game(config);
}

function stopGame() {
    if (game) {
        game.destroy();
        window.game = null;
    }
}

function preload()
{
    this.load.image('sky', '/assets/sky.png');
    this.load.image('ground', '/assets/platform.png');
    this.load.image('star', '/assets/star.png');
    this.load.image('bomb', '/assets/bomb.png');
    this.load.spritesheet('dude', '/assets/dude.png', {frameWidth: 32, frameHeight: 48});

    // char 1
    this.load.spritesheet('alex', '/assets/topdown_shooter/characters/1.png', {frameWidth: 16, frameHeight: 21});
    this.load.spritesheet('alex_north', '/assets/topdown_shooter/characters/1_north.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_right', '/assets/topdown_shooter/characters/1_side.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_left', '/assets/topdown_shooter/characters/1_side2.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_south', '/assets/topdown_shooter/characters/1_south2.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_diagrightup', '/assets/topdown_shooter/characters/1_diagup.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_diagleftup', '/assets/topdown_shooter/characters/1_diagup2.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_diagrightdown', '/assets/topdown_shooter/characters/1_diagdown.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('alex_diagleftdown', '/assets/topdown_shooter/characters/1_diagdown2.png', {frameWidth: 20, frameHeight: 24});

    // char 2
    this.load.spritesheet('steve', '/assets/topdown_shooter/characters/2.png', {frameWidth: 16, frameHeight: 21});
    this.load.spritesheet('steve_north', '/assets/topdown_shooter/characters/2_north.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_right', '/assets/topdown_shooter/characters/2_side.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_left', '/assets/topdown_shooter/characters/2_side2.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_south', '/assets/topdown_shooter/characters/2_south2.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_diagrightup', '/assets/topdown_shooter/characters/2_diagup.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_diagleftup', '/assets/topdown_shooter/characters/2_diagup2.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_diagrightdown', '/assets/topdown_shooter/characters/2_diagdown.png', {frameWidth: 20, frameHeight: 24});
    this.load.spritesheet('steve_diagleftdown', '/assets/topdown_shooter/characters/2_diagdown2.png', {frameWidth: 20, frameHeight: 24});

    // bullets
    this.load.spritesheet('bullet_onion', '/assets/topdown_shooter/other/bullet_onion.png', {frameWidth: 22, frameHeight: 22});
    this.load.spritesheet('bullet_tomato', '/assets/topdown_shooter/other/bullet_tomato.png', {frameWidth: 22, frameHeight: 22});

    // explisives
    this.load.spritesheet('explode_yellow', '/assets/topdown_shooter/other/explode1.png', {frameWidth: 50, frameHeight: 50});
}

function create()
{
    this.add.image(400, 300, 'sky');

    platforms = this.physics.add.staticGroup();

    platforms.create(400, 568, 'ground').setScale(2).refreshBody();

    platforms.create(600, 400, 'ground');
    platforms.create(50, 250, 'ground');
    platforms.create(750, 220, 'ground');

    player = this.physics.add.sprite(playerX, playerY, 'alex');

    player.setBounce(0);
    player.setCollideWorldBounds(true);
    player.playerId = playerId;

    //char 1
    this.anims.create({key: 'alex_stand', frames: [{key: 'alex', frame: 0}], frameRate: 1});

    this.anims.create({key: 'alex_north', frames: this.anims.generateFrameNumbers('alex_north', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'alex_right', frames: this.anims.generateFrameNumbers('alex_right', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'alex_left', frames: this.anims.generateFrameNumbers('alex_left', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'alex_south', frames: this.anims.generateFrameNumbers('alex_south', {start: 0, end: 3}), frameRate: 20, repeat: -1});

    this.anims.create({key: 'alex_diagrightup', frames: this.anims.generateFrameNumbers('alex_diagrightup', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'alex_diagleftup', frames: this.anims.generateFrameNumbers('alex_diagleftup', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'alex_diagrightdown', frames: this.anims.generateFrameNumbers('alex_diagrightdown', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'alex_diagleftdown', frames: this.anims.generateFrameNumbers('alex_diagleftdown', {start: 0, end: 3}), frameRate: 20, repeat: -1});

    //char 2
    this.anims.create({key: 'steve_stand', frames: [{key: 'steve', frame: 0}], frameRate: 1});

    this.anims.create({key: 'steve_north', frames: this.anims.generateFrameNumbers('steve_north', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'steve_right', frames: this.anims.generateFrameNumbers('steve_right', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'steve_left', frames: this.anims.generateFrameNumbers('steve_left', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'steve_south', frames: this.anims.generateFrameNumbers('steve_south', {start: 0, end: 3}), frameRate: 20, repeat: -1});

    this.anims.create({key: 'steve_diagrightup', frames: this.anims.generateFrameNumbers('steve_diagrightup', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'steve_diagleftup', frames: this.anims.generateFrameNumbers('steve_diagleftup', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'steve_diagrightdown', frames: this.anims.generateFrameNumbers('steve_diagrightdown', {start: 0, end: 3}), frameRate: 20, repeat: -1});
    this.anims.create({key: 'steve_diagleftdown', frames: this.anims.generateFrameNumbers('steve_diagleftdown', {start: 0, end: 3}), frameRate: 20, repeat: -1});

    // bullets
    this.anims.create({key: 'bullet_onion', frames: this.anims.generateFrameNumbers('bullet_onion', {start: 0, end: 5}), frameRate: 6, repeat: 0});
    this.anims.create({key: 'bullet_tomato', frames: this.anims.generateFrameNumbers('bullet_tomato', {start: 0, end: 5}), frameRate: 6, repeat: 0});

    //explisives
    this.anims.create({key: 'explode_yellow', frames: this.anims.generateFrameNumbers('explode_yellow', {start: 0, end: 2}), frameRate: 6, repeat: 0});

    // use keyboard
    cursors = this.input.keyboard.createCursorKeys();

    // texts
    this.add.text(10, 10, 'Map: ' + gameName, {fontSize: '12px', fill: '#000'});
    lifeText = this.add.text(10, 25, 'Life: 0/0', {fontSize: '12px', fill: '#000'});
    killsText = this.add.text(10, 40, 'Kills: 0', {fontSize: '12px', fill: '#000'});
    deathText = this.add.text(10, 55, 'Death: 0', {fontSize: '12px', fill: '#000'});

    entities = this.physics.add.group();
    bullets = this.physics.add.group();

    //  Collide the player and the stars with the platforms
    this.physics.add.collider(player, platforms);
    this.physics.add.collider(entities, platforms);
    this.physics.add.collider(player, bullets, playerHitByBullet, null, this);

    //  Checks to see if the player overlaps with any of the stars, if he does call the collectStar function
//    this.physics.add.overlap(player, entities, collectStar, null, this);

//    this.physics.add.collider(player, bombs, hitBomb, null, this);

    // AT THIS POINT THE GAME IS STARTED AND CAN DISPLAY PLAYERS !!!!!
    sendReadyPacket();
}

function update()
{
    var isMoving1 = false;

    // player 1 move
    if (cursors.left.isDown)
    {
        player.setVelocityX(-150 * playerSpeed);
        isMoving1 = true;

        if (cursors.left.isDown && cursors.up.isDown) {
            player.anims.play('alex_diagleftup', true);
        } else if (cursors.left.isDown && cursors.down.isDown) {
            player.anims.play('alex_diagleftdown', true);
        } else {
            player.anims.play('alex_left', true);
        }

    } else if (cursors.right.isDown)
    {
        player.setVelocityX(150 * playerSpeed);
        isMoving1 = true;

        if (cursors.right.isDown && cursors.up.isDown) {
            player.anims.play('alex_diagrightup', true);
        } else if (cursors.right.isDown && cursors.down.isDown) {
            player.anims.play('alex_diagrightdown', true);
        } else
            player.anims.play('alex_right', true);

    } else {
        player.setVelocityX(0);
    }

    if (cursors.up.isDown)
    {
        player.setVelocityY(-150 * playerSpeed);
        isMoving1 = true;

        if (cursors.up.isDown && cursors.right.isDown) {
            player.anims.play('alex_diagrightup', true);
        } else if (cursors.up.isDown && cursors.left.isDown) {
            player.anims.play('alex_diagleftup', true);
        } else
            player.anims.play('alex_north', true);

    } else if (cursors.down.isDown)
    {
        player.setVelocityY(150 * playerSpeed);
        isMoving1 = true;

        if (cursors.down.isDown && cursors.right.isDown) {
            player.anims.play('alex_diagrightdown', true);
        } else if (cursors.down.isDown && cursors.left.isDown) {
            player.anims.play('alex_diagleftdown', true);
        } else
            player.anims.play('alex_south', true);
    } else {
        player.setVelocityY(0);
    }

    if (!isMoving1) {
        player.anims.play('alex_stand', true);
    }

    // shot / attack (important to chack this before send move packet)
    if (cursors.space.isDown && canAttack)
    {
        canAttack = false;
        setTimeout(function () {
            canAttack = true;
        }, attackDelay);
        var playerVelocityX = 0;
        var playerVelocityY = 1;
        if (playerX !== player.x || playerY !== player.y) {
            playerVelocityX = player.x - playerX;
            playerVelocityY = player.y - playerY;
        }
        //send attack packet
        sendPlayerAttack('bullet_tomato', playerX, playerY, playerVelocityX, playerVelocityY);
    }

    //if player move, send move packet
    if (playerX !== player.x || playerY !== player.y) {
//        console.log("Movin player from " + playerX + "/" + playerY);
        playerX = player.x;
        playerY = player.y;
//        console.log("Movin player to " + playerX + "/" + playerY);
        sendMovePacket(player.x, player.y);
    }
}

function setPlayerId(id) {
//    console.log("THIS playerId: " + id);
    playerId = id;
}

function setGameName(name) {
    gameName = name;
}

function setPlayerSpawn(x, y) {
    playerX = x;
    playerY = y;
}

function setPlayerLife(life, max) {
    lifeText.setText('Life: ' + life + "/" + max);
}

function setPlayerKills(kills) {
    killsText.setText('Kills: ' + kills);
}

function setPlayerDeath(death) {
    deathText.setText('Death: ' + death);
}

function getEntity(id, callback) {
//    console.log("getting entity : " + id);
    entities.children.iterate(function (entity) {
        if (entity.playerId === id) {
//            console.log("get entity " + entity.playerId + " OK");
            callback(entity);
        }
    });
}

function addEntity(id, name, x, y, life, max) {
    if (id === playerId) {
//        console.log("added THIS player entity");
        setPlayerLife(life, max);
        return;
    }
    var entity = entities.create(100, 450, 'steve');
    entity.playerId = id;
    entity.setBounce(0);
    entity.x = x;
    entity.y = y;
    entity.setCollideWorldBounds(true);
    entity.setTint(0xffffff);
//    console.log("added entity : " + entity.playerId + " " + name + " " + x + "/" + y);
}

function moveEntity(id, x, y) {
    if (id === playerId) {
//        console.log("moved THIS player entity");
        player.x = x;
        player.y = y;
        return;
    }
    getEntity(id, function (entity) {
        entity.x = x;
        entity.y = y;
        // TODO walk animation
    });
}

function removeEntity(id) {
    getEntity(id, function (entity) {
        entity.destroy();
    });
}

function updateEntity(id, life, max) {
    if (id === playerId) {
//        console.log("updated THIS player entity");
        setPlayerLife(life, max);
        return;
    }
}

function playAttack(from, attack, x, y, velocityX, velocityY) {
    var tomato = bullets.create(x, y, attack);
    tomato.setVelocity(velocityX * 150, velocityY * 150);
    tomato.anims.play(attack, true);
    tomato.type = attack;
    tomato.from = from;
    var timer = setTimeout(function () {
        if (tomato !== null)
            tomato.destroy();
    }, 300);
}

function playerHitByBullet(player, bullet) {
    if (bullet.from !== player.playerId) {
        bullet.disableBody(true, true);
        player.anims.play("explode_yellow", true);
//        console.log("player hit by " + bullet.from + " bullet!");
        sendPlayerDamage(bullet.type, bullet.from);
    //    bullet.destroy();
    }
}