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
<form method="post" action="/order/${id}">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="radio" name="loan_period" value="1">1 day
    <input type="radio" name="loan_period" value="21">21 days
    <button type="submit">Order the book!</button>
</form>
</@common.page>