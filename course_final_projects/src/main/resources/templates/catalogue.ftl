<#import "parts/common.ftl" as common>
<#import "parts/authorization.ftl" as auth>

<@common.page title = "Catalogue">
    <div>
        <@auth.logout />
    </div>
    <div>
        Add supervisor
        <form method="post" action="/supervisor/add">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="text" name="title" placeholder="Title"/>
            <input type="text" name="author" placeholder="Author"/>
            <input type="text" name="publisher" placeholder="Publisher"/>
            <input type="date" name="publishingDate" placeholder="Date of publishing" />
            <input type="text" name="ISBN" placeholder="ISBN" />
            <input type="number" name="quantity" placeholder="Number of books" />
            <button type="submit">Add the book</button>
        </form>
    </div>
    <div>
        Add book
        <form method="post" action="/book/add">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="text" name="title" placeholder="Title"/>
            <input type="text" name="author" placeholder="Author"/>
            <input type="text" name="publisher" placeholder="Publisher"/>
            <input type="date" name="publishingDate" placeholder="Date of publishing" />
            <input type="text" name="ISBN" placeholder="ISBN" />
            <input type="number" name="quantity" placeholder="Number of books" />
            <button type="submit">Add the book</button>
        </form>
    </div>
    <div>
        Delete book
        <form method="post" action="/book/delete">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="text" name="ISBN" placeholder="ISBN" />
            <button type="submit">Delete the book</button>
        </form>
    </div>
    <div>
        Update book
        <form method="post" action="/book/update">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="text" name="title" placeholder="Title"/>
            <input type="text" name="author" placeholder="Author"/>
            <input type="text" name="publisher" placeholder="Publisher"/>
            <input type="date" name="publishingDate" placeholder="Date of publishing" />
            <input type="text" name="ISBN" placeholder="ISBN" required/>
            <input type="number" name="quantity" placeholder="Number of books" />
            <button type="submit">Update the book</button>
        </form>
    </div>
    <div>Books' catalogue</div>
    <form method="post" action="search/byAuthor">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" name="parameter" placeholder="Author's name">
        <button type="submit">Enter</button>
    </form>
    <form method="post" action="search/byTitle">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" name="parameter" placeholder="Book's title">
        <button type="submit">Enter</button>
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