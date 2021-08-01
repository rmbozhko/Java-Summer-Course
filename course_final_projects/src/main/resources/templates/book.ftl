<#import "parts/common.ftl" as common>
<@common.page title="Order the book">
<div>
    <p>${id}</p>
    <b>${title}</b>
    <i>${author}</i>
    <span>${publisher}</span>
    <b>${publishingDate}</b>
    <i>${ISBN}</i>
    <span>${quantity}</span>
</div>
<form method="get" action="/order/${id}">
    <button type="submit">Order the book!</button>
</form>
</@common.page>