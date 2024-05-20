'use strict'

window.addEventListener('load', () => {
    let urlParams = new URLSearchParams(window.location.search);
    let param = urlParams.get('error');
    let form = document.getElementById('form')
    const validMsg = document.getElementById('hidden-valid').innerText

    if (param != null) {
        let alert = document.createElement('div')
        alert.className = 'alert alert-danger'
        alert.role = 'alert'
        alert.textContent = validMsg
        form.prepend(alert)

    }
})