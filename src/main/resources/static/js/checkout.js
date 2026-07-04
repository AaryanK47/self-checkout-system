async function scanBarcode() {

    const barcodeInput = document.getElementById("barcodeInput");
    const barcode = barcodeInput.value.trim();

    if (barcode === "") {
        alert("Please enter a barcode.");
        return;
    }

    // Find product by barcode
    const response = await fetch(`/api/scan/${barcode}`);

    if (!response.ok) {
        alert("Product not found.");
        barcodeInput.value = "";
        barcodeInput.focus();
        return;
    }

    const product = await response.json();

    // Add product to cart
    await fetch(`/api/cart/add/${product.id}`, {
        method: "POST"
    });

    barcodeInput.value = "";
    barcodeInput.focus();

    refreshCart();
}

async function refreshCart() {

    const response = await fetch("/api/cart");
    const cart = await response.json();

    const cartBody = document.getElementById("cartBody");
    cartBody.innerHTML = "";

    cart.items.forEach(item => {

        cartBody.innerHTML += `
            <tr>

                <td>${item.product.name}</td>

                <td>

                    <button
                        class="btn btn-secondary btn-sm"
                        onclick="decreaseQuantity(${item.product.id})">

                        -

                    </button>

                    <span class="mx-2 fw-bold">

                        ${item.quantity}

                    </span>

                    <button
                        class="btn btn-success btn-sm"
                        onclick="increaseQuantity(${item.product.id})">

                        +

                    </button>

                </td>

                <td>₹${item.product.price}</td>

                <td>₹${item.lineTotal}</td>

                <td>

                    <button
                        class="btn btn-danger btn-sm"
                        onclick="removeItem(${item.product.id})">

                        Remove

                    </button>

                </td>

            </tr>
        `;
    });

    document.getElementById("subtotal").innerText = cart.subtotal;
    document.getElementById("tax").innerText = cart.tax;
    document.getElementById("total").innerText = cart.total;
}

async function increaseQuantity(productId) {

    await fetch(`/api/cart/increase/${productId}`, {
        method: "PUT"
    });

    refreshCart();
}

async function decreaseQuantity(productId) {

    await fetch(`/api/cart/decrease/${productId}`, {
        method: "PUT"
    });

    refreshCart();
}

async function removeItem(productId) {

    await fetch(`/api/cart/remove/${productId}`, {
        method: "DELETE"
    });

    refreshCart();
}

// Load cart when page opens
refreshCart();