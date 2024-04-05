<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/users/register" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="account_type" class="form-label">Укажите тип вашего аккаунта: соискатель или
                    работодатель</label>
                <select name="accountType" id="account_type" class="form-select" required>
                    <option selected>Тип аккаунта</option>
                    <option value="EMPLOYEE">Соискатель</option>
                    <option value="EMPLOYER">Работодатель</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Имя</label>
                <input name="name" type="text" class="form-control" id="name" placeholder="Иван" required>
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
                <label for="email" class="form-label">Email адрес</label>
                <input name="email" type="email" class="form-control" id="email" placeholder="example@mail.com"
                       required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input name="password" type="password" class="form-control" id="password" required>
                <div id="passwordРудз" class="form-text">Пароль должен быть не менее 4 символов, иметь 1 заглавную букву
                    и 1 цифру
                </div>
            </div>
            <div class="mb-3">
                <label for="phone_number" class="form-label">Номер телефона</label>
                <input name="phoneNumber" type="text" class="form-control" id="phone_number"
                       placeholder="996-ddd-dd-dd-dd" required>
            </div>
            <div class="mb-3">
                <label for="formFile" class="form-label">Загрузить аватар</label>
                <input name="file" class="form-control" type="file" id="formFile">
            </div>
            <button type="submit" class="btn btn-primary">Создать</button>
        </form>
    </div>

</@main.layout>