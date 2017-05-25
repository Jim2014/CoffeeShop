
<!-- Static navbar -->
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Coffee Shop</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li id="menu-home")><a href="/home">Home</a></li>
            <li id="menu-product-list"><a href="/product/list">Product</a></li>
            <sec:authorize access="hasRole('USER')">
            <li id="menu-order-userList"><a href="/order/userList">My Orders</a></li>
            </sec:authorize>
                
            
            <sec:authorize access="hasRole('ADMIN')">
            <li id="menu-order"><a href="/order/list">All Orders</a></li>
            <li id="menu-person"><a href="/person/list">Person</a></li>
            </sec:authorize>

            
          </ul>
          <ul class="nav navbar-nav navbar-right">
            
            <sec:authorize access="!hasRole('USER')">
            <li><a href="/login">Login</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('USER')">
	            <li><a href="/profile">Profile</a></li>
	            <li><a href="/logout">Logout</a></li>
            </sec:authorize>
          </ul>
        </div>
      </div>
    </nav>
