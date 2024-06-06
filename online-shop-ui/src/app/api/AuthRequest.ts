
import { API_BASE_URL } from "../../env-config";

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
