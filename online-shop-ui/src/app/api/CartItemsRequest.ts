import { API_BASE_URL } from "../../env-config";

/**
 * API Request to get user's cart items.
 * @param token The JWT Token
 * @param username User's username.
 * @returns Response.
 */
export const getCartItems = async (token: string, username: string) => {
    const response = await fetch(`${API_BASE_URL}/api/cartItem?&username=${username}`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}

/**
 * API Request to delete a cart item.
 * @param token The JWT Token.
 * @param id Cart item's ID.
 * @returns Response.
 */
export const deleteCartItem = async (token: string, id: number) => {
    const response = await fetch(`${API_BASE_URL}/api/cartItem/${id}`, {
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
 * API Request to change quantity of a cart item.
 * @param token The JWT Token.
 * @param id Cart item's ID.
 * @param quantity Cart item's new quantity.
 * @returns Response.
 */
export const changeQuantity = async (token: string, id: number, quantity: number) => {
    const response = await fetch(`${API_BASE_URL}/api/cartItem/${id}/quantity?quantity=${quantity}`, {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })

    if (response.status == 401) {
        return response;
    }

    return response.json();
}
