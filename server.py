from flask import Flask, request
from flask.ext.socketio import SocketIO
import os

app = Flask(__name__)

# green, red, yellow, blue

COLORS = ['green', 'red', 'yellow', 'blue']
KEYD = [ 'xdotool keydown {}'.format(num) for num in [1,2,3,4] ]
KEYU = [ 'xdotool keyup {}'.format(num) for num in [1,2,3,4] ]
ENTER = 'xdotool key KP_Enter'

@app.route("/put", methods=['GET', 'POST'])
def put():
	colors = [ request.args.get(color) == '1' for color in COLORS ]
	keyu = [ key for key, color in zip(KEYD, colors) if color ]
	keyd = [ key for key, color in zip(KEYU, colors) if color ]
	keys = keyu + [ENTER] + keyd
	[ os.system(key) for key in keys ]
	return 'ok!'

if __name__ == "__main__":
	#app.run(debug=True, port=5000)
	socketio.run(app)
