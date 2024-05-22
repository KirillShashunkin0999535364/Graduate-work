
import { API_BASE_URL } from "../../env-config";
import { Category } from "../components/pages/products/AddProductPage";

/**
 * API Request to get all the products.
 * @param token The JWT Token.
 * @returns Response.
 */
export const getProducts = async (token: string) => {
    const response = await fetch(`${API_BASE_URL}/api/product`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to get a certain product.
 * @param token The JWT Token.
 * @param id The product's ID.
 * @returns Response.
 */
export const getProduct = async (token: string, id: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to add product to the cart.
 * @param token The JWT Token.
 * @param id The product's ID.
 * @param quantity The product's quantity.
 * @returns Response.
 */
export const addProductToCart = async (token: string, id: number, quantity: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}/add?quantity=${quantity}`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to add a new product.
 * @param token The JWT Token.
 * @param name The product's name.
 * @param categories The product's categories.
 * @param price The product's price.
 * @param description The product's description.
 * @returns Response.
 */
export const addProduct = async (token: string, name: string, categories: Category[], price: number, description: string) => {
    const productDTO = {
        name,
        price,
        categories,
        description
    }

    const response = await fetch(`${API_BASE_URL}/api/product`, {
        method: 'POST',
        body: JSON.stringify(productDTO),
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': "application/json"
        },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to get seller's products.
 * @param token The JWT Token.
 * @param username The seller's username.
 * @returns Response.
 */
export const getSellerProducts = async (token: string, username: string) => {
    const response = await fetch(`${API_BASE_URL}/api/product/seller/${username}`, {
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': "application/json"
        },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to delete a product.
 * @param token The JWT Token.
 * @param id The product's id.
 * @returns Response.
 */
export const deleteProduct = async (token: string, id: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}`, {
        method: 'DELETE',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to get product's photo.
 * @param id Product's id.
 * @returns Response Blob.
 */
export const getProductPhoto = async (id: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}/photo`, {})

    return response.blob();
}

/**
 * API Request to set product's photo.
 * @param token The JWT Token.
 * @param id The product's ID.
 * @param photo The product's new photo.
 * @returns Response.
 */
export const setProductPhoto = async (token: string, id: number, photo: File | null) => {
    const formData = new FormData();
    formData.append("photo", photo ?? "");

    const response = await fetch(`${API_BASE_URL}/api/product/${id}/photo`, {
        method: "POST",
        headers: { Authorization: `Bearer ${token}` },
        body: formData
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to update product.
 * @param token The JWT Token.
 * @param id The product's ID.
 * @param name The product's name.
 * @param categories The product's categories.
 * @param price The product's price.
 * @param description The product's description.
 * @returns Response.
 */
export const updateProduct = async (token: string, id: number, name: string, categories: Category[], price: number, description: string) => {
    const productDTO = {
        name,
        price,
        categories,
        description
    }

    const response = await fetch(`${API_BASE_URL}/api/product/${id}`, {
        method: 'PUT',
        body: JSON.stringify(productDTO),
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': "application/json"
        },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}
