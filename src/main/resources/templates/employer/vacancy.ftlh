<#import  "../layout.ftlh" as main>
<@main.layout>


    <div class="card text-center mb-4">
        <div class="card-header">
            <h1>${vacancy.name}</h1>
        </div>
        <div class="card-body">

            <div class="col-sm-6 my-4 mb-sm-0 mx-auto">
                <table class="table text-start table-striped">
                    <tbody>
                    <tr>
                        <th scope="row">Описание:</th>
                        <td><#if vacancy.description??>${vacancy.description}<#else> - </#if></td>
                    </tr>

                    <tr>
                        <th scope="row">Родительская категория:</th>
                        <td>
                            <#if vacancy.category??>
                                <#if vacancy.category.parent??> ${vacancy.category.parent} <#else> - </#if>
                            <#else>
                                N/A
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Категория:</th>
                        <td>
                            <#if vacancy.category??>
                                <#if vacancy.category.name??> ${vacancy.category.name} <#else> - </#if>
                            <#else>
                                N/A
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Заработная плата:</th>
                        <td><#if vacancy.salary??> ${vacancy.salary} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Опыт от:</th>
                        <td><#if vacancy.expFrom??> ${vacancy.expFrom} (лет)<#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Опыт до:</th>
                        <td><#if vacancy.expTo??> ${vacancy.expTo} (лет)<#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Профиль автора:</th>
                        <td class="pt-2"><a href="/users/profile/${vacancy.userEmail}"
                                            class="text-decoration-none border border-primary rounded-4 p-1">${vacancy.userEmail}</a>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Активно:</th>
                        <td><#if vacancy.isActive??> ${vacancy.isActive?string('Да', 'Нет')} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Дата создания:</th>
                        <td>${vacancy.createdDate.toLocalDate()}</td>
                    </tr>

                    </tbody>
                </table>
            </div>


        </div>
        <div class="card-footer text-body-secondary">
            Дата обновления ${vacancy.updateTime.toLocalDate()}
        </div>

    </div>

</@main.layout>