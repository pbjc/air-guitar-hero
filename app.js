var express = require('express');
var app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);
var applescript = require('applescript');

var port = process.env.PORT || 8080;


var pressKey = function(key) {
    return 'tell application \"System Events\" to key down (key code ' + key + ')';
}

var releaseKey = function(key) {
    return 'tell application \"System Events\" to key up (key code ' + key + ')';
}

app.get('/', function(req, res) {
    res.send('Hello world!');
});

var COLOR_KEY_MAP = { 'green': '18', 'red': '19', 'yellow': '20', 'blue': '21' };
var ACTION_MAP = { 'on': pressKey, 'off': releaseKey };

io.on('connection', function(socket) {
    socket.on('strum', function() {
        pressKey('return');
    });

    socket.on('colors', function(message) {
        var params = message.split(',');
        var key = COLOR_KEY_MAP[params[0]];
        var action = ACTION_MAP[params[1]];

        console.log(key);
        if (key < 18 || key > 22) {
            return;
        }

        applescript.execString(action(key), function(err, rtn) {
            if (err) {
                console.log("ERROR:", err);
            } else {
                console.log("RET:", rtn);
            }
        });
    });
});

server.listen(port);
