<#import "parts/common.ftlh" as c>

<@c.page>
    <#list notes as note>
        <div class="card">
            <h2>Редактирование заметки</h2>
            <form action="/save/${note.id}" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control" name="tag" placeholder="Заголовок" value="${note.tag}">
                </div>
                <div class="form-group">
                    <textarea type="text" class="form-control" name="text"
                              rows="13" <#--value="${note.text}"-->>${note.text}</textarea>
                </div>
                <p>Дата создания: ${note.createDate?if_exists}</p>
                <p>Дата редактирования: ${note.updateDate?if_exists}</p>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <div class="form-inline">
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                </div>
            </form>

            <form method="get" action="/remove/${note.id}" style="align-self: flex-end">
                <button type="submit" class="btn btn-primary ml-2">Удалить</button>
            </form>
        </div>
    <#else>
        Нет заметок
    </#list>
    <h2>История</h2>
    <#list notesHistory as hist>
        <div class="card">
            <b>Заголовок: ${hist.tag}</b>
            <p>Текст: ${hist.text}</p>
            <p>Дата : ${hist.createDate?if_exists}</p>
        </div>
    <#else>
        Нет заметок
    </#list>
</@c.page>
