'use strict'

let form = document.getElementById('form')

form.addEventListener('submit', submitHandler)

function submitHandler(event) {
    event.preventDefault()
    let data = new FormData(event.target)
    let vacancyId = data.get('vacancyId')
    let resumeId = data.get('resumeId')

    makeRequest('/api/responses/vacancy/' + vacancyId + '/resume/' + resumeId, {method: 'get'})
        .then(responseId => {
            console.log('RESPONSE ID: ' + responseId)
            window.location.href = '/messages/' + responseId
        })
        .catch(e => console.log(e))

    async function makeRequest(url, options) {
        let response = await fetch(url, options)

        if (response.ok) {
            return await response.json()
        } else {
            let error = new Error(response.status.toString());
            alert("Failed to response, please make sure you have selected the correct vacancy AND resume to response for")
            error.response = response;
            throw error;
        }
    }
}