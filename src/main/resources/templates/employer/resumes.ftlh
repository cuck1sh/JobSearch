<#import  "../layout.ftlh" as main>
<@main.layout>

    <h1 class="text-center text-danger mb-3">Резюме:</h1>

    <div class="row row-cols-1 row-cols-md-3 g-4 justify-content-center">
        <#list resumes as resume>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <a href="/employer/resumes/${resume.id}"><h3 class="card-title mb-4">${resume.name}</h3></a>

                        <#if resume.category??>
                            <#if resume.category.parent??>
                                <h6 class="card-title">Родительская категория: ${resume.category.parent}</h6>
                            </#if>

                            <#if resume.category.name??>
                                <h6 class="card-title">Категория: ${resume.category.name}</h6>
                            </#if>
                        </#if>

                        <h6 class="card-title">${resume.userEmail}</h6>


                        <#if resume.salary??>
                            <h4 class="card-title">Заработная плата: ${resume.salary}</h4>
                        </#if>

                    </div>
                    <div class="card-footer">
                        <small class="text-body-secondary">Дата обновления ${resume.updateTime.toLocalDate()}</small>
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
            <li class="page-item"><a class="page-link" href="/employer/resumes?page=0&pageSize=5">1</a></li>
            <li class="page-item"><a class="page-link" href="/employer/resumes?page=1&pageSize=5">2</a></li>
            <li class="page-item"><a class="page-link" href="/employer/resumes?page=2&pageSize=5">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>

</@main.layout>