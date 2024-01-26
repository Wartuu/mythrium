
const API = {
    LOGIN_URL: "/api/v1/account/login",
    REGISTER_URL: "/api/v1/account/register",

    NOTE_URL: "/api/v1/note"
}

const sendRequest = async (apiUrl, data, options = {}) => {
    const response = await fetch(apiUrl, {
        method: "POST",

        headers: {
            'Content-Type': 'application/json'
        },

        body: JSON.stringify(data),
        ...options
    });

    return response;
}








export {API, sendRequest};