'use strict'

window.addEventListener('load', () => {
    let urlParams = new URLSearchParams(window.location.search);
    let param = urlParams.get('error');
    let form = document.getElementById('form')

    if (param != null) {
        let alert = document.createElement('div')
        alert.className = 'alert alert-danger'
        alert.role = 'alert'
        alert.textContent = 'Неправильный логин или пароль'
        form.prepend(alert)

    }
})