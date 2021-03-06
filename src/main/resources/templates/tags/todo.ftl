<#--<#import "spring.ftl" as spring/>-->

<#macro todo text id todoGroup>
    <link rel="stylesheet" href='<@spring.url '/styles/todo-item.css' />' type="text/css">

    <form class="todo-item" action="handle-todo" method="POST">
        <p class="todo-text" id="${id}-text" style="display: block">${text}</p>
        <input class="change-todo-input" id="${id}-input" type="text" name="change-todo-text" value="${text}" style="display: none">
        <input value="${id}" name="todo-id" style="display: none">

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <div class="todo-button-wrapper">
            <#if todoGroup == "1">
                <button type="button" value="change" name="todo-action" class="change-todo-button" id="${id}-button" onmousedown="editTodo(${id}, event)"><@spring.message "main_page.todo.change"/></button>
            </#if>
            <button type="button" value="change" name="todo-action" class="change-edit-todo-button" id="${id}-cancel-button" onmousedown="editTodo(${id}, event)" style="display: none"><@spring.message "main_page.todo.cancel"/></button>
            <button type="submit" value="remove" name="todo-action" id="${id}-delete-button"><@spring.message "main_page.todo.done"/></button>
        </div>
    </form>
</#macro>