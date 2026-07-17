const API_BASE_URL = '/api';


async function apiRequest(path, options = {}) {
    const url = `${API_BASE_URL}${path}`;

    const config = {
        headers: {
            'Content-Type': 'application/json',
            ...(options.headers || {})
        },
        ...options
    };


    if (config.body && typeof config.body === 'object') {
        config.body = JSON.stringify(config.body);
    }

    let response;
    try {
        response = await fetch(url, config);
    } catch (networkError) {

        throw new ApiError(
            0,
            'Cannot reach the server. Make sure the backend is running on localhost:8080.',
            null
        );
    }


    if (response.status === 204) {
        return null;
    }


    let body = null;
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
        body = await response.json();
    }

    if (!response.ok) {
        //  ErrorResponse: { status, error, message, errors }
        const message = body?.message || `HTTP ${response.status}`;
        throw new ApiError(response.status, message, body?.errors || null);
    }

    return body;
}


class ApiError extends Error {
    constructor(status, message, fieldErrors) {
        super(message);
        this.name = 'ApiError';
        this.status = status;
        this.fieldErrors = fieldErrors;
    }
}


const api = {
    get: (path) => apiRequest(path, { method: 'GET' }),
    post: (path, body) => apiRequest(path, { method: 'POST', body }),
    put: (path, body) => apiRequest(path, { method: 'PUT', body }),
    delete: (path) => apiRequest(path, { method: 'DELETE' })
};