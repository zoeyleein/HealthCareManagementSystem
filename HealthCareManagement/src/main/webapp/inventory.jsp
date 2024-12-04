<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventory Management</title>
</head>
<body>
    <h1>Inventory</h1>
    
    <table border="1">
        <thead>
            <tr>
                <th>Item ID</th>
                <th>Item Name</th>
                <th>Quantity</th>
                <th>Expiration Date</th>
                <th>Unit Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${inventoryList}">
                <tr>
                    <td>${item.itemID}</td>
                    <td>${item.itemName}</td>
                    <td>${item.quantity}</td>
                    <td>${item.expirationDate}</td>
                    <td>${item.unitPrice}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h2>Add New Item</h2>
    <form action="InventoryServlet" method="post">
        <input type="hidden" name="action" value="add" />
        <label>Item Name: </label><input type="text" name="itemName" required /><br/>
        <label>Quantity: </label><input type="number" name="quantity" required /><br/>
        <label>Expiration Date (yyyy-mm-dd): </label><input type="date" name="expirationDate" required /><br/>
        <label>Unit Price: </label><input type="number" step="0.01" name="unitPrice" required /><br/>
        <button type="submit">Add Item</button>
    </form>

    <h2>Remove Item</h2>
    <form action="InventoryServlet" method="post">
        <input type="hidden" name="action" value="remove" />
        <label>Item Name to Remove: </label><input type="text" name="removeItem" required /><br/>
        <button type="submit">Remove Item</button>
    </form>
</body>
</html>

