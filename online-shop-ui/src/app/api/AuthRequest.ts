
import { API_BASE_URL } from "../../env-config";

/**
 * API Request to register a new user.
 * @param role User's role.
 * @param email User's email.
 * @param username User's username.
 * @param password User's password.
 * @returns Response.
 */
export const register = async (role: string, email: string, username: string, password: string) => {
    const registerDTO = {
        role,
        email,
        username,
        password
    }

    const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
        method: 'POST',
        body: JSON.stringify(registerDTO),
        headers: { 'Content-Type': 'application/json' }
    })

    return response.json();
}

/**
 * API Request to login a user.
 * @param username User's username.
 * @param password User's password.
 * @returns Response.
 */
export const login = async (username: string, password: string) => {
    const loginDTO = {
        username,
        password
    }

    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        body: JSON.stringify(loginDTO),
        headers: { 'Content-Type': 'application/json' }
    })

    return response.json();
}
