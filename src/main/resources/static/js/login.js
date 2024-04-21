'use strict'

let form = document.getElementById('form')

form.addEventListener('submit', onLoginHandler)

function onLoginHandler(event) {
    event.preventDefault()

    let target = event.target
    let data = new FormData(target)
    let user = Object.fromEntries(data)

    let headers = new Headers()
    headers.set('Content-Type', 'application/json')
    headers.set('Authorization', 'Basic ' + btoa(user.email + ':' + user.password))

    console.log(user);
    console.log(headers);

    fetchAuthorized(headers, user)
        .then(r => saveUser(user))
}


async function fetchAuthorized(headers, user) {
    try {
        await makeRequest('http://localhost:8089/api/auth/login', updateOptions({
            method: 'post',
            headers: headers,
            body: JSON.stringify(user)
        }));

    } catch (e) {
        alert(e)
    }
}

function saveUser(user) {
    let userJson = JSON.stringify(user)
    localStorage.setItem('user', userJson)
    window.location.href = 'http://localhost:8089/'
}

function restoreUser() {
    return JSON.parse(localStorage.getItem('user'));
}

// function makeHeaders() {
//     let user = restoreUser()
//     let headers = new Headers()
//
//     headers.set('Content-Type', 'application/json')
//     if (user) {
//         headers.set('Authorization', 'Basic ' + btoa(user.email + ':' + user.password))
//     }
//     return headers
// }
//
// const requestSettings = {
//     method: 'get',
//     headers: makeHeaders()
// }

async function makeRequest(url, options) {
    let settings = options || requestSettings;
    let response = await fetch(url, settings)

    // console.log(response);

    if (response.ok) {
        return await response.json()
    } else {
        let error = new Error(response.status.toString());
        error.response = response;
        throw error;
    }
}

function updateOptions(options) {
    // console.log(options);
    let update = {...options}
    update.mode = 'cors'
    // update.headers = makeHeaders()
    return update
}