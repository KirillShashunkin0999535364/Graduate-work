import { API_BASE_URL } from "../../env-config";

/**
 * API Request to get all the categories.
 * @param token The JWT Token.
 * @returns Response.
 */
export const getCategories = async (token: string) => {
    const response = await fetch(`${API_BASE_URL}/api/categories`, {
        headers: { Authorization: `Bearer ${token}` },
    })

    if(response.status == 401) {
        return response;
    }

    return response.json();
}
