'use strict';

var searchDriver = document.querySelector('#searchDriver');
var stompClient = null;

connect();


function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}


function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
}


function onError(error) {
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var senderhtml = document.querySelector('#senderhtml');

    console.log("текст1" + senderhtml.textContent);

    if(message.sender === senderhtml.textContent) {
        searchDriver.classList.remove("bg-body-tertiary-p-5");
        searchDriver.classList.add("d-none");
        var responseElement = document.querySelector('#response');
        responseElement.classList.add("bg-body-tertiary-p-5");
        var textbox = document.createElement("h1");
        responseElement.appendChild(textbox);
        textbox.innerText = `${message.content}`;

        responseElement.appendChild(textbox);
    }
}

