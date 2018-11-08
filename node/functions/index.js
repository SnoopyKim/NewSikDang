// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
// require
const request = require('request');
const schedule = require('node-schedule');
// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

//Get a database reference to our blog
var db = admin.database();
var restaurantsRef = db.ref("restaurants");

//get REST API data
function saveData() {
  request('http://openapi.gwangjin.go.kr:8088/72676c564e6e6f6d37326c75637a4a/json/GwangjinFoodHygieneBizRestaurant/1/1000/2018', function (error, response, body) {
    console.log('error:', error); // Print the error if one occurred
    console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
    var result = JSON.parse(body).GwangjinFoodHygieneBizRestaurant.row;
    var i, cnt = 0;
    var arrOk = new Array;
    var arrSkip = new Array;
    var today = new Date();
    for (i=0; i<result.length;i++) {
      var cgg = result[i].CGG_CODE; //광진구 코드
      var upso_sno = result[i].UPSO_SNO; //업소일련번호(키값)
      var upso_nm = result[i].UPSO_NM; //업소명
      upso_nm = upso_nm.replace(/\.|#|\$|\[|\]/gi, "");
      var address = result[i].SITE_ADDR; //주소(지번)
      var category = result[i].SNT_UPTAE_NM; //카테고리
      var tel = result[i].UPSO_SITE_TELNO; //전화번호

      var date = parseInt(result[i].BMAN_STDT); //영업자 시작일
      var date_cal = new Date(parseInt(date/10000),parseInt((date%10000)/100)-1,date%100);
      if ((today-date_cal)/(1000*60*60*24) > 100)  {
        arrSkip.push(upso_sno);
        continue;
      }

      arrOk.push(upso_sno);
      restaurantsRef.child(cgg).child(upso_sno+upso_nm).set({
        snum: upso_sno,
        name: upso_nm,
        address: address,
        category: category,
        tel: tel,
        date: date
      });
    }
    console.log("cnt_ok: ", arrOk.length, "\ncnt_skip", arrSkip.length);
  });
}

var event = schedule.scheduleJob('0 0 * * *', saveData);

exports.test = functions.https.onRequest((request, response) => {

  response.send("Hello from Firebase!");
});


/*
// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.addMessage = functions.https.onRequest((req, res) => {
  // Grab the text parameter.
  const original = req.query.text;
  // Push the new message into the Realtime Database using the Firebase Admin SDK.
  return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref.toString());
  });
});*/
