var UI = require('ui');
var Accel = require('ui/accel');
var ajax = require('ajax');

var main = new UI.Card({
  title: 'Strummer',
  subtitle: 'Strum to send a strum command',
});

main.show();

Accel.init();
Accel.config({rate: 50, samples: 10});

var lastTime = Date.now();
main.on('accelData', function(e) {

  if (e.accels[0].y - e.accels[e.samples - 1].y > 100) {
    var n = Date.now();
    if (n - lastTime > 1) {
      ajax(
        {
          url: 'http://10.145.210.252:8080/strum',
          method: 'post',
          type: 'string',
          data: 'strum'
        },
        function(data, status, request) {
          console.log('Response received');
        },
        function(error, status, request) {
          console.log('The ajax request failed: ' + error);
        }
      );
    }
    lastTime = n;
  }
});
