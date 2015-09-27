var express = require('express');
var app = express();
var server = require('http').createServer(app);
var io = require('socket.io')(server);

var port = process.env.PORT || 8080;

function run_cmd(cmd, args) {
	var spawn = require('child_process').spawn;
	var child = spawn(cmd, args);
	var resp = "";
}

var pressKey = function(key) {
	run_cmd('xdotool', ['keydown', key]);
}

var releaseKey = function(key) {
	run_cmd('xdotool', ['keyup', key]);
}

app.get('/', function(req, res) {
    res.send('Hello world!');
});

app.post('/strum', function(req, res) {
	console.log('strum received');
	run_cmd('xdotool', ['key', 'K']);
	res.send('good');
});

var COLOR_KEY_MAP = { 'green': '1', 'red': '2', 'yellow': '3', 'blue': '4' };
var ACTION_MAP = { 'on': pressKey, 'off': releaseKey };

io.on('connection', function(socket) {
    socket.on('strum', function() {
		console.log('strum');
		run_cmd('xdotool', ['key', 'KP_Enter']);
    });

    socket.on('colors', function(message) {
        var params = message.split(',');
        var key = COLOR_KEY_MAP[params[0]];
        var action = ACTION_MAP[params[1]];
		action(key);
    });
});

server.listen(port);
