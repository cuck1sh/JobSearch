<#import  "layout.ftlh" as main>
<@main.layout>

    <form id="form">
        <div class="card mb-3">
            <div class="row">
                <div class="col-md-4">

                    <div class="card border mt-5 ms-5" style="max-width: 18rem;">
                        <div class="card-header bg-transparent border-success">${messenger.employerProfile.name}</div>
                        <div class="card-body">
                            <h5 class="card-title">${messenger.employerProfile.email}</h5>
                            <p class="card-text">Возраст: ${messenger.employerProfile.age}</p>
                            <p class="card-text">Номер телефона: ${messenger.employerProfile.phoneNumber}</p>
                        </div>
                        <div class="card-footer bg-transparent border">${messenger.employerProfile.accountType}</div>

                    </div>
                </div>


                <div class="col-md-8">
                    <div class="card-body" id="messages">
                        <h5 class="card-title mb-4">Переписка</h5>
                    </div>

                    <div class="card-footer bg-transparent border rounded-3">
                        <div class="row">
                            <div class="col-10">
                                <input type="text" id="messageValue" name="message" class="form-control"
                                       placeholder="Сообщение">
                            </div>
                            <div class="col-2">
                                <button type="submit" id="submit"
                                        class="btn bg-success p-2 text-dark bg-opacity-10 rounded-3">Отправить
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <script src="/static/js/messenger.js"></script>



</@main.layout>