
const API = {
    LOGIN_URL: "/api/v1/account/login",
    REGISTER_URL: "/api/v1/account/register"
}

const sendRequest = async (apiUrl, data) => {
    const response = await fetch(apiUrl, {
        method: "POST",

        headers: {
            'Content-Type': 'application/json'
        },

        body: JSON.stringify(data)
    });

    return response;
}






export {API, sendRequest};