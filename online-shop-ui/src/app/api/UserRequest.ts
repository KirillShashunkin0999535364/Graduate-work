import { API_BASE_URL } from "../../env-config";

/**
 * API Request to get user's information.
 * @param username The user's username.
 * @returns Response.
 */
export const getUserInfo = async (username: string) => {
    const response = await fetch(`${API_BASE_URL}/api/user/${username}`, {
        headers: { 'Content-Type': 'application/json' }
    })

    return response.json();
}

/**
 * API Request to get user's photo.
 * @param username User's username.
 * @returns Response Blob.
 */
export const getUserPhoto = async (username: string) => {
    const response = await fetch(`${API_BASE_URL}/api/user/${username}/photo`, {})

    if (response.status == 401) {
        return response;
    }

    return response.blob();
}

/**
 * API Request to set user's photo.
 * @param token The JWT Token.
 * @param username The user's username.
 * @param photo The user's new photo.
 * @returns Response.
 */
export const setUserPhoto = async (token: string, username: string, photo: File | null) => {
    const formData = new FormData();
    formData.append("photo", photo ?? "");

    const response = await fetch(`${API_BASE_URL}/api/user/${username}/photo`, {
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
 * API Request to update user.
 * @param token The JWT Token.
 * @param username The user's username.
 * @param email The user's email.
 * @returns Response.
 */
export const updateUser = async (token: string, username: string, email: string) => {
    const userDTO = {
        email
    };

    const response = await fetch(`${API_BASE_URL}/api/user/${username}`, {
        method: "PUT",
        headers: { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' },
        body: JSON.stringify(userDTO),
    });

    if (response.status == 401) {
        return response;
    }

    return response.json();
}
