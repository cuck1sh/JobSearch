<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/users/update" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="name" class="form-label">Имя</label>
                <input name="name" type="text" class="form-control" id="name" placeholder="Иван">
            </div>
            <div class="mb-3">
                <label for="surname" class="form-label">Фамилия</label>
                <input name="surname" type="text" class="form-control" id="surname" placeholder="Иванов">
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">Возраст</label>
                <input name="age" type="number" class="form-control" id="age" placeholder="1000 лет">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input name="password" type="password" class="form-control" id="password">
                <div id="passwordРудз" class="form-text">Пароль должен быть не менее 4 символов, иметь 1 заглавную букву
                    и 1 цифру
                </div>
            </div>
            <div class="mb-3">
                <label for="phone_number" class="form-label">Номер телефона</label>
                <input name="phoneNumber" type="text" class="form-control" id="phone_number"
                       placeholder="996-ddd-dd-dd-dd>
            </div>
            <div class="mb-3">
                <label for="formFile" class="form-label">Загрузить аватар</label>
                <input name="file" class="form-control" type="file" id="formFile">
            </div>
            <button type="submit" class="btn btn-primary">Изменить</button>
        </form>
    </div>

</@main.layout>