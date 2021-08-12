<#import "parts/common.ftl" as common>
<#import "parts/profile.ftl" as profile>
<@common.page title="Profile | Info">
    <@profile.user_info>
        <div>
            Add a librarian
            <form method="post" action="/admin/add/librarian">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="username" placeholder="username" required/>
                <input type="password" name="password" placeholder="password" required/>
                <input type="text" name="firstName" placeholder="first name" required/>
                <input type="text" name="lastName" placeholder="last name" required/>
                <input type="email" name="email" placeholder="E-Mail" required/>
                <button type="submit">Add the librarian</button>
            </form>
        </div>
        <div>
            Delete a librarian
            <form method="post" action="/admin/delete/librarian">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="username" placeholder="username" required/>
                <button type="submit">Delete the librarian</button>
            </form>
        </div>
        <div>
            Change status of user account
            <form method="post" action="/admin/de_activate/user">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="username" placeholder="username" required/>
                <input type="radio" name="active" value="True" checked>Active
                <input type="radio" name="active" value="False">Blocked
                <button type="submit">Change user's status</button>
            </form>
        </div>
        <div>
            Add book
            <form method="post" action="/admin/add/book">
                <input type="hidden" name="_csrf" value="${_csrf.token}" required/>
                <input type="text" name="title" placeholder="Title" required/>
                <input type="text" name="author" placeholder="Author" required/>
                <input type="text" name="publisher" placeholder="Publisher" required/>
                <input type="date" name="publishingDate" placeholder="Date of publishing" lang="en-us" required/>
                <input type="text" name="ISBN" placeholder="ISBN" required/>
                <input type="number" name="quantity" placeholder="Number of books" required/>
                <button type="submit">Add the book</button>
            </form>
        </div>
        <div>
            Delete book
            <form method="post" action="/admin/delete/book">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="ISBN" placeholder="ISBN" required/>
                <button type="submit">Delete the book</button>
            </form>
        </div>
        <div>
            Update book
            <form method="post" action="/admin/update/book">
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
    </@profile.user_info>
</@common.page>