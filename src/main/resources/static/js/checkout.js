// =======================================================
// Self Checkout System
// checkout.js
// Enhanced Kiosk Version
// Part 1/3
// =======================================================

let html5QrCode = null;

let cameraRunning = false;

let cameraModal = null;
let scanInProgress = false;
// ---------------------------
// Page Load
// ---------------------------

window.addEventListener("load", () => {

    refreshCart();

    const barcodeInput = document.getElementById("barcodeInput");

    barcodeInput.focus();

    barcodeInput.addEventListener("keypress", function (event) {

        if (event.key === "Enter") {

            scanBarcode();

        }

    });

});

// ---------------------------
// Sound
// ---------------------------

function playBeep() {

    const beep = document.getElementById("beepSound");

    if (beep) {

        beep.currentTime = 0;

        beep.play().catch(() => {});

    }

}
function playSuccessSound() {

    const successSound = document.getElementById("successSound");

    if (successSound) {

        successSound.currentTime = 0;

        successSound.play().catch(error => {

            console.log("Unable to play success sound.", error);

        });

    }

}

function updateSuccessPopup() {

    const amount =
        document.getElementById("total").innerText;

    const paymentMethod =
        document.getElementById("paymentMethod").value;

    document.getElementById("popupAmount").innerText =
        "₹" + amount;

    document.getElementById("popupMethod").innerText =
        paymentMethod;

}
// ---------------------------
// Loading Spinner
// ---------------------------

function showSpinner() {

    document
        .getElementById("loadingSpinner")
        .classList
        .add("show");

}

function hideSpinner() {

    document
        .getElementById("loadingSpinner")
        .classList
        .remove("show");

}

// ---------------------------
// Success Popup
// ---------------------------

function showSuccessPopup() {

    document
        .getElementById("successPopup")
        .classList
        .add("show");

}

function hideSuccessPopup() {

    document
        .getElementById("successPopup")
        .classList
        .remove("show");

}

// ---------------------------
// Barcode Scan
// ---------------------------

async function scanBarcode() {

    const barcodeInput = document.getElementById("barcodeInput");

    const barcode = barcodeInput.value.trim();

    if (barcode === "") {

        alert("Please enter a barcode.");

        barcodeInput.focus();

        return;

    }

    try {

        const response = await fetch(`/api/scan/${barcode}`);

        if (!response.ok) {

            alert("Product not found.");

            barcodeInput.value = "";

            barcodeInput.focus();

            return;

        }

        const product = await response.json();

        await fetch(`/api/cart/add/${product.id}`, {

            method: "POST"

        });

        playBeep();

        barcodeInput.value = "";

        barcodeInput.focus();

        await refreshCart(product.id);

    }

    catch (error) {

        console.error(error);

        alert("Unable to scan product.");

    }

}
// ---------------------------
// Refresh Cart
// ---------------------------

async function refreshCart(highlightProductId = null) {

    try {

        const response = await fetch("/api/cart");

        const cart = await response.json();

        const cartBody = document.getElementById("cartBody");

        cartBody.innerHTML = "";

        cart.items.forEach(item => {

            const rowClass =
                highlightProductId === item.product.id
                    ? "scan-glow quantity-updated"
                    : "";

            cartBody.innerHTML += `

                <tr class="${rowClass}">

                    <td>

                        ${item.product.name}

                    </td>

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

                    <td>

                        ₹${item.product.price}

                    </td>

                    <td>

                        ₹${item.lineTotal}

                    </td>

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

        document.getElementById("subtotal").innerText =
            cart.subtotal;

        document.getElementById("tax").innerText =
            cart.tax;

        document.getElementById("total").innerText =
            cart.total;

    }

    catch (error) {

        console.error(error);

        alert("Unable to refresh cart.");

    }

}

// ---------------------------
// Open Camera
// ---------------------------

async function openCamera() {

    if (!cameraModal) {

        cameraModal = new bootstrap.Modal(
            document.getElementById("cameraModal")
        );

    }

    cameraModal.show();

    if (cameraRunning) {
        return;
    }

    scanInProgress = false;

    html5QrCode = new Html5Qrcode("reader");

    try {

        await html5QrCode.start(

            {
                facingMode: "environment"
            },

            {
                fps: 10,
                qrbox: {
                    width: 250,
                    height: 120
                }
            },

            async (decodedText) => {

                if (scanInProgress) {
                    return;
                }

                scanInProgress = true;

                console.log("Barcode Detected : " + decodedText);

                await addScannedProduct(decodedText);

            },

            () => {
                // Ignore scan errors
            }

        );

        cameraRunning = true;

    }

    catch (error) {

        console.error(error);

        alert("Unable to access camera.");

    }

}

// ---------------------------
// Close Camera
// ---------------------------

async function closeCamera() {

    if (!cameraRunning) {

        return;

    }

    await html5QrCode.stop();

    await html5QrCode.clear();

    cameraRunning = false;

}

// ---------------------------
// Add Scanned Product
// ---------------------------
async function addScannedProduct(barcode) {

    try {

        const response = await fetch(`/api/scan/${barcode}`);

        if (!response.ok) {

            alert("Product not found.");

            scanInProgress = false;

            return;

        }

        const product = await response.json();

        await fetch(`/api/cart/add/${product.id}`, {

            method: "POST"

        });

        playBeep();

        refreshCart();

        await closeCamera();

        cameraModal.hide();

    }

    catch (error) {

        console.error(error);

        alert("Scanning failed.");

    }

    finally {

        scanInProgress = false;

    }

}

// ---------------------------
// Increase Quantity
// ---------------------------

async function increaseQuantity(productId) {

    await fetch(`/api/cart/increase/${productId}`, {

        method: "PUT"

    });

    refreshCart(productId);

}

// ---------------------------
// Decrease Quantity
// ---------------------------

async function decreaseQuantity(productId) {

    await fetch(`/api/cart/decrease/${productId}`, {

        method: "PUT"

    });

    refreshCart(productId);

}

// ---------------------------
// Remove Product
// ---------------------------

async function removeItem(productId) {

    await fetch(`/api/cart/remove/${productId}`, {

        method: "DELETE"

    });

    refreshCart();

}
document
    .getElementById("cameraModal")
    .addEventListener("hidden.bs.modal", function () {

        closeCamera();

    });
// ---------------------------
// Checkout
// ---------------------------

async function checkout() {

    const paymentMethod =
        document.getElementById("paymentMethod").value;

    try {

        showSpinner();

        const response = await fetch(

            `/api/payment/checkout?paymentMethod=${encodeURIComponent(paymentMethod)}`,

            {
                method: "POST"
            }

        );

        if (!response.ok) {

            hideSpinner();

            const message = await response.text();

            alert(message);

            return;

        }

        const transactionId = await response.text();

        hideSpinner();

        updateSuccessPopup();

        playSuccessSound();

        showSuccessPopup();

        // Redirect after animation finishes
        setTimeout(() => {

            hideSuccessPopup();

            window.location.href = `/receipt/${transactionId}`;

        }, 2000);

    }

    catch (error) {

        hideSpinner();

        console.error(error);

        alert("Checkout failed.");

    }

}

// ---------------------------
// Cancel Transaction
// (Future Enhancement)
// ---------------------------

function cancelTransaction() {

    if (confirm("Cancel current transaction?")) {

        location.reload();

    }

}

// ---------------------------
// Utility
// ---------------------------

function formatCurrency(value) {

    return "₹" + Number(value).toFixed(2);

}

// ---------------------------
// End of File
// ---------------------------