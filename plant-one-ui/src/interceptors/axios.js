import Vue from 'vue'
import axios from 'axios'

/**
 * Config global for axios
 */
axios.defaults.trailingSlash = true;

// ---- axios interceptors ----- //

// API endpoint should ends with forward slash
axios.interceptors.request.use((config) => {
    const authInfo = Vue.$cookies.get("authInfo")

    if (isNotAuthEndpoint(config.url, config.method)) {
        config.headers.Authorization =  'token' in authInfo ? `Bearer ${authInfo.token}` : '';
    }

    // if (config.url[config.url.length-1] !== '/') {
    //     config.url += '/';
    // }
    return config;
});

const isNotAuthEndpoint = (url, method) => {
    return (
        !isLoginRequest(url, method) && 
        !isSignUpRequest(url, method) &&
        !isResetPasswordRequest(url, method) &&
        !isVerifyTokenRequest(url, method) &&
        !isChangePasswordRequest(url, method)
    )
}

let API_ENDPOINT = "http://localhost:8081/plantone/api/v1";
if (process.env.VUE_APP_API_ENDPOINT) {
    API_ENDPOINT = process.env.VUE_APP_API_ENDPOINT
}

const isLoginRequest = (url, method) => {
    const URL = API_ENDPOINT + "/users/authenticate"
    const METHOD = "post"

    return url == URL && method == METHOD
}

const isSignUpRequest = (url, method) => {
    const URL = API_ENDPOINT + "/users/"
    const METHOD = "post"

    return url == URL && method == METHOD
}

const isResetPasswordRequest = (url, method) => {
    const URL = API_ENDPOINT + "/passwordReset/resetPassword"
    const METHOD = "post"

    return url == URL && method == METHOD
}

const isVerifyTokenRequest = (url, method) => {
    const URL = API_ENDPOINT + "/passwordReset/verifyToken"
    const METHOD = "get"

    return url == URL && method == METHOD
}

const isChangePasswordRequest = (url, method) => {
    const URL = API_ENDPOINT + "/passwordReset/changePassword"
    const METHOD = "post"

    return url == URL && method == METHOD
}