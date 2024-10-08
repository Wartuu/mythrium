import storageManager from './configuration';

const API = {
    LOGIN_URL: 'http://localhost:8080/api/v1/account/login',
    REGISTER_URL: '/api/v1/account/register',

    NOTIFICATION_URL: '/ws/v1/notification',

    NOTE_URL: '/api/v1/note'
}

const sendRequest = async (apiUrl, data, method='POST', options = {}) => {

    let jwt = '';

    let sessionJwt = storageManager.getValue('token', true);
    let permamentJwt = storageManager.getValue('token', false);

    if(sessionJwt.value !== undefined) jwt = 'Bearer ' + sessionJwt.value;
    else if(permamentJwt.value !== undefined) jwt = 'Bearer ' + permamentJwt.value;

    if(data != null) {
        const response = await fetch(apiUrl, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': jwt
            },
    
            body: JSON.stringify(data),
            ...options
        });
    
        return response;
    } else {
        const response = await fetch(apiUrl, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': jwt
            },
    
            ...options
        });
        
    
        return response;
    }
}








export {API, sendRequest};