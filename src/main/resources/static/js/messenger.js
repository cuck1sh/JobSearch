'use strict'

// const csrfToken = document.head.querySelector("[name~=csrf_token][content]").content;

window.addEventListener('load', () => {
    const responseId = document.getElementById("responseId").innerText
    const base_url = 'http://localhost:8089/api/messages/' + responseId;
    let messages = document.getElementById('messages');
    let oldData = [];

    intervalRequest();

    setInterval(intervalRequest, 5000);

    let form = document.getElementById('form');
    form.addEventListener('submit', loginHandler);

    function loginHandler(e) {
        e.preventDefault();
        const form = e.target;
        let data = new FormData(form);

        fetch('/messages/' + responseId, {
            method: 'POST',
            body: data
        });
        document.getElementById("messageValue").value = "";
    }

    async function fetchAuthorized(url, user) {
        try {
            return await makeRequest(url, {method: 'get'});
        } catch (e) {
            alert(e)
        }
    }

    function intervalRequest() {
        try {
            const userJson = localStorage.getItem('user')
            const user = JSON.parse(userJson)

            fetchAuthorized(base_url, user)
                .then(data => {
                    console.log('data: ' + data);

                    let newData = data;

                    if (newData.length > oldData.length) {
                        for (let i = oldData.length; i < newData.length; i++) {
                            createMessage(newData[i].userEmail, newData[i].content, newData[i].timestamp);
                        }
                    }

                    oldData = newData;
                })
                .catch(error => console.log(error));
        } catch (error) {
            console.log(error);
        }
    }

    function createMessage(userEmail, content, timestamp) {
        const dateTime = new Date(timestamp);

        let borderElement = document.createElement('div');
        borderElement.className = 'border-bottom p-2';
        borderElement.innerHTML =
            '<p class="card-text mb-1">' + userEmail + '</p>' +
            '<p class="card-text mb-1">' + content + '</p>' +
            '<p class="card-text"><small class="text-body-secondary">' + dateTime + '</small></p>';
        messages.appendChild(borderElement);
    }



    function makeHeaders() {
        let user = restoreUser()
        let headers = new Headers()

        headers.set('Content-Type', 'application/json')
        if (user) {
            headers.set('Authorization', 'Basic ' + btoa(user.email + ':' + user.password))
        }
        return headers;
    }

    const requestSettings = {
        method: 'get',
        headers: makeHeaders()
    }

    async function makeRequest(url, options) {
        let settings = options || requestSettings;
        let response = await fetch(url, settings)

        if (response.ok) {
            return await response.json()
        } else {
            let error = new Error(response.status.toString());
            error.response = response;
            throw error;
        }
    }

    function updateOptions(options) {
        let update = {...options}
        update.mode = 'cors'
        update.headers = makeHeaders()
        return update
    }

    function restoreUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

});