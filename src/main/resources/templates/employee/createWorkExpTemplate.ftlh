<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-5 text-danger">Блок опыт работы:</h1>
        <form action="/employee/resumes/add/workExp" method="post">

            <input type="hidden" name="resumeId" value=${resumeId}>

            <div class="row justify-content-center">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="years" class="form-label">Годы работы:</label>
                        <input name="years" type="number" class="form-control" id="years" placeholder="стаж" required>
                    </div>

                    <div class="mb-3">
                        <label for="companyName" class="form-label">Название компании:</label>
                        <input name="companyName" type="text" class="form-control" id="companyName"
                               placeholder="Введите название"
                               required>
                    </div>

                    <div class="mb-3">
                        <label for="position" class="form-label">Должность:</label>
                        <input name="position" type="text" class="form-control" id="position" required>
                    </div>

                    <div class="mb-5">
                        <label for="responsibilities" class="form-label">Обязанности:</label>
                        <input name="responsibilities" type="text" class="form-control" id="responsibilities" required>
                    </div>
                </div>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3">Добавить
                </button>
            </div>
        </form>
    </div>

</@main.layout>