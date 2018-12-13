// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');
// require
const request = require('request');
const schedule = require('node-schedule');
// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

//Get a database reference to our project
var db = admin.database();
var restaurantsRef = db.ref("restaurants");

//get REST API data
function saveData() {
  request('http://openapi.gwangjin.go.kr:8088/72676c564e6e6f6d37326c75637a4a/json/GwangjinFoodHygieneBizRestaurant/1/1000/2018', function (error, response, body) {
    console.log('error:', error); // Print the error if one occurred
    console.log('statusCode:', response && response.statusCode); // Print the response status code if a response was received
    // result -> restaurants data list
    var result = JSON.parse(body).GwangjinFoodHygieneBizRestaurant.row;
    var i, cnt = 0;
    var arrOk = new Array;
    var arrSkip = new Array;
    var today = new Date();
    for (i=0; i<result.length;i++) {
      var cgg = result[i].CGG_CODE; //광진구 코드
      var upso_sno = result[i].UPSO_SNO; //업소일련번호(키값)
      var upso_nm = result[i].UPSO_NM; //업소명
      upso_nm = upso_nm.replace(/\.|#|\$|\[|\]/gi, ""); // -> for illegal char
      var address = result[i].SITE_ADDR; //주소(지번)
      var category = result[i].SNT_UPTAE_NM; //카테고리
      var tel = result[i].UPSO_SITE_TELNO; //전화번호

      // if date diff is over 100days, skip it
      var date = result[i].BMAN_STDT; //영업자 시작일
      var i_date = parseInt(date);
      var date_cal = new Date(parseInt(i_date/10000),parseInt((i_date%10000)/100)-1,i_date%100);
      if ((today-date_cal)/(1000*60*60*24) > 100)  {
        arrSkip.push(upso_sno);
        continue;
      }

      // else save data into realtime database
      // location -> /restaurants/area_code/key(snum+name)
      arrOk.push(upso_sno);
      var key = upso_sno+upso_nm;
      restaurantsRef.child(cgg).child(key).child("snum").set(upso_sno);
      restaurantsRef.child(cgg).child(key).child("name").set(upso_nm);
      restaurantsRef.child(cgg).child(key).child("address").set(address);
      restaurantsRef.child(cgg).child(key).child("category").set(category);
      restaurantsRef.child(cgg).child(key).child("tel").set(tel);
      restaurantsRef.child(cgg).child(key).child("date").set(date);

    }
    // write log number of save data & skip data
    console.log("cnt_ok: ", arrOk.length, "\ncnt_skip", arrSkip.length);
  });
}

// basic function for firebase functions
// check it at https://us-central1-newsikdang-21cb1.cloudfunctions.net/test
exports.callData = functions.https.onRequest((request, response) => {
  saveData();
  // set schedule to call saveData() at 0:00 every day
  //var event = schedule.scheduleJob('40 * * * *', saveData());
  //response.send("Hello from Firebase!");
});


// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.sendNotification = functions.database.ref("/restaurants/3040000/{key}").onCreate((snapshot,context) => {
  var r_data = snapshot.val();

  var payload = {
    notification: {
      title: "가게 추가",
      body: r_data.name + " 이(가) 새로 생겼어요!"
    }
  };

  console.log(payload);
  admin.messaging().sendToTopic("addRes", payload)
    .then((response) => {
        console.log("Successfully sent message: ", response);
        return true;
    })
    .catch((error) => {
        console.log("Error sending message: ", error);
        return false;
    })
});
