<#import "parts/common.ftl" as common>
<#import "parts/authorization.ftl" as auth>

<@common.page title = "Catalogue">
    <a href="/user/profile/info">To user profile info</a>
    <div>
        <@auth.signout />
    </div>
    <form method="post" action="search">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" name="parameter" placeholder="Title, author or ISBN">
        <button type="submit">Search for a book</button>
    </form>
    <form method="post" action="sort">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <p><b>Sorting by</b></p>
        <input type="radio" name="sortProperty" value="title">Title
        <input type="radio" name="sortProperty" value="author">Author
        <input type="radio" name="sortProperty" value="publisher">Publisher
        <input type="radio" name="sortProperty" value="publishingDate">Publishing Date
        <button type="submit">Enter</button>
    </form>
    <#list books as book>
        <div>
            <span><a href="/book/${book.id}"><b>${book.title}</b>, <i>${book.author}, ${book.publisher}, ${book.publishingDate}</i></a></span>
        </div>
    <#else>
        <p>No books available</p>
    </#list>
</@common.page>