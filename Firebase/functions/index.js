// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();


 exports.helloWorld = functions.https.onRequest((request, response) => {
  response.send("Hello from Firebase!");
 });
 
 
// Take the text parameter passed to this HTTP endpoint and insert it into the
// Realtime Database under the path /messages/:pushId/original
exports.addMessage = functions.https.onRequest((req, res) => {
  // Grab the text parameter.
  const original = req.query.aaaaa;
  // Push the new message into the Realtime Database using the Firebase Admin SDK.
  return admin.database().ref('/messages').push({original: original}).then((snapshot) => {
    // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
    return res.redirect(303, snapshot.ref.toString());
  });
});

// pushID 가 현재 user이면 데이터set 
exports.makeUppercase = functions.database.ref('/messages/{pushId}/original')
    .onCreate((snapshot, context) => {
      // Grab the current value of what was written to the Realtime Database.
      const original = snapshot.val();
      console.log('Uppercasing', context.params.pushId, original);
      const uppercase = original.toUpperCase();
      // You must return a Promise when performing asynchronous tasks inside a Functions such as
      // writing to the Firebase Realtime Database.
      // Setting an "uppercase" sibling in the Realtime Database returns a Promise.
      return snapshot.ref.parent.child('uppercase').set(uppercase);
    });


// 사용자 권한 액세스 
exports.simpleDbFunction = functions.database.ref('/path')
    .onCreate((snap, context) => {
      if (context.authType === 'ADMIN') {
        // do something
      } else if (context.authType === 'USER') {
        console.log(snap.val(), 'written by', context.auth.uid);
      }
    });
    
// 처음 로그인 했을때 

exports.sendWelcomeSet = functions.auth.user().onCreate((user) => {
  const userId = user.uid; // userID

  return admin.database().ref('/default_set/').once('value').then(function(snapshot) {
    return setNode('/user_set/'+ userId, snapshot.val());

  });
 

});



function setNode(inPath, inNode){
  /*var userSet = {
    morning: inMorning,
    afternoon: inAfternoon,
    night : inNight,
    pay : inPay,
    resolution : inResolution
  };*/
  
  var updates = {};
  updates[inPath] = inNode;

  return admin.database().ref().update(updates);
  
}
