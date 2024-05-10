'use strict'
window.addEventListener('load', () => {

    let form = document.getElementById('form')

    form.addEventListener('submit', submitHandler)

    function submitHandler(event) {
        event.preventDefault();
        const form = event.target;
        let data = new FormData(form);

        return fetchAuthorized('/employer/vacancies/update', data)
    }

    async function fetchAuthorized(url, data) {
        try {
            return await makeRequest(url, {
                method: 'post',
                body: data
            });
        } catch (e) {
            console.log(e)
        }
    }

    async function makeRequest(url, options) {
        let response = await fetch(url, options)

        if (response.ok) {
            return await response.json()
        } else {
            let error = new Error(response.status.toString());
            error.response = response;
            throw error;
        }
    }
})