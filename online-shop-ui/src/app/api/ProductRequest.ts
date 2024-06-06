
import { API_BASE_URL } from "../../env-config";
import { Category } from "../components/pages/products/AddProductPage";


export const getProducts = async (token: string) => {
    const response = await fetch(`${API_BASE_URL}/api/product`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

export const getProduct = async (token: string, id: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

export const addProductToCart = async (token: string, id: number, quantity: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}/add?quantity=${quantity}`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

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

export const getProductPhoto = async (id: number) => {
    const response = await fetch(`${API_BASE_URL}/api/product/${id}/photo`, {})

    return response.blob();
}

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
