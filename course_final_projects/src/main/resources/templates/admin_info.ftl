<#import "parts/common.ftl" as common>
<#import "parts/profile.ftl" as profile>
<@common.page title="Profile | Info">
    <@profile.user_info>
        <div>
            Add supervisor
            <form method="post" action="add/supervisor">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="username" placeholder="username" required/>
                <input type="text" name="password" placeholder="password" required/>
                <button type="submit">Add the supervisor</button>
            </form>
        </div>
        <div>
            Delete supervisor
            <form method="post" action="delete/librarian">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="username" placeholder="username" required/>
                <button type="submit">Delete the librarian</button>
            </form>
        </div>
        <div>
            Change status of user account
            <form method="post" action="de_activate/user">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="username" placeholder="username" required/>
                <input type="radio" name="active" value="True" checked>Active
                <input type="radio" name="active" value="False">Blocked
                <button type="submit">Change user's status</button>
            </form>
        </div>
        <div>
            Add book
            <form method="post" action="add/book">
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
            <form method="post" action="delete/book">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="text" name="ISBN" placeholder="ISBN" />
                <button type="submit">Delete the book</button>
            </form>
        </div>
        <div>
            Update book
            <form method="post" action="update/book">
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