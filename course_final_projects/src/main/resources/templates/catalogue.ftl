<#import "parts/common.ftl" as common>
<#import "parts/authorization.ftl" as auth>

<@common.page title = "Catalogue">
    <div class="container">
    <form method="get" action="search">
        <input type="text" name="parameter" placeholder="Title, author or ISBN">
        <button type="submit">Search for a book</button>
    </form>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><a href="/sort?sortProperty=title">Title</a></th>
            <th scope="col"><a href="/sort?sortProperty=author">Author</a></th>
            <th scope="col"><a href="/sort?sortProperty=publisher">Publisher</a></th>
            <th scope="col"><a href="/sort?sortProperty=publishingDate">Publishing Date</a></th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
    <#list books as book>
        <tr>
            <th scope="row">${book.id}</th>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.publisher}</td>
            <td>${book.publishingDate}</td>
            <td><a class="btn btn-primary" href="/book/${book.id}" role="button">Order</a></td>
        </tr>
    </#list>
        </tbody>
    </table>
    </div>
</@common.page>