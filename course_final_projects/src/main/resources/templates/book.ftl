<#import "parts/common.ftl" as common>
<@common.page title="Order the book">
<div>
    <b>${book.title}</b>
    <i>${book.author}</i>
    <span>${book.publisher}</span>
    <b>${book.publishingDate}</b>
    <i>${book.ISBN}</i>
</div>
<form method="post" action="/order/${id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="radio" name="loan_period" value="1" checked>1 day
    <input type="radio" name="loan_period" value="21">21 days
    <button type="submit">Order the book!</button>
</form>
    <a href="/">Back to catalogue</a>
</@common.page>