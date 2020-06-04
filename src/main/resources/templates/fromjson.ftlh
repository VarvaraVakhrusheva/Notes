<#import "parts/common.ftlh" as c>

<@c.page>
    <form action="/fromjson" method="post" enctype="multipart/form-data">

        <div class="form-group">
                    <textarea type="text" class="form-control" name="json"
                              rows="13"></textarea>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div class="form-inline">
            <button type="submit" class="btn btn-primary">Создать</button>
        </div>
    </form>

</@c.page>
