async function scanBarcode() {

    const barcodeInput = document.getElementById("barcodeInput");

    const barcode = barcodeInput.value.trim();

    if (barcode === "") {
        alert("Please enter barcode");
        return;
    }

    const response = await fetch(`/api/scan/${barcode}`);

    if (!response.ok) {
        alert("Product not found");
        barcodeInput.value = "";
        return;
    }

    const product = await response.json();

    await fetch(`/api/cart/add/${product.id}`, {
        method: "POST"
    });

    barcodeInput.value = "";

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

            <td>${item.quantity}</td>

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

async function removeItem(productId) {

    await fetch(`/api/cart/remove/${productId}`, {
        method: "DELETE"
    });

    refreshCart();
}

refreshCart();