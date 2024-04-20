<#import  "layout.ftlh" as main>
<@main.layout>

    <h1 class="text-center text-danger mb-1">Вакансии:</h1>

    <div class="dropdown d-flex justify-content-center mb-2">
        <button class="btn btn-offset-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            Выберите категорию
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="/">none</a></li>
            <#list categories as category>
                <li><a class="dropdown-item" href="?filter=${category.name}">${category.name}</a></li>
            </#list>
        </ul>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4 justify-content-center">
        <#list vacancies as vacancy>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <a href="vacancies/${vacancy.id}"><h3 class="card-title mb-4">${vacancy.name}</h3></a>

                        <#if vacancy.description??>
                            <h6 class="card-text">${vacancy.description}</h6>
                        </#if>

                        <h6 class="card-title">${vacancy.userEmail}</h6>

                        <#if vacancy.expFrom??>
                            <h6 class="card-title">Опыт от: ${vacancy.expFrom} (лет)</h6>
                        </#if>

                        <#if vacancy.expTo??>
                            <h6 class="card-title">Опыт до: ${vacancy.expTo} (лет)</h6>
                        </#if>

                        <#if vacancy.salary??>
                            <h4 class="card-title">Заработная плата: ${vacancy.salary}</h4>
                        </#if>
                    </div>
                    <div class="card-footer">
                        <small class="text-body-secondary">Дата обновления ${vacancy.updateTime.toLocalDate()}</small>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <nav aria-label="Page navigation example" class="d-flex my-4 justify-content-center">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <li class="page-item"><a class="page-link" href="?page=0&pageSize=5">1</a></li>
            <li class="page-item"><a class="page-link" href="?page=1&pageSize=5">2</a></li>
            <li class="page-item"><a class="page-link" href="?page=2&pageSize=5">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>

</@main.layout>