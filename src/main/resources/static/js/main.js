'use strict';

var usernameForm = document.querySelector('#usernameForm');
var asd = document.querySelector('#asd');


var stompClient = null;
var username = null;


function connect(event) {
    username = document.querySelector('#name').value.trim();

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected);

    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public');

    //asd.classList.add(asd.classList + 'gaga')

    // Tell your username to the server
    stompClient.send("/app/chat.sendMessage",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )
}




usernameForm.addEventListener('submit', connect, true)
