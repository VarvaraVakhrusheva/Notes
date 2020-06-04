<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Поиск по заголовку">
                <button type="submit" class="btn btn-primary ml-2">Найти</button>
            </form>
        </div>
    </div>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        Добавить заметку
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Заголовок">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="text" placeholder="Введите текст"/>
                </div>

                <div class="form-group" style="display: none">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>

        </div>
    </div>
    <div>

    </div>
    <span>&nbsp;</span>
    <div>
        <form method="get" action="/fromjson" style="align-self: flex-start">
            <button type="submit" class="btn btn-primary ml-2">fromJSON</button>
        </form>
    </div>

    <div class="card-columns">
        <#list notes as note>
            <div class="card my-3">

                <div class="m-2">
                    <b>${note.tag}</b>

                </div>
                <div class="m-2">
                    <span>${note.text}</span>
                </div>

                <#if note.filename??>
                    <img src="/img/${note.filename}" class="card-img-top">
                </#if>
                <#--                <div class="card-footer text-muted">-->
                <#--                    ${note.authorName}-->
                <#--                </div>-->
                <div style="display: flex">
                    <form method="get" action="/edit/${note.id}" class="form-inline">
                        <button type="submit" class="btn btn-primary ml-2">Открыть</button>
                    </form>
                    <form method="get" action="/remove/${note.id}" class="form-inline">
                        <button type="submit" class="btn btn-primary ml-2">Удалить</button>
                    </form>
                    <form method="get" action="/tojson/${note.id}" style="align-self: flex-end">
                        <button type="submit" class="btn btn-primary ml-2">toJSON</button>
                    </form>
                </div>
            </div>
        <#else>
            Нет заметок
        </#list>
    </div>
</@c.page>