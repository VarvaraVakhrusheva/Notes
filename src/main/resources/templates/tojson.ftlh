<#import "parts/common.ftlh" as c>

<@c.page>
    <p>${jsonString}</p>
    <form method="get" action="/main">
        <button type="submit">К заметкам</button>
    </form>
</@c.page>
