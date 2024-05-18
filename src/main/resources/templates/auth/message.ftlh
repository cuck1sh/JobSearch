<#import "../layout.ftlh" as main>
<@main.layout ; sping>

    <div class="text-center mt-5">
        <h2><@sping.message 'reset.your.password'/></h2>
        <p class="text-warning">${message}</p>
    </div>

</@main.layout>