'use strict'

document.getElementById('form').addEventListener('submit', onLoginHandler)
let user = {}

function onLoginHandler(e) {
    e.preventDefault()

    const form = e.target
    const userFormData = new FormData(form)
    user = Object.fromEntries(userFormData)

    saveUser(user)
    fetchAuthorized()
}

function saveUser(user) {
    let userAsJSON = JSON.stringify(user)
    localStorage.setItem('user', userAsJSON)
}

function restoreUser() {
    let userAsJSON = localStorage.getItem('user')
    user = JSON.parse(userAsJSON);
    return user;
}

function fetchAuthorized() {
    makeHeaders('http://localhost:8089/users/login', updateOptions({method: 'POST'}))
}

function makeHeaders() {
    let user = restoreUser()
    let headers = new Headers()

    headers.set('Content-Type', 'application/json')
    if (user) {
        headers.set('Authorization', 'Basic ' + btoa(user.email + ':' + user.password))
    }
    return headers
}

const requestSettings = {
    method: 'GET',
    headers: makeHeaders()
}

async function makeRequest(url, options) {
    let settings = options || requestSettings;
    let response = await fetch(url, settings)

    if (response.ok) {
        return await response.json()
    } else {
        let error = new Error(response.statusText);
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