<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-5 text-danger">Блок образование:</h1>
        <form action="/employee/resumes/add/education" method="post">

            <input type="hidden" name="resumeId" value=${resumeId}>

            <div class="row justify-content-center">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="institution" class="form-label">Название образовательного учреждения:</label>
                        <input name="institution" type="text" class="form-control" id="institution"
                               placeholder="Введите название"
                               required>
                    </div>

                    <div class="mb-3">
                        <label for="program" class="form-label">Программа обучения:</label>
                        <input name="program" type="text" class="form-control" id="program" required>
                    </div>

                    <div class="mb-3">
                        <label for="startDate" class="form-label">Дата поступления:</label>
                        <input name="startDate" type="date" class="form-control" id="startDate" required>
                    </div>

                    <div class="mb-3">
                        <label for="endDate" class="form-label">Дата окончания:</label>
                        <input name="endDate" type="date" class="form-control" id="endDate" required>
                    </div>

                    <div class="mb-5">
                        <label for="degree" class="form-label">Полученная степень:</label>
                        <input name="degree" type="text" class="form-control" id="degree">
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